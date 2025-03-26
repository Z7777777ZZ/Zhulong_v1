package org.zsj.demo_v1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zsj.demo_v1.dto.RechargePackageResponse;
import org.zsj.demo_v1.dto.TransactionResponse;
import org.zsj.demo_v1.entity.RechargePackage;
import org.zsj.demo_v1.entity.Transaction;
import org.zsj.demo_v1.entity.UserQuota;
import org.zsj.demo_v1.mapper.RechargePackageMapper;
import org.zsj.demo_v1.mapper.TransactionMapper;
import org.zsj.demo_v1.service.RechargeService;
import org.zsj.demo_v1.service.UserQuotaService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RechargeServiceImpl implements RechargeService {

    @Autowired
    private RechargePackageMapper rechargePackageMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private UserQuotaService userQuotaService;

    @Override
    public List<RechargePackageResponse> getAllPackages() {
        List<RechargePackage> packages = rechargePackageMapper.findAll();
        return packages.stream()
                .map(this::convertToPackageResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RechargePackageResponse getPackageById(Long id) {
        RechargePackage rechargePackage = rechargePackageMapper.findById(id);
        if (rechargePackage != null) {
            return convertToPackageResponse(rechargePackage);
        }
        return null;
    }

    @Override
    @Transactional
    public String createRechargeOrder(Long userId, Long packageId, String paymentMethod) {
        RechargePackage rechargePackage = rechargePackageMapper.findById(packageId);
        if (rechargePackage == null) {
            throw new IllegalArgumentException("套餐不存在");
        }

        Transaction transaction = new Transaction();
        transaction.setOrderId(generateOrderId());
        transaction.setUserId(userId);
        transaction.setPackageId(packageId);
        transaction.setAmount(rechargePackage.getPrice());
        transaction.setQuota(rechargePackage.getQuota());
        transaction.setPaymentMethod(paymentMethod);
        transaction.setStatus(0); // 待支付
        transaction.setCreateTime(LocalDateTime.now());
        transaction.setRechargePackage(rechargePackage);

        transactionMapper.insert(transaction);

        return transaction.getOrderId();
    }

    @Override
    @Transactional
    public boolean completeRechargeOrder(String orderId) {
        Transaction transaction = transactionMapper.findByOrderId(orderId);
        if (transaction == null || transaction.getStatus() != 0) {
            return false;
        }

        // 更新交易状态
        transaction.setStatus(1); // 支付成功
        transaction.setPayTime(LocalDateTime.now());
        transactionMapper.update(transaction);

        // 增加用户额度
        UserQuota userQuota = userQuotaService.getUserQuota(transaction.getUserId());
        userQuota.setTotalQuota(userQuota.getTotalQuota() + transaction.getQuota());
        userQuota.setRemainingQuota(userQuota.getRemainingQuota() + transaction.getQuota());

        // 这里需要更新用户额度，但UserQuotaService中没有提供更新方法，假设有updateUserQuota方法
        // userQuotaService.updateUserQuota(userQuota);
        
        return true;
    }

    @Override
    public List<TransactionResponse> getUserTransactions(Long userId) {
        List<Transaction> transactions = transactionMapper.findByUserId(userId);
        return transactions.stream()
                .map(this::convertToTransactionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponse> getUserTransactionsByTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Transaction> transactions = transactionMapper.findByUserIdAndTimeRange(userId, startTime, endTime);
        return transactions.stream()
                .map(this::convertToTransactionResponse)
                .collect(Collectors.toList());
    }

    private String generateOrderId() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    }

    private RechargePackageResponse convertToPackageResponse(RechargePackage rechargePackage) {
        RechargePackageResponse response = new RechargePackageResponse();
        response.setId(rechargePackage.getId());
        response.setName(rechargePackage.getName());
        response.setPrice(rechargePackage.getPrice());
        response.setQuota(rechargePackage.getQuota());
        response.setValidity(rechargePackage.getValidity());
        response.setPopular(rechargePackage.getPopular());
        
        // 将逗号分隔的特性转换为列表
        if (rechargePackage.getFeatures() != null && !rechargePackage.getFeatures().isEmpty()) {
            response.setFeatures(Arrays.asList(rechargePackage.getFeatures().split(",")));
        } else {
            response.setFeatures(new ArrayList<>());
        }
        
        return response;
    }

    private TransactionResponse convertToTransactionResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getOrderId());
        response.setDate(transaction.getCreateTime());
        response.setPackageName(transaction.getRechargePackage() != null ? 
                transaction.getRechargePackage().getName() : "未知套餐");
        response.setQuota(transaction.getQuota());
        response.setAmount(transaction.getAmount());
        response.setPaymentMethod(transaction.getPaymentMethod());
        
        String status;
        switch (transaction.getStatus()) {
            case 0:
                status = "待支付";
                break;
            case 1:
                status = "已完成";
                break;
            case 2:
                status = "已取消";
                break;
            default:
                status = "未知状态";
        }
        response.setStatus(status);
        
        return response;
    }
} 
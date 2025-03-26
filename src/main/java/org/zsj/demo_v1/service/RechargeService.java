package org.zsj.demo_v1.service;

import org.zsj.demo_v1.dto.RechargePackageResponse;
import org.zsj.demo_v1.dto.TransactionResponse;
import org.zsj.demo_v1.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface RechargeService {
    List<RechargePackageResponse> getAllPackages();
    
    RechargePackageResponse getPackageById(Long id);
    
    String createRechargeOrder(Long userId, Long packageId, String paymentMethod);
    
    boolean completeRechargeOrder(String orderId);
    
    List<TransactionResponse> getUserTransactions(Long userId);
    
    List<TransactionResponse> getUserTransactionsByTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime);
} 
package org.zsj.demo_v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.zsj.demo_v1.dto.ApiResponse;
import org.zsj.demo_v1.dto.RechargePackageResponse;
import org.zsj.demo_v1.dto.TransactionResponse;
import org.zsj.demo_v1.security.services.UserDetailsImpl;
import org.zsj.demo_v1.service.RechargeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/recharge")
public class RechargeController {
    @Autowired
    private RechargeService rechargeService;

    @GetMapping("/packages")
    public ResponseEntity<?> getAllPackages() {
        List<RechargePackageResponse> packages = rechargeService.getAllPackages();
        return ResponseEntity.ok(ApiResponse.success(packages));
    }

    @GetMapping("/packages/{id}")
    public ResponseEntity<?> getPackageById(@PathVariable Long id) {
        RechargePackageResponse packageResponse = rechargeService.getPackageById(id);
        if (packageResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(packageResponse));
    }

    @PostMapping("/order")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createRechargeOrder(
            @RequestParam Long packageId,
            @RequestParam String paymentMethod) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        try {
            String orderId = rechargeService.createRechargeOrder(userDetails.getId(), packageId, paymentMethod);
            return ResponseEntity.ok(ApiResponse.success("订单创建成功", orderId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/complete/{orderId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> completeRechargeOrder(@PathVariable String orderId) {
        boolean completed = rechargeService.completeRechargeOrder(orderId);
        if (completed) {
            return ResponseEntity.ok(ApiResponse.success("充值成功"));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.error("充值失败"));
        }
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserTransactions(
            @RequestParam(required = false) String filter) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        if (filter == null || filter.equals("all")) {
            // 返回所有交易
            List<TransactionResponse> transactions = rechargeService.getUserTransactions(userDetails.getId());
            return ResponseEntity.ok(ApiResponse.success(transactions));
        }
        
        LocalDateTime startTime;
        LocalDateTime endTime = LocalDateTime.now();
        
        switch (filter) {
            case "7days":
                startTime = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIN);
                break;
            case "30days":
                startTime = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.MIN);
                break;
            case "90days":
                startTime = LocalDateTime.of(LocalDate.now().minusDays(90), LocalTime.MIN);
                break;
            default:
                startTime = LocalDateTime.of(LocalDate.now().minusYears(10), LocalTime.MIN);
        }
        
        List<TransactionResponse> transactions = rechargeService.getUserTransactionsByTimeRange(
                userDetails.getId(), startTime, endTime);
        
        return ResponseEntity.ok(ApiResponse.success(transactions));
    }
} 
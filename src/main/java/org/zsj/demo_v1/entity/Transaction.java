package org.zsj.demo_v1.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private Long id;
    private String orderId; // 订单编号
    private Long userId;
    private Long packageId;
    private BigDecimal amount;
    private Integer quota;
    private String paymentMethod; // 支付方式，如"支付宝"、"微信"
    private Integer status; // 0-待支付，1-支付成功，2-已取消
    private LocalDateTime createTime;
    private LocalDateTime payTime;
    
    // 关联属性
    private RechargePackage rechargePackage;
} 
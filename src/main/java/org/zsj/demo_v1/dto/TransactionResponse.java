package org.zsj.demo_v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private String id; // 订单号
    private LocalDateTime date; // 日期
    private String packageName; // 套餐名称
    private Integer quota; // 额度
    private BigDecimal amount; // 金额
    private String paymentMethod; // 支付方式
    private String status; // 状态
} 
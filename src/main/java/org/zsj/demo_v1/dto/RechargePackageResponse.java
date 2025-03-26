package org.zsj.demo_v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RechargePackageResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quota;
    private String validity; // 有效期
    private Boolean popular; // 是否热门
    private List<String> features; // 套餐特性
} 
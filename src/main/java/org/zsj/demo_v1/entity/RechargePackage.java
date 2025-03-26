package org.zsj.demo_v1.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RechargePackage {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quota;
    private String validity; // 有效期，如"30天"
    private Boolean popular; // 是否热门推荐
    private String features; // 套餐特性，逗号分隔
} 
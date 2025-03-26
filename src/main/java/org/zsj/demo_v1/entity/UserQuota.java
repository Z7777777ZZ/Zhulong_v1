package org.zsj.demo_v1.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserQuota {
    private Long id;
    private Long userId;
    private Integer totalQuota;
    private Integer usedQuota;
    private Integer remainingQuota;
    private Integer dailyFreeQuota;
    private LocalDate lastFreeQuotaReset;
} 
package org.zsj.demo_v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String avatar;
    private String role;
    private LocalDateTime registerTime;
    private LocalDateTime lastLoginTime;
    private Integer totalQuota;
    private Integer usedQuota;
    private Integer remainingQuota;
    private Integer dailyFreeQuota;
    private Integer detectionCount; // 检测总数
    private Integer memberDays; // 会员天数（从注册到现在的天数）
} 
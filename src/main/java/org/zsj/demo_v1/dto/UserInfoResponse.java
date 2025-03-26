package org.zsj.demo_v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String avatar;
    private String role;
    private Integer totalQuota;
    private Integer remainingQuota;
    private Integer usedCount;
    private Integer dailyLimit;
} 
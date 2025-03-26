package org.zsj.demo_v1.service;

import org.zsj.demo_v1.entity.UserQuota;

public interface UserQuotaService {
    UserQuota initUserQuota(Long userId);
    
    UserQuota getUserQuota(Long userId);
    
    boolean checkAndResetDailyQuota(Long userId);
    
    boolean consumeQuota(Long userId, Integer quotaAmount);
} 
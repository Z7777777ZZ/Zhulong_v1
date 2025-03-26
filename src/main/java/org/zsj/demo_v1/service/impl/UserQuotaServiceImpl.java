package org.zsj.demo_v1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zsj.demo_v1.entity.UserQuota;
import org.zsj.demo_v1.mapper.UserQuotaMapper;
import org.zsj.demo_v1.service.UserQuotaService;

import java.time.LocalDate;

@Service
public class UserQuotaServiceImpl implements UserQuotaService {
    @Autowired
    private UserQuotaMapper userQuotaMapper;

    @Override
    @Transactional
    public UserQuota initUserQuota(Long userId) {
        UserQuota userQuota = new UserQuota();
        userQuota.setUserId(userId);
        userQuota.setTotalQuota(0);
        userQuota.setUsedQuota(0);
        userQuota.setRemainingQuota(10); // 初始赠送10点额度
        userQuota.setDailyFreeQuota(10); // 每日免费10点额度
        userQuota.setLastFreeQuotaReset(LocalDate.now());
        
        userQuotaMapper.insert(userQuota);
        
        return userQuota;
    }

    @Override
    public UserQuota getUserQuota(Long userId) {
        return userQuotaMapper.findByUserId(userId);
    }

    @Override
    @Transactional
    public boolean checkAndResetDailyQuota(Long userId) {
        UserQuota userQuota = userQuotaMapper.findByUserId(userId);
        if (userQuota == null) {
            return false;
        }
        
        // 检查上次重置时间是否是今天之前
        if (userQuota.getLastFreeQuotaReset() == null || 
            userQuota.getLastFreeQuotaReset().isBefore(LocalDate.now())) {
            // 重置每日免费额度
            userQuotaMapper.updateFreeQuotaReset(userId);
            return true;
        }
        
        return false;
    }

    @Override
    @Transactional
    public boolean consumeQuota(Long userId, Integer quotaAmount) {
        // 先检查并重置每日免费额度
        checkAndResetDailyQuota(userId);
        
        // 获取最新额度信息
        UserQuota userQuota = userQuotaMapper.findByUserId(userId);
        
        // 检查额度是否足够
        if (userQuota.getRemainingQuota() < quotaAmount) {
            return false;
        }
        
        // 消耗额度
        int rows = userQuotaMapper.consumeQuota(userId, quotaAmount);
        
        return rows > 0;
    }
} 
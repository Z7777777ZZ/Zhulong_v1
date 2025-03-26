package org.zsj.demo_v1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zsj.demo_v1.entity.UserQuota;

@Mapper
public interface UserQuotaMapper {
    // 根据用户ID查询额度
    UserQuota findByUserId(Long userId);
    
    // 插入新用户额度
    int insert(UserQuota userQuota);
    
    // 更新用户额度
    int update(UserQuota userQuota);
    
    // 更新用户免费额度重置时间
    int updateFreeQuotaReset(@Param("userId") Long userId);
    
    // 消耗用户额度
    int consumeQuota(@Param("userId") Long userId, @Param("quotaAmount") Integer quotaAmount);
} 
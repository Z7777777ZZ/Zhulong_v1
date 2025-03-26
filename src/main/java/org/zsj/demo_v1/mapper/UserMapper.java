package org.zsj.demo_v1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zsj.demo_v1.entity.User;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    User findByUsername(String username);
    
    // 根据邮箱查询用户
    User findByEmail(String email);
    
    // 插入新用户
    int insert(User user);
    
    // 更新用户信息
    int update(User user);
    
    // 更新用户最后登录时间和IP
    int updateLoginInfo(@Param("id") Long id, @Param("lastLoginIp") String lastLoginIp);
    
    // 根据ID查询用户
    User findById(Long id);
}
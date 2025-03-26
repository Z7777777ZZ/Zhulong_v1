package org.zsj.demo_v1.service;

import org.zsj.demo_v1.dto.JwtResponse;
import org.zsj.demo_v1.dto.LoginRequest;
import org.zsj.demo_v1.dto.RegisterRequest;
import org.zsj.demo_v1.dto.UserInfoResponse;
import org.zsj.demo_v1.dto.UserProfileResponse;
import org.zsj.demo_v1.entity.User;

public interface UserService {
    JwtResponse authenticateUser(LoginRequest loginRequest, String ipAddress);
    
    User registerUser(RegisterRequest registerRequest);
    
    UserInfoResponse getUserInfo(Long userId);
    
    UserProfileResponse getUserProfile(Long userId);
    
    boolean updateUserProfile(Long userId, String username, String email, String phone, String avatar);
    
    boolean updatePassword(Long userId, String currentPassword, String newPassword);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
} 
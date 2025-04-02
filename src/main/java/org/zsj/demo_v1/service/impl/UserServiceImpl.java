package org.zsj.demo_v1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zsj.demo_v1.dto.JwtResponse;
import org.zsj.demo_v1.dto.LoginRequest;
import org.zsj.demo_v1.dto.RegisterRequest;
import org.zsj.demo_v1.dto.UserInfoResponse;
import org.zsj.demo_v1.dto.UserProfileResponse;
import org.zsj.demo_v1.entity.User;
import org.zsj.demo_v1.entity.UserQuota;
import org.zsj.demo_v1.mapper.UserMapper;
import org.zsj.demo_v1.security.JwtUtils;
import org.zsj.demo_v1.security.services.UserDetailsImpl;
import org.zsj.demo_v1.service.DetectionRecordService;
import org.zsj.demo_v1.service.UserQuotaService;
import org.zsj.demo_v1.service.UserService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserQuotaService userQuotaService;
    
    @Autowired
    private DetectionRecordService detectionRecordService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest, String ipAddress) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        
        // 更新登录信息
        User user = userMapper.findById(userDetails.getId());
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(ipAddress);
        userMapper.update(user);
        
        // 检查并重置每日额度
        userQuotaService.checkAndResetDailyQuota(userDetails.getId());
        
        return new JwtResponse(
                jwt, 
                userDetails.getId(), 
                userDetails.getUsername(), 
                userDetails.getEmail(), 
                roles.isEmpty() ? "USER" : roles.get(0));
    }
    
    @Override
    @Transactional
    public User registerUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setAvatar("/avatar/default.png"); // 默认头像
        user.setStatus(1); // 正常状态
        user.setRole("USER"); // 普通用户角色
        user.setRegisterTime(LocalDateTime.now());
        
        userMapper.insert(user);
        
        // 初始化用户额度
        userQuotaService.initUserQuota(user.getId());
        
        return user;
    }
    
    @Override
    public UserInfoResponse getUserInfo(Long userId) {
        User user = userMapper.findById(userId);
        UserQuota userQuota = userQuotaService.getUserQuota(userId);
        
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setId(user.getId());
        userInfoResponse.setUsername(user.getUsername());
        userInfoResponse.setEmail(user.getEmail());
        userInfoResponse.setAvatar(user.getAvatar());
        userInfoResponse.setRole(user.getRole());
        userInfoResponse.setTotalQuota(userQuota.getTotalQuota());
        userInfoResponse.setUsedQuota(userQuota.getUsedQuota());
        userInfoResponse.setRemainingQuota(userQuota.getRemainingQuota());
        userInfoResponse.setUsedCount(userQuota.getUsedQuota());
        userInfoResponse.setDailyLimit(userQuota.getDailyFreeQuota());
        
        return userInfoResponse;
    }
    
    @Override
    public UserProfileResponse getUserProfile(Long userId) {
        User user = userMapper.findById(userId);
        UserQuota userQuota = userQuotaService.getUserQuota(userId);
        int detectionCount = detectionRecordService.getDetectionCountByUserId(userId);
        
        // 计算会员天数（从注册到现在的天数）
        long memberDays = ChronoUnit.DAYS.between(user.getRegisterTime().toLocalDate(), LocalDateTime.now().toLocalDate()) + 1;
        
        UserProfileResponse profileResponse = new UserProfileResponse();
        profileResponse.setId(user.getId());
        profileResponse.setUsername(user.getUsername());
        profileResponse.setEmail(user.getEmail());
        profileResponse.setPhone(user.getPhone());
        profileResponse.setAvatar(user.getAvatar());
        profileResponse.setRole(user.getRole());
        profileResponse.setRegisterTime(user.getRegisterTime());
        profileResponse.setLastLoginTime(user.getLastLoginTime());
        profileResponse.setTotalQuota(userQuota.getTotalQuota());
        profileResponse.setUsedQuota(userQuota.getUsedQuota());
        profileResponse.setRemainingQuota(userQuota.getRemainingQuota());
        profileResponse.setDailyFreeQuota(userQuota.getDailyFreeQuota());
        profileResponse.setDetectionCount(detectionCount);
        profileResponse.setMemberDays((int) memberDays);
        
        return profileResponse;
    }
    
    @Override
    public boolean updateUserProfile(Long userId, String username, String email, String phone, String avatar) {
        User user = userMapper.findById(userId);
        if (user == null) {
            return false;
        }
        
        // 检查用户名是否已被其他用户使用
        if (username != null && !username.equals(user.getUsername())) {
            User existingUser = userMapper.findByUsername(username);
            if (existingUser != null && !existingUser.getId().equals(userId)) {
                throw new IllegalArgumentException("用户名已被使用");
            }
        }
        
        // 检查邮箱是否已被其他用户注册
        if (email != null && !email.equals(user.getEmail())) {
            User existingUser = userMapper.findByEmail(email);
            if (existingUser != null && !existingUser.getId().equals(userId)) {
                throw new IllegalArgumentException("邮箱已被注册");
            }
        }
        
        if (username != null) {
            user.setUsername(username);
        }
        if (email != null) {
            user.setEmail(email);
        }
        if (phone != null) {
            user.setPhone(phone);
        }
        if (avatar != null) {
            user.setAvatar(avatar);
        }
        
        return userMapper.update(user) > 0;
    }
    
    @Override
    public boolean updatePassword(Long userId, String currentPassword, String newPassword) {
        User user = userMapper.findById(userId);
        if (user == null) {
            return false;
        }
        
        // 验证当前密码
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("当前密码不正确");
        }
        
        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        
        return userMapper.update(user) > 0;
    }
    
    @Override
    public boolean existsByUsername(String username) {
        User user = userMapper.findByUsername(username);
        return user != null;
    }
    
    @Override
    public boolean existsByEmail(String email) {
        User user = userMapper.findByEmail(email);
        return user != null;
    }
} 
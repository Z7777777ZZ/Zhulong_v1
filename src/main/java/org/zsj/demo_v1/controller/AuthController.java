package org.zsj.demo_v1.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zsj.demo_v1.dto.*;
import org.zsj.demo_v1.entity.User;
import org.zsj.demo_v1.service.EmailVerificationService;
import org.zsj.demo_v1.service.UserService;
import org.zsj.demo_v1.util.IpUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailVerificationService emailVerificationService;
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        String ipAddress = IpUtil.getClientIp(request);
        JwtResponse jwtResponse = userService.authenticateUser(loginRequest, ipAddress);
        return ResponseEntity.ok(ApiResponse.success(jwtResponse));
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // 检查用户名是否已存在
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("用户名已被使用"));
        }
        
        // 检查邮箱是否已存在
        if (userService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("邮箱已被注册"));
        }
        
        // 验证邮箱验证码
        boolean isCodeValid = emailVerificationService.verifyCode(
                registerRequest.getEmail(), 
                registerRequest.getEmailVerificationCode()
        );
        
        if (!isCodeValid) {
            return ResponseEntity.badRequest().body(ApiResponse.error("验证码无效或已过期"));
        }
        
        // 注册用户
        User user = userService.registerUser(registerRequest);
        
        return ResponseEntity.ok(ApiResponse.success("注册成功", user.getUsername()));
    }
    
    @PostMapping("/verify-email")
    public ResponseEntity<?> sendVerificationCode(@Valid @RequestBody EmailVerificationRequest request) {
        logger.info("收到发送验证码请求: {}", request.getEmail());
        try {
            // 如果邮箱已被注册，但允许发送验证码（可用于找回密码等场景）
            emailVerificationService.sendVerificationCode(request.getEmail());
            logger.info("验证码发送成功: {}", request.getEmail());
            return ResponseEntity.ok(ApiResponse.success("验证码已发送，请查收邮件"));
        } catch (Exception e) {
            logger.error("验证码发送失败: {}, 错误: {}", request.getEmail(), e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error("验证码发送失败: " + e.getMessage()));
        }
    }
}
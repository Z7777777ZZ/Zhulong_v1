package org.zsj.demo_v1.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zsj.demo_v1.dto.ApiResponse;
import org.zsj.demo_v1.dto.JwtResponse;
import org.zsj.demo_v1.dto.LoginRequest;
import org.zsj.demo_v1.dto.RegisterRequest;
import org.zsj.demo_v1.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        // 获取客户端IP
        String ipAddress = request.getRemoteAddr();
        
        JwtResponse jwtResponse = userService.authenticateUser(loginRequest, ipAddress);
        
        return ResponseEntity.ok(ApiResponse.success("登录成功", jwtResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // 检查用户名是否已存在
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error("用户名已被使用"));
        }

        // 检查邮箱是否已存在
        if (userService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error("邮箱已被注册"));
        }

        // 创建新用户
        userService.registerUser(registerRequest);

        return ResponseEntity.ok(ApiResponse.success("注册成功"));
    }
} 
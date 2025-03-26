package org.zsj.demo_v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.zsj.demo_v1.dto.ApiResponse;
import org.zsj.demo_v1.dto.UserProfileResponse;
import org.zsj.demo_v1.security.services.UserDetailsImpl;
import org.zsj.demo_v1.service.UserService;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        UserProfileResponse profileResponse = userService.getUserProfile(userDetails.getId());
        
        return ResponseEntity.ok(ApiResponse.success(profileResponse));
    }

    @PutMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUserProfile(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String avatar) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        try {
            boolean updated = userService.updateUserProfile(userDetails.getId(), username, email, phone, avatar);
            if (updated) {
                return ResponseEntity.ok(ApiResponse.success("个人资料更新成功"));
            } else {
                return ResponseEntity.badRequest().body(ApiResponse.error("个人资料更新失败"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/password")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updatePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        try {
            boolean updated = userService.updatePassword(userDetails.getId(), currentPassword, newPassword);
            if (updated) {
                return ResponseEntity.ok(ApiResponse.success("密码更新成功"));
            } else {
                return ResponseEntity.badRequest().body(ApiResponse.error("密码更新失败"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
} 
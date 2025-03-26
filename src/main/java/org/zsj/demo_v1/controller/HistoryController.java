package org.zsj.demo_v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.zsj.demo_v1.dto.ApiResponse;
import org.zsj.demo_v1.dto.DetectionRecordResponse;
import org.zsj.demo_v1.security.services.UserDetailsImpl;
import org.zsj.demo_v1.service.DetectionRecordService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private DetectionRecordService detectionRecordService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserDetectionRecords() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        List<DetectionRecordResponse> records = detectionRecordService.getUserDetectionRecords(userDetails.getId());
        
        return ResponseEntity.ok(ApiResponse.success(records));
    }

    @GetMapping("/type/{typeId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getDetectionRecordsByType(@PathVariable Integer typeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        List<DetectionRecordResponse> records = detectionRecordService.getUserDetectionRecordsByType(userDetails.getId(), typeId);
        
        return ResponseEntity.ok(ApiResponse.success(records));
    }

    @GetMapping("/time")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getDetectionRecordsByTimeRange(
            @RequestParam(required = false) String timeRange) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        LocalDateTime startTime;
        LocalDateTime endTime = LocalDateTime.now();
        
        if (timeRange == null || timeRange.equals("all")) {
            // 默认返回全部记录
            return ResponseEntity.ok(ApiResponse.success(
                    detectionRecordService.getUserDetectionRecords(userDetails.getId())));
        }
        
        switch (timeRange) {
            case "today":
                startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
                break;
            case "week":
                startTime = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIN);
                break;
            case "month":
                startTime = LocalDateTime.of(LocalDate.now().minusMonths(1), LocalTime.MIN);
                break;
            default:
                startTime = LocalDateTime.of(LocalDate.now().minusYears(10), LocalTime.MIN); // 设置一个足够早的时间
        }
        
        List<DetectionRecordResponse> records = detectionRecordService.getUserDetectionRecordsByTimeRange(
                userDetails.getId(), startTime, endTime);
        
        return ResponseEntity.ok(ApiResponse.success(records));
    }

    @GetMapping("/result/{result}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getDetectionRecordsByResult(@PathVariable String result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        Integer resultValue;
        if (result.equals("ai")) {
            resultValue = 1; // AI生成
        } else if (result.equals("human")) {
            resultValue = 0; // 人工创作
        } else {
            // 返回全部记录
            return ResponseEntity.ok(ApiResponse.success(
                    detectionRecordService.getUserDetectionRecords(userDetails.getId())));
        }
        
        List<DetectionRecordResponse> records = detectionRecordService.getUserDetectionRecordsByResult(
                userDetails.getId(), resultValue);
        
        return ResponseEntity.ok(ApiResponse.success(records));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getDetectionRecordDetail(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        DetectionRecordResponse record = detectionRecordService.getDetectionRecordById(id);
        
        if (record == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(ApiResponse.success(record));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteDetectionRecord(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        boolean deleted = detectionRecordService.deleteDetectionRecord(id, userDetails.getId());
        
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("记录删除成功"));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.error("记录删除失败"));
        }
    }
} 
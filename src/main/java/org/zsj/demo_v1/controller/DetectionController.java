package org.zsj.demo_v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zsj.demo_v1.dto.ApiResponse;
import org.zsj.demo_v1.dto.DetectionResultResponse;
import org.zsj.demo_v1.security.services.UserDetailsImpl;
import org.zsj.demo_v1.service.DetectionRecordService;
import org.zsj.demo_v1.service.UserQuotaService;

import java.util.Random;

@RestController
@RequestMapping("/detection")
public class DetectionController {

    @Autowired
    private DetectionRecordService detectionRecordService;
    
    @Autowired
    private UserQuotaService userQuotaService;
    
    @PostMapping("/detect")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> detectContent(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        
        System.out.println("收到检测请求 - 用户ID: " + userId + ", 文件名: " + file.getOriginalFilename() + ", 类型: " + type);
        
        // 检查用户额度
        if (!userQuotaService.checkAndResetDailyQuota(userId)) {
            System.out.println("每日免费额度已重置");
        }
        
        // 获取用户额度
        if (!userQuotaService.consumeQuota(userId, 1)) {
            System.out.println("用户额度不足，无法进行检测");
            return ResponseEntity.badRequest().body(ApiResponse.error("额度不足，请充值后再试"));
        }
        
        try {
            // 实际项目中这里应该调用AI检测服务
            // 这里我们模拟一个检测结果
            Random random = new Random();
            int probability = random.nextInt(101); // 0-100的随机数
            
            String analysis = probability > 70
                ? "该内容很可能是由AI生成的，检测到了典型的AI生成特征。"
                : "该内容很可能是人工创作的，未检测到明显的AI生成特征。";
            
            // 创建检测记录
            detectionRecordService.createDetectionRecord(
                userId,
                getDetectionTypeId(type),
                file.getOriginalFilename(),
                file.getSize(),
                null, // 内容文本，对于非文本类型为null
                probability > 70 ? 1 : 0, // 结果：1为AI生成，0为人工创作
                probability, // 置信度
                analysis
            );
            
            DetectionResultResponse result = new DetectionResultResponse();
            result.setAiProbability(probability);
            result.setAnalysis(analysis);
            
            return ResponseEntity.ok(ApiResponse.success(result));
            
        } catch (Exception e) {
            System.out.println("检测过程发生错误: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(ApiResponse.error("检测失败，请稍后再试"));
        }
    }
    
    private int getDetectionTypeId(String type) {
        // 根据前端传来的type字符串转换为数据库中的检测类型ID
        switch (type) {
            case "image":
                return 1;
            case "text":
                return 2;
            case "video":
                return 3;
            case "audio":
                return 4;
            default:
                return 1; // 默认为图片检测
        }
    }
} 
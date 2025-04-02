package org.zsj.demo_v1.service;

import org.zsj.demo_v1.dto.DetectionRecordResponse;
import org.zsj.demo_v1.entity.DetectionRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface DetectionRecordService {
    List<DetectionRecordResponse> getUserDetectionRecords(Long userId);
    
    List<DetectionRecordResponse> getUserDetectionRecordsByType(Long userId, Integer typeId);
    
    List<DetectionRecordResponse> getUserDetectionRecordsByTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime);
    
    List<DetectionRecordResponse> getUserDetectionRecordsByResult(Long userId, Integer result);
    
    DetectionRecordResponse getDetectionRecordById(Long id);
    
    int getDetectionCountByUserId(Long userId);
    
    boolean deleteDetectionRecord(Long id, Long userId);
    
    /**
     * 创建检测记录
     * @param userId 用户ID
     * @param typeId 检测类型ID
     * @param fileName 文件名
     * @param fileSize 文件大小
     * @param content 内容文本(对于非文本类型可为null)
     * @param result 检测结果(1:AI生成, 0:人工创作)
     * @param confidence 置信度(0-100)
     * @param analysis 分析结果文本
     * @return 创建的检测记录ID
     */
    Long createDetectionRecord(Long userId, Integer typeId, String fileName, Long fileSize, 
                              String content, Integer result, Integer confidence, String analysis);
} 
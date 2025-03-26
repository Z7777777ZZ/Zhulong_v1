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
} 
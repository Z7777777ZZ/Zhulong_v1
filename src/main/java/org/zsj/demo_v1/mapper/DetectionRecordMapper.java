package org.zsj.demo_v1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zsj.demo_v1.entity.DetectionRecord;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DetectionRecordMapper {
    List<DetectionRecord> findByUserId(Long userId);
    
    List<DetectionRecord> findByUserIdAndType(
            @Param("userId") Long userId, 
            @Param("typeId") Integer typeId);
    
    List<DetectionRecord> findByUserIdAndTimeRange(
            @Param("userId") Long userId, 
            @Param("startTime") LocalDateTime startTime, 
            @Param("endTime") LocalDateTime endTime);
    
    List<DetectionRecord> findByUserIdAndResult(
            @Param("userId") Long userId, 
            @Param("result") Integer result);
    
    DetectionRecord findById(Long id);
    
    int countByUserId(Long userId);
    
    int insert(DetectionRecord detectionRecord);
    
    int update(DetectionRecord detectionRecord);
    
    int deleteById(Long id);
} 
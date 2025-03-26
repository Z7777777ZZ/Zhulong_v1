package org.zsj.demo_v1.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DetectionRecord {
    private Long id;
    private Long userId;
    private Integer detectionTypeId;
    private String fileUrl;
    private String fileName;
    private Long fileSize;
    private String content;
    private Integer result; // 0-非AI生成，1-AI生成
    private BigDecimal confidence;
    private LocalDateTime detectionTime;
    private Integer status; // 0-处理中，1-已完成，2-失败
    private String errorMessage;
    
    // 关联属性
    private DetectionType detectionType;
} 
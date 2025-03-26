package org.zsj.demo_v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetectionRecordResponse {
    private Long id;
    private String fileName;
    private Integer type; // 检测类型ID
    private String typeName; // 检测类型名称
    private LocalDateTime time; // 检测时间
    private String result; // "AI生成" 或 "人工创作"
    private BigDecimal probability; // AI生成概率
    private Integer status; // 0-处理中，1-已完成，2-失败
    private String errorMessage; // 错误信息
    private String fileUrl; // 文件地址
    private String content; // 内容
} 
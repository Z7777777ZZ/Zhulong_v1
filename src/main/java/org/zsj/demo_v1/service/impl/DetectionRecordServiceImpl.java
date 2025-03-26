package org.zsj.demo_v1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zsj.demo_v1.dto.DetectionRecordResponse;
import org.zsj.demo_v1.entity.DetectionRecord;
import org.zsj.demo_v1.mapper.DetectionRecordMapper;
import org.zsj.demo_v1.service.DetectionRecordService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetectionRecordServiceImpl implements DetectionRecordService {

    @Autowired
    private DetectionRecordMapper detectionRecordMapper;

    @Override
    public List<DetectionRecordResponse> getUserDetectionRecords(Long userId) {
        List<DetectionRecord> records = detectionRecordMapper.findByUserId(userId);
        return convertToResponseList(records);
    }

    @Override
    public List<DetectionRecordResponse> getUserDetectionRecordsByType(Long userId, Integer typeId) {
        List<DetectionRecord> records = detectionRecordMapper.findByUserIdAndType(userId, typeId);
        return convertToResponseList(records);
    }

    @Override
    public List<DetectionRecordResponse> getUserDetectionRecordsByTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        List<DetectionRecord> records = detectionRecordMapper.findByUserIdAndTimeRange(userId, startTime, endTime);
        return convertToResponseList(records);
    }

    @Override
    public List<DetectionRecordResponse> getUserDetectionRecordsByResult(Long userId, Integer result) {
        List<DetectionRecord> records = detectionRecordMapper.findByUserIdAndResult(userId, result);
        return convertToResponseList(records);
    }

    @Override
    public DetectionRecordResponse getDetectionRecordById(Long id) {
        DetectionRecord record = detectionRecordMapper.findById(id);
        if (record != null) {
            return convertToResponse(record);
        }
        return null;
    }

    @Override
    public int getDetectionCountByUserId(Long userId) {
        return detectionRecordMapper.countByUserId(userId);
    }

    @Override
    public boolean deleteDetectionRecord(Long id, Long userId) {
        DetectionRecord record = detectionRecordMapper.findById(id);
        if (record != null && record.getUserId().equals(userId)) {
            return detectionRecordMapper.deleteById(id) > 0;
        }
        return false;
    }

    private List<DetectionRecordResponse> convertToResponseList(List<DetectionRecord> records) {
        if (records == null || records.isEmpty()) {
            return new ArrayList<>();
        }
        return records.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private DetectionRecordResponse convertToResponse(DetectionRecord record) {
        DetectionRecordResponse response = new DetectionRecordResponse();
        response.setId(record.getId());
        response.setFileName(record.getFileName());
        response.setType(record.getDetectionTypeId());
        response.setTypeName(record.getDetectionType() != null ? record.getDetectionType().getTypeName() : "未知类型");
        response.setTime(record.getDetectionTime());
        response.setResult(record.getResult() == 1 ? "AI生成" : "人工创作");
        response.setProbability(record.getConfidence() != null ? record.getConfidence() : BigDecimal.ZERO);
        response.setStatus(record.getStatus());
        response.setErrorMessage(record.getErrorMessage());
        response.setFileUrl(record.getFileUrl());
        response.setContent(record.getContent());
        return response;
    }
} 
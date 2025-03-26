package org.zsj.demo_v1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zsj.demo_v1.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TransactionMapper {
    List<Transaction> findByUserId(Long userId);
    
    List<Transaction> findByUserIdAndTimeRange(
            @Param("userId") Long userId, 
            @Param("startTime") LocalDateTime startTime, 
            @Param("endTime") LocalDateTime endTime);
    
    Transaction findById(Long id);
    
    Transaction findByOrderId(String orderId);
    
    int insert(Transaction transaction);
    
    int update(Transaction transaction);
    
    int deleteById(Long id);
} 
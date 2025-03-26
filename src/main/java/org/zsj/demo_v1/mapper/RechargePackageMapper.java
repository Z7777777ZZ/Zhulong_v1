package org.zsj.demo_v1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zsj.demo_v1.entity.RechargePackage;

import java.util.List;

@Mapper
public interface RechargePackageMapper {
    List<RechargePackage> findAll();
    
    RechargePackage findById(Long id);
    
    int insert(RechargePackage rechargePackage);
    
    int update(RechargePackage rechargePackage);
    
    int deleteById(Long id);
} 
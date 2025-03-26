package org.zsj.demo_v1.entity;

import lombok.Data;

@Data
public class DetectionType {
    private Integer id;
    private String typeName;
    private String description;
    private Integer quotaCost;
} 
package com.hospitalmanagement.hospitalmanagementsystem.dto.response;

import lombok.Data;

@Data
public class DepartmentResponseDto {
    private Long id;
    private String name;
    private String description;
    private Integer floorNumber;
}

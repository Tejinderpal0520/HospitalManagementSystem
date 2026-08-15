package com.hospitalmanagement.hospitalmanagementsystem.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class SymptomResponseDto {
    private String suggestedDepartment;
    private String message;
    private List<String> availableDoctors;
}

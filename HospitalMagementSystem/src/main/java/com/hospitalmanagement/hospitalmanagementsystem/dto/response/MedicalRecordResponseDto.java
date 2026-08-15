package com.hospitalmanagement.hospitalmanagementsystem.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicalRecordResponseDto {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private String doctorName;
    private Long appointmentId;
    private LocalDateTime recordDate;
    private String title;
    private String description;
    private String fileUrl;
}

package com.hospitalmanagement.hospitalmanagementsystem.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PrescriptionResponseDto {
    private Long id;
    private Long appointmentId;
    private String diagnosis;
    private String notes;
    private LocalDateTime issuedAt;
    private List<PrescriptionItemResponseDto> items;
}

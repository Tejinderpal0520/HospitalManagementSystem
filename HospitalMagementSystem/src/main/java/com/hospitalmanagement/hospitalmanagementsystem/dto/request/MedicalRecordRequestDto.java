package com.hospitalmanagement.hospitalmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicalRecordRequestDto {
    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;

    private Long appointmentId;

    @NotNull
    private LocalDateTime recordDate;

    @NotBlank
    @Size(min = 3, max = 500)
    private String title;

    private String description;

    private String fileUrl;
}

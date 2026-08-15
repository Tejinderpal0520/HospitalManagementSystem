package com.hospitalmanagement.hospitalmanagementsystem.dto.request;

import com.hospitalmanagement.hospitalmanagementsystem.enums.IntakeInstruction;
import com.hospitalmanagement.hospitalmanagementsystem.enums.MedicineFrequency;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PrescriptionItemRequestDto {
    @NotBlank
    private String medicineName;

    @NotBlank
    private String dosage;

    @NotNull
    private MedicineFrequency frequency;

    @NotNull
    @Min(1)
    @Max(365)
    private Integer durationDays;

    @NotNull
    private IntakeInstruction instructions;

    private String additionalInstructions;
}

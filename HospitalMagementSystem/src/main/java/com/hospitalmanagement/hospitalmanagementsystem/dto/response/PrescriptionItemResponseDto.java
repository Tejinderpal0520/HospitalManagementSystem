package com.hospitalmanagement.hospitalmanagementsystem.dto.response;

import com.hospitalmanagement.hospitalmanagementsystem.enums.IntakeInstruction;
import com.hospitalmanagement.hospitalmanagementsystem.enums.MedicineFrequency;
import lombok.Data;

@Data
public class PrescriptionItemResponseDto {
    private Long id;
    private String medicineName;
    private String dosage;
    private MedicineFrequency frequency;
    private Integer durationDays;
    private IntakeInstruction instructions;
    private String additionalInstructions;
}

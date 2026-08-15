package com.hospitalmanagement.hospitalmanagementsystem.dto.response;

import com.hospitalmanagement.hospitalmanagementsystem.enums.BloodGroupType;
import com.hospitalmanagement.hospitalmanagementsystem.enums.GenderEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponseDto {
    private Long id;
    private String name;
    private String email;
    private String contactNumber;
    private GenderEnum gender;
    private LocalDate birthDate;
    private BloodGroupType bloodGroup;
    private String address;
    private String medicalHistory;
}

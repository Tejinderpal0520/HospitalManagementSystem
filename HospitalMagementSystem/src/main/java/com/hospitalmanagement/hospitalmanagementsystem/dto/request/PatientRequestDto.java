package com.hospitalmanagement.hospitalmanagementsystem.dto.request;

import com.hospitalmanagement.hospitalmanagementsystem.enums.BloodGroupType;
import com.hospitalmanagement.hospitalmanagementsystem.enums.GenderEnum;
import com.hospitalmanagement.hospitalmanagementsystem.enums.UsersRoleEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequestDto {
    private String contactNumber;
    private GenderEnum gender;
    private LocalDate birthDate;
    private BloodGroupType bloodGroup;
    private String address;
    private String medicalHistory;
}

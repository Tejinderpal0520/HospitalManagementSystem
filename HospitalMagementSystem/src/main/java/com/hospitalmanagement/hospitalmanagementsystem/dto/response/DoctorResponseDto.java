package com.hospitalmanagement.hospitalmanagementsystem.dto.response;

import com.hospitalmanagement.hospitalmanagementsystem.entity.Department;
import com.hospitalmanagement.hospitalmanagementsystem.enums.UsersRoleEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Data
public class DoctorResponseDto {
    private String name;
    private String email;
    private UsersRoleEnum role;
    private String contactNumber;
    private Department department;
    private String specialization;
    private String qualification;
    private int experienceYears;
    private BigDecimal consultationFee;
    private List<DayOfWeek> availableDays;
    private LocalTime availableFrom;
    private LocalTime availableTo;
    private String bio;
}

package com.hospitalmanagement.hospitalmanagementsystem.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Data
public class DoctorResponseDto {
    private Long id;
    private String name;
    private String email;
    private com.hospitalmanagement.hospitalmanagementsystem.enums.UsersRoleEnum role;
    private String contactNumber;
    private Long departmentId;
    private String departmentName;
    private String specialization;
    private String qualification;
    private int experienceYears;
    private BigDecimal consultationFee;
    private List<DayOfWeek> availableDays;
    private LocalTime availableFrom;
    private LocalTime availableTo;
    private String bio;
}

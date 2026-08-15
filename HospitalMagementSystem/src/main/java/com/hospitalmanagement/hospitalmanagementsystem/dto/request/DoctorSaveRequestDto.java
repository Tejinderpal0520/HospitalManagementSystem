package com.hospitalmanagement.hospitalmanagementsystem.dto.request;

import com.hospitalmanagement.hospitalmanagementsystem.entity.Department;
import com.hospitalmanagement.hospitalmanagementsystem.enums.UsersRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSaveRequestDto {
    private String name;
    private String email;
    private String password;
    private UsersRoleEnum role;
    private String contactNumber;
    private Long departmentId;
    private String specialization;
    private String qualification;
    private int experienceYears;
    private BigDecimal consultationFee;
    private List<DayOfWeek> availableDays;
    private LocalTime availableFrom;
    private LocalTime availableTo;
    private String bio;
}

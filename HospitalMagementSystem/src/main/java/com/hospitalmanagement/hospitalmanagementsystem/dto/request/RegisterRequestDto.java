package com.hospitalmanagement.hospitalmanagementsystem.dto.request;


import com.hospitalmanagement.hospitalmanagementsystem.enums.BloodGroupType;
import com.hospitalmanagement.hospitalmanagementsystem.enums.GenderEnum;
import com.hospitalmanagement.hospitalmanagementsystem.enums.UsersRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    private String name;
    private String email;
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@#$%^&+=]).{8,}$",
//            message = "Password must contain letters and numbers")
    private String password;
    private UsersRoleEnum role;
    private String contactNumber;
    private GenderEnum genderEnum;
    private LocalDate birthDate;
    private BloodGroupType bloodGroup;
}

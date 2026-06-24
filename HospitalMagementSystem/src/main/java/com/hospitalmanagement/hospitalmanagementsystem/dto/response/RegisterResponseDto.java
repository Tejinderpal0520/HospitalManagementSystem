package com.hospitalmanagement.hospitalmanagementsystem.dto.response;


import com.hospitalmanagement.hospitalmanagementsystem.enums.UsersRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDto {
    private String name;
    private String email;
    private String contactNumber;
    private UsersRoleEnum role;
}

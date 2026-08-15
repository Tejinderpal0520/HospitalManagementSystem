package com.hospitalmanagement.hospitalmanagementsystem.dto.response;

import com.hospitalmanagement.hospitalmanagementsystem.enums.UsersRoleEnum;
import lombok.Data;

@Data
public class UserProfileResponseDto {
    private Long id;
    private String name;
    private String email;
    private UsersRoleEnum role;
    private String contactNumber;
    private Long profileId;
}

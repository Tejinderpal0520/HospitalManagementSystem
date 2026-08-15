package com.hospitalmanagement.hospitalmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordRequestDto {
    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;
}

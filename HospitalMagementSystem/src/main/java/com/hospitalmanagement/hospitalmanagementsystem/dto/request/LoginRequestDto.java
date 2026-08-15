package com.hospitalmanagement.hospitalmanagementsystem.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    String email;
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
//            message = "Password must contain letters and numbers")
    String password;
}

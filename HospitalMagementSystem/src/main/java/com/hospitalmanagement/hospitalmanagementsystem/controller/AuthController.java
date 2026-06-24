package com.hospitalmanagement.hospitalmanagementsystem.controller;


import com.hospitalmanagement.hospitalmanagementsystem.dto.request.LoginRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.RegisterRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.LoginResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.RegisterResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public RegisterResponseDto registerNewUser(@RequestBody RegisterRequestDto registerRequestDto){
        return authService.registerNewUser(registerRequestDto);
    }

    @PostMapping("/login")
    public LoginResponseDto loginUser(@RequestBody LoginRequestDto loginRequestDto){
        return authService.loginUser(loginRequestDto);

    }
}

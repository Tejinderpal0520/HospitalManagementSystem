package com.hospitalmanagement.hospitalmanagementsystem.controller;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.ChangePasswordRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.LoginRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.RegisterRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.LoginResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.RegisterResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.UserProfileResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public RegisterResponseDto registerNewUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        return authService.registerNewUser(registerRequestDto);
    }

    @PostMapping("/login")
    public LoginResponseDto loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authService.loginUser(loginRequestDto);
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDto> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(authService.getCurrentUser(userDetails.getUsername()));
    }

    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ChangePasswordRequestDto request) {
        authService.changePassword(userDetails.getUsername(), request);
        return ResponseEntity.noContent().build();
    }
}

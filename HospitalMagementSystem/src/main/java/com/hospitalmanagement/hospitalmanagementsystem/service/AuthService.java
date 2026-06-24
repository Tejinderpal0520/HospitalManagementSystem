package com.hospitalmanagement.hospitalmanagementsystem.service;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.LoginRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.RegisterRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.LoginResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.RegisterResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Patient;
import com.hospitalmanagement.hospitalmanagementsystem.entity.User;
import com.hospitalmanagement.hospitalmanagementsystem.enums.UsersRoleEnum;
import com.hospitalmanagement.hospitalmanagementsystem.exceptions.ResourceNotFoundException;
import com.hospitalmanagement.hospitalmanagementsystem.repository.PatientRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.UserRepository;
import com.hospitalmanagement.hospitalmanagementsystem.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final PatientRepository patientRepository;

    public RegisterResponseDto registerNewUser(RegisterRequestDto registerRequestDto) {
        User user = modelMapper.map(registerRequestDto, User.class);

        user.setRole(UsersRoleEnum.PATIENT);
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        User savedUser = userRepository.save(user);

        Patient patient = new Patient();
        patient.setUser(savedUser);
        patient.setBirthDate(registerRequestDto.getBirthDate());
        patient.setGender(registerRequestDto.getGenderEnum());
        patient.setBloodGroup(registerRequestDto.getBloodGroup());

        patientRepository.save(patient);

        return modelMapper.map(user, RegisterResponseDto.class);
    }


    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.getUserByEmail(loginRequestDto.getEmail());
        System.out.println("start :- " + passwordEncoder.encode("Test@1234") + " -: end");
        if(user == null) throw new ResourceNotFoundException("User not found");

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Password is wrong");
        }
        String token = jwtUtil.generateToken(user.getEmail(), String.valueOf(user.getRole()));

        return new LoginResponseDto(user.getEmail(), "Bearer " + token);
    }
}


//3. Exception Choice is Weak
//throw new IllegalArgumentException("Password is wrong");
//
//This is lazy. IllegalArgumentException is not meant for authentication failures.
//
//Also, you're leaking information:
//
//        "User not found"
//        "Password is wrong"
//
//This allows attackers to enumerate valid emails.
//
//Fix (production-grade):
//Use a single generic message:
//
//        throw new AuthenticationException("Invalid credentials");

//$2a$10$.yy4iYgj.m9uJ3A9h/.vQ.OlKCRotWEiWJVSDWANTFsNrCkjgI/Qu
//$2a$10$oTxu7dJy1ZKLfZImUH77w.x1g/Q.wTOUxgPSqP/glkmeaRs.50Xoq
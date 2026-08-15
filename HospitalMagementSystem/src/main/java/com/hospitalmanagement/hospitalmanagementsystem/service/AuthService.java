package com.hospitalmanagement.hospitalmanagementsystem.service;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.ChangePasswordRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.LoginRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.RegisterRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.LoginResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.RegisterResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.UserProfileResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Patient;
import com.hospitalmanagement.hospitalmanagementsystem.entity.User;
import com.hospitalmanagement.hospitalmanagementsystem.enums.UsersRoleEnum;
import com.hospitalmanagement.hospitalmanagementsystem.exceptions.ResourceNotFoundException;
import com.hospitalmanagement.hospitalmanagementsystem.repository.DoctorRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.PatientRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.UserRepository;
import com.hospitalmanagement.hospitalmanagementsystem.security.JwtUtil;
import com.hospitalmanagement.hospitalmanagementsystem.util.DtoMapper;
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
    private final DoctorRepository doctorRepository;
    private final DtoMapper dtoMapper;

    public RegisterResponseDto registerNewUser(RegisterRequestDto registerRequestDto) {
        if (userRepository.getUserByEmail(registerRequestDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email already registered");
        }

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

        return modelMapper.map(savedUser, RegisterResponseDto.class);
    }

    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.getUserByEmail(loginRequestDto.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("Invalid credentials");
        }
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new LoginResponseDto(user.getEmail(), token);
    }

    public UserProfileResponseDto getCurrentUser(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        Long profileId = null;
        if (user.getRole() == UsersRoleEnum.PATIENT) {
            profileId = patientRepository.findByUser_Email(email).map(Patient::getId).orElse(null);
        } else if (user.getRole() == UsersRoleEnum.DOCTOR) {
            profileId = doctorRepository.findByUser_Email(email).map(d -> d.getId()).orElse(null);
        }

        return dtoMapper.toUserProfile(user, profileId);
    }

    public void changePassword(String email, ChangePasswordRequestDto request) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}

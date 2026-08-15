package com.hospitalmanagement.hospitalmanagementsystem.service;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.PatientRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.RegisterRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.AppointmentResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.BillResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.PatientResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.RegisterResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Patient;
import com.hospitalmanagement.hospitalmanagementsystem.entity.User;
import com.hospitalmanagement.hospitalmanagementsystem.exceptions.ResourceNotFoundException;
import com.hospitalmanagement.hospitalmanagementsystem.repository.AppointmentRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.BillRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.PatientRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.UserRepository;
import com.hospitalmanagement.hospitalmanagementsystem.util.DtoMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final AppointmentRepository appointmentRepository;
    private final BillRepository billRepository;
    private final DtoMapper dtoMapper;

    public PatientResponseDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient with id " + id + " not found"));
        return dtoMapper.toPatientResponse(patient);
    }

    public List<PatientResponseDto> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(dtoMapper::toPatientResponse)
                .toList();
    }

    public RegisterResponseDto createPatient(RegisterRequestDto registerRequestDto) {
        return authService.registerNewUser(registerRequestDto);
    }

    public PatientResponseDto updatePatient(Long id, @Valid PatientRequestDto patientRequestDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        if (patientRequestDto.getGender() != null) {
            patient.setGender(patientRequestDto.getGender());
        }
        if (patientRequestDto.getBirthDate() != null) {
            patient.setBirthDate(patientRequestDto.getBirthDate());
        }
        if (patientRequestDto.getBloodGroup() != null) {
            patient.setBloodGroup(patientRequestDto.getBloodGroup());
        }
        if (patientRequestDto.getAddress() != null) {
            patient.setAddress(patientRequestDto.getAddress());
        }
        if (patientRequestDto.getContactNumber() != null) {
            patient.setContactNumber(patientRequestDto.getContactNumber());
        }
        if (patientRequestDto.getMedicalHistory() != null) {
            patient.setMedicalHistory(patientRequestDto.getMedicalHistory());
        }

        return dtoMapper.toPatientResponse(patientRepository.save(patient));
    }

    @Transactional
    public String deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        User user = patient.getUser();
        patientRepository.delete(patient);
        if (user != null) {
            user.setActive(false);
            userRepository.save(user);
        }
        return "Patient deleted successfully";
    }

    public List<AppointmentResponseDto> getAllAppointments(Long id) {
        patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return appointmentRepository.findByPatient(id).stream()
                .map(dtoMapper::toAppointmentResponse)
                .toList();
    }

    public List<BillResponseDto> getAllBills(Long id) {
        patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return billRepository.findByPatient(id).stream()
                .map(dtoMapper::toBillResponse)
                .toList();
    }
}

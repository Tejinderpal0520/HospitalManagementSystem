package com.hospitalmanagement.hospitalmanagementsystem.service;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.DoctorSaveRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.DoctorUpdateRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.AppointmentResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.DoctorResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Appointment;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Department;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Doctor;
import com.hospitalmanagement.hospitalmanagementsystem.entity.User;
import com.hospitalmanagement.hospitalmanagementsystem.enums.UsersRoleEnum;
import com.hospitalmanagement.hospitalmanagementsystem.exceptions.ResourceNotFoundException;
import com.hospitalmanagement.hospitalmanagementsystem.repository.DepartmentRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.DoctorRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.UserRepository;
import com.hospitalmanagement.hospitalmanagementsystem.util.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;
    private final DtoMapper dtoMapper;
    private final AppointmentService appointmentService;

    @Transactional
    public DoctorResponseDto createDoctor(DoctorSaveRequestDto dto) {
        if (userRepository.getUserByEmail(dto.getEmail()) != null) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(UsersRoleEnum.DOCTOR);
        user.setContactNumber(dto.getContactNumber());
        User savedUser = userRepository.save(user);

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        Doctor doctor = buildDoctor(dto, savedUser, department);
        Doctor savedDoctor = doctorRepository.save(doctor);

        return dtoMapper.toDoctorResponse(savedDoctor);
    }

    private static Doctor buildDoctor(DoctorSaveRequestDto dto, User savedUser, Department department) {
        Doctor doctor = new Doctor();
        doctor.setUser(savedUser);
        doctor.setDepartment(department);
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setQualification(dto.getQualification());
        doctor.setExperienceYears(dto.getExperienceYears());
        doctor.setConsultationFee(dto.getConsultationFee());
        doctor.setAvailableDays(dto.getAvailableDays());
        doctor.setAvailableFrom(dto.getAvailableFrom());
        doctor.setAvailableTo(dto.getAvailableTo());
        doctor.setBio(dto.getBio());
        return doctor;
    }

    public List<DoctorResponseDto> getAllDoctor() {
        return doctorRepository.findAll().stream()
                .map(dtoMapper::toDoctorResponse)
                .toList();
    }

    public DoctorResponseDto getDoctorResponseById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        return dtoMapper.toDoctorResponse(doctor);
    }

    public List<DoctorResponseDto> getDoctorByDepartment(Long departmentId) {
        return doctorRepository.findByDepartmentId(departmentId).stream()
                .map(dtoMapper::toDoctorResponse)
                .toList();
    }

    public DoctorResponseDto updateDoctor(Long id, DoctorUpdateRequestDto dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        if (dto.getSpecialization() != null) {
            doctor.setSpecialization(dto.getSpecialization());
        }
        if (dto.getQualification() != null) {
            doctor.setQualification(dto.getQualification());
        }
        if (dto.getBio() != null) {
            doctor.setBio(dto.getBio());
        }
        if (dto.getDepartment() != null) {
            doctor.setDepartment(dto.getDepartment());
        }
        if (dto.getExperienceYears() != 0) {
            doctor.setExperienceYears(dto.getExperienceYears());
        }
        if (dto.getConsultationFee() != null) {
            doctor.setConsultationFee(dto.getConsultationFee());
        }
        if (dto.getAvailableDays() != null && !dto.getAvailableDays().isEmpty()) {
            doctor.setAvailableDays(dto.getAvailableDays());
        }
        if (dto.getAvailableFrom() != null) {
            doctor.setAvailableFrom(dto.getAvailableFrom());
        }
        if (dto.getAvailableTo() != null) {
            doctor.setAvailableTo(dto.getAvailableTo());
        }

        return dtoMapper.toDoctorResponse(doctorRepository.save(doctor));
    }

    @Transactional
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        User user = doctor.getUser();
        doctorRepository.delete(doctor);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    public List<AppointmentResponseDto> getAllAppointments(Long id) {
        doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        return appointmentService.findByDoctor(id).stream()
                .map(dtoMapper::toAppointmentResponse)
                .toList();
    }

    public List<LocalTime> getAvailableSlot(Long id, LocalDate date) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        List<LocalTime> allSlots = new ArrayList<>();
        LocalTime slot = doctor.getAvailableFrom();
        while (slot.isBefore(doctor.getAvailableTo())) {
            allSlots.add(slot);
            slot = slot.plusMinutes(30);
        }

        List<Appointment> bookedAppointments = appointmentService.getBookedAppointments(id, date);
        List<LocalTime> bookedSlots = bookedAppointments.stream()
                .map(Appointment::getAppointmentTime)
                .toList();
        allSlots.removeAll(bookedSlots);

        return allSlots;
    }
}

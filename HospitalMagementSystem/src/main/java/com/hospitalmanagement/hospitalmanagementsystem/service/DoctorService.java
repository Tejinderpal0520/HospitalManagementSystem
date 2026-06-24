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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final AppointmentService appointmentService;

    @Transactional
    public DoctorResponseDto createDoctor(DoctorSaveRequestDto dto) {

        // Step 1: Create User
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(UsersRoleEnum.DOCTOR);
        user.setContactNumber(dto.getContactNumber());
        User savedUser = userRepository.save(user);

        // Step 2: Fetch Department from DB
        Department department = departmentRepository
                .findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        // Step 3: Create Doctor
        Doctor doctor = getDoctor(dto, savedUser, department);

        Doctor savedDoctor = doctorRepository.save(doctor);

        return modelMapper.map(savedDoctor, DoctorResponseDto.class);

    }

    private static Doctor getDoctor(DoctorSaveRequestDto dto, User savedUser, Department department) {
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
        return doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .toList();
    }

    public Doctor getDoctorById(Long id) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(id);

        if (doctorOpt.isPresent()) {
            return doctorOpt.get();

        } else {
            throw new RuntimeException("Doctor not found");
        }
//        return
    }

    public List<DoctorResponseDto> getDoctorByDepartment(Long departmentId) {
        return doctorRepository.findByDepartment(departmentId)
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .toList();
    }

    public DoctorResponseDto updateDoctor(Long id, DoctorUpdateRequestDto dto) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // ✅ String fields
//        if (dto.getContactNumber() != null) {
//            doctor.setContactNumber(dto.getContactNumber());
//        }

        if (dto.getSpecialization() != null) {
            doctor.setSpecialization(dto.getSpecialization());
        }

        if (dto.getQualification() != null) {
            doctor.setQualification(dto.getQualification());
        }

        if (dto.getBio() != null) {
            doctor.setBio(dto.getBio());
        }

        // ✅ Enum / object fields
        if (dto.getDepartment() != null) {
            doctor.setDepartment(dto.getDepartment());
        }

        // ✅ Numeric fields (primitive issue handled below)
        if (dto.getExperienceYears() != 0) {
            doctor.setExperienceYears(dto.getExperienceYears());
        }

        if (dto.getConsultationFee() != null) {
            doctor.setConsultationFee(dto.getConsultationFee());
        }

        // ✅ Collection
        if (dto.getAvailableDays() != null && !dto.getAvailableDays().isEmpty()) {
            doctor.setAvailableDays(dto.getAvailableDays());
        }

        // ✅ Time fields
        if (dto.getAvailableFrom() != null) {
            doctor.setAvailableFrom(dto.getAvailableFrom());
        }

        if (dto.getAvailableTo() != null) {
            doctor.setAvailableTo(dto.getAvailableTo());
        }

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return modelMapper.map(updatedDoctor, DoctorResponseDto.class);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }


    public List<AppointmentResponseDto> getAllAppointments(@PathVariable Long id){
        doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        List<Appointment> appointments = appointmentService.findByDoctor(id);

        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .toList();
    }

    public List<LocalTime> getAvailableSlot(Long id, LocalDate date) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        List<LocalTime> allSlots = new ArrayList<>();

        LocalTime slot = doctor.getAvailableFrom();
        while(slot.isBefore(doctor.getAvailableTo())){
            allSlots.add(slot);
            slot = slot.plusMinutes(30);
        }

        List<Appointment> bookedAppointments = appointmentService.getAvailableSlot(id, date);

        List<LocalTime> bookedSlots = bookedAppointments.stream()
                .map(Appointment::getAppointmentTime)
                .toList();
        allSlots.removeAll(bookedSlots);

        return allSlots;
    }
}
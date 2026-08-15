package com.hospitalmanagement.hospitalmanagementsystem.controller;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.DoctorSaveRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.DoctorUpdateRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.AppointmentResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.DoctorResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorResponseDto> createNewDoctor(
            @Valid @RequestBody DoctorSaveRequestDto doctorSaveRequestDto) {
        return ResponseEntity.status(201).body(doctorService.createDoctor(doctorSaveRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctor() {
        return ResponseEntity.ok(doctorService.getAllDoctor());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorResponseById(id));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<DoctorResponseDto>> getDoctorByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(doctorService.getDoctorByDepartment(departmentId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<DoctorResponseDto> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorUpdateRequestDto doctorUpdateRequestDto) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorUpdateRequestDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully");
    }

    @GetMapping("/{id}/appointments")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getAllAppointments(id));
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<List<LocalTime>> getAvailableSlot(
            @PathVariable Long id,
            @RequestParam LocalDate date) {
        return ResponseEntity.ok(doctorService.getAvailableSlot(id, date));
    }
}

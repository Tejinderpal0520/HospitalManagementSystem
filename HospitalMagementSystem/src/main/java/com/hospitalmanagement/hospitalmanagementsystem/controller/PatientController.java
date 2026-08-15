package com.hospitalmanagement.hospitalmanagementsystem.controller;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.PatientRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.RegisterRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.AppointmentResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.BillResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.PatientResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.RegisterResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegisterResponseDto> createPatient(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        return ResponseEntity.status(201).body(patientService.createPatient(registerRequestDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PATIENT')")
    public ResponseEntity<PatientResponseDto> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDto patientRequestDto) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientRequestDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.deletePatient(id));
    }

    @GetMapping("/{id}/appointments")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getAllAppointments(id));
    }

    @GetMapping("/{id}/bills")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'PATIENT')")
    public ResponseEntity<List<BillResponseDto>> getAllBills(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getAllBills(id));
    }
}

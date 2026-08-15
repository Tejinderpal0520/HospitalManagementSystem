package com.hospitalmanagement.hospitalmanagementsystem.controller;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.PrescriptionRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.PrescriptionResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<PrescriptionResponseDto> create(@Valid @RequestBody PrescriptionRequestDto request) {
        return ResponseEntity.status(201).body(prescriptionService.create(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<PrescriptionResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(prescriptionService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<PrescriptionResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody PrescriptionRequestDto request) {
        return ResponseEntity.ok(prescriptionService.update(id, request));
    }

    @GetMapping("/appointment/{appointmentId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<PrescriptionResponseDto> getByAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(prescriptionService.getByAppointment(appointmentId));
    }
}

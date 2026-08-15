package com.hospitalmanagement.hospitalmanagementsystem.controller;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.MedicalRecordRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.MedicalRecordResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<List<MedicalRecordResponseDto>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicalRecordService.getByPatient(patientId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<MedicalRecordResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<MedicalRecordResponseDto> create(@Valid @RequestBody MedicalRecordRequestDto request) {
        return ResponseEntity.status(201).body(medicalRecordService.create(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medicalRecordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

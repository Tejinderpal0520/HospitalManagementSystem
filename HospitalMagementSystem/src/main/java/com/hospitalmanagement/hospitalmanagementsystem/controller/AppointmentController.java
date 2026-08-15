package com.hospitalmanagement.hospitalmanagementsystem.controller;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.AppointmentRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.AppointmentUpdateRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.AppointmentResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    public ResponseEntity<List<AppointmentResponseDto>> getAll() {
        return ResponseEntity.ok(appointmentService.getAll());
    }

    @GetMapping("/today")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'DOCTOR')")
    public ResponseEntity<List<AppointmentResponseDto>> getToday() {
        return ResponseEntity.ok(appointmentService.getTodayAppointments());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT', 'RECEPTIONIST')")
    public ResponseEntity<AppointmentResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('PATIENT', 'RECEPTIONIST', 'ADMIN')")
    public ResponseEntity<AppointmentResponseDto> book(@Valid @RequestBody AppointmentRequestDto request) {
        return ResponseEntity.status(201).body(appointmentService.bookAppointment(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    public ResponseEntity<AppointmentResponseDto> reschedule(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentUpdateRequestDto request) {
        return ResponseEntity.ok(appointmentService.reschedule(id, request));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN', 'RECEPTIONIST')")
    public ResponseEntity<AppointmentResponseDto> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.cancel(id));
    }

    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<AppointmentResponseDto> complete(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body) {
        String notes = body != null ? body.get("doctorNotes") : null;
        return ResponseEntity.ok(appointmentService.complete(id, notes));
    }
}

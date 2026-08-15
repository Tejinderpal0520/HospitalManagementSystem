package com.hospitalmanagement.hospitalmanagementsystem.controller;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.BillRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.PayBillRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.BillResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    public ResponseEntity<List<BillResponseDto>> getAll() {
        return ResponseEntity.ok(billService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'PATIENT')")
    public ResponseEntity<BillResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(billService.getById(id));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'PATIENT')")
    public ResponseEntity<List<BillResponseDto>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(billService.getByPatient(patientId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('RECEPTIONIST', 'ADMIN')")
    public ResponseEntity<BillResponseDto> create(@Valid @RequestBody BillRequestDto request) {
        return ResponseEntity.status(201).body(billService.createBill(request));
    }

    @PatchMapping("/{id}/pay")
    @PreAuthorize("hasAnyRole('RECEPTIONIST', 'ADMIN')")
    public ResponseEntity<BillResponseDto> pay(
            @PathVariable Long id,
            @Valid @RequestBody PayBillRequestDto request) {
        return ResponseEntity.ok(billService.payBill(id, request));
    }
}

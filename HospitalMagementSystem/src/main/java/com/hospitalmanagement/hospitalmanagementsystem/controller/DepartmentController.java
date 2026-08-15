package com.hospitalmanagement.hospitalmanagementsystem.controller;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.DepartmentRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.DepartmentResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.DoctorResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> getAll() {
        return ResponseEntity.ok(departmentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getById(id));
    }

    @GetMapping("/{id}/doctors")
    public ResponseEntity<List<DoctorResponseDto>> getDoctors(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDoctorsByDepartment(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentResponseDto> create(@Valid @RequestBody DepartmentRequestDto request) {
        return ResponseEntity.status(201).body(departmentService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequestDto request) {
        return ResponseEntity.ok(departmentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.hospitalmanagement.hospitalmanagementsystem.controller;

import com.hospitalmanagement.hospitalmanagementsystem.dto.response.SymptomResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.service.SymptomCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/symptoms")
@RequiredArgsConstructor
public class SymptomCheckerController {

    private final SymptomCheckerService symptomCheckerService;

    @PostMapping("/check")
    public ResponseEntity<SymptomResponseDto> check(@RequestBody Map<String, String> body) {
        String symptoms = body.get("symptoms");
        if (symptoms == null || symptoms.isBlank()) {
            throw new IllegalArgumentException("symptoms field is required");
        }
        return ResponseEntity.ok(symptomCheckerService.checkSymptoms(symptoms));
    }

    @GetMapping("/keywords")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> getKeywords() {
        return ResponseEntity.ok(symptomCheckerService.getKeywords());
    }
}

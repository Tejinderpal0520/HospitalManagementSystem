package com.hospitalmanagement.hospitalmanagementsystem.controller;

import com.hospitalmanagement.hospitalmanagementsystem.dto.response.DashboardStatsResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.WeeklyAppointmentDto;
import com.hospitalmanagement.hospitalmanagementsystem.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DashboardStatsResponseDto> getStats() {
        return ResponseEntity.ok(dashboardService.getStats());
    }

    @GetMapping("/appointments/weekly")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<WeeklyAppointmentDto>> getWeeklyAppointments() {
        return ResponseEntity.ok(dashboardService.getWeeklyAppointments());
    }
}

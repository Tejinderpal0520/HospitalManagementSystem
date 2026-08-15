package com.hospitalmanagement.hospitalmanagementsystem.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DashboardStatsResponseDto {
    private long totalPatients;
    private long totalDoctors;
    private long appointmentsToday;
    private BigDecimal totalRevenue;
}

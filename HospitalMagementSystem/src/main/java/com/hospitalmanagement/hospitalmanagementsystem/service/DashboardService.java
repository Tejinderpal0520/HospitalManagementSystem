package com.hospitalmanagement.hospitalmanagementsystem.service;

import com.hospitalmanagement.hospitalmanagementsystem.dto.response.DashboardStatsResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.WeeklyAppointmentDto;
import com.hospitalmanagement.hospitalmanagementsystem.enums.BillStatus;
import com.hospitalmanagement.hospitalmanagementsystem.repository.AppointmentRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.BillRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.DoctorRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final BillRepository billRepository;

    public DashboardStatsResponseDto getStats() {
        DashboardStatsResponseDto stats = new DashboardStatsResponseDto();
        stats.setTotalPatients(patientRepository.count());
        stats.setTotalDoctors(doctorRepository.count());
        stats.setAppointmentsToday(appointmentRepository.findByAppointmentDate(LocalDate.now()).size());
        BigDecimal revenue = billRepository.sumTotalAmountByStatus(BillStatus.PAID);
        stats.setTotalRevenue(revenue != null ? revenue : BigDecimal.ZERO);
        return stats;
    }

    public List<WeeklyAppointmentDto> getWeeklyAppointments() {
        List<WeeklyAppointmentDto> weekly = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            long count = appointmentRepository.findByAppointmentDate(date).size();
            weekly.add(new WeeklyAppointmentDto(date, count));
        }
        return weekly;
    }
}

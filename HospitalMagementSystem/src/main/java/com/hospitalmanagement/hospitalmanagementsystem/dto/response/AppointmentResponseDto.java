package com.hospitalmanagement.hospitalmanagementsystem.dto.response;

import com.hospitalmanagement.hospitalmanagementsystem.enums.AppointmentStatus;
import com.hospitalmanagement.hospitalmanagementsystem.enums.AppointmentType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AppointmentResponseDto {
    private Long id;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
    private String departmentName;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus status;
    private AppointmentType appointmentType;
    private String reason;
    private String symptoms;
    private String doctorNotes;
    private Integer tokenNumber;
    private LocalDateTime bookedAt;
}

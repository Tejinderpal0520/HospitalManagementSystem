package com.hospitalmanagement.hospitalmanagementsystem.dto.request;

import com.hospitalmanagement.hospitalmanagementsystem.enums.AppointmentType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRequestDto {
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Date is required")
    @FutureOrPresent(message = "Cannot book past dates")
    private LocalDate appointmentDate;

    @NotNull(message = "Time is required")
    private LocalTime appointmentTime;

    @NotBlank(message = "Please describe your symptoms")
    private String symptoms;

    @NotBlank(message = "Reason is required")
    private String reason;

    private AppointmentType appointmentType = AppointmentType.IN_PERSON;
}

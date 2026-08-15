package com.hospitalmanagement.hospitalmanagementsystem.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentUpdateRequestDto {
    @NotNull
    @FutureOrPresent
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime appointmentTime;
}

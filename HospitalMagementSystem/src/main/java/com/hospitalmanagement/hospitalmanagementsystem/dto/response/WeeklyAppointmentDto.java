package com.hospitalmanagement.hospitalmanagementsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyAppointmentDto {
    private LocalDate date;
    private long count;
}

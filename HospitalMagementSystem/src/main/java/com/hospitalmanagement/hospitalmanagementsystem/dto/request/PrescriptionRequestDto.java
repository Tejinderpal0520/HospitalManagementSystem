package com.hospitalmanagement.hospitalmanagementsystem.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PrescriptionRequestDto {
    @NotNull
    private Long appointmentId;

    @NotBlank
    private String diagnosis;

    private String notes;

    @NotEmpty
    @Valid
    private List<PrescriptionItemRequestDto> items;
}

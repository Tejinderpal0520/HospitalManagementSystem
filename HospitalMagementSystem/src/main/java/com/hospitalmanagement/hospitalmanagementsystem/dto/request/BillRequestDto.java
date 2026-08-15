package com.hospitalmanagement.hospitalmanagementsystem.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillRequestDto {
    @NotNull
    private Long appointmentId;

    @DecimalMin("0.0")
    private BigDecimal medicineCost = BigDecimal.ZERO;

    @DecimalMin("0.0")
    private BigDecimal otherCharges = BigDecimal.ZERO;
}

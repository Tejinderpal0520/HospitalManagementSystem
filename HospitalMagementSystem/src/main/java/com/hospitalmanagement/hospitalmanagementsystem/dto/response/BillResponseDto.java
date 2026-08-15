package com.hospitalmanagement.hospitalmanagementsystem.dto.response;

import com.hospitalmanagement.hospitalmanagementsystem.enums.BillStatus;
import com.hospitalmanagement.hospitalmanagementsystem.enums.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BillResponseDto {
    private Long id;
    private Long patientId;
    private String patientName;
    private Long appointmentId;
    private BigDecimal consultationFee;
    private BigDecimal medicineCost;
    private BigDecimal otherCharges;
    private BigDecimal totalAmount;
    private BillStatus status;
    private PaymentMethod paymentMethod;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
}

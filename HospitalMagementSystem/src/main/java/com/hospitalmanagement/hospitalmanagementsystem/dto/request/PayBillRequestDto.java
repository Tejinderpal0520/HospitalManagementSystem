package com.hospitalmanagement.hospitalmanagementsystem.dto.request;

import com.hospitalmanagement.hospitalmanagementsystem.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PayBillRequestDto {
    @NotNull
    private PaymentMethod paymentMethod;
}

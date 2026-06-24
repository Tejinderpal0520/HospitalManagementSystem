package com.hospitalmanagement.hospitalmanagementsystem.entity;


import com.hospitalmanagement.hospitalmanagementsystem.enums.BillStatus;
import com.hospitalmanagement.hospitalmanagementsystem.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_bill_patient", columnList = "patient_id"),
        @Index(name = "idx_bill_appointment", columnList = "appointment_id")
})
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who is being billed
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // For which appointment
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    // Money fields (ALWAYS BigDecimal, never double)
    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal consultationFee = BigDecimal.ZERO;

    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal medicineCost = BigDecimal.ZERO;

    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal otherCharges = BigDecimal.ZERO;

    // Total (don’t trust user input blindly)
    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    // Status
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillStatus status = BillStatus.PENDING;

    // Payment method
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    // When payment received
    private LocalDateTime paidAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
//id Long @Id @GeneratedValue Primary key
//patient Patient @ManyToOne Who is being billed
//appointment Appointment @ManyToOne For which appointment
//consultationFee BigDecimal Doctor's fee (copied from Doctor entity)
//medicineCost BigDecimal Pharmacy cost
//otherCharges BigDecimal Lab tests, scan fees etc.
//totalAmount BigDecimal Sum of all above (can be computed)
//status Enum PENDING / PAID / CANCELLED
//paymentMethod Enum CASH / CARD / UPI / INSURANCE
//paidAt LocalDateTime When payment was received
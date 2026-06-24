package com.hospitalmanagement.hospitalmanagementsystem.entity;

import com.hospitalmanagement.hospitalmanagementsystem.enums.IntakeInstruction;
import com.hospitalmanagement.hospitalmanagementsystem.enums.MedicineFrequency;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prescription_id", nullable = false)
    @NotNull
    private Prescription prescription;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 150)
    private String medicineName;

    @NotBlank
    @Size(max = 50)
    private String dosage;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MedicineFrequency frequency;

    @NotNull
    @Min(1)
    @Max(365)
    private Integer durationDays;

    @NotNull
    @Enumerated
    private IntakeInstruction instructions;

    @Size(max = 500)
    private String additionalInstructions;
}
//id Long @Id @GeneratedValue Primary key
//prescription Prescription @ManyToOne Which prescription this belongs to
//medicineName String Drug name e.g. Paracetamol
//dosage String e.g. 500mg
//frequency String e.g. Twice daily / Three times a day
//durationDays Integer How many days to take
//instructions String Before food / After food / With wate
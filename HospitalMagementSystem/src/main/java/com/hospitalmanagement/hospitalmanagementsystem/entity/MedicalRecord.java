package com.hospitalmanagement.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "medical_records", indexes = {
        @Index(name = "idx_patient", columnList = "patient_id"),
        @Index(name = "idx_doctor", columnList = "doctor_id")
})
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @NotNull
    private LocalDateTime recordDate;

    @Column(nullable = false)
    @Size(min = 3, max = 500)
    private String title;

    @Column(columnDefinition = "TEXT")
    @Size(max = 2000)
    private String description;

    private String fileUrl;
}
//id Long @Id @GeneratedValue Primary key
//patient Patient @ManyToOne Which patient
//doctor Doctor @ManyToOne Which doctor uploaded/added this
//recordDate LocalDate Date of the record/test
//title String e.g. Blood Test Report, X-Ray
//description String
//@Column(columnDef=TEXT)
//Details of findings
//fileUrl String Path to uploaded PDF/image file
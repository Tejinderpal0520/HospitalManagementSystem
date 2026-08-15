package com.hospitalmanagement.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NotNull
    @JoinColumn(name = "appointment_id", unique = true, nullable = false)
    private Appointment appointment;

    @NotBlank
    @Size(min = 5, max = 2000)
    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    @Size(max = 2000)
    private String notes;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime issuedAt;

    @OneToMany(mappedBy = "prescription", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<PrescriptionItem> prescriptionItems = new HashSet<>();
}
//id Long @Id @GeneratedValue Primary key
//appointment Appointment @OneToOne Linked to one specific appointment
//diagnosis String
//@Column(columnDef=TEXT)
//Doctor's diagnosis text
//notes String
//@Column(columnDef=TEXT)
//Additional advice e.g. rest, diet
//issuedAt LocalDateTime
//@CreationTimestamp
//Auto timestamp
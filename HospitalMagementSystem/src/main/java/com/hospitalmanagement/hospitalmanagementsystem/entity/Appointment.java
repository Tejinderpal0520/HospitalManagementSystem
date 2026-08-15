package com.hospitalmanagement.hospitalmanagementsystem.entity;

import com.hospitalmanagement.hospitalmanagementsystem.enums.AppointmentStatus;
import com.hospitalmanagement.hospitalmanagementsystem.enums.AppointmentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"doctor_id", "appointment_date", "appointment_time"})
        }
)
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @FutureOrPresent
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime appointmentTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AppointmentType appointmentType;

    @NotBlank
    @Size(max = 500)
    private String reason;

    @Column(columnDefinition = "TEXT")
    @Size(max = 2000)
    private String symptoms;

    @Column(columnDefinition = "TEXT")
    private String doctorNotes;

    @Min(1)
    private Integer tokenNumber;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Prescription prescription;

    @OneToMany(mappedBy = "appointment")
    private Set<MedicalRecord> medicalRecords = new HashSet<>();

    @OneToMany(mappedBy = "appointment")
    private List<Bill> bills = new ArrayList<>();


}
//id Long @Id @GeneratedValue Primary key
//patient Patient @ManyToOne Which patient (FK: patient_id)
//doctor Doctor @ManyToOne Which doctor (FK: doctor_id)
//appointmentDate LocalDate Date of the appointment
//appointmentTime LocalTime Time slot e.g. 10:30
//status Enum SCHEDULED / COMPLETED / CANCELLED
//type Enum IN_PERSON / ONLINE
//symptoms String
//@Column(columnDef=TEXT)
//Patient's described symptoms at booking time
//doctorNotes String
//@Column(columnDef=TEXT)
//Doctor fills this after consultation
//tokenNumber Integer Queue token for that day
//createdAt LocalDateTime
//@CreationTimestamp
//When booking was made

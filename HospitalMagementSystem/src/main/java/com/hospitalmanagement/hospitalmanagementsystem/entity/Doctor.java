package com.hospitalmanagement.hospitalmanagementsystem.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    @NotNull
    private User user;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "department_id")
    private Department department; // Define manyToOne relationship with dept

    @NotBlank
    @Size(min = 2, max = 100)
    private String specialization;

    @NotBlank
    @Size(min = 2, max = 150)
    private String qualification;

    @Min(0)
    @Max(60)
    private int experienceYears;

    @DecimalMin(value = "1.0", inclusive = true)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal consultationFee;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @NotEmpty
    private List<DayOfWeek> availableDays;

    @NotNull
    private LocalTime availableFrom; // Shift start time

    @NotNull
    private LocalTime availableTo; // Shift end time

    @Size(max = 1000)
    @Column(columnDefinition = "TEXT")
    @Pattern(regexp = "^[a-zA-Z0-9 .,\\-()]*$")
    private String bio;

    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    private Set<MedicalRecord> medicalRecords = new HashSet<>();
}
//Field Type / JPA Notes
//id Long @Id @GeneratedValue Primary key
//user User @OneToOne
//@JoinColumn
//Links to User table
//department Department @ManyToOne Which department they belong to (FK: department_id)
//specialization String e.g. Cardiology, Orthopaedics
//qualification String MBBS, MD, MS etc.
//experienceYears Integer Years of experience
//consultationFee BigDecimal Per-visit fee charged
//availableDays String e.g. MON,TUE,WED,FRI
//availableFrom LocalTime Shift start time e.g. 09:00
//availableTo LocalTime Shift end time e.g. 17:00
//bio String
//@Column(columnDef=TEXT)
//Short profile description
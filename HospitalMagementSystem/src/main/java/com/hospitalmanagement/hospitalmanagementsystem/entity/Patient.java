package com.hospitalmanagement.hospitalmanagementsystem.entity;


import com.hospitalmanagement.hospitalmanagementsystem.enums.BloodGroupType;
import com.hospitalmanagement.hospitalmanagementsystem.enums.GenderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenderEnum gender;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private BloodGroupType bloodGroup;

    private String address;

    @Column(length = 10)
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String contactNumber;

    @Column(columnDefinition = "TEXT")
    private String medicalHistory;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    private List<Bill> bills = new ArrayList<>();
}
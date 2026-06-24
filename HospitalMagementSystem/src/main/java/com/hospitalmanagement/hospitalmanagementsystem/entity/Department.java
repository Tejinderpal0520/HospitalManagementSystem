package com.hospitalmanagement.hospitalmanagementsystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Size(max = 1000)
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Min(0)
    @Max(20) // realistic building range
    private Integer floorNumber;
    //Relations
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Doctor> doctors = new HashSet<>(); // Realtion with doctor
}
//id Long @Id @GeneratedValue Primary key
//name String @Column(unique=true) e.g. Cardiology
//description String What conditions this dept handles
//headDoctor Doctor @ManyToOne HOD of the department
//floorNumber Integer Physical floor location
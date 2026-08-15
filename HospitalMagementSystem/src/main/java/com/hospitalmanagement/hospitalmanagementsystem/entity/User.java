package com.hospitalmanagement.hospitalmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospitalmanagement.hospitalmanagementsystem.enums.UsersRoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user_table", indexes = {
        @Index(name = "idx_email", columnList = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z .'-]+$", message = "Invalid name format")
    @Size(min = 2, max = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    @Email
    @NotBlank
    private String email;

    @JsonIgnore
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$",
            message = "Password must contain uppercase, lowercase, digit, special character and be at least 8 characters long"
    )
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UsersRoleEnum role;

    @Column(length = 10)
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String contactNumber;

    private boolean isActive = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(updatable = false)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Patient patient;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Doctor doctor;
}

//id Long @Id @GeneratedValue Auto-incrementing primary key
//name String Full name
//email String @Column(unique=true) Used as username for login
//password String @JsonIgnore Stored as BCrypt hash, never plain text
//role Enum
//@Enumerated(STRING)
//ADMIN / DOCTOR / PATIENT / RECEPTIONIST
//phone String Contact number
//isActive Boolean default=true false = soft deleted (not removed from DB)
//createdAt LocalDateTime
//@CreationTimestamp
package com.hospitalmanagement.hospitalmanagementsystem.repository;

import com.hospitalmanagement.hospitalmanagementsystem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient getPatientById(Long patientId);

    Optional<Patient> findByUser_Email(String email);
}

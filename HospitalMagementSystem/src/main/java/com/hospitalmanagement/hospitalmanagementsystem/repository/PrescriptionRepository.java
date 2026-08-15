package com.hospitalmanagement.hospitalmanagementsystem.repository;

import com.hospitalmanagement.hospitalmanagementsystem.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Optional<Prescription> findByAppointmentId(Long appointmentId);
}

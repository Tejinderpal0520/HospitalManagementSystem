package com.hospitalmanagement.hospitalmanagementsystem.repository;

import com.hospitalmanagement.hospitalmanagementsystem.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT doctor FROM Doctor doctor WHERE doctor.department.id = :departmentId")
    List<Doctor> findByDepartmentId(Long departmentId);

    Optional<Doctor> findByUser_Email(String email);
}
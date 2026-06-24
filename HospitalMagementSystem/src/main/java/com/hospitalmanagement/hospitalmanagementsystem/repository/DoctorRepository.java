package com.hospitalmanagement.hospitalmanagementsystem.repository;

import com.hospitalmanagement.hospitalmanagementsystem.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT doctor FROM Doctor doctor where doctor.department = :department")
    Collection<Doctor> findByDepartment(Long department);
}
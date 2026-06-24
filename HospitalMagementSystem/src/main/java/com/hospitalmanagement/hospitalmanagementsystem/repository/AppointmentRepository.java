package com.hospitalmanagement.hospitalmanagementsystem.repository;

import com.hospitalmanagement.hospitalmanagementsystem.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
//    List<Appointment> findByDoctorId(Long id);
    @Query("SELECT appointment FROM Appointment appointment WHERE appointment.doctor.id = :doctorId")
    List<Appointment> findByDoctor(Long doctorId);

    @Query("SELECT appointment FROM Appointment appointment WHERE appointment.doctor.id = :doctorId AND appointment.appointmentDate = :appointmentDate")
    List<Appointment> findByDoctorAndAppointmentDate(Long doctorId, LocalDate appointmentDate);

}
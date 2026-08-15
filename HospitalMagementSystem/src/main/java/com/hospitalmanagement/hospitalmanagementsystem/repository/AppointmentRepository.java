package com.hospitalmanagement.hospitalmanagementsystem.repository;

import com.hospitalmanagement.hospitalmanagementsystem.entity.Appointment;
import com.hospitalmanagement.hospitalmanagementsystem.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT appointment FROM Appointment appointment WHERE appointment.doctor.id = :doctorId")
    List<Appointment> findByDoctor(Long doctorId);

    @Query("SELECT appointment FROM Appointment appointment WHERE appointment.doctor.id = :doctorId AND appointment.appointmentDate = :appointmentDate")
    List<Appointment> findByDoctorAndAppointmentDate(Long doctorId, LocalDate appointmentDate);

    @Query("SELECT appointment FROM Appointment appointment WHERE appointment.patient.id = :patientId")
    List<Appointment> findByPatient(Long patientId);

    boolean existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(
            Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime, AppointmentStatus status);

    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);

    long countByDoctorIdAndAppointmentDate(Long doctorId, LocalDate appointmentDate);
}

package com.hospitalmanagement.hospitalmanagementsystem.service;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.AppointmentRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.AppointmentUpdateRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.AppointmentResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Appointment;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Doctor;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Patient;
import com.hospitalmanagement.hospitalmanagementsystem.enums.AppointmentStatus;
import com.hospitalmanagement.hospitalmanagementsystem.exceptions.AppointmentConflictException;
import com.hospitalmanagement.hospitalmanagementsystem.exceptions.ResourceNotFoundException;
import com.hospitalmanagement.hospitalmanagementsystem.repository.AppointmentRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.DoctorRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.PatientRepository;
import com.hospitalmanagement.hospitalmanagementsystem.util.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final DtoMapper dtoMapper;

    @Transactional
    public AppointmentResponseDto bookAppointment(AppointmentRequestDto request) {
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        validateSlotAvailable(doctor.getId(), request.getAppointmentDate(), request.getAppointmentTime());

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setSymptoms(request.getSymptoms());
        appointment.setReason(request.getReason());
        appointment.setAppointmentType(request.getAppointmentType());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setTokenNumber(generateTokenNumber(doctor.getId(), request.getAppointmentDate()));

        Appointment saved = appointmentRepository.save(appointment);
        return dtoMapper.toAppointmentResponse(saved);
    }

    public AppointmentResponseDto getById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        return dtoMapper.toAppointmentResponse(appointment);
    }

    public List<AppointmentResponseDto> getAll() {
        return appointmentRepository.findAll().stream()
                .map(dtoMapper::toAppointmentResponse)
                .toList();
    }

    public List<AppointmentResponseDto> getTodayAppointments() {
        return appointmentRepository.findByAppointmentDate(LocalDate.now()).stream()
                .map(dtoMapper::toAppointmentResponse)
                .toList();
    }

    @Transactional
    public AppointmentResponseDto reschedule(Long id, AppointmentUpdateRequestDto request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if (appointment.getStatus() == AppointmentStatus.CANCELED) {
            throw new IllegalArgumentException("Cannot reschedule a cancelled appointment");
        }

        validateSlotAvailable(appointment.getDoctor().getId(), request.getAppointmentDate(),
                request.getAppointmentTime(), id);

        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        return dtoMapper.toAppointmentResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public AppointmentResponseDto cancel(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        appointment.setStatus(AppointmentStatus.CANCELED);
        return dtoMapper.toAppointmentResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public AppointmentResponseDto complete(Long id, String doctorNotes) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        appointment.setStatus(AppointmentStatus.COMPLETED);
        if (doctorNotes != null) {
            appointment.setDoctorNotes(doctorNotes);
        }
        return dtoMapper.toAppointmentResponse(appointmentRepository.save(appointment));
    }

    public List<Appointment> findByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctor(doctorId);
    }

    public List<Appointment> getBookedAppointments(Long doctorId, LocalDate date) {
        return appointmentRepository.findByDoctorAndAppointmentDate(doctorId, date);
    }

    private void validateSlotAvailable(Long doctorId, LocalDate date, LocalTime time) {
        validateSlotAvailable(doctorId, date, time, null);
    }

    private void validateSlotAvailable(Long doctorId, LocalDate date, LocalTime time, Long excludeId) {
        boolean conflict = appointmentRepository
                .existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(
                        doctorId, date, time, AppointmentStatus.CANCELED);
        if (conflict) {
            if (excludeId != null) {
                Appointment existing = appointmentRepository.findAll().stream()
                        .filter(a -> a.getDoctor().getId().equals(doctorId)
                                && a.getAppointmentDate().equals(date)
                                && a.getAppointmentTime().equals(time)
                                && a.getStatus() != AppointmentStatus.CANCELED
                                && !a.getId().equals(excludeId))
                        .findFirst()
                        .orElse(null);
                if (existing == null) {
                    return;
                }
            }
            throw new AppointmentConflictException("Slot already taken");
        }
    }

    private int generateTokenNumber(Long doctorId, LocalDate date) {
        return (int) appointmentRepository.countByDoctorIdAndAppointmentDate(doctorId, date) + 1;
    }
}

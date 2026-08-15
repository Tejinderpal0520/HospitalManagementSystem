package com.hospitalmanagement.hospitalmanagementsystem.service;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.MedicalRecordRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.MedicalRecordResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Appointment;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Doctor;
import com.hospitalmanagement.hospitalmanagementsystem.entity.MedicalRecord;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Patient;
import com.hospitalmanagement.hospitalmanagementsystem.exceptions.ResourceNotFoundException;
import com.hospitalmanagement.hospitalmanagementsystem.repository.AppointmentRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.DoctorRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.MedicalRecordRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.PatientRepository;
import com.hospitalmanagement.hospitalmanagementsystem.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final DtoMapper dtoMapper;

    public List<MedicalRecordResponseDto> getByPatient(Long patientId) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return medicalRecordRepository.findByPatientId(patientId).stream()
                .map(dtoMapper::toMedicalRecordResponse)
                .toList();
    }

    public MedicalRecordResponseDto getById(Long id) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record not found"));
        return dtoMapper.toMedicalRecordResponse(record);
    }

    public MedicalRecordResponseDto create(MedicalRecordRequestDto request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        MedicalRecord record = new MedicalRecord();
        record.setPatient(patient);
        record.setDoctor(doctor);
        record.setRecordDate(request.getRecordDate());
        record.setTitle(request.getTitle());
        record.setDescription(request.getDescription());
        record.setFileUrl(request.getFileUrl());

        if (request.getAppointmentId() != null) {
            Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
            record.setAppointment(appointment);
        }

        return dtoMapper.toMedicalRecordResponse(medicalRecordRepository.save(record));
    }

    public void delete(Long id) {
        if (!medicalRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medical record not found");
        }
        medicalRecordRepository.deleteById(id);
    }
}

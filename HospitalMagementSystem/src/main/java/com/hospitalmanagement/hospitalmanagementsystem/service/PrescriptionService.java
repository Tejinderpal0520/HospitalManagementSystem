package com.hospitalmanagement.hospitalmanagementsystem.service;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.PrescriptionItemRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.PrescriptionRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.PrescriptionResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Appointment;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Prescription;
import com.hospitalmanagement.hospitalmanagementsystem.entity.PrescriptionItem;
import com.hospitalmanagement.hospitalmanagementsystem.enums.AppointmentStatus;
import com.hospitalmanagement.hospitalmanagementsystem.exceptions.ResourceNotFoundException;
import com.hospitalmanagement.hospitalmanagementsystem.repository.AppointmentRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.PrescriptionRepository;
import com.hospitalmanagement.hospitalmanagementsystem.util.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;
    private final DtoMapper dtoMapper;

    @Transactional
    public PrescriptionResponseDto create(PrescriptionRequestDto request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new IllegalArgumentException("Prescription can only be created for completed appointments");
        }

        if (prescriptionRepository.findByAppointmentId(appointment.getId()).isPresent()) {
            throw new IllegalArgumentException("Prescription already exists for this appointment");
        }

        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment);
        prescription.setDiagnosis(request.getDiagnosis());
        prescription.setNotes(request.getNotes());

        for (PrescriptionItemRequestDto itemDto : request.getItems()) {
            PrescriptionItem item = mapItem(itemDto, prescription);
            prescription.getPrescriptionItems().add(item);
        }

        return dtoMapper.toPrescriptionResponse(prescriptionRepository.save(prescription));
    }

    public PrescriptionResponseDto getById(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found"));
        return dtoMapper.toPrescriptionResponse(prescription);
    }

    public PrescriptionResponseDto getByAppointment(Long appointmentId) {
        Prescription prescription = prescriptionRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found for appointment"));
        return dtoMapper.toPrescriptionResponse(prescription);
    }

    @Transactional
    public PrescriptionResponseDto update(Long id, PrescriptionRequestDto request) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found"));

        prescription.setDiagnosis(request.getDiagnosis());
        prescription.setNotes(request.getNotes());
        prescription.getPrescriptionItems().clear();

        for (PrescriptionItemRequestDto itemDto : request.getItems()) {
            prescription.getPrescriptionItems().add(mapItem(itemDto, prescription));
        }

        return dtoMapper.toPrescriptionResponse(prescriptionRepository.save(prescription));
    }

    private PrescriptionItem mapItem(PrescriptionItemRequestDto itemDto, Prescription prescription) {
        PrescriptionItem item = new PrescriptionItem();
        item.setPrescription(prescription);
        item.setMedicineName(itemDto.getMedicineName());
        item.setDosage(itemDto.getDosage());
        item.setFrequency(itemDto.getFrequency());
        item.setDurationDays(itemDto.getDurationDays());
        item.setInstructions(itemDto.getInstructions());
        item.setAdditionalInstructions(itemDto.getAdditionalInstructions());
        return item;
    }
}

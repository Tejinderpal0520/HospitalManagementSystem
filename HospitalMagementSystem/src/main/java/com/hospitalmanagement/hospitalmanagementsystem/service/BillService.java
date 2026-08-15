package com.hospitalmanagement.hospitalmanagementsystem.service;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.BillRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.request.PayBillRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.BillResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Appointment;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Bill;
import com.hospitalmanagement.hospitalmanagementsystem.enums.AppointmentStatus;
import com.hospitalmanagement.hospitalmanagementsystem.enums.BillStatus;
import com.hospitalmanagement.hospitalmanagementsystem.exceptions.ResourceNotFoundException;
import com.hospitalmanagement.hospitalmanagementsystem.repository.AppointmentRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.BillRepository;
import com.hospitalmanagement.hospitalmanagementsystem.util.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final AppointmentRepository appointmentRepository;
    private final DtoMapper dtoMapper;

    public List<BillResponseDto> getAll() {
        return billRepository.findAll().stream()
                .map(dtoMapper::toBillResponse)
                .toList();
    }

    public BillResponseDto getById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
        return dtoMapper.toBillResponse(bill);
    }

    public List<BillResponseDto> getByPatient(Long patientId) {
        return billRepository.findByPatient(patientId).stream()
                .map(dtoMapper::toBillResponse)
                .toList();
    }

    @Transactional
    public BillResponseDto createBill(BillRequestDto request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new IllegalArgumentException("Bill can only be created for completed appointments");
        }

        if (billRepository.findByAppointmentId(appointment.getId()).isPresent()) {
            throw new IllegalArgumentException("Bill already exists for this appointment");
        }

        BigDecimal consultationFee = appointment.getDoctor().getConsultationFee() != null
                ? appointment.getDoctor().getConsultationFee()
                : BigDecimal.ZERO;
        BigDecimal medicineCost = request.getMedicineCost() != null ? request.getMedicineCost() : BigDecimal.ZERO;
        BigDecimal otherCharges = request.getOtherCharges() != null ? request.getOtherCharges() : BigDecimal.ZERO;

        Bill bill = new Bill();
        bill.setPatient(appointment.getPatient());
        bill.setAppointment(appointment);
        bill.setConsultationFee(consultationFee);
        bill.setMedicineCost(medicineCost);
        bill.setOtherCharges(otherCharges);
        bill.setTotalAmount(consultationFee.add(medicineCost).add(otherCharges));
        bill.setStatus(BillStatus.PENDING);

        return dtoMapper.toBillResponse(billRepository.save(bill));
    }

    @Transactional
    public BillResponseDto payBill(Long id, PayBillRequestDto request) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found"));

        if (bill.getStatus() == BillStatus.PAID) {
            throw new IllegalArgumentException("Bill is already paid");
        }

        bill.setStatus(BillStatus.PAID);
        bill.setPaymentMethod(request.getPaymentMethod());
        bill.setPaidAt(LocalDateTime.now());

        return dtoMapper.toBillResponse(billRepository.save(bill));
    }
}

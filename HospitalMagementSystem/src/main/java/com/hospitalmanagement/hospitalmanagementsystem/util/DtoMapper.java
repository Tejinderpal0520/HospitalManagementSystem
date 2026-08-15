package com.hospitalmanagement.hospitalmanagementsystem.util;

import com.hospitalmanagement.hospitalmanagementsystem.dto.response.*;
import com.hospitalmanagement.hospitalmanagementsystem.entity.*;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public DoctorResponseDto toDoctorResponse(Doctor doctor) {
        DoctorResponseDto dto = new DoctorResponseDto();
        dto.setId(doctor.getId());
        if (doctor.getUser() != null) {
            dto.setName(doctor.getUser().getName());
            dto.setEmail(doctor.getUser().getEmail());
            dto.setRole(doctor.getUser().getRole());
            dto.setContactNumber(doctor.getUser().getContactNumber());
        }
        if (doctor.getDepartment() != null) {
            dto.setDepartmentId(doctor.getDepartment().getId());
            dto.setDepartmentName(doctor.getDepartment().getName());
        }
        dto.setSpecialization(doctor.getSpecialization());
        dto.setQualification(doctor.getQualification());
        dto.setExperienceYears(doctor.getExperienceYears());
        dto.setConsultationFee(doctor.getConsultationFee());
        dto.setAvailableDays(doctor.getAvailableDays());
        dto.setAvailableFrom(doctor.getAvailableFrom());
        dto.setAvailableTo(doctor.getAvailableTo());
        dto.setBio(doctor.getBio());
        return dto;
    }

    public PatientResponseDto toPatientResponse(Patient patient) {
        PatientResponseDto dto = new PatientResponseDto();
        dto.setId(patient.getId());
        if (patient.getUser() != null) {
            dto.setName(patient.getUser().getName());
            dto.setEmail(patient.getUser().getEmail());
            dto.setContactNumber(patient.getUser().getContactNumber());
        }
        dto.setGender(patient.getGender());
        dto.setBirthDate(patient.getBirthDate());
        dto.setBloodGroup(patient.getBloodGroup());
        dto.setAddress(patient.getAddress());
        dto.setMedicalHistory(patient.getMedicalHistory());
        return dto;
    }

    public AppointmentResponseDto toAppointmentResponse(Appointment appointment) {
        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setId(appointment.getId());
        if (appointment.getPatient() != null && appointment.getPatient().getUser() != null) {
            dto.setPatientId(appointment.getPatient().getId());
            dto.setPatientName(appointment.getPatient().getUser().getName());
        }
        if (appointment.getDoctor() != null) {
            dto.setDoctorId(appointment.getDoctor().getId());
            if (appointment.getDoctor().getUser() != null) {
                dto.setDoctorName(appointment.getDoctor().getUser().getName());
            }
            if (appointment.getDoctor().getDepartment() != null) {
                dto.setDepartmentName(appointment.getDoctor().getDepartment().getName());
            }
        }
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setStatus(appointment.getStatus());
        dto.setAppointmentType(appointment.getAppointmentType());
        dto.setReason(appointment.getReason());
        dto.setSymptoms(appointment.getSymptoms());
        dto.setDoctorNotes(appointment.getDoctorNotes());
        dto.setTokenNumber(appointment.getTokenNumber());
        dto.setBookedAt(appointment.getCreatedAt());
        return dto;
    }

    public BillResponseDto toBillResponse(Bill bill) {
        BillResponseDto dto = new BillResponseDto();
        dto.setId(bill.getId());
        if (bill.getPatient() != null) {
            dto.setPatientId(bill.getPatient().getId());
            if (bill.getPatient().getUser() != null) {
                dto.setPatientName(bill.getPatient().getUser().getName());
            }
        }
        if (bill.getAppointment() != null) {
            dto.setAppointmentId(bill.getAppointment().getId());
        }
        dto.setConsultationFee(bill.getConsultationFee());
        dto.setMedicineCost(bill.getMedicineCost());
        dto.setOtherCharges(bill.getOtherCharges());
        dto.setTotalAmount(bill.getTotalAmount());
        dto.setStatus(bill.getStatus());
        dto.setPaymentMethod(bill.getPaymentMethod());
        dto.setPaidAt(bill.getPaidAt());
        dto.setCreatedAt(bill.getCreatedAt());
        return dto;
    }

    public PrescriptionResponseDto toPrescriptionResponse(Prescription prescription) {
        PrescriptionResponseDto dto = new PrescriptionResponseDto();
        dto.setId(prescription.getId());
        if (prescription.getAppointment() != null) {
            dto.setAppointmentId(prescription.getAppointment().getId());
        }
        dto.setDiagnosis(prescription.getDiagnosis());
        dto.setNotes(prescription.getNotes());
        dto.setIssuedAt(prescription.getIssuedAt());
        if (prescription.getPrescriptionItems() != null) {
            dto.setItems(prescription.getPrescriptionItems().stream()
                    .map(this::toPrescriptionItemResponse)
                    .toList());
        }
        return dto;
    }

    public PrescriptionItemResponseDto toPrescriptionItemResponse(PrescriptionItem item) {
        PrescriptionItemResponseDto dto = new PrescriptionItemResponseDto();
        dto.setId(item.getId());
        dto.setMedicineName(item.getMedicineName());
        dto.setDosage(item.getDosage());
        dto.setFrequency(item.getFrequency());
        dto.setDurationDays(item.getDurationDays());
        dto.setInstructions(item.getInstructions());
        dto.setAdditionalInstructions(item.getAdditionalInstructions());
        return dto;
    }

    public MedicalRecordResponseDto toMedicalRecordResponse(MedicalRecord record) {
        MedicalRecordResponseDto dto = new MedicalRecordResponseDto();
        dto.setId(record.getId());
        if (record.getPatient() != null) {
            dto.setPatientId(record.getPatient().getId());
        }
        if (record.getDoctor() != null) {
            dto.setDoctorId(record.getDoctor().getId());
            if (record.getDoctor().getUser() != null) {
                dto.setDoctorName(record.getDoctor().getUser().getName());
            }
        }
        if (record.getAppointment() != null) {
            dto.setAppointmentId(record.getAppointment().getId());
        }
        dto.setRecordDate(record.getRecordDate());
        dto.setTitle(record.getTitle());
        dto.setDescription(record.getDescription());
        dto.setFileUrl(record.getFileUrl());
        return dto;
    }

    public DepartmentResponseDto toDepartmentResponse(Department department) {
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        dto.setFloorNumber(department.getFloorNumber());
        return dto;
    }

    public UserProfileResponseDto toUserProfile(User user, Long profileId) {
        UserProfileResponseDto dto = new UserProfileResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setContactNumber(user.getContactNumber());
        dto.setProfileId(profileId);
        return dto;
    }
}

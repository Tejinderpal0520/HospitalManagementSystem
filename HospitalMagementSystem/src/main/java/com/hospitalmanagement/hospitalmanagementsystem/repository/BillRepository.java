package com.hospitalmanagement.hospitalmanagementsystem.repository;

import com.hospitalmanagement.hospitalmanagementsystem.entity.Bill;
import com.hospitalmanagement.hospitalmanagementsystem.enums.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT bill FROM Bill bill WHERE bill.patient.id = :patientId")
    List<Bill> findByPatient(Long patientId);

    Optional<Bill> findByAppointmentId(Long appointmentId);

    @Query("SELECT COALESCE(SUM(b.totalAmount), 0) FROM Bill b WHERE b.status = :status")
    BigDecimal sumTotalAmountByStatus(BillStatus status);
}

package com.hospitalmanagement.hospitalmanagementsystem.service;

import com.hospitalmanagement.hospitalmanagementsystem.dto.response.SymptomResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Department;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Doctor;
import com.hospitalmanagement.hospitalmanagementsystem.repository.DepartmentRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SymptomCheckerService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    private static final Map<String, String> SYMPTOM_MAP = new LinkedHashMap<>();

    static {
        SYMPTOM_MAP.put("chest pain", "Cardiology");
        SYMPTOM_MAP.put("heart", "Cardiology");
        SYMPTOM_MAP.put("palpitation", "Cardiology");
        SYMPTOM_MAP.put("breathing", "General Medicine");
        SYMPTOM_MAP.put("cough", "General Medicine");
        SYMPTOM_MAP.put("asthma", "General Medicine");
        SYMPTOM_MAP.put("headache", "Neurology");
        SYMPTOM_MAP.put("seizure", "Neurology");
        SYMPTOM_MAP.put("migraine", "Neurology");
        SYMPTOM_MAP.put("bone", "Orthopedics");
        SYMPTOM_MAP.put("joint", "Orthopedics");
        SYMPTOM_MAP.put("fracture", "Orthopedics");
        SYMPTOM_MAP.put("stomach", "General Medicine");
        SYMPTOM_MAP.put("vomiting", "General Medicine");
        SYMPTOM_MAP.put("diarrhea", "General Medicine");
        SYMPTOM_MAP.put("skin", "Dermatology");
        SYMPTOM_MAP.put("rash", "Dermatology");
        SYMPTOM_MAP.put("acne", "Dermatology");
        SYMPTOM_MAP.put("eye", "General Medicine");
        SYMPTOM_MAP.put("vision", "General Medicine");
        SYMPTOM_MAP.put("fever", "General Medicine");
        SYMPTOM_MAP.put("cold", "General Medicine");
    }

    public SymptomResponseDto checkSymptoms(String symptomsText) {
        String lower = symptomsText.toLowerCase();
        String matchedDept = "General Medicine";

        for (Map.Entry<String, String> entry : SYMPTOM_MAP.entrySet()) {
            if (lower.contains(entry.getKey())) {
                matchedDept = entry.getValue();
                break;
            }
        }

        Department dept = departmentRepository.findByNameIgnoreCase(matchedDept).orElse(null);
        List<Doctor> doctors = dept != null
                ? doctorRepository.findByDepartmentId(dept.getId())
                : doctorRepository.findAll();

        SymptomResponseDto response = new SymptomResponseDto();
        response.setSuggestedDepartment(matchedDept);
        response.setMessage("Based on your symptoms, we recommend visiting " + matchedDept);
        response.setAvailableDoctors(doctors.stream()
                .filter(d -> d.getUser() != null)
                .map(d -> d.getUser().getName())
                .toList());
        return response;
    }

    public Map<String, String> getKeywords() {
        return SYMPTOM_MAP;
    }
}

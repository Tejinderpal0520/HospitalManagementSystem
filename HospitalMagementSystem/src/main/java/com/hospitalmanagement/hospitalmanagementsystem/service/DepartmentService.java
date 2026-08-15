package com.hospitalmanagement.hospitalmanagementsystem.service;

import com.hospitalmanagement.hospitalmanagementsystem.dto.request.DepartmentRequestDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.DepartmentResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.dto.response.DoctorResponseDto;
import com.hospitalmanagement.hospitalmanagementsystem.entity.Department;
import com.hospitalmanagement.hospitalmanagementsystem.exceptions.ResourceNotFoundException;
import com.hospitalmanagement.hospitalmanagementsystem.repository.DepartmentRepository;
import com.hospitalmanagement.hospitalmanagementsystem.repository.DoctorRepository;
import com.hospitalmanagement.hospitalmanagementsystem.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final DtoMapper dtoMapper;

    public List<DepartmentResponseDto> getAll() {
        return departmentRepository.findAll().stream()
                .map(dtoMapper::toDepartmentResponse)
                .toList();
    }

    public DepartmentResponseDto getById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return dtoMapper.toDepartmentResponse(department);
    }

    public List<DoctorResponseDto> getDoctorsByDepartment(Long id) {
        departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return doctorRepository.findByDepartmentId(id).stream()
                .map(dtoMapper::toDoctorResponse)
                .toList();
    }

    public DepartmentResponseDto create(DepartmentRequestDto request) {
        Department department = new Department();
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setFloorNumber(request.getFloorNumber());
        return dtoMapper.toDepartmentResponse(departmentRepository.save(department));
    }

    public DepartmentResponseDto update(Long id, DepartmentRequestDto request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setFloorNumber(request.getFloorNumber());
        return dtoMapper.toDepartmentResponse(departmentRepository.save(department));
    }

    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Department not found");
        }
        departmentRepository.deleteById(id);
    }
}

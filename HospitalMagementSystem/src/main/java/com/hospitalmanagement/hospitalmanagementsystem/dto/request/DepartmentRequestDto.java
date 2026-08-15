package com.hospitalmanagement.hospitalmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentRequestDto {
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Size(max = 1000)
    private String description;

    @NotNull
    private Integer floorNumber;
}

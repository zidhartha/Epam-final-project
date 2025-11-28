package com.epam.rd.autocode.assessment.appliances.Dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeDto {

    private long id;

    @NotBlank
    private String name;
    @NotBlank
    private String Department;
    private String email;
}

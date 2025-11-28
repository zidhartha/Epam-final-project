package com.epam.rd.autocode.assessment.appliances.Dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ManufacturerDto {
    @NotBlank
    private String name;
}

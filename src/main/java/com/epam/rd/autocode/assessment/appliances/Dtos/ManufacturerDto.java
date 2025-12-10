package com.epam.rd.autocode.assessment.appliances.Dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ManufacturerDto {
    private Long id;
    @NotBlank(message = "{manufacturer.name.is.mandatory}")
    private String name;
}

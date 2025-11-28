package com.epam.rd.autocode.assessment.appliances.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApplianceDto {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    private Long manufacturerId;


}

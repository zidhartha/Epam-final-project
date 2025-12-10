package com.epam.rd.autocode.assessment.appliances.Dtos;

import com.epam.rd.autocode.assessment.appliances.model.Category;
import com.epam.rd.autocode.assessment.appliances.model.Manufacturer;
import com.epam.rd.autocode.assessment.appliances.model.PowerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApplianceDto {

    private Long id;

    @NotBlank(message = "{appliance.name.is.mandatory}")
    private String name;

    @NotNull(message = "{appliance.price.is.mandatory}")
    private BigDecimal price;

    private String model;

    @NotNull(message = "{appliance.category.is.mandatory}")
    private Category category;

    @NotNull(message = "{appliance.powerType.is.mandatory}")
    private PowerType powerType;

    @NotNull(message = "{appliance.manufacturer.is.mandatory}")
    private Long manufacturerId;
    private Integer power;
    private String description;
    private String characteristic;
}

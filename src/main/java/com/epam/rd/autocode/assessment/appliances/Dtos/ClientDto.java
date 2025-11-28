package com.epam.rd.autocode.assessment.appliances.Dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientDto {

    private long id;

    @NotBlank
    private String name;
    @NotBlank
    private String card;
    private String email;

}

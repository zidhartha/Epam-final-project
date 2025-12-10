package com.epam.rd.autocode.assessment.appliances.Dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRowDto {
    private Long id;
    private Long applianceId;
    private String applianceName;
    private Long number;
    private BigDecimal amount;
}
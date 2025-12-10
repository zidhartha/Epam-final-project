package com.epam.rd.autocode.assessment.appliances.Dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class OrdersDto {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private Long clientId;
    private String clientName;
    private Set<OrderRowDto> orderRows;
    private BigDecimal amount;
    private boolean approved;
}

package com.epam.rd.autocode.assessment.appliances.Dtos;

import com.epam.rd.autocode.assessment.appliances.model.Client;
import com.epam.rd.autocode.assessment.appliances.model.Employee;
import com.epam.rd.autocode.assessment.appliances.model.OrderRow;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Set;

@Data
public class OrdersDto {
    private Employee employee;

    private Client client;

    private Set<OrderRow> orderRowSet;
}

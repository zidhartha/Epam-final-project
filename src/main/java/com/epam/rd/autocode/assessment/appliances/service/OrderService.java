package com.epam.rd.autocode.assessment.appliances.service;

import com.epam.rd.autocode.assessment.appliances.Dtos.ManufacturerDto;
import com.epam.rd.autocode.assessment.appliances.Dtos.OrdersDto;
import com.epam.rd.autocode.assessment.appliances.model.Manufacturer;
import com.epam.rd.autocode.assessment.appliances.model.Orders;

import java.util.List;

public interface OrderService {
    List<OrdersDto> getAll();
    OrdersDto getById(Long id);
    OrdersDto create(Orders orders);
    OrdersDto update(Long id,Orders order);
    void delete(Long id);
}

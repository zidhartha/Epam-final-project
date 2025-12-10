package com.epam.rd.autocode.assessment.appliances.service;

import com.epam.rd.autocode.assessment.appliances.Dtos.ManufacturerDto;
import com.epam.rd.autocode.assessment.appliances.Dtos.OrdersDto;
import com.epam.rd.autocode.assessment.appliances.model.Manufacturer;
import com.epam.rd.autocode.assessment.appliances.model.Orders;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    List<OrdersDto> getAll();
    OrdersDto getById(Long id);
    OrdersDto create(OrdersDto orders);
    OrdersDto update(Long id,OrdersDto order);
    void delete(Long id);
    void addRow(Long orderId, Long applianceId, Long quantity);
    void setApproved(Long id, boolean approved);
    List<OrdersDto> getClientOrders(String email);
    void removeRow(Long orderId,Long rowId);
}

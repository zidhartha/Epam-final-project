package com.epam.rd.autocode.assessment.appliances.service.impl;

import com.epam.rd.autocode.assessment.appliances.Dtos.ManufacturerDto;
import com.epam.rd.autocode.assessment.appliances.Dtos.OrdersDto;
import com.epam.rd.autocode.assessment.appliances.model.Manufacturer;
import com.epam.rd.autocode.assessment.appliances.model.Orders;
import com.epam.rd.autocode.assessment.appliances.repository.OrdersRepository;
import com.epam.rd.autocode.assessment.appliances.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<OrdersDto> getAll() {
        return ordersRepository.findAll().stream().
                map(x  -> modelMapper.map(x, OrdersDto.class)).toList();
    }

    @Override
    public OrdersDto getById(Long id) {
        return ordersRepository.findById(id).map(x -> modelMapper.map(x,OrdersDto.class)).
                orElseThrow(() -> new RuntimeException("No Employee found by this id"));
    }

    @Override
    public OrdersDto create(Orders order) {
        Orders orderCreated = ordersRepository.save(order);
        return modelMapper.map(orderCreated, OrdersDto.class);
    }

    @Override
    public OrdersDto update(Long id, Orders orders) {
        Orders orderToBeChanged = ordersRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Client not found"));

        orderToBeChanged.setApproved(orders.getApproved());
        orderToBeChanged.setClient(orders.getClient());
        orderToBeChanged.setEmployee(orders.getEmployee());
        orderToBeChanged.setOrderRowSet(orders.getOrderRowSet());

        return modelMapper.map(orderToBeChanged,OrdersDto.class);
    }

    @Override
    public void delete(Long id) {
        ordersRepository.deleteById(id);
    }
}

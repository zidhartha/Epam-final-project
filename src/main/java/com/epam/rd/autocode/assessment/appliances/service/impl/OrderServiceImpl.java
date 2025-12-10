package com.epam.rd.autocode.assessment.appliances.service.impl;

import com.epam.rd.autocode.assessment.appliances.Dtos.OrderRowDto;
import com.epam.rd.autocode.assessment.appliances.Dtos.OrdersDto;
import com.epam.rd.autocode.assessment.appliances.Exceptions.ApplianceNotFoundException;
import com.epam.rd.autocode.assessment.appliances.Exceptions.ClientNotFoundException;
import com.epam.rd.autocode.assessment.appliances.Exceptions.EmployeeNotFoundException;
import com.epam.rd.autocode.assessment.appliances.Exceptions.OrderNotFoundException;
import com.epam.rd.autocode.assessment.appliances.aspect.Loggable;
import com.epam.rd.autocode.assessment.appliances.model.*;
import com.epam.rd.autocode.assessment.appliances.repository.*;
import com.epam.rd.autocode.assessment.appliances.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final ApplianceRepository applianceRepository;
    private final OrderRowRepository orderRowRepository;

    @Loggable
    @Override
    public List<OrdersDto> getAll() {
        return ordersRepository.findAll()
                .stream()
                .map(order -> {
                    OrdersDto dto = modelMapper.map(order, OrdersDto.class);

                    dto.setOrderRows(
                            order.getOrderRowSet().stream()
                                    .map(r -> {
                                        OrderRowDto rowDto = modelMapper.map(r, OrderRowDto.class);
                                        rowDto.setApplianceName(r.getAppliance().getName());
                                        return rowDto;
                                    })
                                    .collect(Collectors.toSet())
                    );

                    return dto;
                })
                .toList();
    }


    @Loggable
    @Override
    public OrdersDto getById(Long id) {

        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrdersDto dto = modelMapper.map(order, OrdersDto.class);

        dto.setOrderRows(
                order.getOrderRowSet().stream()
                        .map(r -> {
                            OrderRowDto rowDto = modelMapper.map(r, OrderRowDto.class);
                            rowDto.setApplianceName(r.getAppliance().getName());
                            return rowDto;
                        })
                        .collect(Collectors.toSet())
        );

        return dto;
    }

    @Loggable
    @Override
    public OrdersDto create(OrdersDto dto) {

        Orders order = new Orders();
        order.setApproved(false);

        // get the entities involved
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(ClientNotFoundException::new);

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(EmployeeNotFoundException::new);

        order.setClient(client);
        order.setEmployee(employee);

        order.setOrderRowSet(Set.of());

        Orders saved = ordersRepository.save(order);

        return modelMapper.map(saved, OrdersDto.class);
    }

    @Loggable
    @Override
    public OrdersDto update(Long id, OrdersDto dto) {
        Orders existing = ordersRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);

        fillEntity(existing, dto);

        Orders saved = ordersRepository.save(existing);

        return modelMapper.map(saved, OrdersDto.class);
    }

    @Loggable
    @Override
    public void delete(Long id) {
        ordersRepository.deleteById(id);
    }


    private void fillEntity(Orders entity, OrdersDto dto) {

        entity.setApproved(dto.isApproved());

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(ClientNotFoundException::new);
        entity.setClient(client);

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(EmployeeNotFoundException::new);
        entity.setEmployee(employee);


        if (dto.getOrderRows() != null) {
            entity.setOrderRowSet(
                    dto.getOrderRows().stream()
                            .map(rowDto -> modelMapper.map(rowDto, com.epam.rd.autocode.assessment.appliances.model.OrderRow.class))
                            .collect(java.util.stream.Collectors.toSet())
            );
        }
    }


    @Loggable
    @Override
    public void addRow(Long orderId, Long applianceId, Long quantity) {

        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        Appliance appliance = applianceRepository.findById(applianceId)
                .orElseThrow(ApplianceNotFoundException::new);

        OrderRow row = new OrderRow();
        row.setAppliance(appliance);
        row.setNumber(quantity);

        BigDecimal subtotal = appliance.getPrice().multiply(BigDecimal.valueOf(quantity));
        row.setAmount(subtotal);

        // first save the row then add it to the order and then save the order itself
        orderRowRepository.save(row);

        order.getOrderRowSet().add(row);


        ordersRepository.save(order);
    }


    @Override
    public void setApproved(Long id, boolean approved) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);

        order.setApproved(approved);

        ordersRepository.save(order);
    }

    @Override
    public List<OrdersDto> getClientOrders(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        return ordersRepository.findByClientEmail(client.getEmail())
                .stream()
                .map(order -> {
                    OrdersDto dto = modelMapper.map(order, OrdersDto.class);

                    dto.setOrderRows(
                            order.getOrderRowSet().stream()
                                    .map(r -> {
                                        OrderRowDto rowDto = modelMapper.map(r, OrderRowDto.class);
                                        rowDto.setApplianceName(r.getAppliance().getName());
                                        return rowDto;
                                    })
                                    .collect(Collectors.toSet())
                    );


                    BigDecimal total_amount = order.getOrderRowSet().stream()
                            .map(OrderRow::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    dto.setAmount(total_amount);
                    return dto;
                })
                .toList();
    }

    @Loggable
    @Override
    public void removeRow(Long orderId, Long rowId) {

        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        OrderRow row = orderRowRepository.findById(rowId)
                .orElseThrow(() -> new RuntimeException("OrderRow not found"));


        order.getOrderRowSet().remove(row);


        ordersRepository.save(order);


        orderRowRepository.delete(row);
    }

}

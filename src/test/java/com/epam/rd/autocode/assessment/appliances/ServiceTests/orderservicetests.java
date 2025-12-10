package com.epam.rd.autocode.assessment.appliances.ServiceTests;

import com.epam.rd.autocode.assessment.appliances.Dtos.OrdersDto;
import com.epam.rd.autocode.assessment.appliances.model.*;
import com.epam.rd.autocode.assessment.appliances.repository.*;
import com.epam.rd.autocode.assessment.appliances.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class orderservicetests {

    @Mock
    private OrdersRepository ordersRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ApplianceRepository applianceRepository;
    @Mock
    private OrderRowRepository orderRowRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void testGetById_success() {
        Orders order = new Orders();
        order.setId(1L);
        order.setOrderRowSet(Set.of());

        OrdersDto dto = new OrdersDto();
        dto.setId(1L);

        when(ordersRepository.findById(1L)).thenReturn(Optional.of(order));
        when(modelMapper.map(order, OrdersDto.class)).thenReturn(dto);

        OrdersDto result = orderService.getById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testCreate_success() {
        OrdersDto dto = new OrdersDto();
        dto.setClientId(5L);
        dto.setEmployeeId(10L);

        Client client = new Client();
        Employee emp = new Employee();

        Orders saved = new Orders();
        saved.setId(100L);

        OrdersDto resultDto = new OrdersDto();
        resultDto.setId(100L);

        when(clientRepository.findById(5L)).thenReturn(Optional.of(client));
        when(employeeRepository.findById(10L)).thenReturn(Optional.of(emp));
        when(ordersRepository.save(any(Orders.class))).thenReturn(saved);
        when(modelMapper.map(saved, OrdersDto.class)).thenReturn(resultDto);

        OrdersDto result = orderService.create(dto);

        assertEquals(100L, result.getId());
    }

    @Test
    void testAddRow_success() {
        Orders order = new Orders();
        order.setOrderRowSet(new HashSet<>());

        Appliance app = new Appliance();
        app.setPrice(BigDecimal.valueOf(10));

        OrderRow savedRow = new OrderRow();

        when(ordersRepository.findById(1L)).thenReturn(Optional.of(order));
        when(applianceRepository.findById(2L)).thenReturn(Optional.of(app));
        when(orderRowRepository.save(any(OrderRow.class))).thenReturn(savedRow);

        orderService.addRow(1L, 2L, 3L);

        verify(orderRowRepository).save(any(OrderRow.class));
        verify(ordersRepository).save(order);
        assertEquals(1, order.getOrderRowSet().size());
    }
}
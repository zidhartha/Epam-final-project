package com.epam.rd.autocode.assessment.appliances.ServiceTests;

import com.epam.rd.autocode.assessment.appliances.Dtos.EmployeeDto;
import com.epam.rd.autocode.assessment.appliances.Exceptions.EmployeeNotFoundException;
import com.epam.rd.autocode.assessment.appliances.model.Employee;
import com.epam.rd.autocode.assessment.appliances.repository.EmployeeRepository;
import com.epam.rd.autocode.assessment.appliances.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
@Mock
private EmployeeRepository employeeRepository;
@Mock
private PasswordEncoder passwordEncoder;
@Mock
private ModelMapper modelMapper;

@InjectMocks
private EmployeeServiceImpl employeeService;

@Test
void testGetById_success() {
    Employee employee = new Employee();
    employee.setId(1L);

    EmployeeDto dto = new EmployeeDto();
    dto.setId(1L);

    when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
    when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(dto);

    EmployeeDto result = employeeService.getById(1L);

    assertEquals(1L, result.getId());
}

@Test
void testGetById_notFound() {
    when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(EmployeeNotFoundException.class, () -> employeeService.getById(1L));
}

@Test
void testCreate_success() {
    EmployeeDto dto = new EmployeeDto();
    Employee mapped = new Employee();
    Employee saved = new Employee();
    saved.setId(2L);

    EmployeeDto resultDto = new EmployeeDto();
    resultDto.setId(2L);

    when(modelMapper.map(dto, Employee.class)).thenReturn(mapped);
    when(employeeRepository.save(mapped)).thenReturn(saved);
    when(modelMapper.map(saved, EmployeeDto.class)).thenReturn(resultDto);

    EmployeeDto result = employeeService.create(dto);

    assertEquals(2L, result.getId());
}
}

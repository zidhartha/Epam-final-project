package com.epam.rd.autocode.assessment.appliances.service.impl;

import com.epam.rd.autocode.assessment.appliances.Dtos.ClientDto;
import com.epam.rd.autocode.assessment.appliances.Dtos.EmployeeDto;
import com.epam.rd.autocode.assessment.appliances.model.Client;
import com.epam.rd.autocode.assessment.appliances.model.Employee;
import com.epam.rd.autocode.assessment.appliances.repository.EmployeeRepository;
import com.epam.rd.autocode.assessment.appliances.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    private ModelMapper modelMapper;
    @Override
    public List<EmployeeDto> getAll() {
        return employeeRepository.findAll().stream().
                map(x  -> modelMapper.map(x, EmployeeDto.class)).toList();
    }

    @Override
    public EmployeeDto getById(Long id) {
        return employeeRepository.findById(id).map(x -> modelMapper.map(x,EmployeeDto.class)).
                orElseThrow(() -> new RuntimeException("No Employee found by this id"));
    }

    @Override
    public EmployeeDto create(Employee employee) {
        Employee employeeCreated = employeeRepository.save(employee);
        return modelMapper.map(employeeCreated, EmployeeDto.class);
    }

    @Override
    public EmployeeDto update(Long id, Employee employee) {
        Employee employeeToBeChanged = employeeRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Client not found"));
        employeeToBeChanged.setDepartment(employee.getDepartment());
        employeeToBeChanged.setEmail(employee.getEmail());
        employeeToBeChanged.setName(employee.getName());
        employeeToBeChanged.setPassword(employee.getPassword());

        return modelMapper.map(employeeToBeChanged,EmployeeDto.class);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}

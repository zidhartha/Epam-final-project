package com.epam.rd.autocode.assessment.appliances.service.impl;

import com.epam.rd.autocode.assessment.appliances.Dtos.EmployeeDto;
import com.epam.rd.autocode.assessment.appliances.Exceptions.EmployeeNotFoundException;
import com.epam.rd.autocode.assessment.appliances.aspect.Loggable;
import com.epam.rd.autocode.assessment.appliances.model.Employee;
import com.epam.rd.autocode.assessment.appliances.repository.EmployeeRepository;
import com.epam.rd.autocode.assessment.appliances.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Loggable
    @Override
    public List<EmployeeDto> getAll() {
        return employeeRepository.findAll()
                .stream()
                .map(e -> modelMapper.map(e, EmployeeDto.class))
                .toList();
    }
    @Loggable
    @Override
    public EmployeeDto getById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException( " " + id));

        return modelMapper.map(employee, EmployeeDto.class);
    }
    @Loggable
    @Override
    public EmployeeDto create(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        Employee saved = employeeRepository.save(employee);
        return modelMapper.map(saved, EmployeeDto.class);
    }
    @Loggable
    @Override
    public EmployeeDto update(Long id, EmployeeDto employeeDto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);

        existing.setName(employeeDto.getName());
        existing.setEmail(employeeDto.getEmail());
        existing.setDepartment(employeeDto.getDepartment());

        if (employeeDto.getPassword() != null && !employeeDto.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        }

        Employee updated = employeeRepository.save(existing);

        return modelMapper.map(updated, EmployeeDto.class);
    }

    @Loggable
    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
    public EmployeeDto getEditDtoById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);


        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getDepartment());

        return dto;
    }
}

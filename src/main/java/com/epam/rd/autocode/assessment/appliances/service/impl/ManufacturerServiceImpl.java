package com.epam.rd.autocode.assessment.appliances.service.impl;

import com.epam.rd.autocode.assessment.appliances.Dtos.EmployeeDto;
import com.epam.rd.autocode.assessment.appliances.Dtos.ManufacturerDto;
import com.epam.rd.autocode.assessment.appliances.model.Employee;
import com.epam.rd.autocode.assessment.appliances.model.Manufacturer;
import com.epam.rd.autocode.assessment.appliances.repository.ManufacturerRepository;
import com.epam.rd.autocode.assessment.appliances.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<ManufacturerDto> getAll() {
        return manufacturerRepository.findAll().stream().
                map(x  -> modelMapper.map(x, ManufacturerDto.class)).toList();
    }

    @Override
    public ManufacturerDto getById(Long id) {
        return manufacturerRepository.findById(id).map(x -> modelMapper.map(x,ManufacturerDto.class)).
                orElseThrow(() -> new RuntimeException("No Employee found by this id"));
    }

    @Override
    public ManufacturerDto create(Manufacturer manufacturer) {
        Manufacturer manufacturerCreated = manufacturerRepository.save(manufacturer);
        return modelMapper.map(manufacturerCreated, ManufacturerDto.class);
    }

    @Override
    public ManufacturerDto update(Long id, Manufacturer manufacturer) {
        Manufacturer manufacturerToBeChanged = manufacturerRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Client not found"));
        manufacturerToBeChanged.setName(manufacturer.getName());

        return modelMapper.map(manufacturerToBeChanged,ManufacturerDto.class);
    }

    @Override
    public void delete(Long id) {
        manufacturerRepository.deleteById(id);
    }
}

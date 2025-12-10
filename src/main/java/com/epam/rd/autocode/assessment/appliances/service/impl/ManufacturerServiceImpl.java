package com.epam.rd.autocode.assessment.appliances.service.impl;

import com.epam.rd.autocode.assessment.appliances.Dtos.ManufacturerDto;
import com.epam.rd.autocode.assessment.appliances.Exceptions.ManufacturerNotFoundException;
import com.epam.rd.autocode.assessment.appliances.aspect.Loggable;
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
    @Loggable
    @Override
    public List<ManufacturerDto> getAll() {
        return manufacturerRepository.findAll()
                .stream()
                .map(m -> modelMapper.map(m, ManufacturerDto.class))
                .toList();
    }

    @Loggable
    @Override
    public ManufacturerDto getById(Long id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(ManufacturerNotFoundException::new);

        return modelMapper.map(manufacturer, ManufacturerDto.class);
    }

    @Loggable
    @Override
    public ManufacturerDto create(ManufacturerDto dto) {
        Manufacturer manufacturer = modelMapper.map(dto, Manufacturer.class);
        Manufacturer saved = manufacturerRepository.save(manufacturer);
        return modelMapper.map(saved, ManufacturerDto.class);
    }

    @Loggable
    @Override
    public ManufacturerDto update(Long id, ManufacturerDto dto) {
        Manufacturer existing = manufacturerRepository.findById(id)
                .orElseThrow(ManufacturerNotFoundException::new);

        existing.setName(dto.getName());

        Manufacturer saved = manufacturerRepository.save(existing);
        return modelMapper.map(saved, ManufacturerDto.class);
    }

    @Loggable
    @Override
    public void delete(Long id) {
        manufacturerRepository.deleteById(id);
    }
}

package com.epam.rd.autocode.assessment.appliances.service.impl;

import com.epam.rd.autocode.assessment.appliances.Dtos.ApplianceDto;

import com.epam.rd.autocode.assessment.appliances.model.Appliance;
import com.epam.rd.autocode.assessment.appliances.repository.ApplianceRepository;
import com.epam.rd.autocode.assessment.appliances.service.ApplianceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ApplianceServiceImpl implements ApplianceService {
    private final ApplianceRepository applianceRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<ApplianceDto> getAll() {
        return applianceRepository.findAll().stream().map(x -> modelMapper.map(x,ApplianceDto.class)).toList();
    }

    @Override
    public ApplianceDto get(Long id) {
       return applianceRepository.findById(id).map(x -> modelMapper.map(x, ApplianceDto.class)).
               orElseThrow(() -> new RuntimeException("Appliance not found"));
    }

    @Override
    public ApplianceDto create(Appliance appliance) {
        Appliance newappliance = applianceRepository.save(appliance);
        return modelMapper.map(newappliance, ApplianceDto.class);
    }

    @Override
    public ApplianceDto update(Long id, Appliance appliance) {
        Appliance applianceToBeChanged = applianceRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        applianceToBeChanged.setName(appliance.getName());
        applianceToBeChanged.setCategory(appliance.getCategory());
        applianceToBeChanged.setDescription(appliance.getDescription());
        applianceToBeChanged.setManufacturer(appliance.getManufacturer());
        applianceToBeChanged.setModel(appliance.getModel());
        applianceToBeChanged.setPower(appliance.getPower());
        applianceToBeChanged.setPowerType(appliance.getPowerType());
        applianceToBeChanged.setPrice(appliance.getPrice());
        applianceToBeChanged.setCharacteristic(appliance.getCharacteristic());
        return modelMapper.map(applianceToBeChanged, ApplianceDto.class);
    }

    @Override
    public void delete(Long id) {
        applianceRepository.deleteById(id);

    }
}

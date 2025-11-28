package com.epam.rd.autocode.assessment.appliances.service;

import com.epam.rd.autocode.assessment.appliances.Dtos.ManufacturerDto;
import com.epam.rd.autocode.assessment.appliances.model.Manufacturer;

import java.util.List;

public interface ManufacturerService {
    List<ManufacturerDto> getAll();
    ManufacturerDto getById(Long id);
    ManufacturerDto create(Manufacturer manufacturer);
    ManufacturerDto update(Long id,Manufacturer manufacturer);
    void delete(Long id);
}

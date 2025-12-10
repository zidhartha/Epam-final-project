package com.epam.rd.autocode.assessment.appliances.service.impl;

import com.epam.rd.autocode.assessment.appliances.Dtos.ApplianceDto;

import com.epam.rd.autocode.assessment.appliances.Exceptions.ApplianceNotFoundException;
import com.epam.rd.autocode.assessment.appliances.Exceptions.ManufacturerNotFoundException;
import com.epam.rd.autocode.assessment.appliances.aspect.Loggable;
import com.epam.rd.autocode.assessment.appliances.model.Appliance;
import com.epam.rd.autocode.assessment.appliances.model.OrderRow;
import com.epam.rd.autocode.assessment.appliances.repository.ApplianceRepository;
import com.epam.rd.autocode.assessment.appliances.repository.ManufacturerRepository;
import com.epam.rd.autocode.assessment.appliances.repository.OrderRowRepository;
import com.epam.rd.autocode.assessment.appliances.repository.OrdersRepository;
import com.epam.rd.autocode.assessment.appliances.service.ApplianceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplianceServiceImpl implements ApplianceService {

    private final ApplianceRepository applianceRepository;
    private final ModelMapper modelMapper;
    private final ManufacturerRepository manufacturerRepository;
    private final OrderRowRepository orderRowRepository;
    private final OrdersRepository ordersRepository;
    @Override
    @Loggable
    public List<ApplianceDto> getAll() {
        return applianceRepository.findAll()
                .stream()
                .map(a -> modelMapper.map(a, ApplianceDto.class))
                .toList();
    }

    @Override
    public ApplianceDto get(Long id) {
        Appliance appliance = applianceRepository.findById(id)
                .orElseThrow(() -> new ApplianceNotFoundException(" " + id));
        return modelMapper.map(appliance, ApplianceDto.class);
    }

    @Loggable
    @Override
    public ApplianceDto create(ApplianceDto dto) {

        Appliance appliance = modelMapper.map(dto, Appliance.class);


        appliance.setManufacturer(
                manufacturerRepository.findById(dto.getManufacturerId())
                        .orElseThrow(ManufacturerNotFoundException::new)
        );

        Appliance saved = applianceRepository.save(appliance);
        return modelMapper.map(saved, ApplianceDto.class);
    }

    @Loggable
    @Override
    public ApplianceDto update(Long id, ApplianceDto dto) {

        Appliance appliance = applianceRepository.findById(id)
                .orElseThrow(() -> new ApplianceNotFoundException(" " + id));

        appliance.setName(dto.getName());
        appliance.setCategory(dto.getCategory());
        appliance.setDescription(dto.getDescription());
        appliance.setModel(dto.getModel());
        appliance.setPower(dto.getPower());
        appliance.setPowerType(dto.getPowerType());
        appliance.setCharacteristic(dto.getCharacteristic());
        appliance.setPrice(dto.getPrice());

        appliance.setManufacturer(
                manufacturerRepository.findById(dto.getManufacturerId())
                        .orElseThrow(ManufacturerNotFoundException::new)
        );

        Appliance updated = applianceRepository.save(appliance);
        return modelMapper.map(updated, ApplianceDto.class);
    }

    @Override
    @Loggable
    @Transactional
    public void delete(Long applianceId) {


        List<OrderRow> rows = orderRowRepository.findByAppliance_Id(applianceId);

        for (OrderRow row : rows) {
            ordersRepository.removeOrderRowFromOrders(row.getId());
        }


        orderRowRepository.deleteByAppliance_Id(applianceId);

        applianceRepository.deleteById(applianceId);
    }

}
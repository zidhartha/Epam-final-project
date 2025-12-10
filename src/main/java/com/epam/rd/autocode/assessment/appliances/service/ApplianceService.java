package com.epam.rd.autocode.assessment.appliances.service;

import com.epam.rd.autocode.assessment.appliances.Dtos.ApplianceDto;
import com.epam.rd.autocode.assessment.appliances.model.Appliance;
import com.epam.rd.autocode.assessment.appliances.repository.ApplianceRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ApplianceService {

    List<ApplianceDto> getAll();
    ApplianceDto get(Long id);
    ApplianceDto create(ApplianceDto applianceDto);
    ApplianceDto update(Long id,ApplianceDto applianceDto);
    void delete(Long id);


}

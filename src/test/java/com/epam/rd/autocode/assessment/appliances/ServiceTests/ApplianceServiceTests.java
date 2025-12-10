package com.epam.rd.autocode.assessment.appliances.ServiceTests;

import com.epam.rd.autocode.assessment.appliances.Dtos.ApplianceDto;
import com.epam.rd.autocode.assessment.appliances.Exceptions.ApplianceNotFoundException;
import com.epam.rd.autocode.assessment.appliances.model.Appliance;
import com.epam.rd.autocode.assessment.appliances.model.Manufacturer;
import com.epam.rd.autocode.assessment.appliances.repository.ApplianceRepository;
import com.epam.rd.autocode.assessment.appliances.repository.ManufacturerRepository;
import com.epam.rd.autocode.assessment.appliances.service.impl.ApplianceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ApplianceServiceTests {

    @Mock
    private ApplianceRepository applianceRepository;

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ApplianceServiceImpl applianceService;

    @Test
    void testGet_success(){
        Appliance appliance = new Appliance();
        appliance.setId(1L);

        ApplianceDto applianceDto = new ApplianceDto();
        applianceDto.setId(1L);

        when(applianceRepository.findById(1L)).thenReturn(Optional.of(appliance));
        when(modelMapper.map(appliance,ApplianceDto.class)).thenReturn(applianceDto);

        ApplianceDto result = applianceService.get(1L);

        assertEquals(1L,result.getId());

        verify(applianceRepository).findById(1L);

    }
    @Test
    void testGet_notFound() {
        when(applianceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApplianceNotFoundException.class, () -> applianceService.get(1L));
    }


    @Test
    void testCreate_Success(){
        ApplianceDto dto = new ApplianceDto();
        dto.setManufacturerId(10L);

        Manufacturer m = new Manufacturer();
        m.setId(10L);

        Appliance mapped = new Appliance();
        mapped.setId(5L);

        Appliance saved = new Appliance();
        saved.setId(5L);

        ApplianceDto resultDto = new ApplianceDto();
        resultDto.setId(5L);

        when(modelMapper.map(dto, Appliance.class)).thenReturn(mapped);
        when(manufacturerRepository.findById(10L)).thenReturn(Optional.of(m));
        when(applianceRepository.save(mapped)).thenReturn(saved);
        when(modelMapper.map(saved, ApplianceDto.class)).thenReturn(resultDto);

        ApplianceDto result = applianceService.create(dto);

        assertEquals(5L, result.getId());
        verify(applianceRepository).save(mapped);
    }

}

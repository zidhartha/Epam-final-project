package com.epam.rd.autocode.assessment.appliances.ServiceTests;

import com.epam.rd.autocode.assessment.appliances.Dtos.ManufacturerDto;
import com.epam.rd.autocode.assessment.appliances.Exceptions.ManufacturerNotFoundException;
import com.epam.rd.autocode.assessment.appliances.model.Manufacturer;
import com.epam.rd.autocode.assessment.appliances.repository.ManufacturerRepository;
import com.epam.rd.autocode.assessment.appliances.service.impl.ManufacturerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class ManufacturerServiceTests {
    @Mock
    private ManufacturerRepository manufacturerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ManufacturerServiceImpl manufacturerService;

    @Test
    void testGetById_success() {
        Manufacturer m = new Manufacturer();
        m.setId(1L);

        ManufacturerDto dto = new ManufacturerDto();
        dto.setId(1L);

        when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(m));
        when(modelMapper.map(m, ManufacturerDto.class)).thenReturn(dto);

        ManufacturerDto result = manufacturerService.getById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testGetById_notFound() {
        when(manufacturerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ManufacturerNotFoundException.class, () -> manufacturerService.getById(1L));
    }

    @Test
    void testCreate_success() {
        ManufacturerDto dto = new ManufacturerDto();
        Manufacturer mapped = new Manufacturer();
        Manufacturer saved = new Manufacturer();
        saved.setId(20L);

        ManufacturerDto resultDto = new ManufacturerDto();
        resultDto.setId(20L);

        when(modelMapper.map(dto, Manufacturer.class)).thenReturn(mapped);
        when(manufacturerRepository.save(mapped)).thenReturn(saved);
        when(modelMapper.map(saved, ManufacturerDto.class)).thenReturn(resultDto);

        ManufacturerDto result = manufacturerService.create(dto);

        assertEquals(20L, result.getId());
    }
}

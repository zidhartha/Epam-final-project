package com.epam.rd.autocode.assessment.appliances.ServiceTests;

import com.epam.rd.autocode.assessment.appliances.Dtos.ClientDto;
import com.epam.rd.autocode.assessment.appliances.Exceptions.ClientNotFoundException;
import com.epam.rd.autocode.assessment.appliances.model.Client;
import com.epam.rd.autocode.assessment.appliances.repository.ClientRepository;
import com.epam.rd.autocode.assessment.appliances.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private ClientServiceImpl clientService;


    @Test
    void test_get_by_id_success(){
        Client client = new Client();
        client.setId(1L);

        ClientDto dto = new ClientDto();
        dto.setId(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(modelMapper.map(client, ClientDto.class)).thenReturn(dto);

        ClientDto result = clientService.getById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testGetById_notFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () -> clientService.getById(1L));
    }

    @Test
    void testCreate_success() {
        ClientDto dto = new ClientDto();
        dto.setName("A");

        Client mapped = new Client();
        mapped.setName("A");

        Client saved = new Client();
        saved.setId(10L);

        ClientDto resultDto = new ClientDto();
        resultDto.setId(10L);

        when(modelMapper.map(dto, Client.class)).thenReturn(mapped);
        when(clientRepository.save(mapped)).thenReturn(saved);
        when(modelMapper.map(saved, ClientDto.class)).thenReturn(resultDto);

        ClientDto result = clientService.create(dto);

        assertEquals(10L, result.getId());
        verify(clientRepository).save(mapped);
    }
}


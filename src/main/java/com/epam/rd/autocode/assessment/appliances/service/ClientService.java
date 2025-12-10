package com.epam.rd.autocode.assessment.appliances.service;

import com.epam.rd.autocode.assessment.appliances.Dtos.ClientDto;
import com.epam.rd.autocode.assessment.appliances.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClientService {
    ClientDto findByEmail(String email);
    List<ClientDto> getAll();
    ClientDto getById(Long id);
    ClientDto create(ClientDto clientDto);
    ClientDto update(Long id,ClientDto clientDto);
    void delete(Long id);

}

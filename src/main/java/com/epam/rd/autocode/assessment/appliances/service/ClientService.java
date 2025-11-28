package com.epam.rd.autocode.assessment.appliances.service;

import com.epam.rd.autocode.assessment.appliances.Dtos.ClientDto;
import com.epam.rd.autocode.assessment.appliances.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClientService {

    List<ClientDto> getAll();
    ClientDto getById(Long id);
    ClientDto create(Client client);
    ClientDto update(Long id,Client client);
    void delete(Long id);
}

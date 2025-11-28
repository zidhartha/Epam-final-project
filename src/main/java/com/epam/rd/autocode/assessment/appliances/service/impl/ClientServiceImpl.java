package com.epam.rd.autocode.assessment.appliances.service.impl;

import com.epam.rd.autocode.assessment.appliances.Dtos.ClientDto;
import com.epam.rd.autocode.assessment.appliances.model.Client;
import com.epam.rd.autocode.assessment.appliances.repository.ClientRepository;
import com.epam.rd.autocode.assessment.appliances.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ClientDto> getAll() {
        return clientRepository.findAll().stream().
                map(x  -> modelMapper.map(x,ClientDto.class)).toList();
    }

    @Override
    public ClientDto getById(Long id) {
        return clientRepository.findById(id).map(x -> modelMapper.map(x,ClientDto.class)).
                orElseThrow(() -> new RuntimeException("No client found by this id"));
    }

    @Override
    public ClientDto create(Client client) {
        Client clientsaved = clientRepository.save(client);
        return modelMapper.map(clientsaved, ClientDto.class);
    }

    @Override
    public ClientDto update(Long id, Client client) {
        Client clientToBeChanged = clientRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Client not found"));
        clientToBeChanged.setCard(client.getCard());
        clientToBeChanged.setEmail(client.getEmail());
        clientToBeChanged.setName(client.getName());
        clientToBeChanged.setPassword(client.getPassword());

        return modelMapper.map(clientToBeChanged,ClientDto.class);
    }



    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}

package com.epam.rd.autocode.assessment.appliances.service.impl;

import com.epam.rd.autocode.assessment.appliances.Dtos.ClientDto;
import com.epam.rd.autocode.assessment.appliances.Exceptions.ClientNotFoundException;
import com.epam.rd.autocode.assessment.appliances.aspect.Loggable;
import com.epam.rd.autocode.assessment.appliances.model.Client;
import com.epam.rd.autocode.assessment.appliances.repository.ClientRepository;
import com.epam.rd.autocode.assessment.appliances.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Loggable
    @Override
    public List<ClientDto> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(client -> modelMapper.map(client, ClientDto.class))
                .toList();
    }

    @Loggable
    @Override
    public ClientDto getById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);

        return modelMapper.map(client, ClientDto.class);
    }

    @Override
    public ClientDto create(ClientDto clientDto) {

        Client client = modelMapper.map(clientDto, Client.class);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        Client saved = clientRepository.save(client);
        return modelMapper.map(saved, ClientDto.class);
    }

    @Loggable
    @Override
    public ClientDto update(Long id, ClientDto clientDto) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);

        existing.setName(clientDto.getName());
        existing.setEmail(clientDto.getEmail());
        existing.setCard(clientDto.getCard());

        // only if password is new then we update
        if (clientDto.getPassword() != null && !clientDto.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(clientDto.getPassword()));
        }

        Client saved = clientRepository.save(existing);

        return modelMapper.map(saved, ClientDto.class);
    }

    @Override
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException();
        }

        clientRepository.deleteById(id);
    }

    public ClientDto findByEmail(String email) {
       Optional<Client> client = clientRepository.findByEmail(email);
       if(client.isPresent()){
           Client realClient = client.get();
           return modelMapper.map(realClient,ClientDto.class);
       }
       throw new ClientNotFoundException();
    }
}

package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.ClientAddRequest;
import com.dartech.azalymanagementserver.controller.Request.ClientUpdateRequest;
import com.dartech.azalymanagementserver.dto.ClientDto;
import com.dartech.azalymanagementserver.entity.Client;
import com.dartech.azalymanagementserver.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    ClientRepository clientRepository;
    ModelMapper modelMapper;

    @Autowired
    ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ClientDto save(ClientAddRequest clientAddRequest) {
        Client client = modelMapper.map(clientAddRequest, Client.class);
        client.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        client = clientRepository.save(client);
        ClientDto clientDto = modelMapper.map(client, ClientDto.class);
        return clientDto;
    }

    @Override
    public ClientDto update(ClientUpdateRequest clientUpdateRequest) {
        Client client = modelMapper.map(clientUpdateRequest, Client.class);
        System.out.println(client);
        client.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        client.setLastModifiedDate(LocalDateTime.now().toString());
        client = clientRepository.save(client);
        ClientDto clientDto = modelMapper.map(client, ClientDto.class);
        return clientDto;
    }

    public int Addition(int number1, int number2) {
        return number1 + number2;
    }

    @Override
    public List<ClientDto> getAll() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDto> clientDtos = new ArrayList<>();
        for(Client client : clients) {
            clientDtos.add(modelMapper.map(client, ClientDto.class));
        };
        return clientDtos;
    }

    @Override
    public void delete(String id) {
        clientRepository.deleteById(id);
    }
}

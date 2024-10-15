package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.ClientAddRequest;
import com.dartech.azalymanagementserver.controller.Request.ClientUpdateRequest;
import com.dartech.azalymanagementserver.dto.ClientDto;
import com.dartech.azalymanagementserver.entity.Client;

import java.util.List;

public interface ClientService {

    ClientDto save(ClientAddRequest clientAddRequest);

    ClientDto update(ClientUpdateRequest clientUpdateRequest);

    List<ClientDto> getAll();

    void delete(String id);
}

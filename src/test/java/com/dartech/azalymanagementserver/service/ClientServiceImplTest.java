package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.ClientAddRequest;
import com.dartech.azalymanagementserver.dto.ClientDto;
import com.dartech.azalymanagementserver.entity.Client;
import com.dartech.azalymanagementserver.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void ClientServiceImpl_Save_ReturnClientDto() {
        ClientAddRequest clientAddRequest = ClientAddRequest.builder()
                .firstName("Haroun")
                .lastName("Darjaj")
                .build();
        Client client = new Client();
        client.setFirstName("haroun");
        client.setLastName("darjaj");

        ClientDto client1 = new ClientDto();
        client1.setFirstName("haroun");
        client1.setLastName("darjaj");

        System.out.println(clientAddRequest);
        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);
        when(modelMapper.map(Mockito.any(ClientAddRequest.class), Mockito.eq(Client.class))).thenReturn(client);
        when(modelMapper.map(Mockito.any(Client.class), Mockito.eq(ClientDto.class))).thenReturn(client1);
        // Mockito.doCallRealMethod().when(Mockito.any(Client.class)).setCreatedBy(Mockito.any(String.class));

        ClientDto clientDto = clientService.save(clientAddRequest);

        assertNotNull(clientDto);
        assertEquals(client.getFirstName(), clientDto.getFirstName());
    }
}
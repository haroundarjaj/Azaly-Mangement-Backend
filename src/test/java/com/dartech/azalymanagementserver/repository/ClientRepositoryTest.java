package com.dartech.azalymanagementserver.repository;

import com.dartech.azalymanagementserver.AzalyManagementServerApplication;
import com.dartech.azalymanagementserver.entity.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;

    @Test
    public void ClientRepository_Save_ReturnClient() {

        /* Client client = Client.builder()
                .firstName("haroun")
                .lastName("darjaj")
                .email("darjaj.haroun@gmail.com")
                .build(); */

        Client client = new Client();
        client.setFirstName("haroun");
        client.setLastName("darjaj");
        client.setEmail("darjaj.haroun@gmail.com");
        client.setIsIndividual("true");
        System.out.println("client");
        System.out.println(client);
        System.out.println(client.getFirstName());

        Client client1 = clientRepository.save(client);

        // mongoTemplate.save(client, "Client");

        //assertTrue(mongoTemplate.findAll(Client.class, "Client").size() == 1);
        System.out.println(client1);
        assertNotNull(client1);
        assertNotNull(client1.getId());
        assertTrue(client1.getFirstName().equals("haroun"));

    }

    @Test
    void ClientRepository_findAll() {
        Client client = new Client();
        client.setFirstName("haroun");
        client.setLastName("darjaj");
        client.setEmail("darjaj.haroun@gmail.com");
        client.setIsIndividual("true");

        clientRepository.save(client);
        List<Client> clients = clientRepository.findAll();
        System.out.println(clients.size());
        assertTrue(clients.size() == 1);
    }

}
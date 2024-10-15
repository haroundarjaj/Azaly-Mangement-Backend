package com.dartech.azalymanagementserver.controller.API;

import com.dartech.azalymanagementserver.controller.Request.ClientAddRequest;
import com.dartech.azalymanagementserver.controller.Request.ClientUpdateRequest;
import com.dartech.azalymanagementserver.dto.ClientDto;
import com.dartech.azalymanagementserver.entity.Client;
import com.dartech.azalymanagementserver.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/client")
@CrossOrigin("*")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid ClientAddRequest clientAddRequest) {

        ClientDto client = clientService.save(clientAddRequest);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity upate(@RequestBody @Valid ClientUpdateRequest clientUpdateRequest) {

        ClientDto client = clientService.update(clientUpdateRequest);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {

        List<ClientDto> clients = clientService.getAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {

        clientService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}

package com.example.demo.controllers;

import com.example.demo.entity.ClienteEntity;
import com.example.demo.requests.ClienteCreateRequest;
import com.example.demo.requests.ClienteUpdateRequest;
import com.example.demo.responses.ClienteResponse;
import com.example.demo.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping({"/api/clientes"})
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @GetMapping()
    public ArrayList<ClienteEntity> getClientes() {
        return clienteService.getClientes();
    }

    @PostMapping()
    public ClienteResponse saveCliente(@RequestBody ClienteCreateRequest clienteCreateRequest) {
        return clienteService.saveCliente(clienteCreateRequest);
    }

    @PutMapping()
    public ClienteResponse updateCliente(@RequestBody ClienteUpdateRequest clienteUpdateRequest) {
        return clienteService.updateCliente(clienteUpdateRequest);
    }

    @DeleteMapping(path = "/{id}")
    public ClienteResponse deleteCliente(@PathVariable("id") Long id) {
        return clienteService.deleteCliente(id);
    }
}

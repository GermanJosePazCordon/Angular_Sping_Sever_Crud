package com.example.demo.services;

import com.example.demo.entity.ChequeraEntity;
import com.example.demo.entity.ClienteEntity;
import com.example.demo.entity.CuentaEntity;
import com.example.demo.models.ChequeraModel;
import com.example.demo.models.CuentaModel;
import com.example.demo.repositories.ChequeraRepository;
import com.example.demo.repositories.CuentaRepository;
import com.example.demo.requests.ClienteCreateRequest;
import com.example.demo.requests.ClienteUpdateRequest;
import com.example.demo.responses.ClienteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repositories.ClienteRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    CuentaRepository cuentaRepository;
    @Autowired
    ChequeraRepository chequeraRepository;
    @Autowired
    CuentaService cuentaService;

    public ClienteService(ClienteRepository clienteRepository, CuentaRepository cuentaRepository, ChequeraRepository chequeraRepository, CuentaService cuentaService) {
        this.clienteRepository = clienteRepository;
        this.cuentaRepository = cuentaRepository;
        this.chequeraRepository = chequeraRepository;
        this.cuentaService = cuentaService;
    }

    public ArrayList<ClienteEntity> getClientes() {
        return (ArrayList<ClienteEntity>) clienteRepository.findAll();
    }

    public ClienteResponse saveCliente(ClienteCreateRequest clienteCreateRequest) {
        ClienteResponse response = null;
        ClienteEntity newClient = null;

        //Validando datos

        boolean flag = false;
        if(clienteCreateRequest.getDpi() instanceof Long)
        {
            flag = true;
        }

        if(!flag)
        {
            response = new ClienteResponse();
            response.setMsg("DPI debe ser numerico");
            return response;
        }

        if(clienteCreateRequest.getDpi().toString().length() != 13)
        {
            response = new ClienteResponse();
            response.setMsg("DPI debe tener 13 digitos");
            return response;
        }

        if(clienteCreateRequest.getDpi() < 0)
        {
            response = new ClienteResponse();
            response.setMsg("DPI debe ser positivo");
            return response;
        }

        if(clienteCreateRequest.getGenero() != 'M' && clienteCreateRequest.getGenero() != 'F' && clienteCreateRequest.getGenero() != 'O')
        {
            response = new ClienteResponse();
            response.setMsg("Genero debe ser M, F u O");
            return response;
        }

        Optional<ClienteEntity> currentClient = clienteRepository.findById(clienteCreateRequest.getDpi());
        if(currentClient.isPresent())
        {
            response = new ClienteResponse();
            response.setMsg("Cliente ya existe");
            return response;
        }

        newClient = new ClienteEntity();
        newClient.setDpi(clienteCreateRequest.getDpi());
        newClient.setNombre(clienteCreateRequest.getNombre());
        newClient.setTelefono(clienteCreateRequest.getTelefono());
        newClient.setDireccion(clienteCreateRequest.getDireccion());
        newClient.setGenero(clienteCreateRequest.getGenero());
        newClient.setEstado(clienteCreateRequest.getEstado());

        response = new ClienteResponse();
        response.setCliente(clienteRepository.save(newClient));
        response.setMsg("Cliente creado exitosamente");
        return response;
    }

    public ClienteResponse updateCliente(ClienteUpdateRequest clienteUpdateRequest) {
        ClienteResponse response = null;
        ClienteEntity newClient = null;

        //Validando datos
        boolean flag = false;
        if(clienteUpdateRequest.getDpi() instanceof Long)
        {
            flag = true;
        }

        if(!flag)
        {
            response = new ClienteResponse();
            response.setMsg("DPI debe ser numerico");
            return response;
        }

        if(clienteUpdateRequest.getDpi().toString().length() != 13)
        {
            response = new ClienteResponse();
            response.setMsg("DPI debe tener 13 digitos");
            return response;
        }

        if(clienteUpdateRequest.getDpi() < 0)
        {
            response = new ClienteResponse();
            response.setMsg("DPI debe ser positivo");
            return response;
        }

        if(clienteUpdateRequest.getGenero() != 'M' && clienteUpdateRequest.getGenero() != 'F' && clienteUpdateRequest.getGenero() != 'O')
        {
            response = new ClienteResponse();
            response.setMsg("Genero debe ser M, F u O");
            return response;
        }

        Optional<ClienteEntity> currentClient = clienteRepository.findById(clienteUpdateRequest.getDpi());
        if(currentClient.isPresent())
        {
            newClient = currentClient.get();
            newClient.setNombre(clienteUpdateRequest.getNombre());
            newClient.setTelefono(clienteUpdateRequest.getTelefono());
            newClient.setDireccion(clienteUpdateRequest.getDireccion());
            newClient.setGenero(clienteUpdateRequest.getGenero());

            response = new ClienteResponse();
            response.setMsg("Cliente actualizado exitosamente");
            response.setCliente(clienteRepository.save(newClient));
            return response;
        }
        else
        {
            response = new ClienteResponse();
            response.setMsg("Cliente no existe");
            return response;
        }
    }

    public ClienteResponse deleteCliente(Long id) {
        ClienteResponse response = null;
        ClienteEntity deleteCliente = null;
        ArrayList<CuentaModel> cuentas = null;

        Optional<ClienteEntity> currentCliente = clienteRepository.findById(id);
        if(currentCliente.isEmpty()){
            response = new ClienteResponse();
            response.setMsg("Cliente no existe");
            return response;
        }
        //Cancelando clientes
        deleteCliente = currentCliente.get();
        deleteCliente.setEstado('C');
        clienteRepository.save(deleteCliente);

        //Cancelando cuentas
        cuentas = clienteRepository.findCuentaByIdClienteIs(id);
        for (CuentaModel cuenta: cuentas) {
            cuentaService.deleteCuenta(cuenta.getId_cuenta());
        }
        response = new ClienteResponse();
        response.setMsg("Cliente eliminado exitosamente");
        return response;
    }
}

package com.example.demo.services;

import com.example.demo.entity.ChequeraEntity;
import com.example.demo.entity.ClienteEntity;
import com.example.demo.entity.CuentaEntity;
import com.example.demo.models.ChequeraModel;
import com.example.demo.models.ClienteModel;
import com.example.demo.models.CuentaModel;
import com.example.demo.repositories.ChequeraRepository;
import com.example.demo.repositories.ClienteRepository;
import com.example.demo.repositories.CuentaRepository;
import com.example.demo.requests.CuentaCreateRequest;
import com.example.demo.requests.CuentaUpdateRequest;
import com.example.demo.responses.ClienteResponse;
import com.example.demo.responses.CuentaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {
    @Autowired
    CuentaRepository cuentaRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ChequeraRepository chequeraRepository;
    @Autowired
    ChequeraService chequeraService;

    public CuentaService(CuentaRepository cuentaRepository, ClienteRepository clienteRepository, ChequeraRepository chequeraRepository, ChequeraService chequeraService) {
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
        this.chequeraRepository = chequeraRepository;
        this.chequeraService = chequeraService;
    }

    public ArrayList<CuentaEntity> getCuentas(){
        return  (ArrayList<CuentaEntity>) cuentaRepository.findAll();
    }

    public CuentaResponse saveCuenta(CuentaCreateRequest cuentaCreateRequest) throws ParseException {
        CuentaResponse response = null;
        CuentaEntity newCuenta = null;

        //Validando datos
        if(cuentaCreateRequest.getSaldo() < 0){
            response = new CuentaResponse();
            response.setMsg("El saldo no puede ser negativo");
            return response;
        }

        if(cuentaCreateRequest.getId_cliente().toString().length() != 13)
        {
            response = new CuentaResponse();
            response.setMsg("DPI debe tener 13 digitos");
            return response;
        }

        if(cuentaCreateRequest.getId_cliente() < 0){
            response = new CuentaResponse();
            response.setMsg("El id del cliente no puede ser negativo");
            return response;
        }

        Optional<ClienteEntity> currentCliente = clienteRepository.findById(cuentaCreateRequest.getId_cliente());
        if(currentCliente.isEmpty()){
            response = new CuentaResponse();
            response.setMsg("El cliente no existe");
            return response;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        newCuenta = new CuentaEntity();
        newCuenta.setFecha_creacion(formatter.parse(cuentaCreateRequest.getFecha_creacion()));
        newCuenta.setEstado(cuentaCreateRequest.getEstado());
        newCuenta.setSaldo(cuentaCreateRequest.getSaldo());
        newCuenta.setId_cliente(cuentaCreateRequest.getId_cliente());
        newCuenta.setTipo_cuenta(cuentaCreateRequest.getTipo_cuenta());

        response = new CuentaResponse();
        response.setCuenta(cuentaRepository.save(newCuenta));
        response.setMsg("Cuenta creada con exito");
        return response;
    }

    public CuentaResponse updateCuenta(CuentaUpdateRequest cuentaUpdateRequest) throws ParseException {
        CuentaResponse response;
        CuentaEntity updateCuenta = null;

        Optional<CuentaEntity> currenCuenta = cuentaRepository.findById(cuentaUpdateRequest.getId_cuenta());
        if(currenCuenta.isPresent()){
            updateCuenta = currenCuenta.get();
            updateCuenta.setSaldo(cuentaUpdateRequest.getSaldo());

            response = new CuentaResponse();
            response.setCuenta(cuentaRepository.save(updateCuenta));
            response.setMsg("Cuenta actualizada con exito");
            return response;
        }
        return null;
    }

    public CuentaResponse deleteCuenta(int id_cuenta){
        CuentaResponse response = null;
        CuentaEntity deleteCuenta = null;
        ArrayList<ChequeraModel> chequeras = null;

        Optional<CuentaEntity> currentCuenta = cuentaRepository.findById(id_cuenta);
        if(currentCuenta.isEmpty()){
            response = new CuentaResponse();
            response.setMsg("Cuenta no encontrada");
            return response;
        }
        deleteCuenta = currentCuenta.get();
        deleteCuenta.setEstado('C');
        cuentaRepository.save(deleteCuenta);

        //Cancelando chequeras
        chequeras = cuentaRepository.findChequeraByIdCuentaIs(id_cuenta);
        for (ChequeraModel chequera: chequeras) {
            chequeraService.deleteChequera(chequera.getId_chequera());
        }
        response = new CuentaResponse();
        response.setMsg("Cuenta eliminada con exito");
        return response;
    }

    public ArrayList<CuentaModel> findCuentaByIdClienteIs(Long dpi){
        return cuentaRepository.findCuentaByIdClienteIs(dpi);
    }
}

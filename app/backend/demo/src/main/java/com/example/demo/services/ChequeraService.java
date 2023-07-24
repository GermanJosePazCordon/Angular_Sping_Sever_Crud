package com.example.demo.services;

import com.example.demo.entity.ChequeraEntity;
import com.example.demo.entity.CuentaEntity;
import com.example.demo.models.ChequeModel;
import com.example.demo.models.ChequeraModel;
import com.example.demo.models.CuentaModel;
import com.example.demo.repositories.ChequeraRepository;
import com.example.demo.repositories.CuentaRepository;
import com.example.demo.requests.ChequeraCreateRequest;
import com.example.demo.requests.ChequeraUpdateRequest;
import com.example.demo.responses.ChequeResponse;
import com.example.demo.responses.ChequeraResponse;
import com.example.demo.responses.CuentaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ChequeraService {
    @Autowired
    ChequeraRepository chequeraRepository;
    @Autowired
    CuentaRepository cuentaRepository;
    @Autowired
    ChequeService chequeService;

    public ChequeraService(ChequeraRepository chequeraRepository, CuentaRepository cuentaRepository) {
        this.chequeraRepository = chequeraRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public ArrayList<ChequeraEntity> getChequeras(){
        return (ArrayList<ChequeraEntity>) chequeraRepository.findAll();
    }

    public ChequeraResponse saveChequera(ChequeraCreateRequest chequeraCreateRequest) throws ParseException {
        ChequeraResponse response = null;
        ChequeraEntity newChequera = null;

        if(chequeraCreateRequest.getCantidad() <= 0){
            response = new ChequeraResponse();
            response.setMsg("Cantidad de cheques debe ser mayor a 0");
            return response;
        }

        if(chequeraCreateRequest.getId_cuenta() <= 0){
            response = new ChequeraResponse();
            response.setMsg("Id de cuenta debe ser mayor a 0");
            return response;
        }

        Optional<CuentaEntity> currentCuenta = cuentaRepository.findById(chequeraCreateRequest.getId_cuenta());
        if(currentCuenta.isEmpty()){
            response = new ChequeraResponse();
            response.setMsg("Cuenta no encontrada");
            return response;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        newChequera = new ChequeraEntity();
        newChequera.setFecha_emision(formatter.parse(chequeraCreateRequest.getFecha_emision()));
        newChequera.setCantidad(chequeraCreateRequest.getCantidad());
        newChequera.setId_cuenta(chequeraCreateRequest.getId_cuenta());
        newChequera.setEstado(chequeraCreateRequest.getEstado());

        response = new ChequeraResponse();
        response.setChequera(chequeraRepository.save(newChequera));
        response.setMsg("Chequera creada correctamente");
        return response;
    }

    public ChequeraResponse updateChequera(ChequeraUpdateRequest chequeraUpdateRequest) throws ParseException {
        ChequeraResponse response = null;
        ChequeraEntity newChequera = null;

        Optional<ChequeraEntity> currentChequera = chequeraRepository.findById(chequeraUpdateRequest.getId_chequera());
        if(currentChequera.isEmpty()){
            response = new ChequeraResponse();
            response.setMsg("Chequera no encontrada");
            return response;
        }

        newChequera = currentChequera.get();
        newChequera.setCantidad(chequeraUpdateRequest.getCantidad());

        response = new ChequeraResponse();
        response.setChequera(chequeraRepository.save(newChequera));
        response.setMsg("Chequera actualizada correctamente");
        return response;
    }

    public ChequeraResponse deleteChequera(int id_chequera){
        ChequeraResponse response = null;
        ChequeraEntity deleteChequera = null;
        ArrayList<ChequeModel> cheques = null;

        Optional<ChequeraEntity> currentChequera = chequeraRepository.findById(id_chequera);
        if(currentChequera.isEmpty()){
            response = new ChequeraResponse();
            response.setMsg("Chequera no encontrada");
            return response;
        }
        deleteChequera = currentChequera.get();
        deleteChequera.setEstado('C');
        chequeraRepository.save(deleteChequera);

        //Cancelando cheques
        cheques = chequeraRepository.findChequesByIdChequera(id_chequera);
        for(ChequeModel cheque : cheques){
            chequeService.deleteCheque(cheque.getId_cheque());
        }
        response = new ChequeraResponse();
        response.setMsg("Chequera eliminada correctamente");
        return response;
    }

}

package com.example.demo.services;

import com.example.demo.entity.ChequeEntity;
import com.example.demo.entity.ChequeraEntity;
import com.example.demo.entity.ClienteEntity;
import com.example.demo.entity.CuentaEntity;
import com.example.demo.models.CuentaModel;
import com.example.demo.repositories.ChequeRepository;
import com.example.demo.repositories.ChequeraRepository;
import com.example.demo.repositories.CuentaRepository;
import com.example.demo.requests.ChequeCreateRequest;
import com.example.demo.requests.ChequeUpdateRequest;
import com.example.demo.responses.ChequeResponse;
import com.example.demo.responses.ChequeraResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class ChequeService {
    @Autowired
    ChequeRepository chequeRepository;
    @Autowired
    ChequeraRepository chequeraRepository;
    @Autowired
    CuentaRepository cuentaRepository;

    public ChequeService(ChequeRepository chequeRepository, ChequeraRepository chequeraRepository, CuentaRepository cuentaRepository) {
        this.chequeRepository = chequeRepository;
        this.chequeraRepository = chequeraRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public ArrayList<ChequeEntity> getCheques(){
        return  (ArrayList<ChequeEntity>) chequeRepository.findAll();
    }

    public ChequeResponse saveCheque(ChequeCreateRequest chequeCreateRequest) throws ParseException {
        ChequeResponse response = null;
        ChequeEntity newCheque = null;

        if(chequeCreateRequest.getId_chequera() <= 0){
            response = new ChequeResponse();
            response.setMsg("Id de chequera debe ser mayor a 0");
            return response;
        }

        if(chequeCreateRequest.getMonto() <= 0){
            response = new ChequeResponse();
            response.setMsg("Monto debe ser mayor a 0");
            return response;
        }

        Optional<ChequeraEntity> currentChequera = chequeraRepository.findById(chequeCreateRequest.getId_chequera());
        if(currentChequera.isEmpty()){
            response = new ChequeResponse();
            response.setMsg("La chequera no existe");
            return response;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        int cantidadCheques = chequeRepository.countChequesByIdChequera(chequeCreateRequest.getId_chequera());
        if(cantidadCheques >= currentChequera.get().getCantidad()){
            response = new ChequeResponse();
            response.setMsg("La chequera ya no tiene cheques disponibles");
            return response;
        }

        newCheque = new ChequeEntity();
        newCheque.setFecha_emision(formatter.parse(chequeCreateRequest.getFecha_emision()));
        newCheque.setFecha_recibido(formatter.parse(chequeCreateRequest.getFecha_recibido()));
        newCheque.setMonto(chequeCreateRequest.getMonto());
        newCheque.setLugar(chequeCreateRequest.getLugar());
        newCheque.setBeneficiario(chequeCreateRequest.getBeneficiario());
        newCheque.setEstado(chequeCreateRequest.getEstado());
        newCheque.setId_chequera(chequeCreateRequest.getId_chequera());

        response = new ChequeResponse();
        response.setCheque(chequeRepository.save(newCheque));
        response.setMsg("Cheque creado correctamente");
        return response;
    }

    public ChequeResponse updateCheque(ChequeUpdateRequest chequeUpdateRequest) throws ParseException {
        ChequeResponse response = null;
        ChequeEntity updateCheque = null;

        if(chequeUpdateRequest.getMonto() <= 0){
            response = new ChequeResponse();
            response.setMsg("Monto debe ser mayor a 0");
            return response;
        }

        Optional<ChequeEntity> currentCheque = chequeRepository.findById(chequeUpdateRequest.getId_cheque());
        if(currentCheque.isEmpty()){
            response = new ChequeResponse();
            response.setMsg("Cheque no encontrado");
            return response;
        }
        updateCheque = currentCheque.get();
        updateCheque.setMonto(chequeUpdateRequest.getMonto());
        updateCheque.setLugar(chequeUpdateRequest.getLugar());
        updateCheque.setBeneficiario(chequeUpdateRequest.getBeneficiario());

        response = new ChequeResponse();
        response.setCheque(chequeRepository.save(updateCheque));
        response.setMsg("Cheque actualizado correctamente");
        return response;
    }

    public ChequeResponse deleteCheque(int id_cheque){
        ChequeResponse response = null;
        ChequeEntity deleteCheque = null;

        Optional<ChequeEntity> currentCheque = chequeRepository.findById(id_cheque);
        if(currentCheque.isEmpty()){
            response = new ChequeResponse();
            response.setMsg("Cheque no encontrado");
            return response;
        }
        deleteCheque = currentCheque.get();
        if(deleteCheque.getEstado() != 'U'){
            deleteCheque.setEstado('C');
        }
        chequeRepository.save(deleteCheque);

        response = new ChequeResponse();
        response.setMsg("Cheque eliminado correctamente");
        return response;
    }

    public ChequeResponse usarCheque(int id_cheque) throws ParseException {
        ChequeResponse response = null;
        CuentaModel currentCuenta = null;
        ChequeEntity updateCheque = null;
        CuentaEntity updateCuenta = null;

        Optional<ChequeEntity> currentCheque = chequeRepository.findById(id_cheque);
        if(currentCheque.isEmpty()){
            response = new ChequeResponse();
            response.setMsg("Cheque no encontrado");
            return response;
        }
        currentCuenta = chequeRepository.findCuentaByIdChequeIs(id_cheque);
        if(currentCuenta == null){
            response = new ChequeResponse();
            response.setMsg("Cuenta no encontrada");
            return response;
        }
        if(currentCuenta.getSaldo() - currentCheque.get().getMonto() < 0){
            response = new ChequeResponse();
            response.setMsg("Saldo insuficiente");
            return response;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(new Date());

        updateCheque = currentCheque.get();
        updateCheque.setFecha_recibido(formatter.parse(date));
        updateCheque.setEstado('U');
        chequeRepository.save(updateCheque);

        updateCuenta = new CuentaEntity();
        updateCuenta.setId_cuenta(currentCuenta.getId_cuenta());
        updateCuenta.setFecha_creacion(currentCuenta.getFecha_creacion());
        updateCuenta.setEstado(currentCuenta.getEstado());
        updateCuenta.setSaldo(currentCuenta.getSaldo() - currentCheque.get().getMonto());
        updateCuenta.setTipo_cuenta(currentCuenta.getTipo_cuenta());
        updateCuenta.setId_cliente(currentCuenta.getId_cliente());
        cuentaRepository.save(updateCuenta);

        response = new ChequeResponse();
        response.setMsg("Cheque usado correctamente");
        response.setCheque(updateCheque);
        return response;
    }
}

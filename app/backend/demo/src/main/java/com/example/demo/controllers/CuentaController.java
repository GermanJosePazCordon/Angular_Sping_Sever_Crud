package com.example.demo.controllers;

import com.example.demo.entity.CuentaEntity;
import com.example.demo.models.CuentaModel;
import com.example.demo.requests.CuentaCreateRequest;
import com.example.demo.requests.CuentaUpdateRequest;
import com.example.demo.responses.CuentaResponse;
import com.example.demo.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;

@RestController
@RequestMapping({"/api/cuentas"})
public class CuentaController {
    @Autowired
    CuentaService cuentaService;

    @GetMapping()
    public ArrayList<CuentaEntity> getCuentas(){
        return cuentaService.getCuentas();
    }
    @PostMapping()
    public CuentaResponse saveCuenta(@RequestBody CuentaCreateRequest cuentaCreateRequest) throws ParseException {
        return cuentaService.saveCuenta(cuentaCreateRequest);
    }

    @PutMapping()
    public CuentaResponse updateCuenta(@RequestBody CuentaUpdateRequest cuentaUpdateRequest) throws ParseException {
        return cuentaService.updateCuenta(cuentaUpdateRequest);
    }

    @DeleteMapping(path = "/{id}")
    public CuentaResponse deleteCuenta(@PathVariable("id") int id){
        return cuentaService.deleteCuenta(id);
    }

    @GetMapping(path = "/{id}")
    public ArrayList<CuentaModel> getCuenta(@PathVariable("id") Long id){
        return cuentaService.findCuentaByIdClienteIs(id);
    }
}

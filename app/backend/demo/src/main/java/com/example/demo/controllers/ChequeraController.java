package com.example.demo.controllers;

import com.example.demo.entity.ChequeraEntity;
import com.example.demo.requests.ChequeraCreateRequest;
import com.example.demo.requests.ChequeraUpdateRequest;
import com.example.demo.responses.ChequeraResponse;
import com.example.demo.services.ChequeraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;

@RestController
@RequestMapping({"/api/chequeras"})
public class ChequeraController {
    @Autowired
    ChequeraService chequeraService;

    @GetMapping()
    public ArrayList<ChequeraEntity> getChequeras(){
        return (ArrayList<ChequeraEntity>) chequeraService.getChequeras();
    }

    @PostMapping()
    public ChequeraResponse saveChequera(@RequestBody ChequeraCreateRequest chequeraCreateRequest) throws ParseException {
        return chequeraService.saveChequera(chequeraCreateRequest);
    }

    @PutMapping()
    public ChequeraResponse updateChequera(@RequestBody ChequeraUpdateRequest chequeraUpdateRequest) throws ParseException {
        return chequeraService.updateChequera(chequeraUpdateRequest);
    }

    @DeleteMapping(path = "/{id}")
    public ChequeraResponse deleteChequera(@PathVariable("id") int id){
        return chequeraService.deleteChequera(id);
    }
}

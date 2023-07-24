package com.example.demo.controllers;

import com.example.demo.entity.ChequeEntity;
import com.example.demo.requests.ChequeCreateRequest;
import com.example.demo.requests.ChequeUpdateRequest;
import com.example.demo.responses.ChequeResponse;
import com.example.demo.services.ChequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;

@RestController
@RequestMapping({"/api/cheques"})
public class ChequeController {
    @Autowired
    ChequeService chequeService;

    @GetMapping()
    public ArrayList<ChequeEntity> getCheques(){
        return (ArrayList<ChequeEntity>) chequeService.getCheques();
    }

    @PostMapping()
    public ChequeResponse saveCheque(@RequestBody ChequeCreateRequest chequeCreateRequest) throws ParseException {
        return chequeService.saveCheque(chequeCreateRequest);
    }

    @PutMapping()
    public ChequeResponse updateCheque(@RequestBody ChequeUpdateRequest chequeUpdateRequest) throws ParseException {
        return chequeService.updateCheque(chequeUpdateRequest);
    }

    @DeleteMapping(path = "/{id}")
    public ChequeResponse deleteCheque(@PathVariable("id") int id){
        return chequeService.deleteCheque(id);
    }

    @GetMapping(path = "/usar/{id}")
    public ChequeResponse usarCheque(@PathVariable("id") int id) throws ParseException {
        return chequeService.usarCheque(id);
    }
}

package com.example.demo.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ChequeraUpdateRequest {
    private int id_chequera;
    private String fecha_emision;
    private int cantidad;
    private char estado;
    private int id_cuenta;
}

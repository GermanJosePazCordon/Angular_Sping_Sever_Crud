package com.example.demo.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ChequeUpdateRequest {
    private int id_cheque;
    private String fecha_emision;
    private String fecha_recibido;
    private float monto;
    private String lugar;
    private String beneficiario;
    private char estado;
    private int id_chequera;
}

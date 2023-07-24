package com.example.demo.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CuentaUpdateRequest {
    private int id_cuenta;
    private String fecha_creacion;
    private char estado;
    private float saldo;
    private char tipo_cuenta;
    private Long id_cliente;
}

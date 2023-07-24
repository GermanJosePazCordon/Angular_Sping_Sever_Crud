package com.example.demo.requests;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class ClienteCreateRequest {
    private Long dpi;
    private String nombre;
    private String telefono;
    private String direccion;
    private char genero;
    private char estado;
}

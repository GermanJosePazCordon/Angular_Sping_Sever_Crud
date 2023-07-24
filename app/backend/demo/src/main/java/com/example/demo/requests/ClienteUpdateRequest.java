package com.example.demo.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteUpdateRequest {
    private Long dpi;
    private String nombre;
    private String telefono;
    private String direccion;
    private char genero;
    private char estado;
}

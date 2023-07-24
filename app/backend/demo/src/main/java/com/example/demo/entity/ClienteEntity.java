package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cliente")
public class ClienteEntity {
    @Id
    @Column(name = "dpi", nullable = false)
    private Long dpi;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "telefono", nullable = false)
    private String telefono;
    @Column(name = "direccion", nullable = false)
    private String direccion;
    @Column(name = "genero", nullable = false)
    private char genero;
    @Column(name = "estado", nullable = false)
    private char estado;
}

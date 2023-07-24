package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "chequera")
public class ChequeraEntity {
    @Id
    @Column(name = "id_chequera", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_chequera;
    @Column(name = "fecha_emision", nullable = false)
    private Date fecha_emision;
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    @Column(name = "estado", nullable = false)
    private char estado;
    @Column(name = "id_cuenta", nullable = false)
    private int id_cuenta;
}

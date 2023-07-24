package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "cheque")
public class ChequeEntity {
    @Id
    @Column(name = "id_cheque", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cheque;
    @Column(name = "fecha_emision", nullable = false)
    private Date fecha_emision;
    @Column(name = "fecha_recibido", nullable = false)
    private Date fecha_recibido;
    @Column(name = "monto", nullable = false)
    private float monto;
    @Column(name = "lugar", nullable = false)
    private String lugar;
    @Column(name = "beneficiario", nullable = false)
    private String beneficiario;
    @Column(name = "estado", nullable = false)
    private char estado;
    @Column(name = "id_chequera", nullable = false)
    private int id_chequera;
}

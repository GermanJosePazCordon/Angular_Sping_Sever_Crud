package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "cuenta")
public class CuentaEntity {
    @Id
    @Column(name = "id_cuenta", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cuenta;
    @Column(name = "fecha_creacion", nullable = false)
    private Date fecha_creacion;
    @Column(name = "estado", nullable = false)
    private char estado;
    @Column(name = "saldo", nullable = false)
    private float saldo;
    @Column(name = "tipo_cuenta", nullable = false)
    private char tipo_cuenta;
    @Column(name = "id_cliente", nullable = false)
    private Long id_cliente;
}

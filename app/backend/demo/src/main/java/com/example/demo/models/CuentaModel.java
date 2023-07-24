package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CuentaModel {
    @Id
    private int id_cuenta;
    private Date fecha_creacion;
    private char estado;
    private float saldo;
    private char tipo_cuenta;
    private Long id_cliente;
}

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
public class ChequeraModel {
    @Id
    private int id_chequera;
    private Date fecha_emision;
    private int cantidad;
    private char estado;
    private int id_cuenta;
}

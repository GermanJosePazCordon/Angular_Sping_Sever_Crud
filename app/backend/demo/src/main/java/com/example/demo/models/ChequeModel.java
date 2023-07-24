package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChequeModel {
    @Id
    private int id_cheque;
    private Date fecha_emision;
    private Date fecha_recibido;
    private float monto;
    private String lugar;
    private String beneficiario;
    private char estado;
    private int id_chequera;
}

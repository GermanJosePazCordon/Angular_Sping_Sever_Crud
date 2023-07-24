package com.example.demo.responses;

import com.example.demo.entity.ChequeraEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChequeraResponse {
    private ChequeraEntity chequera;
    private String msg;
}

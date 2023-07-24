package com.example.demo.responses;

import com.example.demo.entity.ChequeEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChequeResponse {
    private ChequeEntity cheque;
    private String msg;
}

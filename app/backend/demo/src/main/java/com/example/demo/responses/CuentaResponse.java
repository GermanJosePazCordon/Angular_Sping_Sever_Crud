package com.example.demo.responses;

import com.example.demo.entity.CuentaEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaResponse {
    private CuentaEntity cuenta;
    private String msg;
}

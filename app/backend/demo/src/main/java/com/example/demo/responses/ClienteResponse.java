package com.example.demo.responses;

import com.example.demo.entity.ClienteEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponse {
    private ClienteEntity cliente;
    private String msg;
}

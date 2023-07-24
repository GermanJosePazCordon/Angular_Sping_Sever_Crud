package com.example.demo.repositories;

import com.example.demo.entity.CuentaEntity;
import com.example.demo.models.ChequeraModel;
import com.example.demo.models.ClienteModel;
import com.example.demo.models.CuentaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CuentaRepository extends JpaRepository<CuentaEntity, Integer> {
    @Query("SELECT NEW CuentaModel(c.id_cuenta, c.fecha_creacion, c.estado, c.saldo, c.tipo_cuenta, c.id_cliente) FROM CuentaEntity c WHERE c.id_cliente = :dpi ")
    public abstract ArrayList<CuentaModel> findCuentaByIdClienteIs(@Param("dpi") Long dpi);

    @Query("SELECT NEW ChequeraModel (c.id_chequera, c.fecha_emision, c.cantidad, c.estado, c.id_cuenta) FROM ChequeraEntity c WHERE c.id_cuenta = :id_cuenta ")
    public abstract ArrayList<ChequeraModel> findChequeraByIdCuentaIs(@Param("id_cuenta") int id_cuenta);

}

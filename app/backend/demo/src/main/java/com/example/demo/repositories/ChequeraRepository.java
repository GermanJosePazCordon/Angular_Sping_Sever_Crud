package com.example.demo.repositories;

import com.example.demo.entity.ChequeraEntity;
import com.example.demo.models.ChequeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ChequeraRepository extends JpaRepository<ChequeraEntity, Integer> {
    @Query("SELECT NEW ChequeModel(c.id_cheque, c.fecha_emision, c.fecha_recibido, c.monto, c.lugar, c.beneficiario, c.estado, c.id_chequera) FROM ChequeEntity c " +
            "WHERE c.id_chequera = :id")
    public abstract ArrayList<ChequeModel> findChequesByIdChequera(@Param("id") int id);
}

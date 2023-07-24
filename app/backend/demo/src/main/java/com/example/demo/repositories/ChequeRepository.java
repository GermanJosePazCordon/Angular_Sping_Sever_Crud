package com.example.demo.repositories;

import com.example.demo.entity.ChequeEntity;
import com.example.demo.models.CuentaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChequeRepository extends JpaRepository<ChequeEntity, Integer> {
    @Query(" SELECT NEW CuentaModel(c.id_cuenta, c.fecha_creacion, c.estado, c.saldo, c.tipo_cuenta, c.id_cliente) FROM CuentaEntity c " +
            " INNER JOIN ChequeraEntity ch ON c.id_cuenta = ch.id_cuenta " +
            " INNER JOIN ChequeEntity chq ON ch.id_chequera = chq.id_chequera " +
            " WHERE chq.id_cheque = :id_cheque ")
    public abstract CuentaModel findCuentaByIdChequeIs(@Param("id_cheque") int id_cheque);

    @Query(value="SELECT COUNT(*) FROM cheque c WHERE c.id_chequera = :id", nativeQuery = true)
    public abstract int countChequesByIdChequera(@Param("id") int id);
}

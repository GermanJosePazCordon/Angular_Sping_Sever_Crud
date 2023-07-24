DROP TABLE IF EXISTS cheque;GO
DROP TABLE IF EXISTS chequera;GO
DROP TABLE IF EXISTS cuenta;GO
DROP TABLE IF EXISTS cliente;GO

CREATE TABLE cliente(
    dpi BIGINT ,
    nombre VARCHAR(50) ,
    telefono VARCHAR(20) ,
    direccion VARCHAR(200) ,
    genero VARCHAR(1) ,
    estado VARCHAR(1),
    PRIMARY KEY (dpi),
);
GO

CREATE TABLE cuenta(
    id_cuenta INT IDENTITY ,
    fecha_creacion DATE ,
    estado VARCHAR(1) ,
    saldo float ,
    tipo_cuenta VARCHAR(1) ,
    id_cliente BIGINT ,
    PRIMARY KEY (id_cuenta),
    FOREIGN KEY (id_cliente) REFERENCES cliente(dpi),
);
GO

CREATE TABLE chequera(
    id_chequera INT IDENTITY ,
    fecha_emision DATE ,
    cantidad INT ,
    estado VARCHAR(1),
    id_cuenta INT ,
    PRIMARY KEY (id_chequera),
    FOREIGN KEY (id_cuenta) REFERENCES cuenta(id_cuenta)
);
GO

CREATE TABLE cheque(
    id_cheque INT IDENTITY ,
    fecha_emision DATE ,
    fecha_recibido DATE ,
    monto float ,
    lugar VARCHAR(50) ,
    beneficiario VARCHAR(50) ,
    estado VARCHAR(1),
    id_chequera INT ,
    PRIMARY KEY (id_cheque),
    FOREIGN KEY (id_chequera) REFERENCES chequera(id_chequera)
);
GO

UPDATE cliente SET estado = 'A'

UPDATE cuenta SET estado = 'A'

UPDATE chequera SET estado = 'A'

UPDATE cheque SET estado = 'A'

SELECT * FROM chequera

select * from cliente
select * from cuenta
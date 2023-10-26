CREATE TABLE USUARIOS
(
    ID_USUARIO NUMBER GENERATED ALWAYS AS IDENTITY,
    CEDULA     VARCHAR2(8) NOT NULL,
    NOMBRE     VARCHAR(50) NOT NULL;
APELLIDO
VARCHAR(50) NOT NULL;
    ID_PERFIL
NUMBER NOT NULL,
    ID_INSTITUCION NUMBER NOT NULL,
    EMAIL VARCHAR2(255) NOT NULL,
    CONTRASENIA VARCHAR2(255) NOT NULL,
    ESTADO VARCHAR (20) NOT NULL,
    FECHA_NACIMIENTO DATE,
    CONSTRAINT PK_USUARIOS PRIMARY KEY (ID_USUARIO),
    CONSTRAINT CEDULA UNIQUE (CEDULA),
    CONSTRAINT EMAIL UNIQUE (EMAIL)
);

CREATE TABLE INTERVENCIONES
(
    ID_INTERVENCION NUMBER GENERATED ALWAYS AS IDENTITY,
    ID_USUARIO      NUMBER       NOT NULL,
    ID_TIPO         NUMBER       NOT NULL,
    ID_EQUIPO       NUMBER       NOT NULL,
    MOTIVO          VARCHAR(255) NOT NULL,
    FECHA_HORA      DATE         NOT NULL,
    COMENTARIOS     VARCHAR(255),
    CONSTRAINT PK_INTERVENCIONES PRIMARY KEY (ID_INTERVENCION)
);

CREATE TABLE TIPOS_INTERVENCIONES
(
    ID_TIPO     NUMBER GENERATED ALWAYS AS IDENTITY,
    NOMBRE_TIPO VARCHAR(30) NOT NULL,
    ESTADO      VARCHAR(20) NOT NULL,
    CONSTRAINT PK_TIPOSINTERVENCIONES PRIMARY KEY (ID_TIPO),
    CONSTRAINT UK_NOMBRETIPOINTERVENCIONES UNIQUE (NOMBRE_TIPO)
);

CREATE TABLE TIPOS_EQUIPOS
(
    ID_TIPO     NUMBER GENERATED ALWAYS AS IDENTITY,
    NOMBRE_TIPO VARCHAR(30) NOT NULL,
    CONSTRAINT PK_TIPO PRIMARY KEY (ID_TIPO),
    CONSTRAINT UK_NOMBRETIPOSEQUIPOS UNIQUE (NOMBRE_TIPO)
);

CREATE TABLE EQUIPOS
(
    ID_EQUIPO         NUMBER GENERATED ALWAYS AS IDENTITY,
    ID_INTERNO        VARCHAR(50)  NOT NULL,
    ID_UBICACION      NUMBER       NOT NULL,
    NRO_SERIE         VARCHAR(100) NOT NULL,
    NOMBRE            VARCHAR(100) NOT NULL,
    ID_TIPO           NUMBER       NOT NULL,
    ID_PROVEEDOR      NUMBER,
    ID_PAIS           NUMBER,
    ID_MODELO         NUMBER,
    IMAGEN            CLOB,
    FECHA_ADQUISICION DATE,
    CONSTRAINT PK_EQUIPOS PRIMARY KEY (ID_EQUIPO),
    CONSTRAINT UK_IDINTERNOEQUIPOS UNIQUE (ID_INTERNO),
    CONSTRAINT UK_NROSERIEEQUIPOS UNIQUE (NRO_SERIE)
);

CREATE TABLE PROVEEDORES_EQUIPOS
(
    ID_PROVEEDOR NUMBER GENERATED ALWAYS AS IDENTITY,
    NOMBRE       VARCHAR(30) NOT NULL,
    CONSTRAINT PK_PROVEEDORES PRIMARY KEY (ID_PROVEEDOR)
);

CREATE TABLE PAISES
(
    ID_PAIS NUMBER GENERATED ALWAYS AS IDENTITY,
    NOMBRE  VARCHAR(50) NOT NULL,
    CONSTRAINT PK_PAISES PRIMARY KEY (ID_PAIS)
);

CREATE TABLE MODELOS_EQUIPOS
(
    ID_MODELO NUMBER GENERATED ALWAYS AS IDENTITY,
    ID_MARCA  NUMBER      NOT NULL,
    NOMBRE    VARCHAR(50) NOT NULL,
    CONSTRAINT PK_MODELOSEQUIPOS PRIMARY KEY (ID_MODELO),
    CONSTRAINT UK_MODELOSEQUIPOS UNIQUE (NOMBRE)
);

CREATE TABLE MARCAS_MODELO
(
    ID_MARCA NUMBER GENERATED ALWAYS AS IDENTITY,
    NOMBRE   VARCHAR(30) NOT NULL,
    CONSTRAINT PK_MARCASMODELOS PRIMARY KEY (ID_MARCA)
);

CREATE TABLE PERMISOS
(
    ID_PERMISO   NUMBER GENERATED ALWAYS AS IDENTITY,
    TIPO_PERMISO VARCHAR(20) NOT NULL,
    CONSTRAINT PK_PERMISOS PRIMARY KEY (ID_PERMISO),
    CONSTRAINT UK_TIPO_PERMISOPERMISOS UNIQUE (TIPO_PERMISO)
);

CREATE TABLE PERFILES_PERMISOS
(
    ID_PERFIL  NUMBER,
    ID_PERMISO NUMBER NOT NULL,
    CONSTRAINT PK_PERFILESPERMISOS PRIMARY KEY (ID_PERFIL, ID_PERMISO)
);

CREATE TABLE USUARIOS_TELEFONOS
(
    ID_USUARIO NUMBER,
    NUMERO     VARCHAR(20) NOT NULL,
    CONSTRAINT PK_USUARIOSTELEFONOS PRIMARY KEY (ID_USUARIO, NUMERO),
    CONSTRAINT PK_NUMERO UNIQUE (NUMERO)
);

CREATE TABLE PERFILES
(
    ID_PERFIL     NUMBER GENERATED ALWAYS AS IDENTITY,
    NOMBRE_PERFIL VARCHAR(20) NOT NULL,
    ESTADO        VARCHAR(20) NOT NULL,
    CONSTRAINT PK_PERFILES PRIMARY KEY (ID_PERFIL),
    CONSTRAINT UK_NOMBREPERFILES UNIQUE (NOMBRE_PERFIL)
);

CREATE TABLE OPERACIONES
(
    ID_OPERACION     NUMBER GENERATED ALWAYS AS IDENTITY,
    NOMBRE_OPERACION VARCHAR(30) NOT NULL,
    CONSTRAINT PK_OPERACIONES PRIMARY KEY (ID_OPERACION),
    CONSTRAINT UK_NOMBREOPERACIONES UNIQUE (NOMBRE_OPERACION)
);

CREATE TABLE AUDITORIAS
(
    ID_AUDITORIA NUMBER GENERATED ALWAYS AS IDENTITY,
    FECHA_HORA   DATE   NOT NULL,
    ID_USUARIO   NUMBER NOT NULL,
    ID_OPERACION NUMBER NOT NULL,
    CONSTRAINT PK_AUDITORIAS PRIMARY KEY (ID_AUDITORIA)
);


CREATE TABLE INSTITUCIONES
(
    ID_INSTITUCION NUMBER GENERATED ALWAYS AS IDENTITY,
    NOMBRE         VARCHAR(40) NOT NULL,
    CONSTRAINT PK_INSTITUCIONES PRIMARY KEY (ID_INSTITUCION)
);
CREATE TABLE UBICACIONES
(
    ID_UBICACION   NUMBER GENERATED ALWAYS AS IDENTITY,
    NOMBRE         VARCHAR(30) NOT NULL,
    SECTOR         VARCHAR(30),
    PISO           NUMBER,
    NUMERO         NUMBER,
    ID_INSTITUCION NUMBER,
    CONSTRAINT PK_UBICACIONES PRIMARY KEY (ID_UBICACION)
);
CREATE TABLE BAJA_EQUIPOS
(
    ID_BAJA     NUMBER GENERATED ALWAYS AS IDENTITY,
    ID_USUARIO  NUMBER      NOT NULL,
    ID_EQUIPO   NUMBER      NOT NULL,
    RAZON       VARCHAR(30) NOT NULL,
    FECHA       DATE        NOT NULL,
    ESTADO      VARCHAR(20) NOT NULL,
    COMENTARIOS VARCHAR(100),
    CONSTRAINT PK_BAJAEQUIPOS PRIMARY KEY (ID_BAJA)
);

CREATE TABLE EQUIPOS_UBICACIONES
(
    ID_MOVIMIENTO NUMBER GENERATED ALWAYS AS IDENTITY,
    ID_EQUIPO     NUMBER,
    ID_UBICACION  NUMBER,
    FECHA         DATE,
    CONSTRAINT PK_EQUIPOSUBICACIONES PRIMARY KEY (ID_MOVIMIENTO)
);

ALTER TABLE AUDITORIAS
    ADD CONSTRAINT FK_AUDITORIAS_USUARIO FOREIGN KEY (ID_USUARIO) REFERENCES USUARIOS (ID_USUARIO) ADD CONSTRAINT FK_AUDITORIAS_OPERACION FOREIGN KEY (ID_OPERACION) REFERENCES OPERACIONES(ID_OPERACION);

ALTER TABLE PERFILES_PERMISOS
    ADD CONSTRAINT FK_PERFILESPERMISOS_PERFILES FOREIGN KEY (ID_PERFIL) REFERENCES PERFILES (ID_PERFIL) ADD CONSTRAINT FK_PERFILESPERMISOS_PERMISOS FOREIGN KEY (ID_PERMISO) REFERENCES PERMISOS(ID_PERMISO);

ALTER TABLE USUARIOS_TELEFONOS
    ADD CONSTRAINT FK_USUARIOSTELEFONOS_USUARIOS FOREIGN KEY (ID_USUARIO) REFERENCES USUARIOS (ID_USUARIO);

ALTER TABLE EQUIPOS
    ADD CONSTRAINT FK_EQUIPOS_UBICACIONES FOREIGN KEY (ID_UBICACION) REFERENCES UBICACIONES (ID_UBICACION) ADD CONSTRAINT FK_EQUIPOS_TIPOSEQUIPOS FOREIGN KEY (ID_TIPO) REFERENCES TIPOS_EQUIPOS(ID_TIPO)
ADD CONSTRAINT FK_EQUIPOS_PROVEEDORES FOREIGN KEY (ID_PROVEEDOR) REFERENCES PROVEEDORES_EQUIPOS(ID_PROVEEDOR)
ADD CONSTRAINT FK_EQUIPOS_PAISES FOREIGN KEY (ID_PAIS) REFERENCES PAISES(ID_PAIS)
ADD CONSTRAINT FK_EQUIPOS_MODELOSEQUIPOS FOREIGN KEY (ID_MODELO) REFERENCES MODELOS_EQUIPOS(ID_MODELO);

ALTER TABLE INTERVENCIONES
    ADD CONSTRAINT FK_INTERVENCIONES_USUARIOS FOREIGN KEY (ID_USUARIO) REFERENCES USUARIOS (ID_USUARIO) ADD CONSTRAINT FK_INTERVENCIONES_TIPOSINTERVENCIONES FOREIGN KEY (ID_TIPO) REFERENCES TIPOS_INTERVENCIONES(ID_TIPO)
ADD CONSTRAINT FK_INTERVENCIONES_EQUIPOS FOREIGN KEY (ID_EQUIPO) REFERENCES EQUIPOS(ID_EQUIPO);

ALTER TABLE USUARIOS
    ADD CONSTRAINT FK_USUARIOS_PERFILES FOREIGN KEY (ID_PERFIL) REFERENCES PERFILES (ID_PERFIL) ADD CONSTRAINT FK_USUARIOS_INSTITUCIONES FOREIGN KEY (ID_INSTITUCION) REFERENCES INSTITUCIONES(ID_INSTITUCION);

ALTER TABLE UBICACIONES
    ADD CONSTRAINT FK_UBICACIONES_INSTITUCIONES FOREIGN KEY (ID_INSTITUCION) REFERENCES INSTITUCIONES (ID_INSTITUCION);

ALTER TABLE BAJA_EQUIPOS
    ADD CONSTRAINT FK_BAJAEQUIPOS_USUARIOS FOREIGN KEY (ID_USUARIO) REFERENCES USUARIOS (ID_USUARIO) ADD CONSTRAINT FK_BAJAEQUIPOS_EQUIPOS FOREIGN KEY (ID_EQUIPO) REFERENCES EQUIPOS(ID_EQUIPO);

ALTER TABLE MODELOS_EQUIPOS
    ADD CONSTRAINT FK_MODELOSEQUIPOS_MARCASMODELOS FOREIGN KEY (ID_MARCA) REFERENCES MARCAS_MODELO (ID_MARCA);

ALTER TABLE EQUIPOS_UBICACIONES
    ADD CONSTRAINT FK_EQUB_EQUIPOS FOREIGN KEY (ID_EQUIPO) REFERENCES EQUIPOS (ID_EQUIPO),
ADD CONSTRAINT FK_EQUB_UBICACIONES FOREIGN KEY (ID_UBICACIONES) REFERENCES UBICACIONES(ID_UBICACION);

ALTER TABLE UBICACIONES
    ADD CONSTRAINT FK_UBICACIONES_INSTITUTO FOREIGN KEY (ID_INSTITUCION) REFERENCES INSTITUCIONES (ID_INSTITUCION);

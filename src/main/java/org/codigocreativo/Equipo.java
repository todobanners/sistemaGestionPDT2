package org.codigocreativo;

import java.util.Date;
import java.util.List;

// Creamos la clase Equipo
public class Equipo {
    //Definimos sus atributos
    //id, nombre, tipo, modelo, numero de serie, pais de origen, proveedor, fecha de adquisicion, identificacion interna,
    //ubicacion, imagen, intervencion, estado

    private int id;
    private String nombre;
    private TipoEquipo tipo;
    //private Modelo modelo;
    private int numeroSerie;
    private Date fechaAdquisicion;
    private int identificacionInterna;
    private Ubicacion ubicacion;
    private Imagen imagen;
    private List<Intervencion> intervencion;
    private Estado estado;

    public void darDeAlta() {

    }
    public void darDeBaja() {

    }

}

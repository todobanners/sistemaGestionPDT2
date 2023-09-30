package org.codigocreativo;

import java.util.Date;

public class Usuario {
    // id, nombre, apellido, contrasena, telefono, rol, historialOperacion, email, ci, fechaNacimiento, institucion
    private int id;
    private String nombre;
    private String apellido;
    private String contrasena;
    private String telefono;
    private Perfil rol;
    //private List<Auditoria> historialOperacion;
    private String email;
    private String ci;
    private Date fechaNacimiento;
    //private Institucion institucion;

    public boolean login() {
        return false;
    }
    public boolean logout() {
        return false;
    }
}

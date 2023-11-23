package org.example.controlador;

import codigocreativo.uy.servidorapp.entidades.Usuario;

public class Sesion {
    private static Sesion instance;
    private static Usuario usuario;

    private Sesion(Usuario usuario) {
        this.usuario = usuario;
    }
    //crea una nueva instancia de la sesión si la instancia actual es null o si el
    //usuario proporcionado no es el mismo que el usuario de la sesión actual
    public static Sesion getInstancia(Usuario usuario) {
        if(instance == null || (usuario != null && !usuario.equals(instance.getUsuario()))) {
            instance = new Sesion(usuario);
        }
        return instance;
    }

    public static Sesion getInstancia() {
        return instance;
    }

    public static Usuario getUsuario() {
        return usuario;
    }
}

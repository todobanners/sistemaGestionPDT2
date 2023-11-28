package org.example.controlador;

import codigocreativo.uy.servidorapp.DTO.UsuarioDto;

public class Sesion {
    private static Sesion instance;
    private static UsuarioDto usuario;

    private Sesion(UsuarioDto usuario) {
        this.usuario = usuario;
    }
    //crea una nueva instancia de la sesión si la instancia actual es null o si el
    //usuario proporcionado no es el mismo que el usuario de la sesión actual
    public static Sesion getInstancia(UsuarioDto usuario) {
        if(instance == null || (usuario != null && !usuario.equals(instance.getUsuario()))) {
            instance = new Sesion(usuario);
        }
        return instance;
    }

    public static Sesion getInstancia() {
        return instance;
    }

    public static UsuarioDto getUsuario() {
        return usuario;
    }
}

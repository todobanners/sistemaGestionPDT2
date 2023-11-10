package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.entidades.Usuario;
import org.example.controlador.Sesion;

import javax.swing.*;

public class ModificarDatosPropiosGUI {
    private JPanel modificarDatosPropios;
    private JLabel textito;
    private JLabel otrotextito;

    public JPanel getPanel() {
        return modificarDatosPropios;
    }

    public ModificarDatosPropiosGUI() {
        Sesion sesion = Sesion.getInstancia();
        Usuario usuario = sesion.getUsuario();

        textito.setText(usuario.getNombreUsuario());
        otrotextito.setText(usuario.getNombre());
    }
}

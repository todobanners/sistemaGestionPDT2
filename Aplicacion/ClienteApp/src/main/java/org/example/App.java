package org.example;

import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.controlador.AplicacionVentana;
import org.example.vista.EquiposGUI;

import javax.naming.NamingException;
import javax.swing.*;

public class App
{
    public static void main(String[] args) throws NamingException, ServiciosException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        new AplicacionVentana("CodigoCreativo - Sistema de gestion de mantenimiento");
    }
}

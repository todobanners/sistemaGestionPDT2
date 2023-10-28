package org.example;

import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.controlador.AplicacionVentana;

import javax.naming.NamingException;
import javax.swing.*;


/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args) throws NamingException, ServiciosException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        new AplicacionVentana("CodigoCreativo - Sistema de gestion de mantenimiento");

    }
}

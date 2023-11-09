package org.example;

import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.vista.LoginForm;

import javax.naming.NamingException;
import javax.swing.*;

public class App
{
    public static void main(String[] args) throws NamingException, ServiciosException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //new AplicacionVentana("CodigoCreativo - Sistema de gestion de mantenimiento");
        //muestro login si el usaurio no se encuentra logueado

        //new AplicacionLogin("CodigoCreativo - Login");
        // Supongamos que tienes un método estático que devuelve el usuario actualmente logueado
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
    }
}

package org.example;

import org.example.vista.Usuario.LoginForm;

import javax.swing.*;

public class App
{
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        /*
            Login de usuarios, crea una instancia de LoginForm y la muestra
        */
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
    }
}

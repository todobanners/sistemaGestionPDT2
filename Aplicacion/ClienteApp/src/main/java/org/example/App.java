package org.example;

import org.example.controlador.CustomFlatLaf;
import org.example.vista.Usuario.LoginForm;

import javax.swing.*;

public class App
{
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    try {
        UIManager.setLookAndFeel(new CustomFlatLaf());
    } catch (Exception e) {
        e.printStackTrace();
    }

    LoginForm loginForm = new LoginForm();
    loginForm.setVisible(true);
}
}

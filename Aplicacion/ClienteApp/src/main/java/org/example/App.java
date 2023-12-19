package org.example;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.example.vista.Usuario.LoginForm;

import javax.swing.*;

public class App
{
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LoginForm loginForm = new LoginForm();
        //mostramos la app en maximizado
        loginForm.setVisible(true);
    }
}

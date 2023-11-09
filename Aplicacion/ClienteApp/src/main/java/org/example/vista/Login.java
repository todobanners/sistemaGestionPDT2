package org.example.vista;

import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.controlador.AplicacionVentana;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Login {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton ingresarButton;
    private JLabel logo;

    public JPanel getPanel(){

        return panel1;
    }
    public Login() {
        logo.setIcon(new ImageIcon("org/example/ccblanco.jpg"));
        Border border = BorderFactory.createLineBorder(null, 0);
        textField1.setBorder(border);

        Color colorLetras;
        colorLetras = new Color(255, 255, 255);
        textField1.setCaretColor(colorLetras);
        passwordField1.setCaretColor(colorLetras);
        textField1.setDisabledTextColor(colorLetras);
        passwordField1.setBorder(border);
        ingresarButton.addActionListener(e -> {
            String usuario = textField1.getText();
            String password = passwordField1.getText();
            if (usuario.equals("admin") && password.equals("admin")) {
                JOptionPane.showMessageDialog(null, "Bienvenido");
                try {
                    new AplicacionVentana("CodigoCreativo - Sistema de gestion de mantenimiento");

                } catch (ServiciosException ex) {
                    throw new RuntimeException(ex);
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (InstantiationException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            }
        });
    }
}


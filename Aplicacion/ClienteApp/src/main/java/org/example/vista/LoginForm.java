package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.persistence.NoResultException;
import org.example.Conexion;
import org.example.controlador.AplicacionVentana;

import javax.naming.NamingException;
import javax.swing.*;

public class LoginForm extends JFrame {
    private JButton loginButton;
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton registroButton;

    public LoginForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel1);
        pack();
        setSize(300, 500);

        loginButton.addActionListener(e -> {
            try {
                login();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        registroButton.addActionListener(e -> {

            try {
                new RegistroUsuarioNuevo("CodigoCreativo - Registro de Usuario");
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
            setVisible(false);
        });
    }

    private void login() throws Exception {
        String username = textField1.getText();
        String password = new String(passwordField1.getPassword());

        Usuario usuario = Conexion.obtenerUsuarioBean().login(username,password);

        if (usuario == null) {
            throw new Exception("Usuario o contraseña incorrectos");
        } else if (usuario.getEstado().equals(Estados.SIN_VALIDAR)) {
            throw new Exception("Usuario aun no validado, consulte a un administrador para conocer su estado");
        } else if (usuario.getEstado().equals(Estados.ELIMINADO)) {
            throw new Exception("Usuario eliminado");
        } else {
            JOptionPane.showMessageDialog(null, "Bienvenido");
            setVisible(false);
            new AplicacionVentana("CodigoCreativo - Sistema de gestion de mantenimiento");
        }
    }

        /*if (usuario != null) {
            JOptionPane.showMessageDialog(null, "Bienvenido");
            setVisible(false);
            new AplicacionVentana("CodigoCreativo - Sistema de gestion de mantenimiento");
        } else if (usuario.getEstado().equals(Estados.SIN_VALIDAR.toString())) {
            JOptionPane.showMessageDialog(null, "Usuario aun no validado, consulte a un administrador para conocer su estado");
        } else if (usuario.getEstado().equals(Estados.ELIMINADO)) {
            JOptionPane.showMessageDialog(null, "Usuario eliminado");
        } else {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
        }*/
}
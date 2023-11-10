package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.entidades.Usuario;
import org.example.Conexion;
import org.example.controlador.Sesion;

import javax.naming.NamingException;
import javax.swing.*;

public class ModificarDatosPropiosGUI {
    private JPanel modificarDatosPropios;
    private JLabel textito;
    private JLabel otrotextito;
    private JTextField id;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JTextField email;
    private JPanel contenedorFechaNacimiento;
    private JTextField cedula;
    private JTextField apellido1;
    private JTextField nombre1;
    private JTextField telefono;
    private JButton confirmarButton;
    private JButton cancelarButton;

    public JPanel getPanel() {
        return modificarDatosPropios;
    }

    public ModificarDatosPropiosGUI() {
        Sesion sesion = Sesion.getInstancia();
        Usuario usuario = sesion.getUsuario();

        //textito.setText(usuario.getNombreUsuario());
        //otrotextito.setText(usuario.getNombre());

        usernameTextField.setText(usuario.getNombreUsuario());
        passwordField1.setText(usuario.getContrasenia());
        passwordField2.setText(usuario.getContrasenia());
        email.setText(usuario.getEmail());
        cedula.setText(usuario.getCedula());
        apellido1.setText(usuario.getApellido());
        nombre1.setText(usuario.getNombre());
        id.setText(usuario.getId().toString());

        confirmarButton.addActionListener(e -> {
           //Guardo los datos en la tabla
            Usuario userModificado = new Usuario();
            userModificado.setId(Long.parseLong(id.getText()));
            userModificado.setNombreUsuario(usernameTextField.getText());
            userModificado.setContrasenia(passwordField1.getText());
            userModificado.setEmail(email.getText());
            userModificado.setCedula(cedula.getText());
            userModificado.setApellido(apellido1.getText());
            userModificado.setNombre(nombre1.getText());

            try {
                Conexion.obtenerUsuarioBean().modificarUsuario(userModificado);
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }


        });
    }
}


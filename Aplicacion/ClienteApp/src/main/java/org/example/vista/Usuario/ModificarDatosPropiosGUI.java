package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.entidades.Usuario;
import org.example.modelo.Conexion;
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

            usuario.setId(Long.parseLong(id.getText()));
            usuario.setNombreUsuario(usernameTextField.getText());
            usuario.setContrasenia(passwordField1.getText());
            usuario.setEmail(email.getText());
            usuario.setCedula(cedula.getText());
            usuario.setApellido(apellido1.getText());
            usuario.setNombre(nombre1.getText());

            //desplegar mensaje de confirmación para aceptar o cancelar
            //si acepta, guardar los datos en la base de datos
            //si cancela, no hacer nada

            JOptionPane.showMessageDialog(null, "¿Está seguro que desea modificar los datos?", "Confirmación", JOptionPane.YES_NO_OPTION);

            if (JOptionPane.YES_OPTION == 0) {
                //guardar los datos en la base de datos
                //desplegar mensaje de confirmación de que se guardaron los datos
                try {
                    Conexion.obtenerUsuarioBean().modificarUsuario(usuario);
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }

                JOptionPane.showMessageDialog(null, "Los datos se han modificado correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                //no hacer nada
            }




        });
    }
}


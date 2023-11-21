package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.entidades.Usuario;
import com.github.lgooddatepicker.components.DatePicker;
import com.toedter.calendar.JDateChooser;
import org.example.modelo.Conexion;
import org.example.controlador.Sesion;
import org.example.modelo.DatePickerUtil;
import org.example.modelo.Validator;

import javax.naming.NamingException;
import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ModificarDatosPropiosGUI {
    private JPanel modificarDatosPropios;
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
//TODO: Falta telefono

// JDateChooser selectorFecha = new JDateChooser();
//DatePicker selectorFecha = new DatePicker();
    DatePicker selectorFecha = DatePickerUtil.createCustomDatePicker();

    public JPanel getPanel() {
        return modificarDatosPropios;
    }

    public ModificarDatosPropiosGUI() {
        Sesion sesion = Sesion.getInstancia();
        Usuario usuario = sesion.getUsuario();

        contenedorFechaNacimiento.add(selectorFecha);

        usernameTextField.setText(usuario.getNombreUsuario());
        passwordField1.setText(usuario.getContrasenia());
        passwordField2.setText(usuario.getContrasenia());
        email.setText(usuario.getEmail());
        cedula.setText(usuario.getCedula());
        apellido1.setText(usuario.getApellido());
        nombre1.setText(usuario.getNombre());
        id.setText(usuario.getId().toString());
        //Date fecha = Date.from(usuario.getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant());
        selectorFecha.setDate(usuario.getFechaNacimiento());

        //TODO: Falta verificacion de fecha

        confirmarButton.addActionListener(e -> {
           //Guardo los datos en la tabla
            //Valido los campos
            if (passwordField1.getText().isEmpty() || passwordField2.getText().isEmpty() || email.getText().isEmpty() || cedula.getText().isEmpty() || apellido1.getText().isEmpty() || nombre1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!passwordField1.getText().equals(passwordField2.getText())) {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Validator.validarEmail(email.getText())) {
                JOptionPane.showMessageDialog(null, "El email ingresado no es válido", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Validator.contieneSoloNumeros(cedula.getText()) || !Validator.validarMinimoCaracteres(cedula.getText(), 8) || !Validator.validarMaximoCaracteres(cedula.getText(), 8)) {
                JOptionPane.showMessageDialog(null, "La cédula ingresada no es válida, solo numeros y 8 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Validator.contieneSoloLetras(nombre1.getText()) || !Validator.validarMaximoCaracteres(nombre1.getText(), 50) || !Validator.validarMinimoCaracteres(nombre1.getText(), 3)) {
                JOptionPane.showMessageDialog(null, "El nombre ingresado no es válido", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Validator.contieneSoloLetras(apellido1.getText()) || !Validator.validarMaximoCaracteres(apellido1.getText(), 50) || !Validator.validarMinimoCaracteres(apellido1.getText(), 3)) {
                JOptionPane.showMessageDialog(null, "El apellido ingresado no es válido", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Validator.validarContrasena(passwordField1.getText())) {
                JOptionPane.showMessageDialog(null, "La contraseña ingresada no es válida, debe tener al menos 8 caracteres, una letra y un número", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //Genero objeto usuario
                usuario.setId(Long.parseLong(id.getText()));
                usuario.setNombreUsuario(usernameTextField.getText());
                usuario.setContrasenia(passwordField1.getText());
                usuario.setEmail(email.getText());
                usuario.setCedula(cedula.getText());
                usuario.setApellido(apellido1.getText());
                usuario.setNombre(nombre1.getText());

                //Date fechaElegida = selectorFecha.getDate();
                //LocalDate localdate = fechaElegida.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                usuario.setFechaNacimiento(selectorFecha.getDate());

                //desplegar mensaje de confirmación para aceptar o cancelar
                //si acepta, guardar los datos en la base de datos
                //si cancela, no hacer nada

                int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea modificar los datos?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    //guardar los datos en la base de datos
                    //desplegar mensaje de confirmación de que se guardaron los datos
                    try {
                        Conexion.obtenerUsuarioBean().modificarUsuario(usuario);
                    } catch (NamingException ex) {
                        throw new RuntimeException(ex);
                    }

                    JOptionPane.showMessageDialog(null, "Los datos se han modificado correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Operacion cancelada, los datos no se han modificado", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public void limpiarCampos() {
        usernameTextField.setText("");
        passwordField1.setText("");
        passwordField2.setText("");
        email.setText("");
        cedula.setText("");
        apellido1.setText("");
        nombre1.setText("");
        selectorFecha.setDate(null);
    }
}


package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.DTO.UsuarioDto;
import codigocreativo.uy.servidorapp.DTO.UsuariosTelefonoDto;
import com.github.lgooddatepicker.components.DatePicker;
import org.example.controlador.Sesion;
import org.example.modelo.Conexion;
import org.example.modelo.Utilidades;
import org.example.modelo.Validator;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    private JButton buttonTelefonos;

    DatePicker selectorFecha = Utilidades.createCustomDatePicker();

    public JPanel getPanel() {
        return modificarDatosPropios;
    }

    public ModificarDatosPropiosGUI() {
        Sesion sesion = Sesion.getInstancia();
        UsuarioDto usuario = sesion.getUsuario();

        contenedorFechaNacimiento.add(selectorFecha);

        usernameTextField.setText(usuario.getNombreUsuario());
        email.setText(usuario.getEmail());
        cedula.setText(usuario.getCedula());
        apellido1.setText(usuario.getApellido());
        nombre1.setText(usuario.getNombre());
        id.setText(usuario.getId().toString());
        selectorFecha.setDate(usuario.getFechaNacimiento());
        //Obtener los telefonos del usuario
        Set<UsuariosTelefonoDto> telefonos = usuario.getUsuariosTelefonos();
        String telefonosString = "";
        for (UsuariosTelefonoDto telefono : telefonos) {
            telefonosString += telefono.getNumero() + ",";
        }
        //Eliminar la ultima coma
        if (!telefonosString.isEmpty()) {
            telefonosString = telefonosString.substring(0, telefonosString.length() - 1);
        }
        telefono.setText(telefonosString);

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
                usuario.setContrasenia(Utilidades.hashClave(passwordField1.getText()));
                usuario.setEmail(email.getText());
                usuario.setCedula(cedula.getText());
                usuario.setApellido(apellido1.getText());
                usuario.setNombre(nombre1.getText());

                List<String> tel = Arrays.asList(telefono.getText().split(","));
                Set<UsuariosTelefonoDto> telefonosDto = new LinkedHashSet<>();
                for (String telefono : tel) {
                    UsuariosTelefonoDto telefonoDto = new UsuariosTelefonoDto();
                    telefonoDto.setNumero(telefono);
                    telefonoDto.setIdUsuario(usuario);
                    telefonosDto.add(telefonoDto);
                }
                usuario.setUsuariosTelefonos(telefonosDto);

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
                } else {
                    JOptionPane.showMessageDialog(null, "Operacion cancelada, los datos no se han modificado", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //mostrar cartel de opcion si desea o no cancelar registro
                //si acepta, limpiar los campos
                //si cancela, no hacer nada
                int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea cancelar la modificación de los datos?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    //todo: Debe llevar a la pantalla de Home
                } else {
                    JOptionPane.showMessageDialog(null, "Operacion cancelada, los datos no se han modificado", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                }
                limpiarCampos();
            }
        });
        buttonTelefonos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Obtener los telefonos del textfield asumiendo que estan separados por comas
                String telefonos = telefono.getText();
                List<String> listaTelefonos = Arrays.asList(telefonos.split(","));

                ModificarTelefonos modificarTelefonos = new ModificarTelefonos(listaTelefonos);

                modificarTelefonos.pack();

                modificarTelefonos.setVisible(true);

                //Obtener los telefonos ingresados
                List<String> telefonosIngresados = modificarTelefonos.getTelefonos();

                //Limpiar el textfield
                telefono.setText("");

                //Agregar los telefonos al textfield
                if (!telefonosIngresados.isEmpty()) {
                    for (String telefono : telefonosIngresados) {
                        ModificarDatosPropiosGUI.this.telefono.setText(ModificarDatosPropiosGUI.this.telefono.getText() + telefono + ",");
                    }
                    //Eliminar la ultima coma
                    telefono.setText(telefono.getText().substring(0, telefono.getText().length() - 1));
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


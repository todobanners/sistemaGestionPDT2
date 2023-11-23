package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.entidades.*;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.github.lgooddatepicker.components.DatePicker;
import com.toedter.calendar.JDateChooser;
import org.example.modelo.Conexion;
import org.example.modelo.DatePickerUtil;
import org.example.modelo.Validator;
import org.example.vista.Usuario.LoginForm;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import static javax.swing.UIManager.setLookAndFeel;
//todo: Validar nombre usuario existente, cedula existente, email existente
//todo: Usar hasheo para password
//todo: Boton cancelar no hace nada, debe volver a la pantalla de login


public class RegistroUsuarioNuevo extends JFrame {
    private JPanel registroUsuario;
    private JTextField nombreTextField;
    private JTextField apellidoTextField;
    private JTextField cedulaTextField;
    private JButton cancelarButton;
    private JButton aceptarButton;
    private JComboBox comboBoxTipo;
    private JTextField userTextField;
    private JTextField emailTextField;
    private JTextField telefonoTextField;
    private JPasswordField claveRepetir;
    private JPasswordField clave;
    private JPanel fechaNacimiento;
    private JLabel logo;
    private JPanel internoRegistro;

    DatePicker selectorFecha = DatePickerUtil.createCustomDatePicker();

    public RegistroUsuarioNuevo(String s) throws NamingException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super(s);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(registroUsuario);
        pack();
        setSize(500, 900);
        setVisible(true);
        ImageIcon imagen = new ImageIcon("Aplicacion/ClienteApp/src/main/recursos/ccblanco.jpg");
        Image img = imagen.getImage();
        Image imgRedimensionada = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon imagenRedimensionada = new ImageIcon(imgRedimensionada);
        logo.setIcon(imagenRedimensionada);
        logo.setText("");

        fechaNacimiento.add(selectorFecha);
        userTextField.setEnabled(false);
        //Por razones de validacion este campo es autogenerado con una CI valida
        cedulaTextField.setText(Validator.generarCedulaUruguaya());

        //Obtencion de valores del combobox
        for (Perfil p : Conexion.obtenerPerfilBean().obtenerPerfiles()){
            comboBoxTipo.addItem(p);
        }

        apellidoTextField.addCaretListener(e ->{
            String nombre = nombreTextField.getText().toLowerCase();
            String apellido = apellidoTextField.getText().toLowerCase();
            String nombreUsuario = nombre+ "." + apellido;
            userTextField.setText(nombreUsuario);
        });
        nombreTextField.addCaretListener(e ->{
            String nombre = nombreTextField.getText().toLowerCase();
            String apellido = apellidoTextField.getText().toLowerCase();
            String nombreUsuario = nombre+ "." + apellido;
            userTextField.setText(nombreUsuario);
        });

        aceptarButton.addActionListener(e -> {
            Usuario usuario = new Usuario();
            //Validamos los campos
            // Verifico que los campos esten todos completos
            if (nombreTextField.getText().isEmpty() || apellidoTextField.getText().isEmpty() || cedulaTextField.getText().isEmpty() || userTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || telefonoTextField.getText().isEmpty() || clave.getPassword().length == 0 || claveRepetir.getPassword().length == 0 || selectorFecha.getDate() == null){
                JOptionPane.showMessageDialog(null, "Debe completar todos los campos");
            }
            // Verifico que el nombre y apellido contengan solo letras
            else if (Validator.contieneSoloLetras(nombreTextField.getText()) || Validator.contieneSoloLetras(apellidoTextField.getText())){
                JOptionPane.showMessageDialog(null, "Los campos nombre y apellido solo pueden contener letras");
            }
            // Verifico que la cedula sea valida
            else if (!Validator.validarCedula(cedulaTextField.getText())){
                JOptionPane.showMessageDialog(null, "La cédula ingresada no es válida");
            }
            // Verifico que el email sea valido
            else if (!Validator.validarEmail(emailTextField.getText())){
                JOptionPane.showMessageDialog(null, "El email ingresado no es valido");
            }
            // Verifico que el telefono contenga solo numeros
            else if (Validator.contieneSoloNumeros(telefonoTextField.getText())){
                JOptionPane.showMessageDialog(null, "El campo telefono solo puede contener numeros");
            }
            // Verifico que las claves coinciden
            else if (!Arrays.equals(clave.getPassword(), claveRepetir.getPassword())){
                JOptionPane.showMessageDialog(null, "Las claves ingresadas no coinciden");
            }
            // Verifico que la contraseña tenga almenos una letra y un numero
            else if (!Validator.validarContrasena(new String(clave.getPassword()))){
                JOptionPane.showMessageDialog(null, "La contraseña ingresada debe tener almenos una letra y un numero y contener almenos 8 caracteres");
            }
            else {
                //Guardo los datos en la tabla
                //Genero objeto usuario
                Institucion institucion = new Institucion();
                institucion.setId(1L);
                usuario.setIdInstitucion(institucion);
                usuario.setNombre(nombreTextField.getText());
                usuario.setApellido(apellidoTextField.getText());
                usuario.setCedula(cedulaTextField.getText());
                usuario.setNombreUsuario(userTextField.getText()); //El nombre de usuario esta formado por el nombre.apellido en minuscula
                usuario.setEmail(emailTextField.getText());
                usuario.setContrasenia(clave.getPassword().toString());
                usuario.setFechaNacimiento(selectorFecha.getDate());
                Perfil perfil = (Perfil) comboBoxTipo.getSelectedItem();
                usuario.setIdPerfil(perfil);
                usuario.setEstado(Estados.SIN_VALIDAR);
                UsuariosTelefonoId usuariosTelefonoId = new UsuariosTelefonoId();
                usuariosTelefonoId.setNumero(telefonoTextField.getText());
                usuariosTelefonoId.setIdUsuario(usuario.getId());

                //usuario.setTelefono(telefonoTextField.getText());
                try {
                    Conexion.obtenerUsuarioBean().crearUsuario(usuario);
                    JOptionPane.showMessageDialog(null, "Usuario creado con exito");
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, "No se pudo crear el usuario Error:"+exception.getMessage());
                }
            }
            //setVisible(false);
            //new LoginForm();
        });

        cancelarButton.addActionListener(e -> {
            setVisible(false);
            LoginForm loginForm = null;
            try {
                loginForm = new LoginForm();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException ex) {
                throw new RuntimeException(ex);
            }
            loginForm.setVisible(true);
        });



    }

}

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
import java.util.Date;
import java.util.regex.Pattern;

import static javax.swing.UIManager.setLookAndFeel;

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

        //Obtencion de valores del combobox
        for (Perfil p : Conexion.obtenerPerfilBean().obtenerPerfiles()){
            comboBoxTipo.addItem(p);
        }

        //Validamos los campos

        //El campo nombre solo debe permitir letras
        if (!Validator.contieneSoloLetras(nombreTextField.getText())){
            JOptionPane.showMessageDialog(null, "El nombre ingresado no es valido");
        }


        apellidoTextField.addCaretListener(e ->{
            String nombre = nombreTextField.getText().toLowerCase();
            String apellido = apellidoTextField.getText().toLowerCase();
            String nombreUsuario = nombre+ "." + apellido;
            userTextField.setText(nombreUsuario);
        });

        aceptarButton.addActionListener(e -> {
            Usuario usuario = new Usuario();

            //Verifico que los campos esten todos completos
            if (nombreTextField.getText().isEmpty() || apellidoTextField.getText().isEmpty() || cedulaTextField.getText().isEmpty() || userTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || telefonoTextField.getText().isEmpty() || clave.getPassword().length == 0 || claveRepetir.getPassword().length == 0){
                JOptionPane.showMessageDialog(null, "Debe completar todos los campos");
            } else if (Validator.contieneSoloLetras(nombreTextField.getText()) || Validator.contieneSoloLetras(apellidoTextField.getText())) {// verifico que los campos de texto solo tengan texto
                JOptionPane.showMessageDialog(null, "El nombre o apellido ingresado no es valido");
            } else if (!Validator.validarMinimoCaracteres(nombreTextField.getText(), 3) || !Validator.validarMinimoCaracteres(apellidoTextField.getText(), 3)) {// verifico que los campos de texto tengan almenos 3 caracteres
                JOptionPane.showMessageDialog(null, "El nombre o apellido ingresado debe tener almenos 3 caracteres");
            } else if (!Validator.validarMaximoCaracteres(nombreTextField.getText(), 50) || !Validator.validarMaximoCaracteres(apellidoTextField.getText(), 50)) {// verifico que los campos de texto tengan menos de 50 caracteres
                JOptionPane.showMessageDialog(null, "El nombre o apellido ingresado debe tener menos de 50 caracteres");
            } else if (!Validator.contieneSoloNumeros(cedulaTextField.getText()) || !Validator.validarMinimoCaracteres(cedulaTextField.getText(), 8) || !Validator.validarMaximoCaracteres(cedulaTextField.getText(), 8)) {// verifico que la cedula solo tenga numeros y tenga 8 caracteres
                JOptionPane.showMessageDialog(null, "La cedula ingresada no es valida");
            } else if (!Validator.validarMinimoCaracteres(userTextField.getText(), 3)) {// verifico que el nombre de usuario tenga almenos 3 caracteres
                JOptionPane.showMessageDialog(null, "El nombre de usuario ingresado debe tener almenos 3 caracteres");
            } else if (!Validator.validarMaximoCaracteres(userTextField.getText(), 50)) {// verifico que el nombre de usuario tenga menos de 50 caracteres
                JOptionPane.showMessageDialog(null, "El nombre de usuario ingresado debe tener menos de 50 caracteres");
            } else if (!Validator.validarMinimoCaracteres(clave.getPassword().toString(), 8)) {// verifico que la contraseña tenga almenos 8 caracteres
                JOptionPane.showMessageDialog(null, "La contraseña ingresada debe tener almenos 8 caracteres");
            } else if (!Validator.validarMaximoCaracteres(clave.getPassword().toString(), 50)) {// verifico que la contraseña tenga menos de 50 caracteres
                JOptionPane.showMessageDialog(null, "La contraseña ingresada debe tener menos de 50 caracteres");
            } else if (!Validator.validarContrasena(clave.getPassword().toString())) {// verifico que la contraseña tenga almenos una letra y un numero
                JOptionPane.showMessageDialog(null, "La contraseña ingresada debe tener almenos una letra y un numero");
            } else if (!Validator.validarEmail(emailTextField.getText())) {// verifico que el email sea valido
                JOptionPane.showMessageDialog(null, "El email ingresado no es valido");
            } else if (selectorFecha.getDate().isAfter(LocalDate.now())) {// verifico que la fecha de nacimiento sea menor a la fecha actual
                JOptionPane.showMessageDialog(null, "La fecha de nacimiento debe ser menor a la fecha actual");
            } else if (selectorFecha.getDate().isBefore(LocalDate.of(1900, 1, 1))) {// verifico que la fecha de nacimiento sea mayor a 1900
                JOptionPane.showMessageDialog(null, "La fecha de nacimiento debe ser mayor a 1900");
            } else if (selectorFecha.getDate().isAfter(LocalDate.now())) {// verifico que la fecha de nacimiento sea menor a la fecha actual
                JOptionPane.showMessageDialog(null, "La fecha de nacimiento debe ser menor a la fecha actual");
            } else {
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
                fechaNacimiento.add(selectorFecha);
                selectorFecha.setDate(usuario.getFechaNacimiento());
                Perfil perfil = (Perfil) comboBoxTipo.getSelectedItem();
                usuario.setIdPerfil(perfil);
                usuario.setEstado(Estados.SIN_VALIDAR);
                UsuariosTelefonoId usuariosTelefonoId = new UsuariosTelefonoId();
                usuariosTelefonoId.setNumero(telefonoTextField.getText());
                usuariosTelefonoId.setIdUsuario(usuario.getId());
            }
            //usuario.setTelefono(telefonoTextField.getText());
            try {
                Conexion.obtenerUsuarioBean().crearUsuario(usuario);
                JOptionPane.showMessageDialog(null, "Usuario creado con exito");
            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(null, "No se pudo crear el usuario Error:"+exception.getMessage());
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

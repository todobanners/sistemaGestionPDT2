package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.DTO.InstitucionDto;
import codigocreativo.uy.servidorapp.DTO.PerfilDto;
import codigocreativo.uy.servidorapp.DTO.UsuarioDto;
import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.entidades.UsuariosTelefonoId;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.github.lgooddatepicker.components.DatePicker;
import org.example.modelo.Conexion;
import org.example.modelo.Utilidades;
import org.example.modelo.Validator;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
//todo: Validar nombre de usuario existente, cédula existente, email existente
//todo: Usar hash para contraseña
//todo: Quitar el generador de cédula


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

    DatePicker selectorFecha = Utilidades.createCustomDatePicker();

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
        //Por razones de validación este campo es autogenerado con una CI válida
        cedulaTextField.setText(Validator.generarCedulaUruguaya());

        //Obtención de valores del combobox
        for (PerfilDto p : Conexion.obtenerPerfilBean().obtenerPerfiles()){
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
            UsuarioDto usuario = new UsuarioDto();
            //Validamos los campos
            // Verifico que los campos estén todos completos
            if (nombreTextField.getText().isEmpty() || apellidoTextField.getText().isEmpty() || cedulaTextField.getText().isEmpty() || userTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || telefonoTextField.getText().isEmpty() || clave.getPassword().length == 0 || claveRepetir.getPassword().length == 0 || selectorFecha.getDate() == null){
                JOptionPane.showMessageDialog(null, "Debe completar todos los campos");
            }
            // Verifico que el nombre y apellido contengan solo letras
            else if (Validator.contieneSoloLetras(nombreTextField.getText()) || Validator.contieneSoloLetras(apellidoTextField.getText())){
                JOptionPane.showMessageDialog(null, "Los campos nombre y apellido solo pueden contener letras");
            }
            // Verifico que la cédula sea válida
            else if (!Validator.validarCedula(cedulaTextField.getText())){
                JOptionPane.showMessageDialog(null, "La cédula ingresada no es válida");
            }
            // Verifico que el email sea válido
            else if (!Validator.validarEmail(emailTextField.getText())){
                JOptionPane.showMessageDialog(null, "El email ingresado no es válido");
            }
            // Verifico que el teléfono contenga solo números
            else if (Validator.contieneSoloNumeros(telefonoTextField.getText())){
                JOptionPane.showMessageDialog(null, "El campo teléfono solo puede contener números");
            }
            // Verifico que las claves coincidan
            else if (!Arrays.equals(clave.getPassword(), claveRepetir.getPassword())){
                JOptionPane.showMessageDialog(null, "Las claves ingresadas no coinciden");
            }
            // Verifico que la contraseña tenga al menos una letra y un número
            else if (!Validator.validarContrasena(new String(clave.getPassword()))){
                JOptionPane.showMessageDialog(null, "La contraseña ingresada debe tener al menos una letra y un número y contener al menos 8 caracteres");
            }
            else {
                //Guardo los datos en la tabla
                //Genero objeto usuario
                InstitucionDto institucion = new InstitucionDto();
                institucion.setId(1L);
                usuario.setIdInstitucion(institucion);
                usuario.setNombre(nombreTextField.getText());
                usuario.setApellido(apellidoTextField.getText());
                usuario.setCedula(cedulaTextField.getText());
                usuario.setNombreUsuario(userTextField.getText()); //El nombre de usuario está formado por el nombre.apellido en minúscula
                usuario.setEmail(emailTextField.getText());

                //hasheo la contraseña


                usuario.setContrasenia(Utilidades.hashClave(clave.getText()));
                usuario.setFechaNacimiento(selectorFecha.getDate());
                PerfilDto perfil = (PerfilDto) comboBoxTipo.getSelectedItem();
                usuario.setIdPerfil(perfil);
                usuario.setEstado(Estados.SIN_VALIDAR);
                UsuariosTelefonoId usuariosTelefonoId = new UsuariosTelefonoId();
                usuariosTelefonoId.setNumero(telefonoTextField.getText());
                usuariosTelefonoId.setIdUsuario(usuario.getId());

                //usuario.setTelefono(telefonoTextField.getText());
                try {
                    Conexion.obtenerUsuarioBean().crearUsuario(usuario);
                    JOptionPane.showMessageDialog(null, "Usuario creado con éxito, deberá esperar a que un administrador lo valide");
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, "No se pudo crear el usuario Error:"+exception.getMessage());
                }
            }
        });

        cancelarButton.addActionListener(e -> {
            //Muestra cartel de confirmación de cancelación
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Esta acción borrará todo y volverás al Login","¿Deseas cancelar?",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){
                //Genera un nuevo formulario de login
                limpiarCampos();
                setVisible(false);
                LoginForm loginForm = null;
                try {
                    loginForm = new LoginForm();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
                }
                loginForm.setVisible(true);
            }
        });



    }
    private void limpiarCampos() {
        nombreTextField.setText("");
        apellidoTextField.setText("");
        cedulaTextField.setText("");
        userTextField.setText("");
        emailTextField.setText("");
        telefonoTextField.setText("");
        clave.setText("");
        claveRepetir.setText("");
        selectorFecha.setDate(null);
    }

}
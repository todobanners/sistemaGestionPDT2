package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.toedter.calendar.JDateChooser;
import org.example.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

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
    JDateChooser selectorFecha = new JDateChooser();

    public RegistroUsuarioNuevo(String s) throws NamingException {
        super(s);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(registroUsuario);
        setVisible(true);
        pack();
        fechaNacimiento.add(selectorFecha);

        //Obtencion de valores del combobox
        for (Perfil p : Conexion.obtenerPerfilBean().obtenerPerfiles()){
            comboBoxTipo.addItem(p);
        }

      apellidoTextField.addCaretListener(e ->{
        String nombre = nombreTextField.getText();
        String apellido = apellidoTextField.getText();
        String nombreUsuario = nombre.toLowerCase() + "." + apellido.toLowerCase();
        userTextField.setText(nombreUsuario);});

        userTextField.setEnabled(false);

    aceptarButton.addActionListener(e -> {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombreTextField.getText());
            usuario.setApellido(apellidoTextField.getText());
            usuario.setCedula(cedulaTextField.getText());
            //El nombre de usuario esta formado por el nombre.apellido en minuscula
            usuario.setNombreUsuario(userTextField.getText());


            //El campo email solo debe permitir emails
            String regExEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
            Pattern pattern = Pattern.compile(regExEmail);
            if (!pattern.matcher(emailTextField.getText()).matches()){
                JOptionPane.showMessageDialog(null, "El email ingresado no es valido");
                usuario = null;
            }else {
                usuario.setEmail(emailTextField.getText());
            }
            //usuario.setTelefono(telefonoTextField.getText());


            Date fechaElegida = (Date) selectorFecha.getDate();
            LocalDate localDate = fechaElegida.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            //la fecha de nacimiento debe ser menor a la fecha actual

            if (localDate.isAfter(LocalDate.now())){
                JOptionPane.showMessageDialog(null, "La fecha de nacimiento debe ser menor a la fecha actual");
                usuario = null;
            }else {
                usuario.setFechaNacimiento(localDate);
            }

            Perfil perfil = (Perfil) comboBoxTipo.getSelectedItem();
            usuario.setIdPerfil(perfil);
            usuario.setEstado(Estados.SIN_VALIDAR);

            //Verificar contraseña que sean iguales y contengan almenos 8 caracteres letras y numeros
            String clave1 = new String(clave.getPassword());
            String clave2 = new String(claveRepetir.getPassword());
            if (clave1.equals(clave2) && clave1.length() >= 8){
                usuario.setContrasenia(clave1);
            }else{
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden o no tienen almenos 8 caracteres");
                usuario = null;
            }

            try {
                Conexion.obtenerUsuarioBean().crearUsuario(usuario);
                JOptionPane.showMessageDialog(null, "Usuario creado con exito");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            //setVisible(false);
            //new LoginForm();
        });

        cancelarButton.addActionListener(e -> {
            setVisible(false);
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });



    }
}

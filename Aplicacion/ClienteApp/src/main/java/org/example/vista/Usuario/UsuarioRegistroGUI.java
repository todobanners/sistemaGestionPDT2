package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.toedter.calendar.JDateChooser;
import org.example.modelo.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class UsuarioRegistroGUI {
    private JPanel registrarUsuario;
    private JButton cancelarButton;
    private JButton agregarUsuarioButton;
    private JTextField cedula;
    private JComboBox perfil;
    private JComboBox institucion;
    private JTextField nombre;
    private JTextField apellido;
    private JComboBox estado;
    private JTextField email;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JPanel calendarioContenedor;
    JDateChooser fechaChooser = new JDateChooser();

    public JPanel getPanel(){
        return registrarUsuario;
    }

    public UsuarioRegistroGUI() throws NamingException {
        calendarioContenedor.add(fechaChooser);//agrego el calendario al panel
        for (Perfil p : Conexion.obtenerPerfilBean().obtenerPerfiles()){
            perfil.addItem(p);
        }
        for (Institucion i : Conexion.obtenerInstitucionBean().obtenerInstituciones()){
            institucion.addItem(i);
        }
        for (Estados e : Estados.values()){
            estado.addItem(e);
        }

        agregarUsuarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Usuario user = new Usuario();
                user.setCedula(cedula.getText());
                Perfil perfilAgregar = (Perfil) perfil.getSelectedItem();
                user.setIdPerfil(perfilAgregar);
                Institucion institucionAgregar = (Institucion) institucion.getSelectedItem();
                user.setIdInstitucion(institucionAgregar);
                user.setEmail(email.getText());
                user.setContrasenia(passwordField1.getText());
                Date fechaElegida = (Date) fechaChooser.getDate();
                LocalDate localDate = fechaElegida.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                user.setFechaNacimiento(localDate);

                user.setEstado((Estados) estado.getSelectedItem());
                user.setNombre(nombre.getText());
                user.setApellido(apellido.getText());
                try {
                    Conexion.obtenerUsuarioBean().crearUsuario(user);
                    // informar que usuario se registro
                    JOptionPane.showMessageDialog(null, "Usuario registrado con exito");
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
}

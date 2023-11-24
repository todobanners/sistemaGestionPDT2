/*







                                        Este requisito no va implementado, este registro debe sacarse
                                        //TODO: Borrar UsuarioRegistroGUI.java







*/

package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.DTO.InstitucionDto;
import codigocreativo.uy.servidorapp.DTO.PerfilDto;
import codigocreativo.uy.servidorapp.DTO.UsuarioDto;
import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.github.lgooddatepicker.components.DatePicker;
import org.example.modelo.Conexion;
import org.example.modelo.Utilidades;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    DatePicker fechaChooser = Utilidades.createCustomDatePicker();

    public JPanel getPanel(){
        return registrarUsuario;
    }

    public UsuarioRegistroGUI() throws NamingException {

        calendarioContenedor.add(fechaChooser);//agrego el calendario al panel

        generadorCombos();

        agregarUsuarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UsuarioDto user = new UsuarioDto();
                user.setCedula(cedula.getText());
                PerfilDto perfilAgregar = (PerfilDto) perfil.getSelectedItem();
                user.setIdPerfil(perfilAgregar);
                InstitucionDto institucionAgregar = (InstitucionDto) institucion.getSelectedItem();
                user.setIdInstitucion(institucionAgregar);
                user.setEmail(email.getText());
                user.setContrasenia(passwordField1.getText());
                user.setFechaNacimiento(fechaChooser.getDate());
                user.setEstado((Estados) estado.getSelectedItem());
                user.setNombre(nombre.getText());
                user.setApellido(apellido.getText());
                user.setNombreUsuario(nombre.getText().toLowerCase()+"."+apellido.getText().toLowerCase());
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

    public void generadorCombos() throws NamingException {
        //genero el combo de perfiles
        for (PerfilDto p : Conexion.obtenerPerfilBean().obtenerPerfiles()){
            perfil.addItem(p);
        }
        for (InstitucionDto i : Conexion.obtenerInstitucionBean().obtenerInstituciones()){
            institucion.addItem(i);
        }
        for (Estados e : Estados.values()){
            estado.addItem(e);
        }
    }
}

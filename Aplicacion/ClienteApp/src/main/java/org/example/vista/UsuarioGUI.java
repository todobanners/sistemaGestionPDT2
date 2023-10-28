package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Usuario;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioGUI {
    private JList list1;
    private JPanel userGUI;
    private JTextField cedula;
    private JComboBox comboBoxPerfil;
    private JComboBox comboBoxInstitucion;
    private JPasswordField pass1;
    private JPasswordField pass2;
    private JTextField email;
    private JComboBox ComboBoxEstado;
    private JTextField nombre;
    private JTextField apellido;
    private JButton enviarButton;
    private JButton cancelarButton;
    private JButton modificarButton;
    private JButton eliminarButton;

    public JPanel getPanel(){
        return userGUI;
    }

    public UsuarioGUI() throws NamingException {/*
        list1.setListData(Conexion.obtenerUsuarioBean().obtenerUsuarios().toArray());
        for (Perfil p : Conexion.obtenerPerfilBean().obtenerPerfiles()){
            comboBoxPerfil.addItem(p.getNombrePerfil());
        }
        for (Institucion i : Conexion.obtenerInstitucionBean().obtenerInstituciones()){
            comboBoxInstitucion.addItem(i.getNombre());
        }*/
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Usuario user = new Usuario();
                    user.setCedula(cedula.getText());
                    Perfil perfil = (Perfil) comboBoxPerfil.getSelectedItem();
                    user.setIdPerfil(perfil);
                    Institucion institucion = (Institucion) comboBoxInstitucion.getSelectedItem();
                    user.setIdInstitucion(institucion);
                    user.setEmail(email.getText());
                    user.setContrasenia(pass1.getText());
                    user.setEstado(ComboBoxEstado.getSelectedItem().toString());
                    user.setNombre(nombre.getText());
                    user.setApellido(apellido.getText());
                    //Conexion.obtenerUsuarioBean().crearUsuario(user);



                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //crearemos una institucion y un perfil
//y luego lo agregaremos a la base de datos
                try {
                    Perfil perfil = new Perfil();
                    perfil.setNombrePerfil("Administrador");
                    perfil.setEstado("Activo");
                   // Conexion.obtenerPerfilBean().crearPerfil(perfil);
                    Perfil perfil2 = new Perfil();
                    perfil2.setNombrePerfil("Usuario");
                    perfil2.setEstado("Activo");
                    //Conexion.obtenerPerfilBean().crearPerfil(perfil2);
                    Institucion institucion = new Institucion();
                    Institucion institucion2 = new Institucion();
                    institucion2.setNombre("Hospital de Clínicas");
                    institucion.setNombre("Hospital Alfredo Vidal y Fuentes");
                   // Conexion.obtenerInstitucionBean().agregarInstitucion(institucion);
                    //Conexion.obtenerInstitucionBean().agregarInstitucion(institucion2);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}

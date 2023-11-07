package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import com.toedter.calendar.JDateChooser;
import org.example.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class UsuarioGUI {
    private JPanel userGUI;
    private JScrollPane scrollTabla;
    private JTable tableUsuarios;
    private JComboBox comboEstado;
    private JComboBox comboFiltro;
    private JTextField textFieldValorFiltro;
    private JButton aplicarButton;
    private JButton Limpiar;
    private JPanel panel;
    JDateChooser fechaChooser = new JDateChooser();

    public JPanel getPanel() {
        return userGUI;
    }

    public UsuarioGUI() throws NamingException {
        //Se muestra un listado con los usuarios registrados en la tabla

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Cedula");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("email");
        model.addColumn("Fecha nacimiento");
        model.addColumn("Estado");
        model.addColumn("Institucion");
        model.addColumn("Tipo de user");
        tableUsuarios.setModel(model);
        //le introducimos datos a la tabla
        List listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuarios();

        for (int i = 0; i < listaUsuarios.size(); i++) {
            Usuario usuario = (Usuario) listaUsuarios.get(i);
            Object[] data = new Object[9];
            data[0] = usuario.getId();
            data[1] = usuario.getCedula();
            data[2] = usuario.getNombre();
            data[3] = usuario.getApellido();
            data[4] = usuario.getEmail();
            data[5] = usuario.getFechaNacimiento();
            data[6] = usuario.getEstado();
            data[7] = usuario.getIdInstitucion().getNombre();
            data[8] = usuario.getIdPerfil().getNombrePerfil();
            model.addRow(data);
        }

        comboEstado.addItem("sin validar");
        comboEstado.addItem("activo");
        comboEstado.addItem("eliminado");


        comboFiltro.addItem("nombre");
        comboFiltro.addItem("apellido");
        comboFiltro.addItem("email");


        aplicarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);//limpia la tabla
                //Se muestra un listado con los usuarios registrados en la tabla
                tableUsuarios.setModel(model);
                //le introducimos datos a la tabla
                List listaUsuarios = null;
                try {
                    listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuariosFiltrado((String) comboFiltro.getSelectedItem(), textFieldValorFiltro.getText());
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }

                for (int i = 0; i < listaUsuarios.size(); i++) {
                    Usuario usuario = (Usuario) listaUsuarios.get(i);
                    Object[] data = new Object[9];
                    data[0] = usuario.getId();
                    data[1] = usuario.getCedula();
                    data[2] = usuario.getNombre();
                    data[3] = usuario.getApellido();
                    data[4] = usuario.getEmail();
                    data[5] = usuario.getFechaNacimiento();
                    data[6] = usuario.getEstado();
                    data[7] = usuario.getIdInstitucion().getNombre();
                    data[8] = usuario.getIdPerfil().getNombrePerfil();
                    model.addRow(data);
                }
            }
        });
        Limpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*// se crean unos datos de prueba para mostrar en la tabla
                Perfil perfil = new Perfil();
                perfil.setNombrePerfil("Administrador");
                perfil.setEstado("alta");
                Perfil perfil2 = new Perfil();
                perfil2.setNombrePerfil("Usuario");
                perfil2.setEstado("alta");
                Perfil perfil3 = new Perfil();
                perfil3.setEstado("alta");
                perfil3.setNombrePerfil("Invitado");
                try {
                    Conexion.obtenerPerfilBean().crearPerfil(perfil);
                    Conexion.obtenerPerfilBean().crearPerfil(perfil2);
                    Conexion.obtenerPerfilBean().crearPerfil(perfil3);
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }
                Institucion institucion = new Institucion();
                institucion.setNombre("UTEC");
                Institucion institucion2 = new Institucion();
                institucion2.setNombre("UDELAR");
                Institucion institucion3 = new Institucion();
                institucion3.setNombre("ORT");
                try {
                    Conexion.obtenerInstitucionBean().agregarInstitucion(institucion);
                    Conexion.obtenerInstitucionBean().agregarInstitucion(institucion2);
                    Conexion.obtenerInstitucionBean().agregarInstitucion(institucion3);
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }*/
            }
        });
        comboEstado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);//limpia la tabla
                //Se muestra un listado con los usuarios registrados en la tabla
                tableUsuarios.setModel(model);
                //le introducimos datos a la tabla
                List listaUsuarios = null;
                try {
                    listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuariosFiltrado("estado", (String) comboEstado.getSelectedItem());
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }

                for (int i = 0; i < listaUsuarios.size(); i++) {
                    Usuario usuario = (Usuario) listaUsuarios.get(i);
                    Object[] data = new Object[9];
                    data[0] = usuario.getId();
                    data[1] = usuario.getCedula();
                    data[2] = usuario.getNombre();
                    data[3] = usuario.getApellido();
                    data[4] = usuario.getEmail();
                    data[5] = usuario.getFechaNacimiento();
                    data[6] = usuario.getEstado();
                    data[7] = usuario.getIdInstitucion().getNombre();
                    data[8] = usuario.getIdPerfil().getNombrePerfil();
                    model.addRow(data);
                }
            }
        });
    }
}

package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.toedter.calendar.JDateChooser;
import org.example.modelo.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class UsuarioGUI {
    private JPanel userGUI;
    private JComboBox filtroEstadoCombo;
    private JComboBox filtroTipoCombo;
    private JTextField filtroNombre;
    private JTextField filtroApellido;
    private JTextField filtroUsername;
    private JTextField filtroEmail;
    private JButton filtroLimpiarBoton;
    private JButton filtroFiltrarBoton;
    private JTable tableUsuarios;

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
            data[7] = usuario.getIdInstitucion();
            data[8] = usuario.getIdPerfil().getNombrePerfil();
            model.addRow(data);
        }

        for (Estados e : Estados.values()) {
            filtroEstadoCombo.addItem(e);
        }

        filtroFiltrarBoton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);//limpia la tabla
                //Se muestra un listado con los usuarios registrados en la tabla
                tableUsuarios.setModel(model);
                //le introducimos datos a la tabla
                List listaUsuarios = null;
                //listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuariosFiltrado((String) comboFiltro.getSelectedItem(), textFieldValorFiltro.getText());

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
        filtroLimpiarBoton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //TODO: Colocar funcion de limpiar
            }
        });
        /*comboEstado.addActionListener(new ActionListener() {
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
        });*/
    }
}

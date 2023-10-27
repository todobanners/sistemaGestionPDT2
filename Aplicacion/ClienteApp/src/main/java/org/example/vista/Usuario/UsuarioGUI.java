package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.entidades.Usuario;
import com.toedter.calendar.JDateChooser;
import org.example.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.List;


public class UsuarioGUI {
    private JPanel userGUI;
    private JScrollPane scrollTabla;
    private JTable tableUsuarios;
    private JPanel panel;
    JDateChooser fechaChooser = new JDateChooser();

    public JPanel getPanel() {
        return userGUI;
    }

    public UsuarioGUI() throws NamingException {
        //Se muestra un listado con los usuarios registrados en la tabla

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("email");
        model.addColumn("Fecha nacimiento");
        model.addColumn("Estado");
        model.addColumn("Institucion");
        model.addColumn("Perfil");
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






    }
}

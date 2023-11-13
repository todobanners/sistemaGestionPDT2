package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.toedter.calendar.JDateChooser;
import org.example.modelo.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
/*
    TODO:
        * En filtrado queda formatear la consulta para que en la
          seleccion de filtro coincida con los campos de la tabla           //Realizado
        * Se puede hacer un switch case para cada tipo de filtro            //Realizado
        * Falta que cuando se elija email se valide que sea un email
        *falta filtrar por ID
        * En el listado queda detalle estetico de que campo ID sea
          mas pequeño
        * En Acciones falta la funcionalidad del boton Editar y Borrar
        * Falta que se muestre el calendario                                //Realizado
        * Falta que se muestre el combo de institucion                      //Realizado
        * Falta las validaciones y mensajes de error
        * Falta el telefono, tanto en registro como en visualizacion

        Estas tareas estan asignadas a Eduardo
*/

public class UsuarioGUI {
    private JPanel userGUI;
    private JComboBox filtroEstadoCombo;
    private JComboBox filtroTipoCombo;
    private JTextField filtroValor;
    private JButton filtroLimpiarBoton;
    private JButton filtroFiltrarBoton;
    private JTable tableUsuarios;
    private JComboBox filtroBuscarCombo;
    private JTextField accCampoNombre;
    private JButton borrarSeleccionadoButton;
    private JButton editarSeleccionadoButton;
    private JTextField accCampoApellido;
    private JTextField accCampoCedula;
    private JTextField accCampoEmail;
    private JTextField accCampoTelefono;
    private JPanel accContenedorFecha;
    private JComboBox accComboPerfil;
    private JComboBox accComboInstitucion;
    private JComboBox accComboEstado;
    private JTextField accCampoUsername;
    private JButton limpiarCamposButton;

    JDateChooser fechaChooser = new JDateChooser();
    public JPanel getPanel() {
        return userGUI;
    }

    public UsuarioGUI() throws NamingException {
        //Se muestra un listado con los usuarios registrados en la tabla
        List<Usuario> tabla = Conexion.obtenerUsuarioBean().obtenerUsuarios();
        generarTabla(tabla);
        cargarCombos();
        accContenedorFecha.add(fechaChooser);//agrego el calendario al panel

        filtroFiltrarBoton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Usuario> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuariosFiltrado(filtroBuscador(filtroBuscarCombo.getSelectedIndex()), filtroValor.getText());
                    generarTabla(listaUsuarios);
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        filtroLimpiarBoton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFiltros();
                try {
                    List<Usuario> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuarios();
                    generarTabla(listaUsuarios);
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        filtroEstadoCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Estados estado = (Estados) filtroEstadoCombo.getSelectedItem();

                    List<Usuario> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuariosFiltrado("estado", estado.toString());
                    generarTabla(listaUsuarios);
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        tableUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int fila = tableUsuarios.getSelectedRow();
                Long id = (Long) tableUsuarios.getValueAt(fila, 0);
                Usuario usuario = null;
                try {
                    usuario = Conexion.obtenerUsuarioBean().obtenerUsuario(id);
                    accCampoNombre.setText(usuario.getNombre());
                    accCampoApellido.setText(usuario.getApellido());
                    accCampoCedula.setText(usuario.getCedula());
                    accCampoEmail.setText(usuario.getEmail());
                    //accCampoTelefono.setText(usuario.getTelefono());

                    Perfil perfil = Conexion.obtenerPerfilBean().obtenerPerfil(usuario.getIdPerfil().getId());
                    // Obtener el ID del perfil que quieres seleccionar en el JComboBox
                    Long perfilId = perfil.getId();
                    // Recorrer el JComboBox para encontrar el objeto Perfil con el ID correspondiente
                    for (int i = 0; i < accComboPerfil.getItemCount(); i++) {
                        Perfil item = (Perfil) accComboPerfil.getItemAt(i);
                        if (Objects.equals(item.getId(), perfilId)) {
                            accComboPerfil.setSelectedItem(item);
                            break; // Una vez encontrado, se sale del bucle
                        }
                    }

                    Institucion institucion = Conexion.obtenerInstitucionBean().obtenerInstitucionPorId(usuario.getIdInstitucion().getId());
                    Long institucionId = institucion.getId();
                    // Recorrer el JComboBox para encontrar el objeto Institucion con el ID correspondiente
                    for (int i = 0; i < accComboInstitucion.getItemCount(); i++) {
                        Institucion item = (Institucion) accComboInstitucion.getItemAt(i);
                        if (item.getId() == institucionId) {
                            accComboInstitucion.setSelectedItem(item);
                            break; // Una vez encontrado, se sale del bucle
                        }
                    }
                    accComboEstado.setSelectedItem(usuario.getEstado());
                    accCampoUsername.setText(usuario.getNombreUsuario());
                    //convertir LocalDate a Date
                    Date fecha = Date.from(usuario.getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    fechaChooser.setDate(fecha);

                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        limpiarCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accCampoNombre.setText("");
                accCampoApellido.setText("");
                accCampoCedula.setText("");
                accCampoEmail.setText("");
                accCampoTelefono.setText("");
                accComboPerfil.setSelectedIndex(0);
                accComboInstitucion.setSelectedIndex(0);
                accComboEstado.setSelectedIndex(0);
                accCampoUsername.setText("");
                fechaChooser.setDate(null);
            }
        });
    }

    public void limpiarFiltros(){
        filtroEstadoCombo.setSelectedIndex(0);
        filtroTipoCombo.setSelectedIndex(0);
        filtroValor.setText("");
        fechaChooser.setDate(null);
    }

    public void generarTabla(List tabla) throws NamingException {
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
        model.setRowCount(0);
        for (int i = 0; i < tabla.size(); i++) {
            Usuario usuario = (Usuario) tabla.get(i);
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

    public void cargarCombos() throws NamingException {
        //Se cargan los combos de estado
        for (Estados e : Estados.values()) {
            filtroEstadoCombo.addItem(e);
            accComboEstado.addItem(e);
        }
        //Se cargan los combos de tipo de usuario
        for (Perfil p : Conexion.obtenerPerfilBean().obtenerPerfiles()) {
            filtroTipoCombo.addItem(p);
            accComboPerfil.addItem(p);
        }
        //Se cargan los combos de institucion
        for (Institucion i : Conexion.obtenerInstitucionBean().obtenerInstituciones()) {
            accComboInstitucion.addItem(i);
        }
        //Se cargan los combos de buscar por
        filtroBuscarCombo.addItem("Nombre");
        filtroBuscarCombo.addItem("Apellido");
        filtroBuscarCombo.addItem("Nombre de usuario");
        filtroBuscarCombo.addItem("email");
    }

    public String filtroBuscador(int campo){
        String eleccion = "";
        switch (campo){
            case 0:
                //Filtro por nombre
                eleccion = "nombre";
                break;
            case 1:
                //Filtro por apellido
                eleccion = "apellido";
                break;
            case 2:
                //Filtro por nombre de usuario
                eleccion = "nombreUsuario";
                break;
            case 3:
                //Filtro por email
                eleccion = "email";
                break;
        }
        return eleccion;
    }
}

package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.github.lgooddatepicker.components.DatePicker;
import com.toedter.calendar.JDateChooser;
import org.example.modelo.Conexion;
import org.example.modelo.DatePickerUtil;
import org.example.modelo.Validator;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
    TODO:
        * Falta las validaciones y mensajes de error
        * Falta el telefono, tanto en registro como en visualizacion

        Estas tareas estan asignadas a Eduardo
*/

public class UsuarioGUI {
    private JPanel userGUI;
    private JPanel accContenedorFecha;

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

    private JComboBox accComboPerfil;
    private JComboBox accComboInstitucion;
    private JComboBox accComboEstado;
    private JTextField accCampoUsername;
    private JButton limpiarCamposButton;
    private JTextField accCampoID;

    DatePicker fechaChooser = DatePickerUtil.createCustomDatePicker();
    public JPanel getPanel() {
        return userGUI;
    }

    public UsuarioGUI() throws NamingException {
        //Se muestra un listado con los usuarios registrados en la tabla
        List<Usuario> tabla = Conexion.obtenerUsuarioBean().obtenerUsuarios();
        generarTabla(tabla);
        cargarCombos();
        accContenedorFecha.add(fechaChooser);//agrego el calendario al panel

        filtroFiltrarBoton.addActionListener(e -> {
            try {
                //Verificar si se selecciono el campo email y validar que sea un email
                if (filtroBuscarCombo.getSelectedIndex() == 3 && !Validator.validarEmail(filtroValor.getText())) {
                    JOptionPane.showMessageDialog(null, "El campo debe ser un email valido");
                } else if (Validator.contieneSoloLetras(filtroValor.getText()) && filtroBuscarCombo.getSelectedIndex() == 0 || filtroBuscarCombo.getSelectedIndex() == 1) {
                    JOptionPane.showMessageDialog(null, "El campo no puede contener numeros");
                } else {
                    List<Usuario> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuariosFiltrado(filtroBuscador(filtroBuscarCombo.getSelectedIndex()), filtroValor.getText());
                    generarTabla(listaUsuarios);
                }
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        });
        filtroLimpiarBoton.addActionListener(e -> {
            limpiarFiltros();
            try {
                List<Usuario> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuarios();
                generarTabla(listaUsuarios);
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        });
        filtroEstadoCombo.addActionListener(e -> {
            try {
                Estados estado = (Estados) filtroEstadoCombo.getSelectedItem();

                List<Usuario> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuariosFiltrado("estado", estado.toString());
                generarTabla(listaUsuarios);
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
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
                    Perfil perfil = Conexion.obtenerPerfilBean().obtenerPerfil(usuario.getIdPerfil().getId());
                    // Obtener el ID del perfil que quieres seleccionar en el JComboBox
                    Long perfilId = perfil.getId();
                    Institucion institucion = Conexion.obtenerInstitucionBean().obtenerInstitucionPorId(usuario.getIdInstitucion().getId());
                    Long institucionId = institucion.getId();
                    Date fecha = Date.from(usuario.getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant());

                    accCampoNombre.setText(usuario.getNombre());
                    accCampoApellido.setText(usuario.getApellido());
                    accCampoCedula.setText(usuario.getCedula());
                    accCampoEmail.setText(usuario.getEmail());
                    //accCampoTelefono.setText(usuario.getTelefono());

                    // Recorrer el JComboBox para encontrar el objeto Perfil con el ID correspondiente
                    for (int i = 0; i < accComboPerfil.getItemCount(); i++) {
                        Perfil item = (Perfil) accComboPerfil.getItemAt(i);
                        if (Objects.equals(item.getId(), perfilId)) {
                            accComboPerfil.setSelectedItem(item);
                            break; // Una vez encontrado, se sale del bucle
                        }
                    }

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
                    accCampoID.setText(usuario.getId().toString());
                    fechaChooser.setDate(usuario.getFechaNacimiento());
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        limpiarCamposButton.addActionListener(e -> {
            accCampoNombre.setText("");
            accCampoApellido.setText("");
            accCampoCedula.setText("");
            accCampoEmail.setText("");
            accCampoTelefono.setText("");
            accComboPerfil.setSelectedIndex(0);
            accComboInstitucion.setSelectedIndex(0);
            accComboEstado.setSelectedIndex(0);
            accCampoUsername.setText("");
            accCampoID.setText("");
            fechaChooser.setDate(null);
        });
        editarSeleccionadoButton.addActionListener(e -> {
            Long id = Long.valueOf(accCampoID.getText());
            Usuario usuario = null;

            if (accCampoNombre.getText().isEmpty() || accCampoApellido.getText().isEmpty() || accCampoCedula.getText().isEmpty() || accCampoEmail.getText().isEmpty() || accCampoTelefono.getText().isEmpty() || fechaChooser.getDate() == null){
                JOptionPane.showMessageDialog(null, "Debe completar todos los campos");
            }
            // Verifico que el nombre y apellido contengan solo letras
            else if (Validator.contieneSoloLetras(accCampoNombre.getText()) || Validator.contieneSoloLetras(accCampoApellido.getText())){
                JOptionPane.showMessageDialog(null, "Los campos nombre y apellido solo pueden contener letras");
            }
            // Verifico que la cedula sea valida
            else if (!Validator.validarCedula(accCampoCedula.getText())){
                JOptionPane.showMessageDialog(null, "La cédula ingresada no es válida");
            }
            // Verifico que el email sea valido
            else if (!Validator.validarEmail(accCampoEmail.getText())){
                JOptionPane.showMessageDialog(null, "El email ingresado no es valido");
            }
            // Verifico que el telefono contenga solo numeros
            else if (Validator.contieneSoloNumeros(accCampoTelefono.getText())){
                JOptionPane.showMessageDialog(null, "El campo telefono solo puede contener numeros");
            } else {

                try {
                    usuario = Conexion.obtenerUsuarioBean().obtenerUsuario(id);
                    usuario.setNombre(accCampoNombre.getText());
                    usuario.setApellido(accCampoApellido.getText());
                    usuario.setCedula(accCampoCedula.getText());
                    usuario.setEmail(accCampoEmail.getText());
                    //usuario.setTelefono(accCampoTelefono.getText());
                    usuario.setIdPerfil((Perfil) accComboPerfil.getSelectedItem());
                    usuario.setIdInstitucion((Institucion) accComboInstitucion.getSelectedItem());
                    usuario.setEstado((Estados) accComboEstado.getSelectedItem());
                    usuario.setNombreUsuario(accCampoUsername.getText());
                    usuario.setFechaNacimiento(fechaChooser.getDate());

                    Conexion.obtenerUsuarioBean().modificarUsuario(usuario);
                    JOptionPane.showMessageDialog(null, "Usuario modificado con exito");

                    limpiarCamposButton.doClick();
                    List<Usuario> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuarios();
                    generarTabla(listaUsuarios);

                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }



            }

        });
        borrarSeleccionadoButton.addActionListener(e -> {
            Long id = Long.valueOf(accCampoID.getText());
            Usuario usuario = null;
            //consultar al usuario si realmente desea borrar el usuario
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "¿Esta seguro que desea eliminar el usuario?","Warning",dialogButton);
            if(dialogResult == JOptionPane.NO_OPTION){
                JOptionPane.showMessageDialog(null, "Operacion cancelada");
            }else if(dialogResult == JOptionPane.YES_OPTION){
                try {
                    usuario = Conexion.obtenerUsuarioBean().obtenerUsuario(id);
                    Conexion.obtenerUsuarioBean().eliminarUsuario(usuario);
                    JOptionPane.showMessageDialog(null, "Usuario eliminado con exito");
                    List<Usuario> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuarios();
                    generarTabla(listaUsuarios);
                    limpiarCamposButton.doClick();
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        filtroTipoCombo.addActionListener(e -> {
            try {
                //obtenemos el id de la seleccion del combo
                String idPerfil = ((Perfil) filtroTipoCombo.getSelectedItem()).getId().toString();

                List<Usuario> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuariosFiltrado("idPerfil", idPerfil);
                generarTabla(listaUsuarios);
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
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

        //Se le asignan los nombres a las columnas
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
        TableColumn columna = tableUsuarios.getColumnModel().getColumn(0);
        columna.setPreferredWidth(30);
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
            filtroEstadoCombo.addItem(e.getValor());
            accComboEstado.addItem(e.getValor());
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
        filtroBuscarCombo.addItem("Email");
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

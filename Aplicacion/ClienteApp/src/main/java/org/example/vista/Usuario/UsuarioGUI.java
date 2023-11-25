package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.DTO.InstitucionDto;
import codigocreativo.uy.servidorapp.DTO.PerfilDto;
import codigocreativo.uy.servidorapp.DTO.UsuarioDto;
import codigocreativo.uy.servidorapp.DTO.UsuariosTelefonoDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.github.lgooddatepicker.components.DatePicker;
import org.example.modelo.Conexion;
import org.example.modelo.Utilidades;
import org.example.modelo.Validator;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/*
    TODO:
        * Faltan las validaciones y mensajes de error
        * Falta el teléfono, tanto en registro como en visualización

        Estas tareas están asignadas a Eduardo
*/

public class UsuarioGUI {
    private JPanel userGUI;
    private JPanel accContenedorFecha;

    private JComboBox<Estados> filtroEstadoCombo;
    private JComboBox<PerfilDto> filtroTipoCombo;
    private JTextField filtroValor;
    private JButton filtroLimpiarBoton;
    private JButton filtroFiltrarBoton;
    private JTable tableUsuarios;
    private JComboBox<String> filtroBuscarCombo;
    private JTextField accCampoNombre;
    private JButton borrarSeleccionadoButton;
    private JButton editarSeleccionadoButton;
    private JTextField accCampoApellido;
    private JTextField accCampoCedula;
    private JTextField accCampoEmail;
    private JTextField accCampoTelefono;

    private JComboBox<PerfilDto> accComboPerfil;
    private JComboBox<InstitucionDto> accComboInstitucion;
    private JComboBox<Estados> accComboEstado;
    private JTextField accCampoUsername;
    private JButton limpiarCamposButton;
    private JTextField accCampoID;
    private JButton buttonEditarTelefono;

    DatePicker fechaChooser = Utilidades.createCustomDatePicker();
    public JPanel getPanel() {
        return userGUI;
    }

    public UsuarioGUI() throws NamingException {
        // Se muestra un listado con los usuarios registrados en la tabla
        List<UsuarioDto> tabla = Conexion.obtenerUsuarioBean().obtenerUsuarios();
        generarTabla(tabla);
        cargarCombos();
        accContenedorFecha.add(fechaChooser); // agrego el calendario al panel

        filtroFiltrarBoton.addActionListener(e -> {
            try {
                // Verificar si se seleccionó el campo email y validar que sea un email
                if (filtroValor.getText().isEmpty()) {//el campo no puede estar vacio
                    JOptionPane.showMessageDialog(null, "El campo Valor a filtrar no puede estar vacío");
                } else if (Validator.contieneSoloLetras(filtroValor.getText()) && filtroBuscarCombo.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "El campo no puede contener números");
                } else if (Validator.contieneSoloLetras(filtroValor.getText()) && filtroBuscarCombo.getSelectedIndex() == 1) {
                    JOptionPane.showMessageDialog(null, "El campo no puede contener números");
                } else if (filtroValor.getText().isEmpty()) {//el campo no puede estar vacio
                    JOptionPane.showMessageDialog(null, "El campo Valor a filtrar no puede estar vacío");
                } else if (filtroBuscarCombo.getSelectedIndex() == 3 && !Validator.validarEmail(filtroValor.getText())) {
                    JOptionPane.showMessageDialog(null, "El campo debe ser un email válido");
                }else {
                    List<UsuarioDto> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuariosFiltrado(filtroBuscador(filtroBuscarCombo.getSelectedIndex()), filtroValor.getText());
                    generarTabla(listaUsuarios);
                }
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        });
        filtroLimpiarBoton.addActionListener(e -> {
            limpiarFiltros();
            try {
                List<UsuarioDto> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuarios();
                generarTabla(listaUsuarios);
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        });
        filtroEstadoCombo.addActionListener(e -> {
            try {
                // Estados estado = Estados.valueOf((String) filtroEstadoCombo.getSelectedItem());

                List<UsuarioDto> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuariosPorEstado((Estados) filtroEstadoCombo.getSelectedItem());
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
                Long id = (Long) tableUsuarios.getModel().getValueAt(fila, 0);
                UsuarioDto usuario;
                try {
                    usuario = Conexion.obtenerUsuarioBean().obtenerUsuario(id);
                    PerfilDto perfil = Conexion.obtenerPerfilBean().obtenerPerfil(usuario.getIdPerfil().getId());
                    // Obtener el ID del perfil que quieres seleccionar en el JComboBox
                    Long perfilId = perfil.getId();
                    InstitucionDto institucion = Conexion.obtenerInstitucionBean().obtenerInstitucionPorId(usuario.getIdInstitucion().getId());
                    Long institucionId = institucion.getId();
                    fechaChooser.setDate(usuario.getFechaNacimiento());
                    accCampoNombre.setText(usuario.getNombre());
                    accCampoApellido.setText(usuario.getApellido());
                    accCampoCedula.setText(usuario.getCedula());
                    accCampoEmail.setText(usuario.getEmail());


                    //Obtener los telefonos del usuario
                    Set<UsuariosTelefonoDto> telefonos = usuario.getUsuariosTelefonos();
                    String telefonosString = "";
                    for (UsuariosTelefonoDto telefono : telefonos) {
                        telefonosString += telefono.getNumero() + ",";
                    }
                    //Eliminar la ultima coma
                    if (!telefonosString.isEmpty()) {
                        telefonosString = telefonosString.substring(0, telefonosString.length() - 1);
                    }
                    accCampoTelefono.setText(telefonosString);



                    // accCampoTelefono.setText(usuario.getTelefono());

                    // Recorrer el JComboBox para encontrar el objeto Perfil con el ID correspondiente
                    for (int i = 0; i < accComboPerfil.getItemCount(); i++) {
                        PerfilDto item = accComboPerfil.getItemAt(i);
                        if (Objects.equals(item.getId(), perfilId)) {
                            accComboPerfil.setSelectedItem(item);
                            break; // Una vez encontrado, se sale del bucle
                        }
                    }

                    // Recorrer el JComboBox para encontrar el objeto Institución con el ID correspondiente
                    for (int i = 0; i < accComboInstitucion.getItemCount(); i++) {
                        InstitucionDto item = accComboInstitucion.getItemAt(i);
                        if (Objects.equals(item.getId(), institucionId)) {
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
            UsuarioDto usuario;

            if (accCampoNombre.getText().isEmpty() || accCampoApellido.getText().isEmpty() || accCampoCedula.getText().isEmpty() || accCampoEmail.getText().isEmpty() || accCampoTelefono.getText().isEmpty() || fechaChooser.getDate() == null){
                JOptionPane.showMessageDialog(null, "Debe completar todos los campos");
            }
            // Verifico que el nombre y apellido contengan solo letras
            else if (Validator.contieneSoloLetras(accCampoNombre.getText()) || Validator.contieneSoloLetras(accCampoApellido.getText())){
                JOptionPane.showMessageDialog(null, "Los campos nombre y apellido solo pueden contener letras");
            }
            // Verifico que la cédula sea válida
            else if (!Validator.validarCedula(accCampoCedula.getText())){
                JOptionPane.showMessageDialog(null, "La cédula ingresada no es válida");
            }
            // Verifico que el email sea válido
            else if (!Validator.validarEmail(accCampoEmail.getText())){
                JOptionPane.showMessageDialog(null, "El email ingresado no es válido");
            }
            // Verifico que el teléfono contenga solo números
            else if (Validator.contieneSoloNumeros(accCampoTelefono.getText())){
                JOptionPane.showMessageDialog(null, "El campo teléfono solo puede contener números");
            } else {
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de modificar los datos?", "Confirmar Modificación", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        usuario = Conexion.obtenerUsuarioBean().obtenerUsuario(id);
                        usuario.setNombre(accCampoNombre.getText());
                        usuario.setApellido(accCampoApellido.getText());
                        usuario.setCedula(accCampoCedula.getText());
                        usuario.setEmail(accCampoEmail.getText());
                        // usuario.setTelefono(accCampoTelefono.getText());
                        usuario.setIdPerfil((PerfilDto) accComboPerfil.getSelectedItem());
                        usuario.setIdInstitucion((InstitucionDto) accComboInstitucion.getSelectedItem());
                        Estados estado = (Estados) accComboEstado.getSelectedItem();
                        usuario.setEstado(estado);
                        usuario.setNombreUsuario(accCampoUsername.getText());
                        usuario.setFechaNacimiento(fechaChooser.getDate());

                        List<String> tel = Arrays.asList(accCampoTelefono.getText().split(","));
                        Set<UsuariosTelefonoDto> telefonosDto = new LinkedHashSet<>();
                        for (String telefono : tel) {
                            UsuariosTelefonoDto telefonoDto = new UsuariosTelefonoDto();
                            telefonoDto.setNumero(telefono);
                            telefonoDto.setIdUsuario(usuario);
                            telefonosDto.add(telefonoDto);
                        }
                        usuario.setUsuariosTelefonos(telefonosDto);

                        Conexion.obtenerUsuarioBean().modificarUsuario(usuario);
                        JOptionPane.showMessageDialog(null, "Usuario modificado con éxito");

                        limpiarCamposButton.doClick();
                        List<UsuarioDto> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuarios();
                        generarTabla(listaUsuarios);

                    } catch (NamingException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        buttonEditarTelefono.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Obtener los telefonos del textfield asumiendo que estan separados por comas
                String telefonos = accCampoTelefono.getText();
                List<String> listaTelefonos = Arrays.asList(telefonos.split(","));

                ModificarTelefonos modificarTelefonos = new ModificarTelefonos(listaTelefonos);

                modificarTelefonos.pack();

                modificarTelefonos.setVisible(true);

                //Obtener los telefonos ingresados
                List<String> telefonosIngresados = modificarTelefonos.getTelefonos();

                //Limpiar el textfield
                accCampoTelefono.setText("");

                //Agregar los telefonos al textfield
                if (!telefonosIngresados.isEmpty()) {
                    for (String telefono : telefonosIngresados) {
                        UsuarioGUI.this.accCampoTelefono.setText(UsuarioGUI.this.accCampoTelefono.getText() + telefono + ",");
                    }
                    //Eliminar la ultima coma
                    accCampoTelefono.setText(accCampoTelefono.getText().substring(0, accCampoTelefono.getText().length() - 1));
                }

            }
        });

        borrarSeleccionadoButton.addActionListener(e -> {
            Long id = Long.valueOf(accCampoID.getText());
            UsuarioDto usuario;
            // consultar al usuario si realmente desea borrar el usuario
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "¿Está seguro que desea eliminar el usuario?","Advertencia",dialogButton);
            if(dialogResult == JOptionPane.NO_OPTION){
                JOptionPane.showMessageDialog(null, "Operación cancelada");
            }else if(dialogResult == JOptionPane.YES_OPTION){
                try {
                    usuario = Conexion.obtenerUsuarioBean().obtenerUsuario(id);
                    Conexion.obtenerUsuarioBean().eliminarUsuario(usuario);
                    JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito");
                    List<UsuarioDto> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuarios();
                    generarTabla(listaUsuarios);
                    limpiarCamposButton.doClick();
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        filtroTipoCombo.addActionListener(e -> {
            try {
                // obtenemos el id de la selección del combo
                Long idPerfil = ((PerfilDto) filtroTipoCombo.getSelectedItem()).getId();

                List<UsuarioDto> listaUsuarios = Conexion.obtenerUsuarioBean().obtenerUsuariosFiltrado("idPerfil.id", idPerfil);
                generarTabla(listaUsuarios);
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void limpiarFiltros(){
        filtroValor.setText("");
    }

    public void generarTabla(List<UsuarioDto> tabla) throws NamingException {
        DefaultTableModel model = new DefaultTableModel();

        // Se le asignan los nombres a las columnas
        model.addColumn("ID");
        model.addColumn("Cédula");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Email");
        model.addColumn("Fecha de nacimiento");
        model.addColumn("Estado");
        model.addColumn("Institución");
        model.addColumn("Tipo de usuario");
        tableUsuarios.setModel(model);

        model.setRowCount(0);
        for (UsuarioDto usuarioDto : tabla) {
            Object[] data = new Object[9];
            data[0] = usuarioDto.getId();
            data[1] = usuarioDto.getCedula();
            data[2] = usuarioDto.getNombre();
            data[3] = usuarioDto.getApellido();
            data[4] = usuarioDto.getEmail();
            data[5] = usuarioDto.getFechaNacimiento();
            data[6] = usuarioDto.getEstado();
            data[7] = usuarioDto.getIdInstitucion().getNombre();
            data[8] = usuarioDto.getIdPerfil().getNombrePerfil();
            model.addRow(data);
        }
        tableUsuarios.removeColumn(tableUsuarios.getColumnModel().getColumn(0));
    }

    public void cargarCombos() throws NamingException {
        // Se cargan los combos de estado
        for (Estados e : Estados.values()) {
            filtroEstadoCombo.addItem(e);
            accComboEstado.addItem(e);
        }
        // Se cargan los combos de tipo de usuario
        for (PerfilDto p : Conexion.obtenerPerfilBean().obtenerPerfiles()) {
            filtroTipoCombo.addItem(p);
            accComboPerfil.addItem(p);
        }
        // Se cargan los combos de institución
        for (InstitucionDto i : Conexion.obtenerInstitucionBean().obtenerInstituciones()) {
            accComboInstitucion.addItem(i);
        }
        // Se cargan los combos de buscar por
        filtroBuscarCombo.addItem("Nombre");
        filtroBuscarCombo.addItem("Apellido");
        filtroBuscarCombo.addItem("Nombre de usuario");
        filtroBuscarCombo.addItem("Email");
    }

    public String filtroBuscador(int campo){
        String eleccion = "";
        switch (campo){
            case 0:
                // Filtro por nombre
                eleccion = "nombre";
                break;
            case 1:
                // Filtro por apellido
                eleccion = "apellido";
                break;
            case 2:
                // Filtro por nombre de usuario
                eleccion = "nombreUsuario";
                break;
            case 3:
                // Filtro por email
                eleccion = "email";
                break;
        }
        return eleccion;
    }
}
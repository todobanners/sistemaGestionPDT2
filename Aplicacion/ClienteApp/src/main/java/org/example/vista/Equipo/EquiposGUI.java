package org.example.vista.Equipo;

import codigocreativo.uy.servidorapp.DTO.*;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.github.lgooddatepicker.components.DatePicker;
import org.example.controlador.Sesion;
import org.example.modelo.Conexion;
import org.example.modelo.Utilidades;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class EquiposGUI {
    private JPanel equipoPanel;
    private JScrollPane tablaScrollPane;
    private JPanel accionesPanel;
    private JTable equiposTable;
    private JPanel formularioPanel;
    private JTextField idInternoText;
    private JTextField nroSerieText;
    private JTextField nombreText;
    private JComboBox<Object> tipoCombo;
    private JComboBox<Object> proveedorCombo;
    private JComboBox<Object> paisCombo;
    private JComboBox<Object> modeloCombo;
    private JComboBox<Object> ubicacionCombo;
    private JComboBox<Object> estadoCombo;
    private JComboBox<Object> filtroComboUbicacion;
    private JComboBox<Object> filtroComboProveedor;
    private JComboBox<Object> filtroComboPais;
    private JComboBox<Object> filtroComboModelo;
    private JComboBox<Object> filtroComboTipo;
    private JComboBox<String> filtroComboSelectorBusqueda;
    private JButton imagenBtn;
    private JPanel fechaAdqContainer;
    private JButton guardarButton;
    private JButton darBajaSeleccionadoButton;
    private JButton editarSeleccionadoButton;
    private JButton registrarMovimientoButton;
    private JPanel panelDatePickerInicio;
    private JLabel filePathField;

    private JTextField filtroCampoValorBusqueda;
    private JButton botonBuscarFiltro;
    private JButton botonLimpiarFiltros;
    private JPanel panelDatePickerFin;
    private JButton exportarAExcelButton;
    private JButton listarMovimientosButton;

    private File imagenSubida;
    DatePicker fechaCompraDate = Utilidades.createCustomDatePicker();

    public JPanel getPanel() {
        return equipoPanel;
    }

    public EquiposGUI() throws Exception {
        //Cargar datos de la tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("ID Interno");
        model.addColumn("Ubicación");
        model.addColumn("Nro Serie");
        model.addColumn("Nombre");
        model.addColumn("Tipo");
        model.addColumn("Proveedor");
        model.addColumn("País");
        model.addColumn("Modelo");
        model.addColumn("Fecha Adquisición");
        equiposTable.setModel(model);
        actualizarTabla(Conexion.obtenerEquipoBean().listarEquipos());
        equiposTable.removeColumn(equiposTable.getColumnModel().getColumn(0));
        fechaAdqContainer.add(fechaCompraDate);
        cargarCombos();

        //Inicializar ambos datepickers de los filtros
        DatePicker fechaInicio = Utilidades.createCustomDatePicker();
        DatePicker fechaFin = Utilidades.createCustomDatePicker();
        panelDatePickerInicio.add(fechaInicio);
        panelDatePickerFin.add(fechaFin);

        imagenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    imagenSubida = fileChooser.getSelectedFile();
                    filePathField.setText(imagenSubida.getAbsolutePath());
                }
            }
        });

        editarSeleccionadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = equiposTable.getSelectedRow();
                if (filaSeleccionada != -1) {
                    long idEquipoSeleccionado = (long) equiposTable.getModel().getValueAt(filaSeleccionada, 0);

                    EquipoDto equipoSeleccionado = null;
                    try {
                        equipoSeleccionado = Conexion.obtenerEquipoBean().obtenerEquipo(idEquipoSeleccionado);
                    } catch (NamingException ex) {
                        throw new RuntimeException(ex);
                    }

                    ModificarEquipo modificarEquipo = null;
                    try {
                        modificarEquipo = new ModificarEquipo(equipoSeleccionado);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    modificarEquipo.mostrarVentana();
                } else {
                    JOptionPane.showMessageDialog(null, "El valor seleccionado no es un equipo válido");
                }
            }
        });

        darBajaSeleccionadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = equiposTable.getSelectedRow();
                if (filaSeleccionada != -1) {
                    long idEquipoSeleccionado = (long) equiposTable.getModel().getValueAt(filaSeleccionada, 0);

                    EquipoDto equipoSeleccionado = null;
                    try {
                        equipoSeleccionado = Conexion.obtenerEquipoBean().obtenerEquipo(idEquipoSeleccionado);
                    } catch (NamingException ex) {
                        throw new RuntimeException(ex);
                    }

                    // Asegúrate de crear la instancia correcta de BajaEquipoGUI
                    BajaEquipoGUI bajaEquipoPanel = null;
                    try {
                        bajaEquipoPanel = new BajaEquipoGUI(equipoSeleccionado);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    // Asegúrate de llamar al método correcto
                    bajaEquipoPanel.mostrarVentana();
                } else {
                    JOptionPane.showMessageDialog(null, "El valor seleccionado no es un equipo válido");
                }
            }
        });


        guardarButton.addActionListener(e -> {
            try {
                EquipoDto equipo = new EquipoDto();
                equipo.setIdInterno(idInternoText.getText());
                equipo.setIdUbicacion(((UbicacionDto) ubicacionCombo.getSelectedItem()));
                equipo.setNroSerie(nroSerieText.getText());
                equipo.setNombre(nombreText.getText());
                equipo.setIdTipo((TiposEquipoDto) tipoCombo.getSelectedItem());
                equipo.setIdProveedor(((ProveedoresEquipoDto) Objects.requireNonNull(proveedorCombo.getSelectedItem())));
                equipo.setIdPais((PaisDto) paisCombo.getSelectedItem());
                equipo.setIdModelo((ModelosEquipoDto) modeloCombo.getSelectedItem());
                equipo.setFechaAdquisicion(fechaCompraDate.getDate());
                equipo.setEstado((Estados) estadoCombo.getSelectedItem());
                equipo.setImagen(Utilidades.subirImagen(imagenSubida));
                agregarEquipo(equipo);
                limpiarCampos();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "No se pudo registrar el equipo " + ex.getMessage());
            }
        });

        //Funcionalidades de los filtros

        botonBuscarFiltro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HashMap<String, Object> filtros = new HashMap<>();

                //Cargar el hashmap con los filtros todos en null
                filtros.put("idInterno", null);
                filtros.put("ubicacion", null);
                filtros.put("nroSerie", null);
                filtros.put("nombre", null);
                filtros.put("tipo", null);
                filtros.put("proveedor", null);
                filtros.put("pais", null);
                filtros.put("modelo", null);
                filtros.put("fechaInicio", null);
                filtros.put("fechaFin", null);


                //Cargar el hashmap con los filtros que se hayan seleccionado
                if (filtroComboSelectorBusqueda.getSelectedItem().equals("Nombre")) {
                    filtros.put("nombre", filtroCampoValorBusqueda.getText());
                } else if (filtroComboSelectorBusqueda.getSelectedItem().equals("Numero de Serie")) {
                    filtros.put("nroSerie", filtroCampoValorBusqueda.getText());
                } else if (filtroComboSelectorBusqueda.getSelectedItem().equals("ID Interno")) {
                    filtros.put("idInterno", filtroCampoValorBusqueda.getText());
                }

                //Fechas
                if (fechaInicio.getDate() != null) {
                    filtros.put("fechaInicio", fechaInicio.getDate());
                }
                if (fechaFin.getDate() != null) {
                    filtros.put("fechaFin", fechaFin.getDate());
                }


                if (!filtroComboUbicacion.getSelectedItem().equals("Todos")) {
                    filtros.put("ubicacion", (UbicacionDto) filtroComboUbicacion.getSelectedItem());
                }
                if (!filtroComboTipo.getSelectedItem().equals("Todos")) {
                    filtros.put("tipo", (TiposEquipoDto) filtroComboTipo.getSelectedItem());
                }
                if (!filtroComboProveedor.getSelectedItem().equals("Todos")) {
                    filtros.put("proveedor", (ProveedoresEquipoDto) filtroComboProveedor.getSelectedItem());
                }
                if (!filtroComboPais.getSelectedItem().equals("Todos")) {
                    filtros.put("pais", (PaisDto) filtroComboPais.getSelectedItem());
                }
                if (!filtroComboModelo.getSelectedItem().equals("Todos")) {
                    filtros.put("modelo", (ModelosEquipoDto) filtroComboModelo.getSelectedItem());
                }


                //Filtrar desde el cliente
                Vector<Vector<Object>> dataVector = new Vector<>();
                try {
                    actualizarTabla(Conexion.obtenerEquipoBean().listarEquipos());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "No se pudo filtrar la tabla");
                }


                model.getDataVector().forEach(row -> {
                    Vector<Object> rowVector = (Vector<Object>) row;

                    boolean shouldAddRow = true;

                    //Id interno
                    if (filtros.get("idInterno") != null && !rowVector.elementAt(1).toString().toLowerCase().contains(filtros.get("idInterno").toString().toLowerCase())) {
                        shouldAddRow = false;
                    }
                    //Ubicacion
                    if (filtros.get("ubicacion") != null && !rowVector.elementAt(2).toString().toLowerCase().contains(((UbicacionDto) filtros.get("ubicacion")).getNombre().toLowerCase())) {
                        shouldAddRow = false;
                    }
                    //Numero de serie
                    if (filtros.get("nroSerie") != null && !rowVector.elementAt(3).toString().toLowerCase().contains(filtros.get("nroSerie").toString().toLowerCase())) {
                        shouldAddRow = false;
                    }
                    //Nombre
                    if (filtros.get("nombre") != null && !rowVector.elementAt(4).toString().toLowerCase().contains(filtros.get("nombre").toString().toLowerCase())) {
                        shouldAddRow = false;
                    }
                    //Tipo
                    if (filtros.get("tipo") != null && !rowVector.elementAt(5).toString().toLowerCase().contains(((TiposEquipoDto) filtros.get("tipo")).getNombreTipo().toLowerCase())) {
                        shouldAddRow = false;
                    }
                    //Proveedor
                    if (filtros.get("proveedor") != null && !rowVector.elementAt(6).toString().toLowerCase().contains(((ProveedoresEquipoDto) filtros.get("proveedor")).getNombre().toLowerCase())) {
                        shouldAddRow = false;
                    }
                    //Pais
                    if (filtros.get("pais") != null && !rowVector.elementAt(7).toString().toLowerCase().contains(((PaisDto) filtros.get("pais")).getNombre().toLowerCase())) {
                        shouldAddRow = false;
                    }
                    //Modelo
                    if (filtros.get("modelo") != null && !rowVector.elementAt(8).toString().toLowerCase().contains(((ModelosEquipoDto) filtros.get("modelo")).getNombre().toLowerCase())) {
                        shouldAddRow = false;
                    }
                    //Fecha de adquisicion
                    if (filtros.get("fechaInicio") != null && filtros.get("fechaFin") != null) {
                        LocalDate fechaInicio = ((LocalDate) filtros.get("fechaInicio"));
                        LocalDate fechaFin = ((LocalDate) filtros.get("fechaFin"));
                        LocalDate fechaActual = ((LocalDate) rowVector.elementAt(9));

                        if (fechaActual.isBefore(fechaInicio) || fechaActual.isAfter(fechaFin)) {
                            shouldAddRow = false;
                        }
                    } else if (filtros.get("fechaInicio") != null) {
                        LocalDate fechaInicio = ((LocalDate) filtros.get("fechaInicio"));
                        LocalDate fechaActual = ((LocalDate) rowVector.elementAt(9));

                        if (fechaActual.isBefore(fechaInicio)) {
                            shouldAddRow = false;
                        }
                    } else if (filtros.get("fechaFin") != null) {
                        LocalDate fechaFin = ((LocalDate) filtros.get("fechaFin"));
                        LocalDate fechaActual = ((LocalDate) rowVector.elementAt(9));

                        if (fechaActual.isAfter(fechaFin)) {
                            shouldAddRow = false;
                        }
                    }

                    if (shouldAddRow) {
                        dataVector.add(rowVector);
                    }

                });
                if (dataVector.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se encontraron resultados para la búsqueda");
                }
                model.setRowCount(0);
                dataVector.forEach(model::addRow);
            }

            private int getColumnIndex(String columnName) {
                for (int i = 0; i < equiposTable.getColumnCount(); i++) {
                    if (equiposTable.getColumnName(i).equals(columnName)) {
                        return i;
                    }
                }
                return -1;
            }
        });
        botonLimpiarFiltros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actualizarTabla(Conexion.obtenerEquipoBean().listarEquipos());
                    filtroComboUbicacion.setSelectedIndex(0);
                    filtroComboProveedor.setSelectedIndex(0);
                    filtroComboPais.setSelectedIndex(0);
                    filtroComboModelo.setSelectedIndex(0);
                    filtroComboTipo.setSelectedIndex(0);
                    filtroComboSelectorBusqueda.setSelectedIndex(0);
                    filtroCampoValorBusqueda.setText("");
                    fechaInicio.setDate(null);
                    fechaFin.setDate(null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "No se pudo limpiar los filtros ");
                }
            }
        });

        exportarAExcelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<String> columnas = new Vector<>();
                columnas.add("ID");
                columnas.add("Ubicacion");
                columnas.add("Numero de Serie");
                columnas.add("Nombre");
                columnas.add("Tipo");
                columnas.add("Proveedor");
                columnas.add("Pais");
                columnas.add("Modelo");
                columnas.add("Fecha Adquisicion");
                Vector<Vector<Object>> dataVector = new Vector<>();
                int rowCount = model.getRowCount();
                int columnCount = model.getColumnCount();

                for (int row = 0; row < rowCount; row++) {
                    Vector<Object> rowData = new Vector<>();

                    for (int col = 1; col < columnCount; col++) {
                        Object cellValue = model.getValueAt(row, col);
                        rowData.add(cellValue);
                    }

                    dataVector.add(rowData);
                }
                try {
                    Utilidades.exportarAExcel(columnas, dataVector, "Equipos");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "No se pudo exportar a excel");
                }
            }
        });
        listarMovimientosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = equiposTable.getSelectedRow();
                if (filaSeleccionada != -1) {
                    long idEquipoSeleccionado = (long) equiposTable.getModel().getValueAt(filaSeleccionada, 0);

                    EquipoDto equipoSeleccionado = null;
                    try {
                        equipoSeleccionado = Conexion.obtenerEquipoBean().obtenerEquipo(idEquipoSeleccionado);
                    } catch (NamingException ex) {
                        throw new RuntimeException(ex);
                    }

                    ListadoMovimientos listadoMovimientos = null;
                    try {
                        listadoMovimientos = new ListadoMovimientos(equipoSeleccionado);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    listadoMovimientos.setVisible(true);
                    listadoMovimientos.pack();
                } else {
                    JOptionPane.showMessageDialog(null, "El valor seleccionado no es un equipo válido");
                }
            }
        });
        registrarMovimientoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = equiposTable.getSelectedRow();
                if (filaSeleccionada != -1) {
                    long idEquipoSeleccionado = (long) equiposTable.getModel().getValueAt(filaSeleccionada, 0);

                    EquipoDto equipoSeleccionado = null;
                    try {
                        equipoSeleccionado = Conexion.obtenerEquipoBean().obtenerEquipo(idEquipoSeleccionado);
                    } catch (NamingException ex) {
                        throw new RuntimeException(ex);
                    }

                    MoverEquipo moverEquipo = null;
                    try {
                        moverEquipo = new MoverEquipo(equipoSeleccionado, Conexion.obtenerUbicacionBean().listarUbicaciones());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    moverEquipo.setVisible(true);

                    HashMap<String, Object> datos = moverEquipo.getDatos();
                    if (datos != null && moverEquipo.getOk()) {
                        EquiposUbicacioneDto equiposUbicacioneDto = new EquiposUbicacioneDto();
                        equiposUbicacioneDto.setIdEquipo(equipoSeleccionado);
                        equiposUbicacioneDto.setIdUbicacion((UbicacionDto) datos.get("ubicacion"));
                        equiposUbicacioneDto.setFecha((LocalDate) datos.get("fecha"));
                        equiposUbicacioneDto.setObservaciones((String) datos.get("observaciones"));

                        equiposUbicacioneDto.setUsuario(Sesion.getUsuario());

                        equipoSeleccionado.setIdUbicacion((UbicacionDto) datos.get("ubicacion"));
                        try {
                            Conexion.obtenerEquipoUbicacionBean().crearEquiposUbicacione(equiposUbicacioneDto);
                            Conexion.obtenerEquipoBean().modificarEquipo(equipoSeleccionado);
                            JOptionPane.showMessageDialog(null, "Movimiento registrado con exito");
                            actualizarTabla(Conexion.obtenerEquipoBean().listarEquipos());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "No se pudo registrar el movimiento" + ex.getMessage());
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Acción cancelada o datos incompletos");
                    }


                } else {
                    JOptionPane.showMessageDialog(null, "El valor seleccionado no es un equipo válido");
                }
            }
        });
    }


    public void actualizarTabla(List<EquipoDto> datos) throws Exception {
        DefaultTableModel model = (DefaultTableModel) equiposTable.getModel();
        model.setRowCount(0);
        datos.forEach(equipo -> {
            model.addRow(new Object[]{
                    equipo.getId(),
                    equipo.getIdInterno(),
                    equipo.getIdUbicacion().getNombre(),
                    equipo.getNroSerie(),
                    equipo.getNombre(),
                    equipo.getIdTipo(),
                    equipo.getIdProveedor(),
                    equipo.getIdPais(),
                    equipo.getIdModelo(),
                    equipo.getFechaAdquisicion()
            });
        });

    }

    public void agregarEquipo(EquipoDto equipo) throws Exception {
        try {
            Conexion.obtenerEquipoBean().crearEquipo(equipo);
            JOptionPane.showMessageDialog(null, "Equipo registrado con exito");
            actualizarTabla(Conexion.obtenerEquipoBean().listarEquipos());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo registrar el equipo" + e.getMessage());
        }
    }

    public void cargarCombos() throws Exception {
        //Agregar un item "Todos" a los combos de filtro
        filtroComboUbicacion.addItem("Todos");
        filtroComboProveedor.addItem("Todos");
        filtroComboPais.addItem("Todos");
        filtroComboModelo.addItem("Todos");
        filtroComboTipo.addItem("Todos");

        for (UbicacionDto ubicacion : Conexion.obtenerUbicacionBean().listarUbicaciones()) {
            ubicacionCombo.addItem(ubicacion);
            filtroComboUbicacion.addItem(ubicacion);
        }
        for (TiposEquipoDto tipo : Conexion.obtenerTipoBean().listarTiposEquipo()) {
            tipoCombo.addItem(tipo);
            filtroComboTipo.addItem(tipo);
        }

        for (ProveedoresEquipoDto proveedor : Conexion.obtenerProveedoresEquipoBean().obtenerProveedoresEquipo()) {
            proveedorCombo.addItem(proveedor);
            filtroComboProveedor.addItem(proveedor);
        }

        for (PaisDto pais : Conexion.obtenerPaisBean().obtenerpais()) {
            paisCombo.addItem(pais);
            filtroComboPais.addItem(pais);
        }

        for (ModelosEquipoDto modelo : Conexion.obtenerModeloBean().listarModelosEquipo()) {
            modeloCombo.addItem(modelo);
            filtroComboModelo.addItem(modelo);
        }

        for (Estados estado : Estados.values()) {
            estadoCombo.addItem(estado);
        }

        filtroComboSelectorBusqueda.addItem("Nombre");
        filtroComboSelectorBusqueda.addItem("Numero de Serie");
        filtroComboSelectorBusqueda.addItem("ID Interno");
    }

    public void limpiarCampos() {
        idInternoText.setText("");
        nroSerieText.setText("");
        nombreText.setText("");
        filePathField.setText("");
        imagenSubida = null;
        fechaCompraDate.setDate(null);
    }
}

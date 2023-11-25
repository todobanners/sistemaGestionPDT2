package org.example.vista.Equipo;
/*
 * TODO: Agregar funcionalidad de los botones
 *  - Dar de baja seleccionado
 *  - Editar seleccionado
 *  - Registrar movimiento
 * TODO: Crear filtros para la tabla
 * TODO: Ver como hacer para que se muestre la imagen del equipo en la pantalla principal
 *  todo: arreglar tamano de botones
 *
 * */

import codigocreativo.uy.servidorapp.DTO.*;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.github.lgooddatepicker.components.DatePicker;
import org.example.modelo.Conexion;
import org.example.modelo.Utilidades;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
    private JComboBox<TiposEquipoDto> tipoCombo;
    private JComboBox<ProveedoresEquipoDto> proveedorCombo;
    private JComboBox<PaisDto> paisCombo;
    private JComboBox<ModelosEquipoDto> modeloCombo;
    private JComboBox<UbicacionDto> ubicacionCombo;
    private JComboBox<Estados> estadoCombo;
    private JComboBox<UbicacionDto> filtroComboUbicacion;
    private JComboBox<ProveedoresEquipoDto> filtroComboProveedor;
    private JComboBox<PaisDto> filtroComboPais;
    private JComboBox<ModelosEquipoDto> filtroComboModelo;
    private JComboBox<MarcasModeloDto> filtroComboMarca;
    private JComboBox<TiposEquipoDto> filtroComboTipo;
    private JComboBox<String> filtroComboSelectorBusqueda;
    private JButton imagenBtn;
    private JPanel fechaAdqContainer;
    private JButton guardarButton;
    private JPanel accionesTablaPanel;
    private JButton darBajaSeleccionadoButton;
    private JButton editarSeleccionadoButton;
    private JButton registrarMovimientoButton;
    private JPanel filtroFechaContenedor;
    private JLabel filePathField;

    private JTextField filtroCampoValorBusqueda;
    private JButton botonBuscarFiltro;
    private JButton botonLimpiarFiltros;

    private File imagenSubida;
    DatePicker fechaCompraDate = Utilidades.createCustomDatePicker();

    public JPanel getPanel() {
        return equipoPanel;
    }

    public EquiposGUI() throws Exception {
        //Cargar datos de la tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("idInterno");
        model.addColumn("Ubicación");
        model.addColumn("nroSerie");
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


        guardarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
            }
        });


        /*equiposTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int filaSeleccionada = equiposTable.getSelectedRow();
                if (filaSeleccionada != -1) {
                    long idEquipoSeleccionado = (long) equiposTable.getModel().getValueAt(filaSeleccionada, 0);

                    Equipo equipoSeleccionado = null;
                    try {
                        equipoSeleccionado = Conexion.obtenerEquipoBean().obtenerEquipo(idEquipoSeleccionado);
                    } catch (NamingException ex) {
                        throw new RuntimeException(ex);
                    }
                    idInternoText.setText(equipoSeleccionado.getIdInterno());
                    ubicacionCombo.setSelectedItem(equipoSeleccionado.getIdUbicacion());
                    nroSerieText.setText(equipoSeleccionado.getNroSerie());
                    nombreText.setText(equipoSeleccionado.getNombre());
                    tipoCombo.setSelectedItem(equipoSeleccionado.getIdTipo());
                    proveedorCombo.setSelectedItem(equipoSeleccionado.getIdProveedor());
                    paisCombo.setSelectedItem(equipoSeleccionado.getIdPais());
                    modeloCombo.setSelectedItem(equipoSeleccionado.getIdModelo());
                    fechaCompraDate.setDate(equipoSeleccionado.getFechaAdquisicion());
                    estadoCombo.setSelectedItem(equipoSeleccionado.getEstado());
                    filePathField.setText(equipoSeleccionado.getImagen());

                } else {
                    JOptionPane.showMessageDialog(null, "El valor seleccionado no es un equipo válido");
                }
            }
        });*/

        //Funcionalidades de los filtros

        botonBuscarFiltro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HashMap<String, Object> filtros = new HashMap<>();

                if (!idInternoText.getText().isEmpty()) {
                    filtros.put("idInterno", idInternoText.getText());
                }
                if (!nroSerieText.getText().isEmpty()) {
                    filtros.put("nroSerie", nroSerieText.getText());
                }
                if (!nombreText.getText().isEmpty()) {
                    filtros.put("nombre", nombreText.getText());
                }
                if (tipoCombo.getSelectedItem() != null) {
                    filtros.put("idTipo", ((TiposEquipoDto) tipoCombo.getSelectedItem()).getId());
                }
                if (proveedorCombo.getSelectedItem() != null) {
                    filtros.put("idProveedor", ((ProveedoresEquipoDto) proveedorCombo.getSelectedItem()).getId());
                }
                if (paisCombo.getSelectedItem() != null) {
                    filtros.put("idPais", ((PaisDto) paisCombo.getSelectedItem()).getId());
                }
                if (modeloCombo.getSelectedItem() != null) {
                    filtros.put("idModelo", ((ModelosEquipoDto) modeloCombo.getSelectedItem()).getId());
                }
                if (fechaCompraDate.getDate() != null) {
                    filtros.put("fechaAdquisicion", fechaCompraDate.getDate());
                }

                // Filtrar los datos en el cliente
                DefaultTableModel model = (DefaultTableModel) equiposTable.getModel();
                Vector<Vector<Object>> dataVector = new Vector<>();

                for (int i = 0; i < model.getRowCount(); i++) {
                    boolean matches = true;
                    for (String key : filtros.keySet()) {
                        if (filtros.get(key) != null && !filtros.get(key).equals(model.getValueAt(i, getColumnIndex(key)))) {
                            matches = false;
                            break;
                        }
                    }
                    if (matches) {
                        dataVector.add((Vector<Object>) model.getDataVector().get(i));
                    }
                }

                model.setRowCount(0);
                for (Vector<Object> row : dataVector) {
                    model.addRow(row);
                }
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
    }


    public void actualizarTabla(List<EquipoDto> datos) throws Exception {
        DefaultTableModel model = (DefaultTableModel) equiposTable.getModel();
        model.setRowCount(0);
        //Conexion.obtenerEquipoBean().listarEquipos()

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
            JOptionPane.showMessageDialog(null, "No se pudo registrar el equipo"+e.getMessage());
        }
    }

    public void cargarCombos() throws Exception {
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

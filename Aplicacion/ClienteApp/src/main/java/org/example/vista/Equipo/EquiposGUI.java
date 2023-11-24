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
import codigocreativo.uy.servidorapp.entidades.*;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.github.lgooddatepicker.components.DatePicker;
import org.example.modelo.Conexion;
import org.example.modelo.Utilidades;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class EquiposGUI {
    private JPanel equipoPanel;
    private JScrollPane tablaScrollPane;
    private JPanel accionesPanel;
    private JTable equiposTable;
    private JPanel formularioPanel;
    private JTextField idInternoText;
    private JComboBox ubicacionCombo;
    private JTextField nroSerieText;
    private JTextField nombreText;
    private JComboBox tipoCombo;
    private JComboBox proveedorCombo;
    private JComboBox paisCombo;
    private JComboBox modeloCombo;
    private JButton imagenBtn;
    private JPanel fechaAdqContainer;
    private JButton guardarButton;
    private JPanel accionesTablaPanel;
    private JButton darBajaSeleccionadoButton;
    private JButton editarSeleccionadoButton;
    private JButton registrarMovimientoButton;
    private JComboBox estadoCombo;
    private JLabel filePathField;
    private File imagenSubida;
    DatePicker fechaCompraDate = Utilidades.createCustomDatePicker();

    public JPanel getPanel() {
        return equipoPanel;
    }

    public EquiposGUI() throws Exception {
        //Cargar datos de la tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Interno");
        model.addColumn("Ubicación");
        model.addColumn("Nro. Serie");
        model.addColumn("Nombre");
        model.addColumn("Tipo");
        model.addColumn("Proveedor");
        model.addColumn("País");
        model.addColumn("Modelo");
        model.addColumn("Fecha Adquisición");
        equiposTable.setModel(model);
        actualizarTabla();
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "No se pudo registrar el equipo " + ex.getMessage());
                }
            }
        });


    }


    public void actualizarTabla() throws Exception {
        DefaultTableModel model = (DefaultTableModel) equiposTable.getModel();
        model.setRowCount(0);
        Conexion.obtenerEquipoBean().listarEquipos().forEach(equipo -> {
            model.addRow(new Object[]{
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
            actualizarTabla();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo registrar el equipo"+e.getMessage());
        }
    }

    public void cargarCombos() throws Exception {
        for (Ubicacion ubicacion : Conexion.obtenerUbicacionBean().listarUbicaciones()) {
            ubicacionCombo.addItem(ubicacion);
        }

        for (TiposEquipo tipo : Conexion.obtenerTipoBean().listarTiposEquipo()) {
            tipoCombo.addItem(tipo);
        }

        for (ProveedoresEquipo proveedor : Conexion.obtenerProveedoresEquipoBean().obtenerProveedoresEquipo()) {
            proveedorCombo.addItem(proveedor);
        }

        for (Pais pais : Conexion.obtenerPaisBean().obtenerpais()) {
            paisCombo.addItem(pais);
        }

        for (ModelosEquipo modelo : Conexion.obtenerModeloBean().listarModelosEquipo()) {
            modeloCombo.addItem(modelo);
        }

        for (Estados estado : Estados.values()) {
            estadoCombo.addItem(estado);
        }
    }
}

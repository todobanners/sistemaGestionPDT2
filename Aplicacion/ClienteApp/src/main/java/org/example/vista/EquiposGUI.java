package org.example.vista;
/*
* TODO: Agregar funcionalidad de los botones
*  - Dar de baja seleccionado
*  - Editar seleccionado
*  - Registrar movimiento
* TODO: Crear filtros para la tabla
* TODO: Ver como hacer para que se muestre la imagen del equipo en la pantalla principal
*
*
* */
import codigocreativo.uy.servidorapp.entidades.*;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import com.toedter.calendar.JDateChooser;
import org.example.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
    JDateChooser fechaCompraDate = new JDateChooser();

    public JPanel getPanel() {
        return equipoPanel;
    }

    public EquiposGUI() throws Exception{
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



        guardarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Equipo equipo = new Equipo();
                    equipo.setIdInterno(idInternoText.getText());
                    equipo.setIdUbicacion(((Ubicacion) Objects.requireNonNull(ubicacionCombo.getSelectedItem())).getId());
                    equipo.setNroSerie(nroSerieText.getText());
                    equipo.setNombre(nombreText.getText());
                    equipo.setIdTipo((TiposEquipo) tipoCombo.getSelectedItem());
                    equipo.setIdProveedor(((ProveedoresEquipo) Objects.requireNonNull(proveedorCombo.getSelectedItem())));
                    equipo.setIdPais((Pais) paisCombo.getSelectedItem());
                    equipo.setIdModelo((ModelosEquipo) modeloCombo.getSelectedItem());
                    Date fechaElegida = (Date) fechaCompraDate.getDate();
                    LocalDate localDate = fechaElegida.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    equipo.setFechaAdquisicion(localDate);
                    equipo.setEstado((Estados) estadoCombo.getSelectedItem());
                    agregarEquipo(equipo);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    public void actualizarTabla() throws Exception {
        DefaultTableModel model = (DefaultTableModel) equiposTable.getModel();
        model.setRowCount(0);
        Conexion.obtenerEquipoBean().listarEquipos().forEach(equipo -> {
            try {
                model.addRow(new Object[]{
                        equipo.getIdInterno(),
                        Conexion.obtenerUbicacionBean().obtenerUbicacionPorId(equipo.getIdUbicacion()),
                        equipo.getNroSerie(),
                        equipo.getNombre(),
                        equipo.getIdTipo(),
                        equipo.getIdProveedor(),
                        equipo.getIdPais(),
                        equipo.getIdModelo(),
                        equipo.getFechaAdquisicion()
                });
            } catch (NamingException e) {
                throw new RuntimeException(e);
            } catch (ServiciosException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void agregarEquipo(Equipo equipo) throws Exception {
        try {
            Conexion.obtenerEquipoBean().crearEquipo(equipo);
            JOptionPane.showMessageDialog(null, "Equipo registrado con exito");
            actualizarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo registrar el equipo");
        }
    }
}

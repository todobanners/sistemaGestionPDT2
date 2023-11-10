package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.*;
import com.toedter.calendar.JDateChooser;
import org.example.Conexion;

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



        guardarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Equipo equipo = new Equipo();
                    equipo.setIdUbicacion((Long) ubicacionCombo.getSelectedItem());
                    equipo.setNroSerie(nroSerieText.getText());
                    equipo.setNombre(nombreText.getText());
                    equipo.setIdTipo((TiposEquipo) tipoCombo.getSelectedItem());
                    equipo.setIdProveedor((ProveedoresEquipo) proveedorCombo.getSelectedItem());
                    equipo.setIdPais((Pais) paisCombo.getSelectedItem());
                    equipo.setIdModelo((ModelosEquipo) modeloCombo.getSelectedItem());
                    Date fechaElegida = (Date) fechaCompraDate.getDate();
                    LocalDate localDate = fechaElegida.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    equipo.setFechaAdquisicion(localDate);
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
        Conexion.obtenerEquipoBean().obtenerEquipos().forEach(equipo -> {
            model.addRow(new Object[]{
                    equipo.getIdInterno(),
                    equipo.getIdUbicacion(),
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

    public void agregarEquipo(Equipo equipo) throws Exception {
        Conexion.obtenerEquipoBean().crearEquipo(equipo);
        JOptionPane.showMessageDialog(null, "Equipo registrado con exito");
        actualizarTabla();
    }
}

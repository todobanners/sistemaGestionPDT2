package org.example.vista;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
        JFrame frame = new JFrame("Equipos");
        frame.setContentPane(equipoPanel);
        frame.setDefaultCloseOperation(2);

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


        fechaAdqContainer.add(fechaCompraDate);

    }
}

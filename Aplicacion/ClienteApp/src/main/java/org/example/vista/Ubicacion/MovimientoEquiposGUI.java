package org.example.vista.Ubicacion;

import com.github.lgooddatepicker.components.DatePicker;
import org.example.modelo.Utilidades;

import javax.swing.*;

public class MovimientoEquiposGUI {
    private JPanel movimientoEquipos;
    private JTable tabla;
    private JScrollPane scroll;
    private JPanel acciones;
    private JPanel filtros;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JPanel contenedorFechaFiltroDesde;
    private JPanel contenedoFechaFiltroHasta;
    private JComboBox comboBox6;
    private JTextField textField1;
    private JComboBox comboBox7;
    private JComboBox comboBox8;
    private JComboBox comboBox9;
    private JTextField textField2;
    private JComboBox comboBox10;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JPanel contenedorFechaSalida;

    DatePicker filtroFechaDesde = Utilidades.createCustomDatePicker();
    DatePicker filtroFechaHasta = Utilidades.createCustomDatePicker();
    DatePicker fechaSalida = Utilidades.createCustomDatePicker();


    public JPanel getPanel(){
        return movimientoEquipos;
    };

    public MovimientoEquiposGUI(){
        contenedorFechaFiltroDesde.add(filtroFechaDesde);
        contenedoFechaFiltroHasta.add(filtroFechaHasta);
        contenedorFechaSalida.add(fechaSalida);

    }
}

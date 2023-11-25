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
    private JComboBox filtroComboEquipo;
    private JComboBox filtroComboIdentificador;
    private JComboBox filtroComboInstitucion;
    private JComboBox filtroComboSector;
    private JComboBox filtroComboUsuario;
    private JPanel contenedorFechaFiltroDesde;
    private JPanel contenedoFechaFiltroHasta;
    private JComboBox agregarComboEquipo;
    private JTextField agregarCampoIdEquipo;
    private JComboBox agregarComboInstitucion;
    private JComboBox agregarComboSector;
    private JComboBox agregarComboUbicacion;
    private JTextField agregarCampoNumero;
    private JComboBox agregarComboUsuario;
    private JTextField agregarCampoObservaciomes;
    private JTextField agregarCampoCama;
    private JTextField agregarCampoPiso;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JPanel contenedorFechaSalida;
    private JButton filtrarButton;
    private JButton limpiarButton;

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

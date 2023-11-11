package org.example.vista;

import codigocreativo.uy.servidorapp.servicios.IntervencionRemote;

import javax.swing.*;

public class ListadoDeIntervencionesGUI {
    private JPanel panelListadoDeIntervenciones;
    private JPanel panelAcciones;
    private JTable tableListadoIntervenciones;
    private JTextField textFechaDesde;
    private JTextField textFechaHasta;
    private JTextField textIdEquipo;
    private JButton generarReporteButton;
    private JComboBox comboTipoDeIntervencion;

    private IntervencionRemote intervencionRemoteBean;

    public JPanel getPanel(){
        return panelListadoDeIntervenciones;
    }

    public ListadoDeIntervencionesGUI() {
        JFrame frame = new JFrame(" Listado de Intervenciones");
    }
}

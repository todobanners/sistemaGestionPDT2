package org.example.vista;

import codigocreativo.uy.servidorapp.servicios.IntervencionRemote;

import javax.swing.*;

public class ListadoDeIntervencionesGUI {
    private JPanel panelListadoDeIntervenciones;
    private JPanel panelAcciones;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;

    private IntervencionRemote intervencionRemoteBean;

    public JPanel getPanel(){
        return panelListadoDeIntervenciones;
    }

    public ListadoDeIntervencionesGUI() {
        JFrame frame = new JFrame(" Listado de Intervenciones");
    }
}

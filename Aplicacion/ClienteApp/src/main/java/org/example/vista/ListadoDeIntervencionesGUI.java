package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.TiposIntervencione;
import codigocreativo.uy.servidorapp.servicios.IntervencionRemote;
import com.toedter.calendar.JDateChooser;
import org.example.modelo.Conexion;
import org.jdesktop.swingx.JXTable;

import javax.naming.NamingException;
import javax.swing.*;

public class ListadoDeIntervencionesGUI {
    private JPanel panelListadoDeIntervenciones;
    private JPanel panelAcciones;
    private JTextField textIdEquipo;
    private JComboBox comboTipoDeIntervencion;
    private JButton filtroBoton;
    private JButton limpiarButton;
    private JScrollPane panelTabla;
    private JXTable tablaPerfiles;
    private JButton trabajarIntervenciónButton;

    JDateChooser textFechaDesde = new JDateChooser();
    JDateChooser textFechaHasta = new JDateChooser();

    private IntervencionRemote intervencionRemoteBean;



    public JPanel getPanel(){
        return panelListadoDeIntervenciones;
    }

    public ListadoDeIntervencionesGUI() throws NamingException {

        JFrame frame = new JFrame(" Listado de Intervenciones");

        try {
            intervencionRemoteBean = Conexion.obtenerIntervencionBean();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con el servidor");
        }

        //obtener los tipos de intervencines
        for (TiposIntervencione tipoIntervencione : Conexion.obtenerTiposIntervencionBean().obtenerTiposIntervenciones()) {
            comboTipoDeIntervencion.addItem(tipoIntervencione);
        }
    }
}

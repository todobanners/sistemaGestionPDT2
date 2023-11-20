package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.TiposIntervencione;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.TipoIntervencioneBean;
import codigocreativo.uy.servidorapp.servicios.TipoIntervencioneRemote;
import org.example.modelo.Conexion;
import org.example.vista.PerfilPermisosGUI;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.hyperlink.AbstractHyperlinkAction;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.HyperlinkProvider;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.event.ActionEvent;

public class TiposDeIntervencionesGUI {
    private JPanel panelTipoDeIntervenciones;
    private JPanel panelAcciones;
    private JTextField textNombre;
    private JComboBox comboEstado;
    private JButton guardarButton;
    private JScrollPane panelTabla;
    private JXTable tablaTiposDeIntervenciones;
    private JButton editarIntervenciónSeleccionadoButton;
    private JButton filtroBoton;
    private JComboBox filtroEstadoCombo;
    private JTextField filtroNombreCampo;
    private JButton limpiarButton;
    private JLabel estadoTipo;
    private DefaultTableModel model;

    private TipoIntervencioneRemote TipoIntervencioneRemoteBean;

    public JPanel getPanel() {
        return panelTipoDeIntervenciones;
    }

    public TiposDeIntervencionesGUI() throws NamingException {

        JFrame frame = new JFrame(" Listado de Intervenciones");

        //Crear modelo de la tabla

        model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Estado");
        tablaTiposDeIntervenciones.setModel(model);

        //Cargar los datos del combobox de estados
        for (Estados estado : Estados.values()) {
            comboEstado.addItem(estado);
            filtroEstadoCombo.addItem(estado);
        }

        try {
            TipoIntervencioneRemoteBean = (TipoIntervencioneRemote) Conexion.obtenerIntervencionBean();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con el servidor");
        }

    }


}

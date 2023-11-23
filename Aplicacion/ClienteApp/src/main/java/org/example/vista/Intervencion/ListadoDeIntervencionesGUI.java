package org.example.vista.Intervencion;

import codigocreativo.uy.servidorapp.DTO.TiposIntervencioneDto;
import codigocreativo.uy.servidorapp.entidades.TiposIntervencione;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.IntervencionRemote;
import com.github.lgooddatepicker.components.DateTimePicker;
import org.example.modelo.Conexion;
import org.example.modelo.Utilidades;
import org.jdesktop.swingx.JXTable;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class ListadoDeIntervencionesGUI {
    private JPanel panelListadoDeIntervenciones;
    private JPanel panelAcciones;
    private JTextField textIdEquipo;
    private JComboBox comboTipoDeIntervencion;
    private JButton filtroBoton;
    private JButton limpiarButton;
    private JScrollPane panelTabla;
    private JXTable tablaInterverncionesFiltradas;
    private JButton trabajarIntervenciónButton;

    private JPanel fechaDesde;
    private JPanel fechaHasta;

    DateTimePicker selectorFecha = Utilidades.createCustomDateTimePicker();
    private IntervencionRemote intervencionRemoteBean;

    private DefaultTableModel model;



    public JPanel getPanel(){
        return panelListadoDeIntervenciones;
    }

    public ListadoDeIntervencionesGUI() throws NamingException, ServiciosException {

        JFrame frame = new JFrame(" Listado de Intervenciones");

        fechaDesde.add(selectorFecha);
        fechaHasta.add(selectorFecha);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Fecha");
        model.addColumn("Tipo de Intervención");
        model.addColumn("Motivo");
        model.addColumn("Id del Equipo");
        model.addColumn("Comentarios");
        tablaInterverncionesFiltradas.setModel(model);

        try {
            intervencionRemoteBean = Conexion.obtenerIntervencionBean();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con el servidor");
        }
        actualizarTabla();

        //obtener los tipos de intervenciones
        for (TiposIntervencioneDto tipoIntervencione : Conexion.obtenerTiposIntervencionBean().obtenerTiposIntervenciones()) {
            comboTipoDeIntervencion.addItem(tipoIntervencione);
        }
    }

    public void actualizarTabla() throws NamingException, ServiciosException {
        model.setRowCount(0);
        Conexion.obtenerIntervencionBean().obtenerTodas().forEach(intervencion -> {
            model.addRow(new Object[]{
                    intervencion.getFechaHora(),
                    intervencion.getIdTipo(),
                    intervencion.getMotivo(),
                    intervencion.getIdEquipo().getId(),
                    intervencion.getComentarios()
            });
        });
    }
}

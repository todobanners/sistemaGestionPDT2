package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.*;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.IntervencionRemote;
import codigocreativo.uy.servidorapp.servicios.TipoIntervencioneRemote;
import org.example.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class IntervencionGUI {
    private JPanel panelIntervencion;
    private JTextField textFechaHora;
    private JComboBox comboTipodeIntervencion;
    private JTextField textMotivo;
    private JTextField textIdEquipo;
    private JTextField textComentarios;
    private JPanel panelAcciones;
    private JButton registrarIntervencionButton;
    private JTable tablaIntervenciones;

    private IntervencionRemote intervencionRemoteBean;

    public JPanel getPanel(){
        return panelIntervencion;
    }

    public IntervencionGUI() throws Exception {
        JFrame frame = new JFrame("Intervenciones");

        //Crear modelo de la tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Fecha");
        model.addColumn("Tipo de Intervención");
        model.addColumn("Motivo");
        model.addColumn("Id del Equipo");
        model.addColumn("Comentarios");
        tablaIntervenciones.setModel(model);

        try {
            intervencionRemoteBean = Conexion.obtenerIntervencionBean();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con el servidor");
        }

        //obtener los tipos de intervencines
        for (TiposIntervencione tipoIntervencione : Conexion.obtenerTiposIntervencionBean().obtenerTiposIntervenciones()) {
            comboTipodeIntervencion.addItem(tipoIntervencione);
        }

        registrarIntervencionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Intervencion intervencion = new Intervencion();
                Usuario user = new Usuario();//creo obj usuario
                user.setId(1L);//le asigno un id hardcodeado
                intervencion.setIdUsuario(user); //agrego el obj usuario con solo el id
                intervencion.setFechaHora(LocalDate.parse(textFechaHora.getText()));
                intervencion.setMotivo(textMotivo.getText());
                Equipo equipo = new Equipo();
                equipo.setId(Long.valueOf(textIdEquipo.getText()));
                intervencion.setIdEquipo(equipo);
                intervencion.setComentarios(textComentarios.getText());
                intervencion.setIdTipo((TiposIntervencione) comboTipodeIntervencion.getSelectedItem());
                    try {
                        Conexion.obtenerIntervencionBean().crear(intervencion);
                        actualizarTabla();
                    } catch (ServiciosException | NamingException ex) {
                        throw new RuntimeException(ex);
                    }
            }
        });

    }
    public void actualizarTabla() throws NamingException, ServiciosException {
        DefaultTableModel model = (DefaultTableModel) tablaIntervenciones.getModel();
        model.setRowCount(0);
        Conexion.obtenerIntervencionBean().obtenerTodas().forEach(intervencion -> {
            model.addRow(new Object[]{
                    intervencion.getFechaHora(),
                    intervencion.getIdTipo(),
                    intervencion.getMotivo(),
                    intervencion.getIdEquipo(),
                    intervencion.getComentarios()
            });
        });
    }
}
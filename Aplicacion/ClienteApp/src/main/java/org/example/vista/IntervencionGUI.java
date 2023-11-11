package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.*;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.IntervencionRemote;
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
        model.addColumn("Fecha / Hora");
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

        TiposIntervencione inter1 = new TiposIntervencione();
        TiposIntervencione inter2 = new TiposIntervencione();
        TiposIntervencione inter3 = new TiposIntervencione();
        inter1.setNombreTipo("Prevencion");
        inter1.setId(1L);
        inter2.setNombreTipo("Falla");
        inter2.setId(2L);
        inter3.setNombreTipo("Resolucion");
        inter3.setId(3L);
        comboTipodeIntervencion.addItem(inter1);
        comboTipodeIntervencion.addItem(inter2);
        comboTipodeIntervencion.addItem(inter3);


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
                                                                  } catch (ServiciosException ex) {
                                                                      throw new RuntimeException(ex);
                                                                  } catch (NamingException ex) {
                                                                      throw new RuntimeException(ex);
                                                                  }
                                                              }
                                                          }
            );

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
package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.Intervencion;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.TiposIntervencione;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.IntervencionRemote;
import org.example.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public IntervencionGUI() {
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

        //Cargar datos de la tabla
        //actualizarTabla();

        //registrarIntervencionButton.addActionListener(new ActionListener() {
          //  @Override
            //public void actionPerformed(ActionEvent e) {
              //  Intervencion intervencion = new Intervencion();
               // intervencion.setFechaHora(textFechaHora.getText());
                //intervencion.setMotivo(textMotivo.getText());
                //intervencion.setIdEquipo(textIdEquipo.getText());
                //intervencion.setComentarios(textComentarios.getText());
                //intervencionRemoteBean.crear(intervencion));
        registrarIntervencionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mostrarPopUpConfirmacion();
            }
        });


    }

    private void mostrarPopUpConfirmacion() {
        JFrame popUpFrame = new JFrame ("Confirmación");
        popUpFrame.setSize(450,100);

        // Crear un JLabel con el mensaje de confirmación
        JLabel mensajeLabel = new JLabel("¿Deseas confirmar el registro de Intervención?");
        mensajeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Crear botones de confirmar y cancelar en el panel emergente
        JButton confirmarButton = new JButton("Confirmar");
        JButton cancelarButton = new JButton("Cancelar");

        // Agregar ActionListener al botón de confirmar
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(popUpFrame, "Registro realizado con éxito");
                popUpFrame.dispose(); // Cerrar el panel emergente después de confirmar
            }
        });

        // Agregar ActionListener al botón de cancelar
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(popUpFrame, "Registro cancelado");
                popUpFrame.dispose(); // Cerrar el panel emergente después de cancelar
            }
        });

        // Crear un panel para organizar los botones en el panel emergente
        JPanel panelEmergente = new JPanel();
        panelEmergente.add(confirmarButton);
        panelEmergente.add(cancelarButton);

        // Agregar componentes al panel emergente
        popUpFrame.add(mensajeLabel);
        popUpFrame.add(panelEmergente);

        // Establecer el diseño del panel emergente
        popUpFrame.setLayout(new BoxLayout(popUpFrame.getContentPane(), BoxLayout.Y_AXIS));

        // Hacer visible el panel emergente
        popUpFrame.setVisible(true);
    }


};








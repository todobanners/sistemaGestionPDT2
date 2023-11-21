package org.example.vista.Intervencion;

import codigocreativo.uy.servidorapp.entidades.*;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.IntervencionRemote;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DateTimePicker;
import org.example.modelo.Conexion;
import org.example.modelo.DatePickerUtil;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.util.Date;

public class IntervencionGUI {
    private JPanel panelIntervencion;
    private JComboBox comboTipodeIntervencion;
    private JTextField textMotivo;
    private JTextField textIdEquipo;
    private JTextField textComentarios;
  //  private JPanel panelAcciones;
    private JButton registrarIntervencionButton;
    private JTable tablaIntervenciones;
    private IntervencionRemote intervencionRemoteBean;


    private JScrollPane panelTabla;
    private JPanel fecha;
    DateTimePicker selectorFecha = DatePickerUtil.createCustomDateTimePicker();
    public JPanel getPanel(){
        return panelIntervencion;
    }

    public IntervencionGUI() throws Exception {
        JFrame frame = new JFrame("Intervenciones");
        fecha.add(selectorFecha);

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
                intervencion.setFechaHora(selectorFecha.getDateTimePermissive().toLocalDate());
                intervencion.setMotivo(textMotivo.getText());
                Equipo equipo = new Equipo();
                equipo.setId(Long.valueOf(textIdEquipo.getText()));
                intervencion.setIdEquipo(equipo);
                intervencion.setComentarios(textComentarios.getText());
                intervencion.setIdTipo((TiposIntervencione) comboTipodeIntervencion.getSelectedItem());
                intervencion.setMotivo(textMotivo.getText());
                intervencion.setFechaHora(selectorFecha.getDateTimePermissive().toLocalDate());


                try {
                    // Antes de crear la intervención, mostrar el cuadro de diálogo de confirmación
                    int opcion = JOptionPane.showConfirmDialog(
                            panelIntervencion, // Componente padre (puedes cambiarlo según tu contexto)
                            "¿Estás seguro de que quieres registrar?", // Mensaje
                            "Confirmación", // Título del cuadro de diálogo
                            JOptionPane.OK_CANCEL_OPTION // Tipo de opciones
                    );

                    // Verificar la opción seleccionada por el usuario
                    if (opcion == JOptionPane.OK_OPTION) {
                        // Si el usuario hizo clic en "Aceptar", proceder con el registro
                        Conexion.obtenerIntervencionBean().crear(intervencion);
                        actualizarTabla();
                    } else {
                        // Si el usuario hizo clic en "Cancelar" o cerró el cuadro de diálogo, no hacer nada
                    }
                } catch (ServiciosException | NamingException ex) {
                    throw new RuntimeException(ex);
                }
            }


                  /*  try {
                        Conexion.obtenerIntervencionBean().crear(intervencion);
                        actualizarTabla();
                    } catch (ServiciosException | NamingException ex) {
                        throw new RuntimeException(ex);
                    }*/

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

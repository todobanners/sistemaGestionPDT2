package org.example.vista.Intervencion;

import codigocreativo.uy.servidorapp.DTO.EquipoDto;
import codigocreativo.uy.servidorapp.DTO.IntervencionDto;
import codigocreativo.uy.servidorapp.DTO.TiposIntervencioneDto;
import codigocreativo.uy.servidorapp.entidades.*;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.IntervencionRemote;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DateTimePicker;
import org.example.controlador.Sesion;
import org.example.modelo.Conexion;
import org.example.modelo.Utilidades;


import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

public class IntervencionGUI {
    private JPanel panelIntervencion;
    private JComboBox comboTipodeIntervencion;
    private JTextField textMotivo;
    private JTextArea textComentarios;
    //  private JPanel panelAcciones;
    private JButton registrarIntervencionButton;
    private JTable tablaIntervenciones;
    private IntervencionRemote intervencionRemoteBean;


    private JScrollPane panelTabla;
    private JPanel fecha;
    private JButton filtroBoton;
    private JComboBox filtroEstadoCombo;
    private JLabel estadoTipo;
    private JButton limpiarButton;
    private JButton exportarAExcelButton;
    private JPanel panelDatePickerInicio;
    private DatePicker dateChooserInicio;
    private DatePicker dateChooserFin;
    private JComboBox comboFiltroTipo;
    private JTextField textFiltroMotivo;
    private JComboBox comboFiltroEquipo;
    private JPanel panelDatePickerFin;
    private JComboBox comboEquipos;
    DateTimePicker selectorFecha = Utilidades.createCustomDateTimePicker();


    public JPanel getPanel() {
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
        //Cargar el combo de estados
        for (Estados estado : Estados.values()) {
            filtroEstadoCombo.addItem(estado);
        }
        //Cargar los combo de tipos de intervenciones
        comboFiltroTipo.addItem("Todos");
        for (TiposIntervencioneDto tipoIntervencione : Conexion.obtenerTiposIntervencionBean().obtenerTiposIntervenciones()) {
            comboFiltroTipo.addItem(tipoIntervencione);
            comboTipodeIntervencion.addItem(tipoIntervencione);
        }
        //Cargar el combo de equipos
        comboFiltroEquipo.addItem("Todos");
        for (EquipoDto equipo : Conexion.obtenerEquipoBean().listarEquipos()) {
            comboFiltroEquipo.addItem(equipo);
            comboEquipos.addItem(equipo);
        }

        //Añadir el date picker de inicio y de fin del filtro
        dateChooserInicio = Utilidades.createCustomDatePicker();
        dateChooserFin = Utilidades.createCustomDatePicker();

        panelDatePickerInicio.add(dateChooserInicio);
        panelDatePickerFin.add(dateChooserFin);


        actualizarTabla();


        registrarIntervencionButton.addActionListener(e -> {
            IntervencionDto intervencion = new IntervencionDto();
            try {
                intervencion.setIdTipo((TiposIntervencioneDto) comboTipodeIntervencion.getSelectedItem())
                        .setFechaHora(selectorFecha.getDateTimePermissive())
                        .setMotivo(textMotivo.getText())
                        .setIdEquipo((EquipoDto) comboEquipos.getSelectedItem())
                        .setComentarios(textComentarios.getText())
                        .setIdUsuario(Conexion.obtenerUsuarioBean().obtenerUsuarioDto(Sesion.getUsuario().getId()));//TODO: cambiar por el dto
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }


            try {
                // Antes de crear la intervención, mostrar el cuadro de diálogo de confirmación
                int opcion = JOptionPane.showConfirmDialog(
                        panelIntervencion, // Componente padre
                        "¿Estás seguro de que quieres registrar?", // Mensaje
                        "Confirmación", // Título del cuadro de diálogo
                        JOptionPane.OK_CANCEL_OPTION // Tipo de opciones
                );

                // Verificar la opción seleccionada por el usuario
                if (opcion == JOptionPane.OK_OPTION) {
                    // Si el usuario hizo clic en "Aceptar", proceder con el registro
                    try {
                        intervencionRemoteBean.crear(intervencion);
                    } catch (ServiciosException ex) {
                        ex.printStackTrace();
                    }
                    actualizarTabla();
                }
            } catch (ServiciosException | NamingException ex) {
                throw new RuntimeException(ex);
            }
        });

        filtroBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, Object> filtro = new HashMap<>();
                if (!filtroEstadoCombo.getSelectedItem().equals("Todos")) {
                    filtro.put("estado", filtroEstadoCombo.getSelectedItem().toString());
                } else {
                    filtro.put("estado", null);
                }
                if (dateChooserInicio.getDate() != null) {
                    filtro.put("fechaInicio", dateChooserInicio.getDate());
                } else {
                    filtro.put("fechaInicio", null);
                }
                if (dateChooserFin.getDate() != null) {
                    filtro.put("fechaFin", dateChooserFin.getDate());
                } else {
                    filtro.put("fechaFin", null);
                }
                if (!(comboFiltroTipo.getSelectedItem().equals("Todos"))) {
                    filtro.put("tipo", comboFiltroTipo.getSelectedItem().toString());
                } else {
                    filtro.put("tipo", null);
                }
                if (!(textFiltroMotivo.getText().equals(""))) {
                    filtro.put("motivo", textFiltroMotivo.getText());
                } else {
                    filtro.put("motivo", null);
                }
                if (!(comboFiltroEquipo.getSelectedItem().equals("Todos"))) {
                    filtro.put("equipo", String.valueOf(((Equipo) comboFiltroEquipo.getSelectedItem()).getId()));

                } else {
                    filtro.put("equipo", null);
                }
                //Debug: mostrar el hashmap en consola
                System.out.println(filtro);

                //Filtrar desde el cliente
                DefaultTableModel modelFiltrado = model;
                model.setRowCount(0);
                modelFiltrado.getDataVector().forEach(row -> {
                    Vector<String> rowVector = (Vector<String>) row;
                    if (filtro.get("estado") != null && !rowVector.elementAt(1).equals(filtro.get("estado"))) {
                        return;
                    }
                    //Filtrar por fecha. Usando objetos Date, no Strings
                    if (filtro.get("fechaInicio") != null && filtro.get("fechaFin") != null) {
                        if (rowVector.elementAt(0).compareTo(filtro.get("fechaInicio").toString()) < 0 || rowVector.elementAt(0).compareTo(filtro.get("fechaFin").toString()) > 0) {
                            return;
                        }
                    } else if (filtro.get("fechaInicio") != null) {
                        if (rowVector.elementAt(0).compareTo(filtro.get("fechaInicio").toString()) < 0) {
                            return;
                        }
                    } else if (filtro.get("fechaFin") != null) {
                        if (rowVector.elementAt(0).compareTo(filtro.get("fechaFin").toString()) > 0) {
                            return;
                        }
                    }
                    if (filtro.get("tipo") != null && !rowVector.elementAt(2).equals(filtro.get("tipo"))) {
                        return;
                    }
                    if (filtro.get("motivo") != null && !rowVector.elementAt(3).equals(filtro.get("motivo"))) {
                        return;
                    }
                    if (filtro.get("equipo") != null && !rowVector.elementAt(4).equals(filtro.get("equipo"))) {
                        return;
                    }
                    model.addRow(rowVector);
                });
                if (model.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No se encontraron resultados para la búsqueda");
                }


            }
        });
    }

    public void actualizarTabla() throws NamingException, ServiciosException {
        DefaultTableModel model = (DefaultTableModel) tablaIntervenciones.getModel();
        model.setRowCount(0);
        for (IntervencionDto intervencion : intervencionRemoteBean.obtenerTodas()) {
            model.addRow(new Object[]{
                    intervencion.getFechaHora(),
                    intervencion.getIdTipo().getNombreTipo(),
                    intervencion.getMotivo(),
                    intervencion.getIdEquipo().getId(),
                    intervencion.getComentarios()
            });
        }
    }

}

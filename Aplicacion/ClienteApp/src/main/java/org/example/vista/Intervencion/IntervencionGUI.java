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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Vector;

public class IntervencionGUI {
    private JPanel panelIntervencion;
    private JComboBox comboTipodeIntervencion;
    private JTextField textMotivo;
    private JTextArea textComentarios;
    private JButton registrarIntervencionButton;
    private JTable tablaIntervenciones;
    private IntervencionRemote intervencionRemoteBean;


    private JScrollPane panelTabla;
    private JPanel fecha;
    private JButton filtroBoton;
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
                        .setIdUsuario(Conexion.obtenerUsuarioBean().obtenerUsuarioDto(Sesion.getUsuario().getId()));
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }

            try {
                // Antes de crear la intervención, mostrar el cuadro de diálogo de confirmación
                int opcion = JOptionPane.showConfirmDialog(panelIntervencion, // Componente padre
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

                    // Limpiar los campos después de registrar la intervención
                    comboTipodeIntervencion.setSelectedIndex(0);
                    textMotivo.setText("");
                    textComentarios.setText("");
                    comboEquipos.setSelectedIndex(0);
                    selectorFecha.clear();
                }
            } catch (ServiciosException | NamingException ex) {
                throw new RuntimeException(ex);
            }
        });


        filtroBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, Object> filtro = new HashMap<>();

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
                if (!(textFiltroMotivo.getText().isEmpty())) {
                    filtro.put("motivo", textFiltroMotivo.getText());
                } else {
                    filtro.put("motivo", null);
                }
                if (!(comboFiltroEquipo.getSelectedItem().equals("Todos"))) {
                    filtro.put("equipo", ((EquipoDto) comboFiltroEquipo.getSelectedItem()).getId());

                } else {
                    filtro.put("equipo", null);
                }
                //Debug: mostrar el hashmap en consola
                System.out.println(filtro);

                //Filtrar desde el cliente
                Vector<Vector<Object>> dataVector = new Vector<>();
                try {
                    actualizarTabla();
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                } catch (ServiciosException ex) {
                    throw new RuntimeException(ex);
                }

                model.getDataVector().forEach(row -> {
                    Vector<Object> rowVector = (Vector<Object>) row;
                    // Initialize a flag to track whether the row passes all conditions
                    boolean shouldAddRow = true;

                    if (filtro.get("fechaInicio") != null && filtro.get("fechaFin") != null) {
                        LocalDate fechaInicio = ((LocalDate) filtro.get("fechaInicio"));
                        LocalDate fechaFin = ((LocalDate) filtro.get("fechaFin"));
                        LocalDate fechaActual = ((LocalDateTime) rowVector.elementAt(0)).toLocalDate();

                        if (fechaActual.isBefore(fechaInicio) || fechaActual.isAfter(fechaFin)) {
                            shouldAddRow = false;
                        }
                    } else if (filtro.get("fechaInicio") != null) {
                        LocalDate fechaInicio = ((LocalDate) filtro.get("fechaInicio"));
                        LocalDate fechaActual = ((LocalDateTime) rowVector.elementAt(0)).toLocalDate();

                        if (fechaActual.isBefore(fechaInicio)) {
                            shouldAddRow = false;
                        }
                    } else if (filtro.get("fechaFin") != null) {
                        LocalDate fechaFin = ((LocalDate) filtro.get("fechaFin"));
                        LocalDate fechaActual = ((LocalDateTime) rowVector.elementAt(0)).toLocalDate();

                        if (fechaActual.isAfter(fechaFin)) {
                            shouldAddRow = false;
                        }
                    }


                    // Filter by tipo (case-insensitive search)
                    if (filtro.get("tipo") != null && !rowVector.elementAt(1).toString().toLowerCase().contains(filtro.get("tipo").toString().toLowerCase())) {
                        shouldAddRow = false;
                    }

// Filter by motivo (case-insensitive search)
                    if (filtro.get("motivo") != null && !rowVector.elementAt(2).toString().toLowerCase().contains(filtro.get("motivo").toString().toLowerCase())) {
                        shouldAddRow = false;
                    }


                    // Filter by equipo
                    if (filtro.get("equipo") != null && !rowVector.elementAt(3).equals(filtro.get("equipo"))) {
                        shouldAddRow = false;
                    }

                    // If the row passes all filters, add it to modelFiltrado
                    if (shouldAddRow) {
                        dataVector.add(rowVector);
                    }
                });
                if (dataVector.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se encontraron resultados para la búsqueda");
                }
                model.setRowCount(0);
                dataVector.forEach(model::addRow);


            }
        });
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateChooserInicio.setDate(null);
                dateChooserFin.setDate(null);
                comboFiltroTipo.setSelectedIndex(0);
                textFiltroMotivo.setText("");
                comboFiltroEquipo.setSelectedIndex(0);
                try {
                    actualizarTabla();
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                } catch (ServiciosException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        exportarAExcelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Vector<String> columnas = new Vector<>();
                    columnas.add("Fecha");
                    columnas.add("Tipo de Intervención");
                    columnas.add("Motivo");
                    columnas.add("Equipo");
                    columnas.add("Comentarios");
                    Vector<Vector<Object>> dataVector = new Vector<>();
                    model.getDataVector().forEach(row -> {
                        Vector<Object> rowVector = (Vector<Object>) row;
                        dataVector.add(rowVector);
                    });
                    Utilidades.exportarAExcel(columnas, dataVector, "Intervenciones");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void actualizarTabla() throws NamingException, ServiciosException {
        DefaultTableModel model = (DefaultTableModel) tablaIntervenciones.getModel();
        model.setRowCount(0);
        for (IntervencionDto intervencion : intervencionRemoteBean.obtenerTodas()) {
            model.addRow(new Object[]{intervencion.getFechaHora(), intervencion.getIdTipo().getNombreTipo(), intervencion.getMotivo(), intervencion.getIdEquipo().getId(), intervencion.getComentarios()});
        }
    }

}

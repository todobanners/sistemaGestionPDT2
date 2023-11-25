package org.example.vista.Intervencion;

import codigocreativo.uy.servidorapp.DTO.TiposIntervencioneDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.TipoIntervencioneRemote;
import org.example.modelo.Conexion;
import org.example.modelo.Validator;
import org.jdesktop.swingx.JXTable;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class TiposDeIntervencionesGUI {
    private JPanel panelTipoDeIntervenciones;
    private JPanel panelAcciones;
    private JTextField textNombre;
    private JComboBox comboEstado;
    private JButton guardarButton;
    private JScrollPane panelTabla;
    private JXTable tablaTiposDeIntervenciones;
    private JButton editarTipoDeIntervencionButton;
    private JButton filtroBoton;
    private JComboBox filtroEstadoCombo;
    private JTextField filtroNombreCampo;
    private JButton limpiarButton;
    private JLabel estadoTipo;
    private JButton darDeBajaElButton;
    private DefaultTableModel model;

    private TipoIntervencioneRemote tipoIntervencioneRemoteBean;

    public JPanel getPanel() {
        return panelTipoDeIntervenciones;
    }

    public TiposDeIntervencionesGUI() throws NamingException {

        JFrame frame = new JFrame(" Listado de Intervenciones");

        //Crear modelo de la tabla

        model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Estado");
        model.addColumn("ID");
        tablaTiposDeIntervenciones.setModel(model);
        tablaTiposDeIntervenciones.removeColumn(tablaTiposDeIntervenciones.getColumnModel().getColumn(2));


        try {
            tipoIntervencioneRemoteBean = (TipoIntervencioneRemote) Conexion.obtenerTiposIntervencionBean();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo conectar con el servidor");
        }
        //Cargar los datos de la tabla
        actualizarTabla();

        //Cargar los datos del combobox de estados
        for (Estados estado : Estados.values()) {
            comboEstado.addItem(estado);
            filtroEstadoCombo.addItem(estado);
        }


        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estás seguro de guardar el tipo de intervención?",
                        "Confirmar Guardado",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (!Validator.validarMinimoCaracteres(textNombre.getText(), 1)) {
                        JOptionPane.showMessageDialog(null, "El nombre debe tener al menos 1 caracter");
                    } else if (!Validator.validarMaximoCaracteres(textNombre.getText(), 30)) {
                        JOptionPane.showMessageDialog(null, "El nombre no puede tener más de 30 caracteres");
                    } else {
                        TiposIntervencioneDto tipoIntervencioneDto = new TiposIntervencioneDto(
                                null,
                                textNombre.getText(),
                                (Estados) comboEstado.getSelectedItem()
                        );

                        tipoIntervencioneRemoteBean.crearTipoIntervencion(tipoIntervencioneDto);

                        // Limpiar los campos después de guardar
                        limpiarCampos();

                        // Actualizar la lista después de guardar
                        actualizarTabla();
                    }
                }
            }
        });

        filtroEstadoCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //filtrar el modelo por estado
                model.setRowCount(0);
                tipoIntervencioneRemoteBean.obtenerTiposIntervenciones().forEach(tipoIntervencione -> {
                    if (tipoIntervencione.getEstado().equals(filtroEstadoCombo.getSelectedItem())) {
                        model.addRow(new Object[]{tipoIntervencione.getNombreTipo(), tipoIntervencione.getEstado()});
                    }
                });
            }
        });
        filtroBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //filtrar el modelo por nombre
                model.setRowCount(0);
                tipoIntervencioneRemoteBean.obtenerTiposIntervenciones().forEach(tipoIntervencione -> {
                    if (containsIgnoreCase(tipoIntervencione.getNombreTipo(), filtroNombreCampo.getText())) {
                        model.addRow(new Object[]{tipoIntervencione.getNombreTipo(), tipoIntervencione.getEstado()});
                    }
                    else {
                        //JOptionPane.showMessageDialog(null, "No se encontraron resultados para la búsqueda");
                    }
                });
            }
        });
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //limpiar los filtros
                filtroEstadoCombo.setSelectedIndex(0);
                filtroNombreCampo.setText("");
                actualizarTabla();
            }
        });
        editarTipoDeIntervencionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tablaTiposDeIntervenciones.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un tipo de intervención");
                    return;
                }
                //abrir ventana para editar el tipo de intervención de la fila seleccionada
                String nombre = (String) model.getValueAt(tablaTiposDeIntervenciones.getSelectedRow(), 0);
                Estados estado = (Estados) model.getValueAt(tablaTiposDeIntervenciones.getSelectedRow(), 1);
                Long id = (Long) model.getValueAt(tablaTiposDeIntervenciones.getSelectedRow(), 2);
                ModificarTipoDeIntervencion modificarTipoDeIntervencion = new ModificarTipoDeIntervencion(nombre, estado);
                if (modificarTipoDeIntervencion.getNombreSel() != null && modificarTipoDeIntervencion.getEstadoSel() != null) {
                    TiposIntervencioneDto tipoIntervencioneDto = new TiposIntervencioneDto(id, nombre, modificarTipoDeIntervencion.getEstadoSel());
                    tipoIntervencioneRemoteBean.modificarTipoIntervencion(tipoIntervencioneDto);
                }
            }
        });
        darDeBajaElButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tablaTiposDeIntervenciones.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un tipo de intervención");
                    return;
                }
                //abrir ventana para editar el tipo de intervención de la fila seleccionada
                String nombre = (String) model.getValueAt(tablaTiposDeIntervenciones.getSelectedRow(), 0);
                Estados estado = (Estados) model.getValueAt(tablaTiposDeIntervenciones.getSelectedRow(), 1);
                Long id = (Long) model.getValueAt(tablaTiposDeIntervenciones.getSelectedRow(), 2);
                ModificarTipoDeIntervencion modificarTipoDeIntervencion = new ModificarTipoDeIntervencion(nombre, estado);
                if (modificarTipoDeIntervencion.getNombreSel() != null && modificarTipoDeIntervencion.getEstadoSel() != null) {
                    TiposIntervencioneDto tipoIntervencioneDto = new TiposIntervencioneDto(id, nombre, modificarTipoDeIntervencion.getEstadoSel());
                    tipoIntervencioneRemoteBean.modificarTipoIntervencion(tipoIntervencioneDto);
                }
            }
        });
    }
    private void limpiarCampos() {
        textNombre.setText("");
    }

    private void actualizarTabla() {
        model.setRowCount(0);
        tipoIntervencioneRemoteBean.obtenerTiposIntervenciones().forEach(tipoIntervencione -> {
            model.addRow(new Object[]{tipoIntervencione.getNombreTipo(), tipoIntervencione.getEstado(), tipoIntervencione.getId()});
        });
    }
}

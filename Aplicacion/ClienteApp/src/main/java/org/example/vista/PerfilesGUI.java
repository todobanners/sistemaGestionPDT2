package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.PerfilRemote;
import org.example.modelo.Conexion;
import org.example.modelo.Validator;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.hyperlink.AbstractHyperlinkAction;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.HyperlinkProvider;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.event.*;

public class PerfilesGUI {
    private JPanel panelPerfiles;
    private JPanel panelAcciones;
    private JScrollPane panelTabla;
    private JTextField textNombre;
    private JComboBox comboEstado;
    private JButton editarPerfilSeleccionadoButton;
    private JButton guardarButton;
    private JXTable tablaPerfiles;
    private JButton borrarPerfilSeleccionadoButton;
    private JButton filtroBoton;
    private JComboBox filtroEstadoCombo;
    private JTextField filtroNombreCampo;
    private JButton limpiarButton;
    private DefaultTableModel model;


    private PerfilRemote perfilesPermisoBean;

    public JPanel getPanel() {
        return panelPerfiles;
    }

    public PerfilesGUI() {
        JFrame frame = new JFrame("Perfiles");


        //Crear modelo de la tabla

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Permisos");
        model.addColumn("Estado");
        tablaPerfiles.setModel(model);

        //Cargar los datos del combobox de estados
        for (Estados estado : Estados.values()) {
            comboEstado.addItem(estado);
            filtroEstadoCombo.addItem(estado);
        }

        //Agregar boton de permisos
        AbstractHyperlinkAction<Object> simpleAction = new AbstractHyperlinkAction<Object>(null) {

            public void actionPerformed(ActionEvent e) {
                Perfil perfil = perfilesPermisoBean.obtenerPerfil((Long) tablaPerfiles.getValueAt(tablaPerfiles.getSelectedRow(), 0));
                PerfilPermisosGUI perfilPermisosGUI = new PerfilPermisosGUI(perfil);

            }

        };
        TableCellRenderer renderer = new DefaultTableRenderer(new HyperlinkProvider(simpleAction));
        tablaPerfiles.getColumnExt(2).setEditable(false);
        tablaPerfiles.getColumnExt(2).setCellRenderer(renderer);


        try {
            perfilesPermisoBean = Conexion.obtenerPerfilBean();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con el servidor");
        }

        //Cargar datos de la tabla
        actualizarTabla();


        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Perfil perfil = new Perfil();
                if (!Validator.validarMaximoCaracteres(textNombre.getText(), 20)) {
                    JOptionPane.showMessageDialog(null, "El nombre no debe tener mas de 20 caracteres");
                }else if (!Validator.validarMinimoCaracteres(textNombre.getText(), 3)) {
                    JOptionPane.showMessageDialog(null, "El nombre debe tener al menos 3 caracteres");
                } else if (Validator.contieneSoloLetras(textNombre.getText())) {
                    JOptionPane.showMessageDialog(null, "El nombre solo debe contener letras");
                } else {
                    perfil.setNombrePerfil(textNombre.getText());
                    perfil.setEstado(Estados.valueOf(comboEstado.getSelectedItem().toString()));
                    perfilesPermisoBean.crearPerfil(perfil);
                    actualizarTabla();
                }
            }
        });
        editarPerfilSeleccionadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Perfil perfil = perfilesPermisoBean.obtenerPerfil((Long) tablaPerfiles.getValueAt(tablaPerfiles.getSelectedRow(), 0));
                if (Validator.validarMaximoCaracteres(textNombre.getText(), 20)) {
                    JOptionPane.showMessageDialog(null, "El nombre no debe tener mas de 20 caracteres");
                }else if (Validator.validarMinimoCaracteres(textNombre.getText(), 2)) {
                    JOptionPane.showMessageDialog(null, "El nombre debe tener al menos 3 caracteres");
                } else if (Validator.contieneSoloLetras(textNombre.getText())) {
                    JOptionPane.showMessageDialog(null, "El nombre solo debe contener letras");
                } else{
                    perfil.setNombrePerfil(textNombre.getText());
                    perfil.setEstado(Estados.valueOf(comboEstado.getSelectedItem().toString()));
                    perfilesPermisoBean.modificarPerfil(perfil);
                    JOptionPane.showMessageDialog(null, "Perfil modificado");
                    actualizarTabla();
                }

            }
        });
        borrarPerfilSeleccionadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Perfil perfil = perfilesPermisoBean.obtenerPerfil((Long) tablaPerfiles.getValueAt(tablaPerfiles.getSelectedRow(), 0));
                perfilesPermisoBean.eliminarPerfil(perfil);
                JOptionPane.showMessageDialog(null, "Perfil eliminado");
                actualizarTabla();
            }
        });
        //Cuando se ahce click en una fila de la tabla
        tablaPerfiles.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //Cargar datos del perfil seleccionado
                Perfil perfil = perfilesPermisoBean.obtenerPerfil((Long) tablaPerfiles.getValueAt(tablaPerfiles.getSelectedRow(), 0));
                textNombre.setText(perfil.getNombrePerfil());
                comboEstado.setSelectedItem(perfil.getEstado());

            }
        });
        filtroEstadoCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Cargar datos de la tabla filtrados por estado
                model.setRowCount(0);
                for (Perfil perfil : perfilesPermisoBean.listarPerfilesPorEstado((Estados) filtroEstadoCombo.getSelectedItem())) {
                    model.addRow(new Object[]{perfil.getId(), perfil.getNombrePerfil(), "Ver permisos", perfil.getEstado()});
                    System.out.println(perfil.getNombrePerfil());
                }
            }
        });
        filtroBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Cargar datos de la tabla filtrados por nombre
                model.setRowCount(0);
                for (Perfil perfil : perfilesPermisoBean.listarPerfilesPorNombre(filtroNombreCampo.getText())) {
                    model.addRow(new Object[]{perfil.getId(), perfil.getNombrePerfil(), "Ver permisos", perfil.getEstado()});
                    System.out.println(perfil.getNombrePerfil());
                }
            }
        });
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Limpiar filtros
                filtroNombreCampo.setText("");
                filtroEstadoCombo.setSelectedIndex(0);
                actualizarTabla();
            }
        });
    }

    private void actualizarTabla() {
        model.setRowCount(0);
        for (Perfil perfil : perfilesPermisoBean.obtenerPerfiles()) {
            model.addRow(new Object[]{perfil.getId(), perfil.getNombrePerfil(), "Ver permisos", perfil.getEstado()});
            System.out.println(perfil.getNombrePerfil());
        }

    }

}

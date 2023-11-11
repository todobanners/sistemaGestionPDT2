package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.PerfilRemote;
import org.example.modelo.Conexion;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.hyperlink.AbstractHyperlinkAction;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.HyperlinkProvider;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                perfil.setNombrePerfil(textNombre.getText());
                perfil.setEstado(Estados.valueOf(comboEstado.getSelectedItem().toString()));
                perfilesPermisoBean.crearPerfil(perfil);
                actualizarTabla();
            }
        });
        editarPerfilSeleccionadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Perfil perfil = perfilesPermisoBean.obtenerPerfil((Long) tablaPerfiles.getValueAt(tablaPerfiles.getSelectedRow(), 0));
                perfil.setNombrePerfil(textNombre.getText());
                perfil.setEstado(Estados.valueOf(comboEstado.getSelectedItem().toString()));
                perfilesPermisoBean.modificarPerfil(perfil);
                JOptionPane.showMessageDialog(null, "Perfil modificado");
                actualizarTabla();
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
    }

    private void actualizarTabla() {
        model.setRowCount(0);
        for (Perfil perfil : perfilesPermisoBean.obtenerPerfiles()) {
            model.addRow(new Object[]{perfil.getId(), perfil.getNombrePerfil(), "Ver permisos", perfil.getEstado()});
            System.out.println(perfil.getNombrePerfil());
        }

    }

}

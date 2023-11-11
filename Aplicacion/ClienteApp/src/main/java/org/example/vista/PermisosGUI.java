package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.Permiso;
import codigocreativo.uy.servidorapp.servicios.PermisoRemote;
import org.example.Conexion;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.hyperlink.AbstractHyperlinkAction;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.HyperlinkProvider;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PermisosGUI {
    private JPanel panelPermisos;
    private JTextField nombreText;
    private JButton guardarButton;
    private JPanel panelAcciones;
    private JXTable tablaPermisos;
    private PermisoRemote permisoBean;
    private DefaultTableModel model;

    public JPanel getPanel() {
        return panelPermisos;
    }

    public PermisosGUI() {
        JFrame frame = new JFrame("Permisos");

        try {
            permisoBean = Conexion.obtenerPermisoBean();
        } catch (NamingException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con el servidor", "Error", JOptionPane.ERROR_MESSAGE);
        }

        //Crear modelo de la tabla
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        tablaPermisos.setModel(model);
    }

    private void actualizarTabla() {
        model.setRowCount(0);
        for (Permiso permiso : permisoBean.obtenerPermisos()) {
            model.addRow(new Object[]{permiso.getId(), permiso.getTipoPermiso()});
        }
    }


}

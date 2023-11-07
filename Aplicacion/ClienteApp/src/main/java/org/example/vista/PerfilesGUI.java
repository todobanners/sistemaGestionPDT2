package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.PerfilRemote;
import codigocreativo.uy.servidorapp.servicios.PerfilesPermisoRemote;
import org.example.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.example.Conexion.obtenerPerfilBean;

public class PerfilesGUI {
    private JPanel panelPerfiles;
    private JPanel panelAcciones;
    private JScrollPane panelTabla;
    private JTextField textNombre;
    private JComboBox comboEstado;
    private JButton asignarPermisosButton;
    private JButton guardarButton;
    private JTable tablaPerfiles;


    private PerfilRemote perfilesPermisoBean;

    public JPanel getPanel(){
        return panelPerfiles;
    }
    public PerfilesGUI() {
        JFrame frame = new JFrame("Perfiles");

        //Crear modelo de la tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Estado");
        tablaPerfiles.setModel(model);

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
    }

    private void actualizarTabla() {
        /*DefaultTableModel model = (DefaultTableModel) tablaPerfiles.getModel();
        model.setRowCount(0);
        for (Perfil perfil : perfilesPermisoBean.obtenerPerfiles()) {
            model.addRow(new Object[]{perfil.getId(), perfil.getNombrePerfil(), perfil.getEstado()});
        }*/
    }
}

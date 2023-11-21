package org.example.vista.Perfil;

import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Permiso;
import codigocreativo.uy.servidorapp.servicios.PerfilRemote;
import codigocreativo.uy.servidorapp.servicios.PermisoRemote;
import org.example.modelo.Conexion;
import org.example.vista.DualListBox;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PerfilPermisosGUI {
    private JPanel panelPerfiPermisos;
    private DualListBox dual;
    private JPanel panelDual;
    private JButton guardarButton;
    private JButton cerrarButton;

    private final Perfil perfil;

    private PerfilRemote perfilesBean;
    private PermisoRemote permisoBean;

    public PerfilPermisosGUI(Perfil p) {
        perfil = p;
        try {
            perfilesBean = Conexion.obtenerPerfilBean();
            permisoBean = Conexion.obtenerPermisoBean();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Perfiles");
        frame.setContentPane(panelPerfiPermisos);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        panelDual.add(dual, BorderLayout.CENTER);
        actualizarDual();

        frame.pack();
        frame.setVisible(true);
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListModel model = dual.getSourceListModel();
                List<Permiso> permisos = permisoBean.obtenerPermisos();
                for (int i = 0; i < model.getSize(); i++) {
                    String permiso = (String) model.getElementAt(i);
                    for (Permiso p : permisos) {
                        if (p.getTipoPermiso().equals(permiso)) {
                            permisos.remove(p);
                            break;
                        }
                    }
                }
                perfil.setPermisos(permisos);
                perfilesBean.modificarPerfil(perfil);
                JOptionPane.showMessageDialog(null, "Permisos modificados correctamente");
                frame.dispose();
            }
        });
        cerrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    private void createUIComponents() {
        dual = new DualListBox();
    }

    private void actualizarDual() {
        List<Permiso> permisosAsignados = perfil.getPermisos();
        List<Permiso> permisosDisponibles = permisoBean.obtenerPermisos();

        for (Permiso p : permisosAsignados) {
            permisosDisponibles.removeIf(permiso -> permiso.getId() == p.getId());
        }

        String[] permisosAsignadosArray = new String[permisosAsignados.size()];
        String[] permisosDisponiblesArray = new String[permisosDisponibles.size()];
        for (int i = 0; i < permisosAsignados.size(); i++) {
            permisosAsignadosArray[i] = permisosAsignados.get(i).getTipoPermiso();
        }
        for (int i = 0; i < permisosDisponibles.size(); i++) {
            permisosDisponiblesArray[i] = permisosDisponibles.get(i).getTipoPermiso();
        }
        dual.addSourceElements(permisosDisponiblesArray);
        dual.addDestinationElements(permisosAsignadosArray);


    }

}

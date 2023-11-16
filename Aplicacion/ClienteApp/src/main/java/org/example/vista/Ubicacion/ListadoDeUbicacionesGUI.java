package org.example.vista.Ubicacion;

import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.modelo.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListadoDeUbicacionesGUI extends JPanel {
    private JPanel ListadoUbicaciones;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JList list1;

    public JPanel getPanel() {
        return ListadoUbicaciones;
    }


    public ListadoDeUbicacionesGUI() throws NamingException, ServiciosException {

        List<Ubicacion> listaUbicaciones = Conexion.obtenerUbicacionBean().listarUbicaciones();
        generarTabla(listaUbicaciones);
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void generarTabla(List<Ubicacion> lista){
        DefaultListModel modeloTabla = new DefaultListModel();
        for (Ubicacion ubicacion : lista) {
            modeloTabla.addElement(ubicacion);
        }
        list1.setModel(modeloTabla);
    }

}

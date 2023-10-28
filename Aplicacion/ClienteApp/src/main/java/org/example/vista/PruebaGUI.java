package org.example.vista;

import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PruebaGUI {
    private JPanel prueba;
    private JList list1;
    private JButton botonButton;

    public JPanel getPanel() {
        return prueba;
    }

    public PruebaGUI() throws NamingException, ServiciosException {

        list1.setListData(Conexion.obtenerDefaultBean().list().toArray());

        botonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}

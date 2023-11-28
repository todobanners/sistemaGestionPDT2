package org.example.vista.Equipo;

import codigocreativo.uy.servidorapp.DTO.EquipoDto;
import codigocreativo.uy.servidorapp.DTO.EquiposUbicacioneDto;
import org.example.modelo.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class ListadoMovimientos extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList listaMovimientos;

    public ListadoMovimientos(EquipoDto equipo) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(500, 500);

        try {
            List< EquiposUbicacioneDto > movimientos = Conexion.obtenerEquipoUbicacionBean().obtenerEquiposUbicacionePorEquipo(equipo.getId());
            //Los dos datos son Ubicacion y fecha
            DefaultListModel modelo = new DefaultListModel();
            for (EquiposUbicacioneDto mov : movimientos) {
                modelo.addElement(mov.getIdUbicacion().getNombre() + " - " + mov.getFecha());
            }
            if (modelo.isEmpty()) {
                modelo.addElement("No hay movimientos");
            }
            listaMovimientos.setModel(modelo);

        } catch (NamingException e) {
            throw new RuntimeException(e);
        }


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}

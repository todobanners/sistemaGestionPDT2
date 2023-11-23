package org.example.vista.Intervencion;

import codigocreativo.uy.servidorapp.enumerados.Estados;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificarTipoDeIntervencion extends JDialog {
    private JPanel panelModificarTipoDeIntervencion;
    private JPanel panelForm;
    private JComboBox<Estados> comboEstado;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JTextField textNombre;

    private Estados estadoSel;
    private String nombreSel;

    public ModificarTipoDeIntervencion(String nombre, Estados e) {
        setContentPane(panelModificarTipoDeIntervencion);

        // Cargar los datos del combobox de estados
        for (Estados estado : Estados.values()) {
            comboEstado.addItem(estado);
        }
        //Se cargan los datos del tipo de intervencion a modificar
        comboEstado.setSelectedItem(e);
        textNombre.setText(nombre);

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                estadoSel = (Estados) comboEstado.getSelectedItem();
                nombreSel = textNombre.getText();

                dispose();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                estadoSel = null;
                nombreSel = null;

                dispose();
            }
        });

        pack();
        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Estados getEstadoSel() {
        return estadoSel;
    }

    public String getNombreSel() {
        return nombreSel;
    }
}
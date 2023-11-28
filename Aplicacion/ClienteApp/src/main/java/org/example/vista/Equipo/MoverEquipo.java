package org.example.vista.Equipo;

import codigocreativo.uy.servidorapp.DTO.EquipoDto;
import codigocreativo.uy.servidorapp.DTO.UbicacionDto;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;

public class MoverEquipo extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<UbicacionDto> comboUbicacion;
    private JTextField textNombreUbi;
    private JTextArea textObs;
    private JTextField textNumeroUbi;
    private JTextField textPisoUbi;
    private JTextField textCamaUbi;
    private JPanel panelDate;
    private JTextField textEquipo;

    private DatePicker datePicker;

    private Boolean ok = false;
    public MoverEquipo(EquipoDto equipo, List<UbicacionDto> ubicaciones) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setTitle("Moviendo equipo: " + equipo.getNombre());

        textEquipo.setText(equipo.getNombre());

        setSize(600, 400);

        for (UbicacionDto ubicacion : ubicaciones) {
            comboUbicacion.addItem(ubicacion);
        }

        comboUbicacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UbicacionDto ubicacion = (UbicacionDto) comboUbicacion.getSelectedItem();
                textNombreUbi.setText(ubicacion.getNombre());
                textNumeroUbi.setText(String.valueOf(ubicacion.getNumero()));
                textPisoUbi.setText(String.valueOf(ubicacion.getPiso()));
                textCamaUbi.setText(String.valueOf(ubicacion.getCama()));
            }
        });
        comboUbicacion.setSelectedIndex(0);

        datePicker = new DatePicker();
        datePicker.setDateToToday();
        panelDate.add(datePicker);

        pack();

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

    public HashMap<String, Object> getDatos() {
        if (comboUbicacion.getSelectedItem() != null || datePicker.getDate() != null || !textObs.getText().isEmpty()) {
            HashMap<String, Object> datos = new HashMap<>();
            datos.put("ubicacion", comboUbicacion.getSelectedItem());
            datos.put("fecha", datePicker.getDate());
            datos.put("observaciones", textObs.getText());
            return datos;
        }
        return null;
    }
    private void onOK() {
        ok = true;
        dispose();
    }

    private void onCancel() {
        dispose();
    }
    public Boolean getOk() {
        return ok;
    }
}

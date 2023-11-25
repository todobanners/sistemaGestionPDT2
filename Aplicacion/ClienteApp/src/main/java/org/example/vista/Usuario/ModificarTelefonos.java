package org.example.vista.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ModificarTelefonos extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton buttonNuevoTel;
    private JScrollPane panelPadre;

    private JPanel panelTelefonos;

    private List<JTextField> telefonoInputs = new ArrayList<JTextField>();


    public ModificarTelefonos(List<String> telefonosParam) {

        //Titulo de la ventana
        setTitle("Gesionar telefonos");

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // Tamaño de la ventana
        contentPane.setPreferredSize(new Dimension(300, 300));

        panelTelefonos = new JPanel();
        panelTelefonos.setLayout(new BoxLayout(panelTelefonos, BoxLayout.Y_AXIS));

        panelPadre.setViewportView(panelTelefonos);

        if (!telefonosParam.isEmpty()) {
            for (String telefono : telefonosParam) {
                nuevoTelefono(telefono);
            }
        }
        else {
            nuevoTelefono(null);
        }



        buttonNuevoTel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nuevoTelefono(null);
            }
        });

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

    private void nuevoTelefono(String telefono) {
        // Crear un nuevo panel con un campo de texto y un boton para eliminarlo
        JPanel panel = new JPanel();
        JTextField textField = new JTextField();
        JButton button = new JButton("X");
        textField.setPreferredSize(new Dimension(120, 25));

        telefonoInputs.add(textField);

        //Setear el texto del campo de texto (si es null, no se setea)
        if (telefono != null) {
            textField.setText(telefono);
        }

        // Agregar el campo de texto y el boton al panel
        panel.add(textField);
        panel.add(button);

        // Agregar el panel al panel de telefonos
        panelTelefonos.add(panel);

        // Actualizar el panel
        panelTelefonos.revalidate();
        panelTelefonos.repaint();

        // Crear listener para el boton
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Eliminar el panel del panel de telefonos
                panelTelefonos.remove(panel);
                // Actualizar el panel
                panelTelefonos.updateUI();
            }
        });
    }
    public List<String> getTelefonos() {
        // Obtener los telefonos ingresados
        List<String> telefonos = new ArrayList<String>();
        for (JTextField textField : telefonoInputs) {
            if (!textField.getText().isEmpty()) {
                telefonos.add(textField.getText());
            }
        }
        return telefonos;
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}

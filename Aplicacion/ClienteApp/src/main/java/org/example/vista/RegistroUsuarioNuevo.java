package org.example.vista;

import javax.swing.*;

public class RegistroUsuarioNuevo extends JFrame {
    private JPanel registroUsuario;
    private JButton button1;
    private JButton button2;
    private JTextField textField1;
    private JTextField textField2;

    public RegistroUsuarioNuevo(String s) {
        super(s);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(registroUsuario);
        setVisible(true);
        pack();


    }
}

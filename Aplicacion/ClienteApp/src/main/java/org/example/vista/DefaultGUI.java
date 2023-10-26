package org.example.vista;

import javax.swing.*;
import java.awt.*;

public class DefaultGUI extends JFrame {
    private static DefaultGUI instancia;
    private JPanel panel1;
    private JPanel panelContenido;

    private DefaultGUI(){
        initComponents();
    }
    static {
        DefaultGUI.instancia = new DefaultGUI();
    }

    public static DefaultGUI getInstancia(){
        return DefaultGUI.instancia;
    }

    public void cambiarPanelContenido(JPanel panel) {
        panel.setSize(800, 444); // Establece el tamaño del JPanel
        panel.setLocation(0, 0); // Establece la ubicación del JPanel

        this.panelContenido.removeAll(); // Elimina todos los componentes del panel de contenido
        this.panelContenido.add(panel); // Agrega el JPanel al centro del panel de contenido
        this.panelContenido.revalidate(); // Válida la disposición del panel de contenido
        this.panelContenido.repaint(); // Repinta el panel de contenido
    }


    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Default");
        setResizable(false);
        setSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new java.awt.GridLayout(1, 1));
        pack();
        setLocationRelativeTo(null);
    }


}

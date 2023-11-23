package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.Equipo;
import org.example.modelo.Conexion;

import javax.imageio.ImageIO;
import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class HomeGUI {
    private JPanel panel1;

    public JPanel getPanel() {
        return panel1;
    }
    public HomeGUI() throws NamingException, IOException {
         // Set layout here
        List<Equipo> equipos = Conexion.obtenerEquipoBean().listarEquipos();
        //mostraremos las imagenes de los equipos en el home
        for (Equipo equipo : equipos) {
            String imageUrl = equipo.getImagen();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                URL url = new URL(imageUrl);
                Image image = ImageIO.read(url);

                // Redimensionar la imagen
                Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                JButton button = new JButton();
                button.setIcon(new ImageIcon(scaledImage));
                button.setText(equipo.getNombre()); // Agregar texto debajo de la imagen
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame frame = new JFrame("Especificaciones del equipo");
                        JTextArea textArea = new JTextArea(equipo.getNombre());
                        frame.getContentPane().add(textArea, BorderLayout.CENTER);
                        frame.pack();
                        frame.setVisible(true);
                    }
                });
                panel1.add(button);
            }
        }
    }
}
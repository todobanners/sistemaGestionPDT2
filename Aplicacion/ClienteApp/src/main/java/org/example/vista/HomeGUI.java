package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.Equipo;
import org.example.modelo.Conexion;

import javax.imageio.ImageIO;
import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class HomeGUI {
    private JPanel panel1;
    private JPanel panelHome;
    private JScrollPane muestraEquipos;

    public JPanel getPanel() {
        return panel1;
    }
    public HomeGUI() throws NamingException, IOException {
        List<Equipo> equipos = Conexion.obtenerEquipoBean().listarEquipos();
        //mostraremos las imagenes de los equipos en el home
        for (Equipo equipo : equipos) {
            //Image image = null;
            URL url = new URL(equipo.getImagen());
            Image image = ImageIO.read(url);

            JLabel label = new JLabel();
            label.setIcon(new ImageIcon(equipo.getImagen()));
            panelHome.add(label);
        }
    }
}

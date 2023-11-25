package org.example.vista;

import codigocreativo.uy.servidorapp.DTO.EquipoDto;
import codigocreativo.uy.servidorapp.entidades.Equipo;
import org.example.controlador.Sesion;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeGUI {
    private JPanel panelo;
    private JPanel panel1;
    private JLabel titulo;
    private JProgressBar progressBar;
    private ExecutorService executorService;

    public JPanel getPanel() {
        return panelo;
    }

    public HomeGUI() throws NamingException, IOException {
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT) {
            @Override
            public Dimension preferredLayoutSize(Container target) {
                if (target.getWidth() > 0) {
                    int w = target.getWidth();
                    int h = super.preferredLayoutSize(target).height;
                    return new Dimension(w, h);
                } else {
                    return super.preferredLayoutSize(target);
                }
            }
        };
        panel1.setLayout(flowLayout);
        executorService = Executors.newSingleThreadExecutor();
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        panel1.add(progressBar, BorderLayout.NORTH);
        JLabel cargando = new JLabel("Cargando equipos, espere porfavor");
        panel1.add(cargando, BorderLayout.CENTER);

        executorService.execute(() -> {
            try {
                List<EquipoDto> equipos = Conexion.obtenerEquipoBean().listarEquipos();
                titulo.setText("Bienvenido/a " + Sesion.getUsuario().getNombre() + " " + Sesion.getUsuario().getApellido() + "!");
                for (EquipoDto equipo : equipos) {
                    String imageUrl = equipo.getImagen();
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        URL url = new URL(imageUrl);
                        Image image = ImageIO.read(url);
                        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                        JButton button = new JButton();
                        button.setIcon(new ImageIcon(scaledImage));
                        button.setText(equipo.getNombre());
                        button.setHorizontalTextPosition(SwingConstants.CENTER);
                        button.setVerticalTextPosition(SwingConstants.BOTTOM);
                        button.addActionListener(e -> {
                            JFrame frame = new JFrame("Especificaciones del equipo");
                            JTextArea textArea = new JTextArea(equipo.getNombre());
                            frame.getContentPane().add(textArea, BorderLayout.CENTER);
                            frame.pack();
                            frame.setVisible(true);
                        });
                        panel1.add(button);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                SwingUtilities.invokeLater(() -> progressBar.setVisible(false));
                SwingUtilities.invokeLater(() -> cargando.setVisible(false));
            }
        });
    }
}
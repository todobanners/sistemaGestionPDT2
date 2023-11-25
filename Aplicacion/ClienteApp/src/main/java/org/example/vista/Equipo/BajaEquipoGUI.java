package org.example.vista.Equipo;

import codigocreativo.uy.servidorapp.DTO.BajaEquipoDto;
import codigocreativo.uy.servidorapp.DTO.EquipoDto;
import codigocreativo.uy.servidorapp.DTO.UsuarioDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.github.lgooddatepicker.components.DatePicker;
import org.example.controlador.Sesion;
import org.example.modelo.Conexion;
import org.example.modelo.Utilidades;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

public class BajaEquipoGUI {

    private JPanel BajaEquipoPanel;
    private JPanel accionesPanel;
    private JPanel formularioPanel;
    private JTextField comentarioText;
    private JTextField razonText;
    //private JComboBox usuarioCombo;
    //private JComboBox equipoCombo;
    //private JComboBox estadoCombo;
    private JPanel fechaAdqContainer;
    private DatePicker fechaCompraDate = Utilidades.createCustomDatePicker();
    private JButton guardarButton;
    private JButton cancelarButton;
    private JButton imagenActual;
    private JLabel labelUserId;


    private EquipoDto equipoSeleccionado;

    public BajaEquipoGUI(EquipoDto equipo) throws Exception {
        this.equipoSeleccionado = equipo;
        initComponents();
        if (equipoSeleccionado != null) {
            cargarDatosBajaEquipo();
        } else {
            JOptionPane.showMessageDialog(null, "Error: El equipo seleccionado es nulo.");
        }
    }

    private void initComponents() throws Exception {
        fechaAdqContainer.add(fechaCompraDate);
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (camposValidos()) {
                        BajaEquipoDto bajaEquipo = obtenerBajaEquipoDesdeFormulario();
                        System.out.println(bajaEquipo.getIdUsuario());
                        Conexion.obtenerBajaEquipoBean().crearBajaEquipo(bajaEquipo);
                        JOptionPane.showMessageDialog(null, "Equipo dado de baja exitosamente");
                        actualizarListaEquipos();
                        cerrarVentana();
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al dar de baja el equipo");
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarVentana();
            }
        });
    }

    private void cargarDatosBajaEquipo() {
        if (equipoSeleccionado != null) {
            comentarioText.setText("");
            razonText.setText("");
            //usuarioCombo.setSelectedItem(equipoSeleccionado);
            //equipoCombo.setSelectedItem(equipoSeleccionado.getNombre());
            //estadoCombo.setSelectedItem(equipoSeleccionado.getEstado());
            fechaCompraDate.setDate(LocalDate.now());
            labelUserId.setText(String.valueOf(Sesion.getUsuario()) + "-" + Sesion.getUsuario().getId());
            // Cargar imagen y mostrarla en el botón, gestionando excepciones y falta de imagen
            try {
                URL url = new URL(equipoSeleccionado.getImagen());
                Image image = ImageIO.read(url);
                Image scaledImage = image.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                imagenActual.setIcon(new ImageIcon(scaledImage));
            } catch (MalformedURLException e) {
                JOptionPane.showMessageDialog(null, "Error: La URL de la imagen no es válida.");
                imagenActual.setIcon(null); // o establecer un icono predeterminado
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error: No se pudo cargar la imagen.");
                imagenActual.setIcon(null); // o establecer un icono predeterminado
            }

        } else {
            JOptionPane.showMessageDialog(null, "Error: No se ha seleccionado ningún equipo");
        }
    }

    private BajaEquipoDto obtenerBajaEquipoDesdeFormulario() {
        BajaEquipoDto bajaEquipo = new BajaEquipoDto();
        bajaEquipo.setId(equipoSeleccionado.getId());
        bajaEquipo.setIdUsuario(Sesion.getUsuario());
        bajaEquipo.setIdEquipo(equipoSeleccionado);
        bajaEquipo.setComentarios(comentarioText.getText());
        bajaEquipo.setRazon(razonText.getText());
        bajaEquipo.setFecha(fechaCompraDate.getDate());
        bajaEquipo.setEstado(Estados.INACTIVO);

        return bajaEquipo;
    }

    private boolean camposValidos() {
        // Verifica si los campos obligatorios están completos
        return !comentarioText.getText().isEmpty()
                && !razonText.getText().isEmpty()
                && fechaCompraDate.getDate() != null;
    }

    private void cerrarVentana() {
        SwingUtilities.getWindowAncestor(BajaEquipoPanel).dispose();
    }

    private void actualizarListaEquipos() throws Exception {
        EquiposGUI equiposGUI = new EquiposGUI();
        try {
            equiposGUI.actualizarTabla();

            // Cambia esta línea
            // BajaEquipo bajaEquipo = new BajaEquipo(equipoSeleccionado);
            // por
            BajaEquipoDto bajaEquipo = new BajaEquipoDto();
            bajaEquipo.setIdEquipo(equipoSeleccionado);
            // Resto del código...
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar la lista de equipos");
        }
    }


    public void mostrarVentana() {
        JFrame frame = new JFrame("Dar de Baja Equipo");
        frame.setContentPane(BajaEquipoPanel); // Reemplaza BajaEquipoPanel con la clase o panel correcto
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}

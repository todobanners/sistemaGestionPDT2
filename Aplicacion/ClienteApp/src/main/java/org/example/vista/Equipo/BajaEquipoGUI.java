package org.example.vista.Equipo;

import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.toedter.calendar.JDateChooser;
import org.example.modelo.Conexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class BajaEquipoGUI {

    private JPanel BajaEquipoPanel;
    private JPanel accionesPanel;
    private JPanel formularioPanel;
    private JPanel accionesTablaPanel;
    private JTextField comentarioText;
    private JTextField razonText;
    private JComboBox usuarioCombo;
    private JComboBox equipoCombo;
    private JComboBox estadoCombo;
    private JPanel fechaAdqContainer;
    private JDateChooser fechaCompraDate = new JDateChooser();
    private JButton guardarButton;
    private JButton cancelarButton;


    private Equipo equipoSeleccionado;

    public BajaEquipoGUI(Equipo equipo) throws Exception {
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

        for (Usuario usuario : Conexion.obtenerUsuarioBean().obtenerUsuarios()) {
            usuarioCombo.addItem(usuario);
        }

        for (Equipo equipo : Conexion.obtenerEquipoBean().listarEquipos()) {
            equipoCombo.addItem(equipo);
        }

        for (Estados estado : Estados.values()) {
            estadoCombo.addItem(estado);
        }

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (camposValidos()) {
                        BajaEquipo bajaEquipo = obtenerBajaEquipoDesdeFormulario();
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
            usuarioCombo.setSelectedItem(equipoSeleccionado);
            equipoCombo.setSelectedItem(equipoSeleccionado.getNombre());
            estadoCombo.setSelectedItem(equipoSeleccionado.getEstado());


            // Asegúrate de cargar la fecha correctamente
            fechaCompraDate.setDate(new Date());
        } else {
            JOptionPane.showMessageDialog(null, "Error: No se ha seleccionado ningún equipo");
        }
    }

    private BajaEquipo obtenerBajaEquipoDesdeFormulario() {
        BajaEquipo bajaEquipo = new BajaEquipo();
        bajaEquipo.setId(equipoSeleccionado.getId());
        bajaEquipo.setIdUsuario((Usuario) usuarioCombo.getSelectedItem());
        bajaEquipo.setIdEquipo(equipoSeleccionado);
        bajaEquipo.setComentarios(comentarioText.getText());
        bajaEquipo.setRazon(razonText.getText());
        Date fechaElegida = fechaCompraDate.getDate();
        LocalDate localDate = fechaElegida.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        bajaEquipo.setFecha(localDate);
        bajaEquipo.setEstado((Estados) estadoCombo.getSelectedItem());
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
            BajaEquipo bajaEquipo = new BajaEquipo();
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

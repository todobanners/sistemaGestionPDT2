package org.example.vista.Ubicacion;

import codigocreativo.uy.servidorapp.DTO.BajaUbicacionDto;
import codigocreativo.uy.servidorapp.DTO.UbicacionDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import com.github.lgooddatepicker.components.DatePicker;
import org.example.controlador.Sesion;
import org.example.modelo.Conexion;
import org.example.modelo.Utilidades;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class BajaUbicacionGUI {
    private JPanel bajaUbicacion;
    private JTextField institucion;
    private JTextField usuario;
    private JTextField ubicacion;
    private JTextField razon;
    private JTextField comentario;
    private JPanel fecha;
    private JButton confirmar;
    //private JButton cancelar;
    private JLabel labelInstitucion;
    private JLabel labelUsuario;
    private JLabel labelUbicacion;
    private JLabel labelRazon;
    private JLabel labelComentario;
    private JLabel labelFecha;

    //JDateChooser selectorFecha = new JDateChooser();
    DatePicker selectorFecha = Utilidades.createCustomDatePicker();

    private UbicacionDto ubicacionSeleccionada;
    //private Usuario user;
    //private Sesion sesion;

    private BajaUbicacionDto baja;

    public BajaUbicacionGUI(UbicacionDto ubicacionSeleccionada, ListadoDeUbicacionesGUI frame) {
        this.ubicacionSeleccionada = ubicacionSeleccionada;
        this.baja = new BajaUbicacionDto();
        initComponents();
        asignarValores();
        try {
            frame.generarTabla(Conexion.obtenerUbicacionBean().listarUbicaciones());
        } catch (NamingException | ServiciosException e) {
            throw new RuntimeException(e);
        }
    }

    private void initComponents() {

        // Configurar eventos de botones
        confirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarBaja();
            }
        });

    }


    private void asignarValores() {
        if (ubicacionSeleccionada !=null) {

            fecha.add(selectorFecha);
            institucion.setText(ubicacionSeleccionada.getIdInstitucion().getNombre());
            razon.setText("");
            comentario.setText("");
            ubicacion.setText(ubicacionSeleccionada.getNombre());
            usuario.setText(Sesion.getUsuario().getNombre());

        }
    }

    private void confirmarBaja() {
        // Obtener los valores de los componentes y aplicar las modificaciones a la ubicación
        BajaUbicacionDto baja = new BajaUbicacionDto();
        baja.setRazon(razon.getText());
        baja.setComentario(comentario.getText());
        baja.setIdUbicacion(ubicacionSeleccionada);
        baja.setIdUsuario(Sesion.getUsuario());

        LocalDate fechaElegida = selectorFecha.getDate();
        baja.setFecha(fechaElegida);

        try {
            // Modificar la ubicación
            Conexion.obtenerBajaUbicacionBean().crearBajaUbicacion(baja);

            // Eliminar la ubicación de la lista
            Conexion.obtenerUbicacionBean().bajaLogicaUbicacion(ubicacionSeleccionada);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "Ubicación eliminada correctamente");

            // Cerrar la ventana
            cerrarVentana();

        } catch (NamingException | ServiciosException ex) {
            // Mostrar mensaje de error
            JOptionPane.showMessageDialog(null, "Error al borrar la ubicación");
            ex.printStackTrace();
        }
    }

    private void cerrarVentana() {
        // Cierra la ventana sin aplicar cambios
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(bajaUbicacion);
        if (frame != null) {
            frame.dispose();
        }
    }

    public Container getPanel() {
        return bajaUbicacion;
    }

    public void mostrarVentana() {
        JFrame frame = new JFrame("Baja Ubicación");
        frame.setContentPane(this.bajaUbicacion);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

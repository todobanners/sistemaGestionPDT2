package org.example.vista.Ubicacion;

import codigocreativo.uy.servidorapp.entidades.BajaUbicacion;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import com.toedter.calendar.JDateChooser;
import org.example.controlador.Sesion;
import org.example.modelo.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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

    JDateChooser selectorFecha = new JDateChooser();


    private Ubicacion ubicacionSeleccionada;
    //private Usuario user;
    //private Sesion sesion;

    private BajaUbicacion baja;

    public BajaUbicacionGUI(Ubicacion ubicacionSeleccionada) {
        this.ubicacionSeleccionada = ubicacionSeleccionada;
        this.baja = new BajaUbicacion();
        initComponents();
        asignarValores();
    }

    private void initComponents() {

        /*bajaUbicacion = new JPanel();
        institucion = new JTextField();
        usuario = new JTextField();
        ubicacion = new JTextField();
        razon = new JTextField();
        comentario = new JTextField();
        fecha = new JPanel();
        confirmar = new JButton();
        //cancelar = new JButton();
        labelComentario = new JLabel("comentario");
        labelFecha = new JLabel("fecha");
        labelUbicacion = new JLabel("ubicacion");
        labelRazon = new JLabel("razon");
        labelInstitucion = new JLabel("institucion");
        labelUsuario = new JLabel("usuario");*/

        // Configurar el diseño del panel
        //bajaUbicacion.setLayout(new GridLayout(8, 3));

        //fecha.add(selectorFecha);

        /*bajaUbicacion.add(labelInstitucion);
        bajaUbicacion.add(institucion);
        bajaUbicacion.add(labelUsuario);
        bajaUbicacion.add(usuario);
        bajaUbicacion.add(labelUbicacion);
        bajaUbicacion.add(ubicacion);
        bajaUbicacion.add(labelRazon);
        bajaUbicacion.add(razon);
        bajaUbicacion.add(labelComentario);
        bajaUbicacion.add(comentario);
        bajaUbicacion.add(labelFecha);
        bajaUbicacion.add(fecha);
        bajaUbicacion.add(confirmar);*/
        //bajaUbicacion.add(cancelar);

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
            usuario.setText(Sesion.getInstancia().getUsuario().getNombre());

        }
    }

    private void confirmarBaja() {
        // Obtener los valores de los componentes y aplicar las modificaciones a la ubicación
        BajaUbicacion baja = new BajaUbicacion();
        baja.setRazon(razon.getText());
        baja.setComentario(comentario.getText());
        baja.setIdInstitucion(ubicacionSeleccionada.getIdInstitucion().getId());
        baja.setIdUbicacion(ubicacionSeleccionada.getId());
        baja.setIdUsuario(Sesion.getInstancia().getUsuario().getId());

        Date fechaElegida = (Date) selectorFecha.getDate();
        LocalDate localDate = fechaElegida.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        baja.setFecha(localDate);

        try {
            // Modificar la ubicación
            Conexion.obtenerBajaUbicacionBean().crearBajaUbicacion(baja);

            // Eliminar la ubicación de la lista
            Conexion.obtenerUbicacionBean().borrarUbicacion(ubicacionSeleccionada.getId());

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

    // Método para verificar si una cadena es un número
    private boolean esNumero(String cadena) {
        try {
            Long.parseLong(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

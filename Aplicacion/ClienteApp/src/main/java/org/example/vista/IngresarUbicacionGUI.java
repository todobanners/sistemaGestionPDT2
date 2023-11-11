package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class IngresarUbicacionGUI {
    private JComboBox<String> Sector;
    private JTextField Nombre;
    private JTextField Número;
    private JTextField Piso;
    private JTextField Cama;
    private JLabel InstitucionLabel;
    private JLabel SectorLabel;
    private JLabel NombreLabel;
    private JLabel PisoLabel;
    private JLabel CamaLabel;
    private JPanel IngresoUbicacionNueva;
    private JButton enviarButton;
    private JButton cancelarButton;
    private JComboBox<String> institucion;

    public JPanel getPanel() {
        return IngresoUbicacionNueva;
    }

    public IngresarUbicacionGUI() throws NamingException {

        Sector.addItem("Policlínico");
        Sector.addItem("Internación");
        Sector.addItem("Emergencia");
        Sector.addItem("CTI");
        Sector.addItem("otro");

        for (Institucion institucion : Conexion.obtenerInstitucionBean().obtenerInstituciones()) {
            this.institucion.addItem(institucion.getNombre());
        }

        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los valores de la interfaz de usuario
                String sector = (String) Sector.getSelectedItem();
                String nombre = Nombre.getText();
                long numero = Long.valueOf(Número.getText());
                long piso = Long.valueOf(Piso.getText());
                String institucionSeleccionada = (String) institucion.getSelectedItem();

                // Crear una instancia de Ubicacion
                Ubicacion ubicacion = new Ubicacion();
                ubicacion.setSector(sector);
                ubicacion.setNombre(nombre);
                ubicacion.setNumero(numero);
                ubicacion.setPiso(piso);

                try {
                    // Obtener la institución
                    Institucion institucion = Conexion.obtenerInstitucionBean().obtenerInstitucionPorNombre(institucionSeleccionada);

                    if (institucion != null) {
                        // Asignar la institución a la ubicación
                        ubicacion.setIdInstitucion(institucion);

                        // Persistir la ubicación
                        Conexion.obtenerUbicacionBean().crearUbicacion(ubicacion);

                        // Mostrar mensaje de éxito
                        JOptionPane.showMessageDialog(null, "Ubicación creada correctamente");
                    } else {
                        // Mostrar mensaje de error
                        JOptionPane.showMessageDialog(null, "No se encontró ninguna institución con ese nombre");
                    }

                } catch (NamingException | ServiciosException ex) {
                    // Mostrar mensaje de error
                    JOptionPane.showMessageDialog(null, "Error al crear la ubicación");
                    throw new RuntimeException(ex);
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Puedes implementar acciones específicas al hacer clic en el botón cancelar
            }
        });
    }
}

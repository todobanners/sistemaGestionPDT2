package org.example.vista.Ubicacion;

import codigocreativo.uy.servidorapp.DTO.InstitucionDto;
import codigocreativo.uy.servidorapp.DTO.UbicacionDto;
import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.enumerados.Sectores;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.modelo.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JComboBox institucion;

    public JPanel getPanel() {
        return IngresoUbicacionNueva;
    }

    public IngresarUbicacionGUI() throws NamingException {

        for (Sectores sector : Sectores.values()) {
            this.Sector.addItem(sector.getValor());
        }

        for (InstitucionDto institucion : Conexion.obtenerInstitucionBean().obtenerInstituciones()) {
            this.institucion.addItem(institucion);
        }

        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los valores de la interfaz de usuario
                String sector = (String) Sector.getSelectedItem();
                String nombre = Nombre.getText();
                String numeroText = Número.getText();
                String pisoText = Piso.getText();
                String camaText = Cama.getText();
                InstitucionDto institucionSeleccionada = (InstitucionDto) institucion.getSelectedItem();

                // Validar campos obligatorios
                if (sector.isEmpty() || nombre.isEmpty() || numeroText.isEmpty() || pisoText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No puede dejar campos en blanco", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar que los campos numericos solo contengan numeros
                if (!esNumero(numeroText) || !esNumero(pisoText) || (!camaText.isEmpty() && !esNumero(camaText))) {
                    JOptionPane.showMessageDialog(null, "Los campos numéricos solo aceptan números", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear una instancia de Ubicacion
                UbicacionDto ubicacion = new UbicacionDto();
                ubicacion.setSector(sector);
                ubicacion.setNombre(nombre);
                ubicacion.setNumero(Long.parseLong(numeroText));
                ubicacion.setPiso(Long.parseLong(pisoText));
                ubicacion.setIdInstitucion(institucionSeleccionada);

                // Validar si el campo cama no está vacío
                if (!camaText.isEmpty()) {
                    ubicacion.setCama(Long.parseLong(camaText));
                }

                try {

                    Conexion.obtenerUbicacionBean().crearUbicacion(ubicacion);

                    // Mostrar mensaje de éxito
                    JOptionPane.showMessageDialog(null, "Ubicación creada correctamente");

                    // Limpiar los campos del formulario
                    limpiarCampos();


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
                // Limpiar los campos del formulario al cancelar
                limpiarCampos();
            }
        });
    }

    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        Sector.setSelectedIndex(0); // Puedes ajustar esto según tus necesidades
        Nombre.setText("");
        Número.setText("");
        Piso.setText("");
        Cama.setText("");
        institucion.setSelectedIndex(0); // Puedes ajustar esto según tus necesidades
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


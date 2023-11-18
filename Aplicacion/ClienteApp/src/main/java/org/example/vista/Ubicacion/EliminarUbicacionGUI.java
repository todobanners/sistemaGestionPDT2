package org.example.vista.Ubicacion;

import codigocreativo.uy.servidorapp.entidades.BajaUbicacion;
import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.modelo.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class EliminarUbicacionGUI {
    private JPanel equipoPanel;
    private JPanel accionesPanel;
    private JPanel formularioPanel;
    private JComboBox ubicacionCombo;
    private JTextField nroSerieText;
    private JTextField nombreText;
    private JComboBox tipoCombo;
    private JPanel fechaBajaContainer;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JButton darDeBajaButton;
    private JTextField comentariosTextField;


    public EliminarUbicacionGUI() throws NamingException, ServiciosException {
        // Llenar los ComboBox con datos necesarios
        llenarUbicacionesComboBox();
        llenarTiposComboBox();

        darDeBajaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener los datos seleccionados
                    String ubicacionSeleccionada = ubicacionCombo.getSelectedItem().toString();
                    String tipoSeleccionado = tipoCombo.getSelectedItem().toString();
                    String nroSerie = nroSerieText.getText();
                    String nombre = nombreText.getText();

                    // Validar que se hayan ingresado los datos necesarios
                    if (ubicacionSeleccionada.isEmpty() || tipoSeleccionado.isEmpty() || nroSerie.isEmpty() || nombre.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos");
                        return;
                    }

                    // Aquí va la lógica para dar de baja la ubicación
                    // ...

                    // Ejemplo de cómo mostrar un mensaje
                    JOptionPane.showMessageDialog(null, "Ubicación dada de baja exitosamente");

                    // Limpiar campos después de la baja
                    limpiarCampos();

                    // Opcional: Actualizar los ComboBox si es necesario
                    llenarUbicacionesComboBox();
                    llenarTiposComboBox();

                } catch (ServiciosException | NamingException ex) {
                    JOptionPane.showMessageDialog(null, "Error al intentar dar de baja la ubicación");
                    ex.printStackTrace(); // Opcional: Imprimir la pila de excepciones para depuración
                }
            }
        });
    }

    private void llenarUbicacionesComboBox() throws NamingException, ServiciosException {
        // Implementa la lógica para llenar el ComboBox de ubicaciones
        List<Ubicacion> ubicaciones = Conexion.obtenerUbicacionBean().listarUbicaciones();
        DefaultComboBoxModel<Ubicacion> ubicacionModel = new DefaultComboBoxModel<>(ubicaciones.toArray(new Ubicacion[0]));
        ubicacionCombo.setModel(ubicacionModel);
    }

    private void llenarTiposComboBox() {
        // Implementa la lógica para llenar el ComboBox de tipos
        // Puedes agregar tus tipos o consultarlos de alguna fuente de datos
        String[] tipos = {"Tipo1", "Tipo2", "Tipo3"};
        DefaultComboBoxModel<String> tipoModel = new DefaultComboBoxModel<>(tipos);
        tipoCombo.setModel(tipoModel);
    }

    private void limpiarCampos() {
        // Limpia los campos después de la baja
        nroSerieText.setText("");
        nombreText.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JFrame frame = new JFrame("EliminarUbicacionGUI");
                    frame.setContentPane(new EliminarUbicacionGUI().equipoPanel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                } catch (NamingException | ServiciosException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

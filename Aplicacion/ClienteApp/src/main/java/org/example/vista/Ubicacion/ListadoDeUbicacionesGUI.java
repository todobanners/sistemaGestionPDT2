package org.example.vista.Ubicacion;

import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.modelo.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListadoDeUbicacionesGUI extends JPanel {
    private JPanel ListadoUbicaciones;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JList<Ubicacion> list1;

    public JPanel getPanel() {
        return ListadoUbicaciones;
    }

    public ListadoDeUbicacionesGUI() throws NamingException, ServiciosException {
        List<Ubicacion> listaUbicaciones = Conexion.obtenerUbicacionBean().listarUbicaciones();
        generarTabla(listaUbicaciones);

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el elemento seleccionado en la lista
                Ubicacion ubicacionSeleccionada = list1.getSelectedValue();

                if (ubicacionSeleccionada != null) {
                    // Puedes abrir una nueva ventana para modificar la ubicación,
                    // o realizar las acciones necesarias según tus requisitos.
                    // Por ejemplo, podrías crear un nuevo formulario de modificación.

                    // Ejemplo de cómo abrir un nuevo formulario para modificar la ubicación
                    ModificarUbicacionGUI modificarUbicacionGUI = new ModificarUbicacionGUI(ubicacionSeleccionada);
                    modificarUbicacionGUI.mostrarVentana();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una ubicación para modificar");
                }
            }
        });


        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el elemento seleccionado en la lista
                Ubicacion ubicacionSeleccionada = list1.getSelectedValue();

                if (ubicacionSeleccionada != null) {
                    // Confirmar con el usuario antes de eliminar
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar esta ubicación?",
                            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        // Llamar al servicio para eliminar la ubicación
                        try {
                            Conexion.obtenerUbicacionBean().borrarUbicacion(ubicacionSeleccionada.getId());
                            // Actualizar la lista después de la eliminación
                            List<Ubicacion> listaUbicaciones = Conexion.obtenerUbicacionBean().listarUbicaciones();
                            generarTabla(listaUbicaciones);
                        } catch (ServiciosException | NamingException ex) {
                            JOptionPane.showMessageDialog(null, "Error al intentar eliminar la ubicación");
                            ex.printStackTrace(); // Opcional: Imprimir la pila de excepciones para depuración
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una ubicación para eliminar");
                }
            }
        });
    }

    public void generarTabla(List<Ubicacion> lista) {
        DefaultListModel<Ubicacion> modeloTabla = new DefaultListModel<>();
        for (Ubicacion ubicacion : lista) {
            modeloTabla.addElement(ubicacion);
        }
        list1.setModel(modeloTabla);
    }
}

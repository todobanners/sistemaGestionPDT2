package org.example.vista.Ubicacion;

import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.modelo.Conexion;
import org.example.modelo.Validator;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class ListadoDeUbicacionesGUI extends JPanel {
    private JPanel ListadoUbicaciones;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JPanel contenedorHerramientas;
    private JPanel contenedorFiltro;
    private JTable table1;

    private JComboBox Sector;
    private JTextField Nombre;
    private JTextField Número;
    private JTextField Piso;
    private JTextField Cama;
    private JComboBox institucion;
    private JButton agregarNuevoButton;


    public JPanel getPanel() {
        return ListadoUbicaciones;
    }

    public ListadoDeUbicacionesGUI() throws NamingException, ServiciosException {
        List<Ubicacion> listaUbicaciones = Conexion.obtenerUbicacionBean().listarUbicaciones();
        generarTabla(listaUbicaciones);

        //anadir sectores desde el enumerado sectores
        for (int i = 0; i < codigocreativo.uy.servidorapp.enumerados.Sectores.values().length; i++) {
            Sector.addItem(codigocreativo.uy.servidorapp.enumerados.Sectores.values()[i].getValor());
        }

        List<Institucion> inst = Conexion.obtenerInstitucionBean().obtenerInstituciones();
        for (int i = 0; i < inst.size(); i++) {
            institucion.addItem(inst.get(i));
        }

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el elemento seleccionado en la lista
                int ubicacionSeleccionada = table1.getSelectedRow();

                if (ubicacionSeleccionada != -1 && ubicacionSeleccionada < listaUbicaciones.size()) {
                    // Puedes abrir una nueva ventana para modificar la ubicación,
                    // o realizar las acciones necesarias según tus requisitos.
                    // Por ejemplo, podrías crear un nuevo formulario de modificación.
                    confirmarAltaOModificar(listaUbicaciones.get(ubicacionSeleccionada), "modificar");
                    // Ejemplo de cómo abrir un nuevo formulario para modificar la ubicación
                    //ModificarUbicacionGUI modificarUbicacionGUI = new ModificarUbicacionGUI(ubicacionSeleccionada);
                    //modificarUbicacionGUI.mostrarVentana();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una ubicación para modificar");
                }
            }
        });


        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el elemento seleccionado en la lista
                int ubicacionSeleccionada = table1.getSelectedRow();

                if (ubicacionSeleccionada != -1 && ubicacionSeleccionada < listaUbicaciones.size()) {
                    // Confirmar con el usuario antes de eliminar
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar esta ubicación?",
                            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        // Llamar al servicio para eliminar la ubicación
                        try {
                            Conexion.obtenerUbicacionBean().borrarUbicacion(listaUbicaciones.get(ubicacionSeleccionada).getId());
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

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int ubicacionSeleccionada = table1.getSelectedRow();
                if (ubicacionSeleccionada != -1 && ubicacionSeleccionada < listaUbicaciones.size()) { // Si se seleccionó una ubicación
                    Ubicacion ubi = listaUbicaciones.get(ubicacionSeleccionada);
                    Sector.setSelectedItem(ubi.getSector());
                    Nombre.setText(ubi.getNombre());
                    Número.setText(String.valueOf(ubi.getNumero()));
                    Piso.setText(String.valueOf(ubi.getPiso()));
                    if (ubi.getCama() == null) {
                        Cama.setText("");
                    }else{
                        Cama.setText(String.valueOf(ubi.getCama()));
                    }
                    institucion.setSelectedItem(ubi.getIdInstitucion().getNombre());
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una ubicación para modificar");
                }
            }
        });
        agregarNuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ubicacion ubi = new Ubicacion();
                ubi.setSector((String) Sector.getSelectedItem());
                ubi.setNombre(Nombre.getText());
                ubi.setNumero(Long.parseLong(Número.getText()));
                ubi.setPiso(Long.parseLong(Piso.getText()));
                ubi.setCama(Long.parseLong(Cama.getText()));
                Institucion institucion1 = (Institucion) institucion.getSelectedItem();
                ubi.setIdInstitucion(institucion1);
                confirmarAltaOModificar(ubi, "alta");

                try {
                    generarTabla(Conexion.obtenerUbicacionBean().listarUbicaciones());
                } catch (ServiciosException | NamingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void generarTabla(List lista) {
        //Generamos la tabla con las ubicaciones
        String[] columnas = {"Sector", "Nombre", "Número", "Piso", "Cama", "Institución"};
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(columnas);
        table1.setModel(modelo);
        for (int i = 0; i < lista.size(); i++) {
            Ubicacion ubi = (Ubicacion) lista.get(i);
            String[] fila = {ubi.getSector(), ubi.getNombre(), String.valueOf(ubi.getNumero()), String.valueOf(ubi.getPiso()), String.valueOf(ubi.getCama()), ubi.getIdInstitucion().getNombre()};
            modelo.addRow(fila);
        }

    }

    private void confirmarAltaOModificar(Ubicacion ubicacionSeleccionada, String accion) {
        // Obtener los valores de los componentes y aplicar las modificaciones a la ubicación
        if (ubicacionSeleccionada != null) {
            String sector = (String) Sector.getSelectedItem();
            String nombre = Nombre.getText();
            String numeroText = Número.getText();
            String pisoText = Piso.getText();
            String camaText = Cama.getText();
            Institucion institucionSeleccionada = (Institucion) institucion.getSelectedItem();

            // Validar campos obligatorios
            if (sector.isEmpty() || nombre.isEmpty() || numeroText.isEmpty() || pisoText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No puede dejar campos en blanco", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que los campos numericos solo contengan números
            if (Validator.contieneSoloNumeros(numeroText) || Validator.contieneSoloNumeros(pisoText) || Validator.contieneSoloNumeros(camaText)) {
                JOptionPane.showMessageDialog(null, "Los campos numéricos solo aceptan números", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Continuar con la lógica de confirmar la modificación...
            ubicacionSeleccionada.setSector(sector);
            ubicacionSeleccionada.setNombre(nombre);
            ubicacionSeleccionada.setNumero(Long.parseLong(numeroText));
            ubicacionSeleccionada.setPiso(Long.parseLong(pisoText));

            // Validar si el campo Cama no está vacío
            if (!camaText.isEmpty()) {
                ubicacionSeleccionada.setCama(Long.parseLong(camaText));
            }

            try {
                // Obtener la institución
                Institucion inst = Conexion.obtenerInstitucionBean().obtenerInstitucionPorNombre(institucionSeleccionada.getNombre());

                if (inst != null) {
                    // Asignar la institución a la ubicación
                    ubicacionSeleccionada.setIdInstitucion(inst);

                    // Modificar o agregar la ubicación
                    if (accion.equals("alta")) {
                        Conexion.obtenerUbicacionBean().crearUbicacion(ubicacionSeleccionada);
                        // Mostrar mensaje de éxito
                        JOptionPane.showMessageDialog(null, "Ubicación agregada correctamente");
                    }
                    else if (accion.equals("modificar")) {
                        Conexion.obtenerUbicacionBean().modificarUbicacion(ubicacionSeleccionada);
                        // Mostrar mensaje de éxito
                        JOptionPane.showMessageDialog(null, "Ubicación modificada correctamente");
                    }


                    // Cerrar la ventana
                    //cerrarVentana();
                } else {
                    // Mostrar mensaje de error
                    JOptionPane.showMessageDialog(null, "No se encontró ninguna institución con ese nombre");
                }

            } catch (NamingException | ServiciosException ex) {
                // Mostrar mensaje de error
                JOptionPane.showMessageDialog(null, "Error al modificar la ubicación");
                ex.printStackTrace();
            }
        }
    }
}

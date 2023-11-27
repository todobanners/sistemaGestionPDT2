package org.example.vista.Ubicacion;

import codigocreativo.uy.servidorapp.DTO.InstitucionDto;
import codigocreativo.uy.servidorapp.DTO.UbicacionDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.modelo.Conexion;
import org.example.modelo.Validator;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ListadoDeUbicacionesGUI extends JPanel {
    private JPanel ListadoUbicaciones;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JPanel contenedorHerramientas;
    private JTable table1;

    private JComboBox Sector;
    private JTextField Nombre;
    private JTextField Numero;
    private JTextField Piso;
    private JTextField Cama;
    private JComboBox institucion;
    private JButton agregarNuevoButton;


    public JPanel getPanel() {
        return ListadoUbicaciones;
    }

    public ListadoDeUbicacionesGUI() throws NamingException, ServiciosException {
        List<UbicacionDto> listaUbicaciones = Conexion.obtenerUbicacionBean().listarUbicaciones();
        generarTabla(listaUbicaciones);

        //anadir sectores desde el enumerado sectores
        for (int i = 0; i < codigocreativo.uy.servidorapp.enumerados.Sectores.values().length; i++) {
            Sector.addItem(codigocreativo.uy.servidorapp.enumerados.Sectores.values()[i].getValor());
        }

        List<InstitucionDto> inst = Conexion.obtenerInstitucionBean().obtenerInstituciones();
        for (int i = 0; i < inst.size(); i++) {
            institucion.addItem(inst.get(i));
        }

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Preguntar al usuario si desea modificar la ubicacion
                int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas modificar esta ubicación?",
                        "Confirmar modificación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    // Obtener el elemento seleccionado en la lista
                    int ubicacionSeleccionada = table1.getSelectedRow();

                    if (ubicacionSeleccionada != -1 && ubicacionSeleccionada < listaUbicaciones.size()) {
                        confirmarAltaOModificar(listaUbicaciones.get(ubicacionSeleccionada), "modificar");
                    } else {
                        JOptionPane.showMessageDialog(null, "Selecciona una ubicación para modificar");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se modificó la ubicación");
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
                        BajaUbicacionGUI bajaUbicacionGUI = new BajaUbicacionGUI(listaUbicaciones.get(ubicacionSeleccionada));
                        bajaUbicacionGUI.mostrarVentana();
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
                    UbicacionDto ubi = listaUbicaciones.get(ubicacionSeleccionada);
                    Sector.setSelectedItem(ubi.getSector());
                    Nombre.setText(ubi.getNombre());
                    Numero.setText(String.valueOf(ubi.getNumero()));
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
                UbicacionDto ubi = new UbicacionDto();
                ubi.setSector((String) Sector.getSelectedItem());
                ubi.setNombre(Nombre.getText());
                ubi.setNumero(Long.parseLong(Numero.getText()));
                ubi.setPiso(Long.parseLong(Piso.getText()));
                //Permito que cama pueda ser null
                String camaText = Cama.getText();
                if (camaText != null && !camaText.isEmpty()) {
                    ubi.setCama(Long.parseLong(camaText));
                } else {
                    ubi.setCama(null);
                }
                InstitucionDto institucion1 = (InstitucionDto) institucion.getSelectedItem();
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
        for (Object o : lista) {
            UbicacionDto ubi = (UbicacionDto) o;
            String[] fila = {ubi.getSector(), ubi.getNombre(), String.valueOf(ubi.getNumero()), String.valueOf(ubi.getPiso()), ubi.getCama() == null ? "" : String.valueOf(ubi.getCama()), ubi.getIdInstitucion().getNombre()};
            modelo.addRow(fila);
        }

    }

    private void confirmarAltaOModificar(UbicacionDto ubicacionSeleccionada, String accion) {
        // Obtener los valores de los componentes y aplicar las modificaciones a la ubicación
        if (ubicacionSeleccionada != null) {
            String sector = (String) Sector.getSelectedItem();
            String nombre = Nombre.getText();
            String numeroText = Numero.getText();
            String pisoText = Piso.getText();
            String camaText = Cama.getText();
            InstitucionDto institucionSeleccionada = (InstitucionDto) institucion.getSelectedItem();

            // Validar campos obligatorios
            if (sector.isEmpty() || nombre.isEmpty() || numeroText.isEmpty() || pisoText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No puede dejar campos en blanco", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que los campos numéricos solo contengan números
            if (Validator.contieneSoloNumeros(numeroText) || Validator.contieneSoloNumeros(pisoText) || (!camaText.isEmpty() && Validator.contieneSoloNumeros(camaText))) {
                JOptionPane.showMessageDialog(null, "Los campos numéricos solo aceptan números", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Continuar con la lógica de confirmar la modificación...
            ubicacionSeleccionada.setSector(sector);
            ubicacionSeleccionada.setNombre(nombre);
            ubicacionSeleccionada.setNumero(Long.parseLong(numeroText));
            ubicacionSeleccionada.setPiso(Long.parseLong(pisoText));

            // Validar si el campo Cama no está vacío
            /*if (!camaText.isEmpty()) {
                ubicacionSeleccionada.setCama(Long.parseLong(camaText));
            }*/

            try {
                // Obtener la institución
                InstitucionDto inst = (InstitucionDto) institucion.getSelectedItem();

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
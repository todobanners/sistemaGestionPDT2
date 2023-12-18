package org.example.vista.Ubicacion;

import codigocreativo.uy.servidorapp.DTO.InstitucionDto;
import codigocreativo.uy.servidorapp.DTO.UbicacionDto;
import codigocreativo.uy.servidorapp.enumerados.Sectores;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.modelo.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ModificarUbicacionGUI {
    private JPanel ModificarUbicacion;
    private JLabel SectorLabel;
    private JComboBox<String> Sector;
    private JLabel NombreLabel;
    private JTextField Nombre;
    private JTextField Número;
    private JLabel PisoLabel;
    private JTextField Piso;
    private JLabel CamaLabel;
    private JTextField Cama;
    private JButton cancelarButton;
    private JButton confirmarButton;
    private JLabel InstitucionLabel;
    private JComboBox<String> institucion;

    private UbicacionDto ubicacionSeleccionada;

    public ModificarUbicacionGUI(UbicacionDto ubicacionSeleccionada) {
        this.ubicacionSeleccionada = ubicacionSeleccionada;
        initComponents();
        asignarValores();
    }

    private void initComponents() {
        // Inicializar los componentes de la interfaz gráfica
        ModificarUbicacion = new JPanel();
        SectorLabel = new JLabel("Sector:");
        Sector = new JComboBox<>();
        NombreLabel = new JLabel("Nombre:");
        Nombre = new JTextField();
        Número = new JTextField();
        PisoLabel = new JLabel("Piso:");
        Piso = new JTextField();
        CamaLabel = new JLabel("Cama:");
        Cama = new JTextField();
        cancelarButton = new JButton("Cancelar");
        confirmarButton = new JButton("Confirmar");
        InstitucionLabel = new JLabel("Institución:");
        institucion = new JComboBox<>();

        // Configurar el diseño del panel
        ModificarUbicacion.setLayout(new GridLayout(7, 2));

        // Agregar componentes al panel
        ModificarUbicacion.add(SectorLabel);
        ModificarUbicacion.add(Sector);
        ModificarUbicacion.add(NombreLabel);
        ModificarUbicacion.add(Nombre);
        ModificarUbicacion.add(new JLabel("Número:"));
        ModificarUbicacion.add(Número);
        ModificarUbicacion.add(PisoLabel);
        ModificarUbicacion.add(Piso);
        ModificarUbicacion.add(CamaLabel);
        ModificarUbicacion.add(Cama);
        ModificarUbicacion.add(InstitucionLabel);
        ModificarUbicacion.add(institucion);
        ModificarUbicacion.add(cancelarButton);
        ModificarUbicacion.add(confirmarButton);

        // Configurar eventos de botones
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarModificacion();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarVentana();
            }
        });

        // Configurar opciones de combobox
        for (Sectores sector : Sectores.values()) {
            Sector.addItem(sector.getValor());
        }

        // Obtener instituciones y agregarlas al combobox
        try {
            List<InstitucionDto> instituciones = Conexion.obtenerInstitucionBean().obtenerInstituciones();
            for (InstitucionDto inst : instituciones) {
                institucion.addItem(inst.getNombre());
            }
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

    private void asignarValores() {
        if (ubicacionSeleccionada != null) {
            Sector.setSelectedItem(ubicacionSeleccionada.getSector());
            Nombre.setText(ubicacionSeleccionada.getNombre());
            Número.setText(String.valueOf(ubicacionSeleccionada.getNumero()));
            Piso.setText(String.valueOf(ubicacionSeleccionada.getPiso()));

            // Verificar si el campo Cama es nulo
            String camaText = (ubicacionSeleccionada.getCama() != null) ? String.valueOf(ubicacionSeleccionada.getCama()) : "No tiene";
            Cama.setText(camaText);

            institucion.setSelectedItem(ubicacionSeleccionada.getIdInstitucion().getNombre());
        }
    }


    private void confirmarModificacion() {
        // Obtener los valores de los componentes y aplicar las modificaciones a la ubicación
        if (ubicacionSeleccionada != null) {
            String sector = (String) Sector.getSelectedItem();
            String nombre = Nombre.getText();
            String numeroText = Número.getText();
            String pisoText = Piso.getText();
            String camaText = Cama.getText();
            String institucionSeleccionada = (String) institucion.getSelectedItem();

            // Validar campos obligatorios
            if (sector.isEmpty() || nombre.isEmpty() || numeroText.isEmpty() || pisoText.isEmpty() || institucionSeleccionada.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No puede dejar campos en blanco", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que los campos numericos solo contengan números
            if (!esNumero(numeroText) || !esNumero(pisoText) || (!camaText.isEmpty() && !esNumero(camaText))) {
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
                InstitucionDto inst = Conexion.obtenerInstitucionBean().obtenerInstitucionPorNombre(institucionSeleccionada);

                if (inst != null) {
                    // Asignar la institución a la ubicación
                    ubicacionSeleccionada.setIdInstitucion(inst);

                    // Modificar la ubicación
                    Conexion.obtenerUbicacionBean().modificarUbicacion(ubicacionSeleccionada);

                    // Mostrar mensaje de éxito
                    JOptionPane.showMessageDialog(null, "Ubicación modificada correctamente");

                    // Cerrar la ventana
                    cerrarVentana();
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

    private void cerrarVentana() {
        // Cierra la ventana sin aplicar cambios
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ModificarUbicacion);
        if (frame != null) {
            frame.dispose();
        }
    }

    public Container getPanel() {
        return ModificarUbicacion;
    }

    public void mostrarVentana() {
        JFrame frame = new JFrame("Modificar Ubicación");
        frame.setContentPane(this.ModificarUbicacion);
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




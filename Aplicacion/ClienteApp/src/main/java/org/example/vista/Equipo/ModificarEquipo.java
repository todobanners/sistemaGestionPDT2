package org.example.vista.Equipo;

import codigocreativo.uy.servidorapp.DTO.*;
import codigocreativo.uy.servidorapp.entidades.*;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.github.lgooddatepicker.components.DatePicker;
import org.example.modelo.Conexion;
import org.example.modelo.Utilidades;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class ModificarEquipo {

    private JPanel ModificarEquipoPanel;
    private JPanel accionesPanel;
    private JPanel formularioPanel;
    private JTextField idInternoText;
    private JComboBox ubicacionCombo;
    private JTextField nroSerieText;
    private JTextField nombreText;
    private JComboBox tipoCombo;
    private JComboBox proveedorCombo;
    private JComboBox paisCombo;
    private JComboBox modeloCombo;
    private JButton imagenBtn;
    private JPanel fechaAdqContainer;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JComboBox estadoCombo;
    private JButton imagenActual;
    private JLabel filePathField;
    private File imagenSubida;
    private DatePicker fechaCompraDate = Utilidades.createCustomDatePicker();

    private EquipoDto equipoSeleccionado;

    public ModificarEquipo(EquipoDto equipo) throws Exception {
        this.equipoSeleccionado = equipo;
        initComponents();
        if (equipoSeleccionado != null) {
            cargarDatosEquipo();
        } else {
            JOptionPane.showMessageDialog(null, "Error: El equipo seleccionado es nulo.");
        }
    }

    private void initComponents() throws Exception {
        fechaAdqContainer.add(fechaCompraDate);



        for (Ubicacion ubicacion : Conexion.obtenerUbicacionBean().listarUbicaciones()) {
            ubicacionCombo.addItem(ubicacion);
        }

        for (TiposEquipo tipo : Conexion.obtenerTipoBean().listarTiposEquipo()) {
            tipoCombo.addItem(tipo);
        }

        for (ProveedoresEquipo proveedor : Conexion.obtenerProveedoresEquipoBean().obtenerProveedoresEquipo()) {
            proveedorCombo.addItem(proveedor);
        }

        for (Pais pais : Conexion.obtenerPaisBean().obtenerpais()) {
            paisCombo.addItem(pais);
        }

        for (ModelosEquipo modelo : Conexion.obtenerModeloBean().listarModelosEquipo()) {
            modeloCombo.addItem(modelo);
        }

        for (Estados estado : Estados.values()) {
            estadoCombo.addItem(estado);
        }
        imagenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    imagenSubida = fileChooser.getSelectedFile();
                    filePathField.setText(imagenSubida.getAbsolutePath());
                }
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (camposValidos()) {
                        EquipoDto equipoModificado = obtenerEquipoDesdeFormulario();
                        Conexion.obtenerEquipoBean().modificarEquipo(equipoModificado);
                        JOptionPane.showMessageDialog(null, "Equipo modificado exitosamente");
                        actualizarListaEquipos();
                        cerrarVentana();
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al modificar el equipo");
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

    private void cargarDatosEquipo() throws IOException {
        if (equipoSeleccionado != null) {
            idInternoText.setText(equipoSeleccionado.getIdInterno());
            ubicacionCombo.setSelectedItem(equipoSeleccionado.getIdUbicacion());
            nroSerieText.setText(equipoSeleccionado.getNroSerie());
            nombreText.setText(equipoSeleccionado.getNombre());
            tipoCombo.setSelectedItem(equipoSeleccionado.getIdTipo());
            proveedorCombo.setSelectedItem(equipoSeleccionado.getIdProveedor());
            paisCombo.setSelectedItem(equipoSeleccionado.getIdPais());
            modeloCombo.setSelectedItem(equipoSeleccionado.getIdModelo());
            estadoCombo.setSelectedItem(equipoSeleccionado.getEstado());
            fechaCompraDate.setDate(equipoSeleccionado.getFechaAdquisicion());
            // Cargar imagen y mostrarla en el botón, gestionando excepciones y falta de imagen
            try {
                URL url = new URL(equipoSeleccionado.getImagen());
                Image image = ImageIO.read(url);
                Image scaledImage = image.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                imagenActual.setIcon(new ImageIcon(scaledImage));
                filePathField.setText(equipoSeleccionado.getImagen());
            } catch (MalformedURLException e) {
                JOptionPane.showMessageDialog(null, "Error: La URL de la imagen no es válida.");
                imagenActual.setIcon(null); // o establecer un icono predeterminado
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error: No se pudo cargar la imagen.");
                imagenActual.setIcon(null); // o establecer un icono predeterminado
            }

        } else {
            JOptionPane.showMessageDialog(null, "Error: No se ha seleccionado ningún equipo");
        }
    }

    private EquipoDto obtenerEquipoDesdeFormulario() {
        EquipoDto equipoModificado = new EquipoDto();
        equipoModificado.setId(equipoSeleccionado.getId());
        equipoModificado.setIdInterno(idInternoText.getText());
        equipoModificado.setIdUbicacion(((UbicacionDto) ubicacionCombo.getSelectedItem()));
        equipoModificado.setNroSerie(nroSerieText.getText());
        equipoModificado.setNombre(nombreText.getText());
        equipoModificado.setIdTipo((TiposEquipoDto) tipoCombo.getSelectedItem());
        equipoModificado.setIdProveedor(((ProveedoresEquipoDto) Objects.requireNonNull(proveedorCombo.getSelectedItem())));
        equipoModificado.setIdPais((PaisDto) paisCombo.getSelectedItem());
        equipoModificado.setIdModelo((ModelosEquipoDto) modeloCombo.getSelectedItem());
        equipoModificado.setFechaAdquisicion(fechaCompraDate.getDate());
        equipoModificado.setEstado((Estados) estadoCombo.getSelectedItem());
        if (imagenSubida != null) {
            try {
                guardarButton.setText("Subiendo imagen...");
                String nuevaImagen = Utilidades.subirImagen(imagenSubida);
                equipoModificado.setImagen(nuevaImagen);
                guardarButton.setText("Guardar");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al subir la nueva imagen");
            }
        } else {
            equipoModificado.setImagen(equipoSeleccionado.getImagen());
        }

        return equipoModificado;
    }

    private boolean camposValidos() {
        // Verifica si los campos obligatorios están completos
        return !idInternoText.getText().isEmpty()
                && !nroSerieText.getText().isEmpty()
                && !nombreText.getText().isEmpty()
                && fechaCompraDate.getDate() != null;
    }


    private void cerrarVentana() {
        // Obtén el JFrame al que pertenece el panel y ciérralo
        SwingUtilities.getWindowAncestor(ModificarEquipoPanel).dispose();
    }

    private void actualizarListaEquipos() throws Exception {
        // Llama a la función de la clase EquiposGUI que actualiza la tabla
        EquiposGUI equiposGUI = new EquiposGUI();
        try {
            equiposGUI.actualizarTabla();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar la lista de equipos");
        }
    }

    public void mostrarVentana() {
        JFrame frame = new JFrame("Modificar Equipo");
        frame.setContentPane(ModificarEquipoPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

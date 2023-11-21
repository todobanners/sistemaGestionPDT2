package org.example.vista.Equipo;

        import codigocreativo.uy.servidorapp.entidades.*;
        import codigocreativo.uy.servidorapp.enumerados.Estados;
        import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
        import com.github.lgooddatepicker.components.DatePicker;
        import com.toedter.calendar.JDateChooser;
        import org.example.modelo.Conexion;
        import org.example.modelo.DatePickerUtil;
        import org.example.vista.EquiposGUI;

        import javax.naming.NamingException;
        import javax.swing.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.time.LocalDate;
        import java.time.ZoneId;
        import java.util.Date;
        import java.util.Objects;

public class ModificarEquipo {

    private JPanel ModificarEquipoPanel;
    private JPanel accionesPanel;
    private JPanel formularioPanel;
    private JPanel accionesTablaPanel;
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
    private DatePicker fechaCompraDate = DatePickerUtil.createCustomDatePicker();

    private Equipo equipoSeleccionado;

    public ModificarEquipo(Equipo equipo) throws Exception {
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
        // Configurar los componentes de la interfaz aquí...

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (camposValidos()) {
                        Equipo equipoModificado = obtenerEquipoDesdeFormulario();
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

    private void cargarDatosEquipo() {
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

        } else {
            JOptionPane.showMessageDialog(null, "Error: No se ha seleccionado ningún equipo");
        }
    }

    private Equipo obtenerEquipoDesdeFormulario() {
        Equipo equipoModificado = new Equipo();
        equipoModificado.setId(equipoSeleccionado.getId());
        equipoModificado.setIdInterno(idInternoText.getText());
        equipoModificado.setIdUbicacion(((Ubicacion) ubicacionCombo.getSelectedItem()));
        equipoModificado.setNroSerie(nroSerieText.getText());
        equipoModificado.setNombre(nombreText.getText());
        equipoModificado.setIdTipo((TiposEquipo) tipoCombo.getSelectedItem());
        equipoModificado.setIdProveedor(((ProveedoresEquipo) Objects.requireNonNull(proveedorCombo.getSelectedItem())));
        equipoModificado.setIdPais((Pais) paisCombo.getSelectedItem());
        equipoModificado.setIdModelo((ModelosEquipo) modeloCombo.getSelectedItem());
        equipoModificado.setFechaAdquisicion(fechaCompraDate.getDate());
        equipoModificado.setEstado((Estados) estadoCombo.getSelectedItem());
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

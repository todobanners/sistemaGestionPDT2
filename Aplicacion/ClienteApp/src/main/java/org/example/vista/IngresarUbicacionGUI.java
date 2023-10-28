package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.entidades.Ubicaciones;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class IngresarUbicacionGUI{
    private JComboBox Sector;
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


    public JPanel getPanel(){
        return IngresoUbicacionNueva;
    }


    public IngresarUbicacionGUI() throws NamingException {

        Sector.addItem("Policlínico");
        Sector.addItem("Internación");
        Sector.addItem("Emergencia");
        Sector.addItem("CTI");
        Sector.addItem("otro");

        //List<Institucion> instituciones = Conexion.obtenerInstitucionBean().obtenerInstituciones();

        for (Institucion institucion : Conexion.obtenerInstitucionBean().obtenerInstituciones()) {
            this.institucion.addItem(institucion);
        }

        enviarButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                Ubicaciones ubicacion = new Ubicaciones();
                ubicacion.setSector((String) Sector.getSelectedItem());
                ubicacion.setNombre(Nombre.getText());
                ubicacion.setNumero(Long.valueOf(Número.getText()));
                ubicacion.setPiso(Long.valueOf(Piso.getText()));
                Object institucionSeleccionada = institucion.getSelectedItem().toString();

                try {
                    ubicacion.setIdInstitucion(Conexion.obtenerInstitucionBean().obtenerInstitucionPorNombre(institucionSeleccionada.toString()));
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }


                try {
                    Conexion.obtenerUbicacionBean().crearUbicacion(ubicacion);
                    JOptionPane.showMessageDialog(null, "Ubicación creada correctamente");
                } catch (NamingException | ServiciosException ex) {
                    JOptionPane.showMessageDialog(null, "Error al crear la ubicación");
                    throw new RuntimeException(ex);

                }

            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Perfil perfil = new Perfil();
                perfil.setNombrePerfil("Administrador");
                perfil.setEstado("alta");
                Perfil perfil2 = new Perfil();
                perfil2.setNombrePerfil("Usuario");
                perfil2.setEstado("alta");
                Perfil perfil3 = new Perfil();
                perfil3.setEstado("alta");
                perfil3.setNombrePerfil("Invitado");
                try {
                    Conexion.obtenerPerfilBean().crearPerfil(perfil);
                    Conexion.obtenerPerfilBean().crearPerfil(perfil2);
                    Conexion.obtenerPerfilBean().crearPerfil(perfil3);
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }
                Institucion institucion = new Institucion();
                institucion.setNombre("UTEC");
                Institucion institucion2 = new Institucion();
                institucion2.setNombre("UDELAR");
                Institucion institucion3 = new Institucion();
                institucion3.setNombre("ORT");
                try {
                    Conexion.obtenerInstitucionBean().agregarInstitucion(institucion);
                    Conexion.obtenerInstitucionBean().agregarInstitucion(institucion2);
                    Conexion.obtenerInstitucionBean().agregarInstitucion(institucion3);
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
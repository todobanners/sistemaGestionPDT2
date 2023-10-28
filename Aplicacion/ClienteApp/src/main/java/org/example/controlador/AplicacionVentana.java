package org.example.controlador;

import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.vista.*;
import org.example.vista.Usuario.UsuarioGUI;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;

import static javax.swing.UIManager.setLookAndFeel;

public class AplicacionVentana extends JFrame {

    public AplicacionVentana(String s) throws ServiciosException, NamingException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super(s);
        //Agregar un LaF
        setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        //Font por defecto para Menues
        Font f = new Font("sans-serif", Font.PLAIN, 12);
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f);

        //iniciar menu
        JMenuBar menuBar=initMenuBar();
        setJMenuBar(menuBar);

        setVisible(true);
        setBounds(10,10,800,800);
        //panel de ventana inicio
        changePanel(new HomeGUI().getPanel());
    }


    JMenuBar initMenuBar(){

        JMenuBar miMenuBar = new JMenuBar();

        //JMenuItem inicio = new JMenuItem("Inicio");
        JMenu menuInicioPrincipal = new JMenu("Inicio");
        JMenu agregar = new JMenu("Ver y registrar");
        JMenu borrar = new JMenu("Borrar registros");
        JMenu Usuarios = new JMenu("Usuarios");
        JMenu Equipos = new JMenu("Equipos");
        JMenu equipos = new JMenu("Equipos");
        JMenu gestionUbicaciones = new JMenu ("Gestión de Ubicaciones");

        JMenuItem ingresarUbicacion = new JMenuItem("Ingresar Ubicación");
        ingresarUbicacion.addActionListener(e -> {
            try {
                changePanel(new IngresarUbicacionGUI().getPanel());
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }

        });


        JMenuItem listadoUbicaciones = new JMenuItem("Listado de Ubicaciones");
        listadoUbicaciones.addActionListener(e -> {
            try {
                changePanel(new IngresarUbicacionGUI().getPanel());
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Menu de dashboard
        JMenuItem menuInicio = new JMenuItem("Inicio");
        menuInicio.addActionListener(e -> {
            try {
                changePanel(new HomeGUI().getPanel());
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            } catch (ServiciosException ex) {
                throw new RuntimeException(ex);
            }
        });

        JMenuItem menuUsuarios = new JMenuItem("Listar Usuarios");
        menuUsuarios.addActionListener(e -> {
            try {
                changePanel(new UsuarioGUI().getPanel());
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        });


        JMenuItem menuVerDatos = new JMenuItem("Pantalla prueba");
        menuVerDatos.addActionListener(e -> {
            try {
                changePanel(new PruebaGUI().getPanel());
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            } catch (ServiciosException ex) {
                throw new RuntimeException(ex);
            }
        });

        JMenuItem menuVerUbicaciones = new JMenuItem("Listado de ubicaciones");
        menuVerUbicaciones.addActionListener(e -> {
            changePanel(new ListadoDeUbicacionesGUI().getPanel());
        });

        JMenuItem menuIngresarUbicacion = new JMenuItem("Ingresar ubicación");
        menuIngresarUbicacion.addActionListener(e -> {
            try {
                changePanel(new IngresarUbicacionGUI().getPanel());
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Agrega el formulario al panel principal (puedes ocultarlo inicialmente si lo deseas)
        /*IngresarUbicacionGUI.setVisible(true);
        getContentPane().add(IngresarUbicacionGUI);

        ListadoDeUbicacionesGUI.setVisible(true);
        getContentPane().add(ListadoDeUbicacionesGUI);*/


        JMenuItem menuEquipos = new JMenuItem("Gestion de equipos");
        menuEquipos.addActionListener(e -> {
            try {
                changePanel(new EquiposGUI().getPanel());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
/*
        JMenuItem menuVerFormulario = new JMenuItem("Registrar y ver personas");
        menuVerFormulario.addActionListener(e -> changePanel(new registroPersonas().getPanel()));

        JMenuItem menuFormVehiculos = new JMenuItem("Registrar y ver vehiculos");
        menuFormVehiculos.addActionListener(e -> changePanel(new registroVehiculos().getPanel()));

        JMenuItem menuBorrarPersonas = new JMenuItem("Borrar Personas");
        menuBorrarPersonas.addActionListener(e -> changePanel(new borrarPersonas().getPanel()));

        JMenuItem menuBorrarVehiculos = new JMenuItem("Borrar Vehiculos");
        menuBorrarVehiculos.addActionListener(e -> changePanel(new borrarVehiculos().getPanel()));*/

        menuInicioPrincipal.add(menuInicio);
        menuInicioPrincipal.add(menuUsuarios);
        menuInicioPrincipal.add(menuVerDatos);

        Usuarios.add(menuUsuarios);
        menuInicioPrincipal.add(menuEquipos);


        gestionUbicaciones.add(menuVerUbicaciones);
        gestionUbicaciones.add(menuIngresarUbicacion);
/*
        agregar.add(menuVerFormulario);
        agregar.add(menuFormVehiculos);

        borrar.add(menuBorrarPersonas);
        borrar.add(menuBorrarVehiculos);*/
        //menuInicioPrincipal.add(menuVerDatos);

        //agregar los elementos a la barra
        miMenuBar.add(menuInicioPrincipal);
        miMenuBar.add(agregar);
        miMenuBar.add(borrar);
        miMenuBar.add(gestionUbicaciones);
        miMenuBar.add(Usuarios);
        miMenuBar.add(equipos);
        return miMenuBar;
    }

    public void changePanel(JPanel jPanel){
        Container c=getContentPane();
        //remover panel actual
        remove(c);
        //seteamos nuevo panel
        setContentPane(jPanel);
        //redibujar
        getContentPane().revalidate();
    }

}
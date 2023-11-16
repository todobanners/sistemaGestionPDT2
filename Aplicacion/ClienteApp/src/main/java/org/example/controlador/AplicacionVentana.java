package org.example.controlador;

import org.example.vista.*;
import org.example.vista.Usuario.LoginForm;
import org.example.vista.Usuario.ModificarDatosPropiosGUI;
import org.example.vista.Usuario.UsuarioGUI;
import org.example.vista.Usuario.UsuarioRegistroGUI;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;

import static javax.swing.UIManager.setLookAndFeel;

public class AplicacionVentana extends JFrame {

    public AplicacionVentana(String s) throws Exception {
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
        changePanel(new EquiposGUI().getPanel());
    }


    JMenuBar initMenuBar(){

        JMenuBar miMenuBar = new JMenuBar();
        // Declaro la barra de menu
        JMenu InicioPrincipal =         new JMenu("Inicio");
        JMenu gestionUsuarios =         new JMenu("Gestión Usuarios");
        JMenu gestionEquipos =          new JMenu("Gestión Equipos");
        JMenu gestionUbicaciones =      new JMenu("Gestión de Ubicaciones");
        JMenu gestionIntervenciones =   new JMenu("Gestión de Intervenciones");
        JMenu gestionPerfiles =         new JMenu("Gestion de Perfiles");
        //Fin declaracion barra de menu

        //Declaro los submenus de Inicio
        JMenuItem modificarDatosPropios = new JMenuItem("Modificar mis datos");
        JMenuItem cerrarSesion =          new JMenuItem("Cerrar Sesion");

        //Declaro el submenu de Gestion de Usuarios
        JMenuItem listarUsuarios =      new JMenuItem("Listar Usuarios");
        JMenuItem registrarUsuario =    new JMenuItem("Registrar Usuario");
        //Fin declaracion submenu de Gestion de Usuarios

        //Declaro el submenu de Gestion de Equipos
        JMenuItem listarEquipos =       new JMenuItem("Listar Equipos");
        JMenuItem registrarEquipo =     new JMenuItem("Registrar Equipo");
        //Fin declaracion submenu de Gestion de Equipos

        //Declaro el submenu de Gestion de Ubicaciones
        JMenuItem listarUbicaciones =   new JMenuItem("Listar Ubicaciones");
        JMenuItem registrarUbicacion =  new JMenuItem("Registrar Ubicacion");
        //Fin declaracion submenu de Gestion de Ubicaciones

        //Declaro el submenu de Gestion de Intervenciones
        JMenuItem listarIntervenciones =new JMenuItem("Listar Intervenciones");
        JMenuItem registrarIntervencion =new JMenuItem("Registrar Intervencion");
        //Fin declaracion submenu de Gestion de Intervenciones

        //Declaro el submenu de Gestion de Perfiles
        JMenuItem listarPerfiles = new JMenuItem("Listar Perfiles");

        //Menu Inicio
        //Menu de dashboard
        modificarDatosPropios.addActionListener(e -> {
            changePanel(new ModificarDatosPropiosGUI().getPanel());
        });
        cerrarSesion.addActionListener(e -> {
            Sesion.getInstancia(null);
            //cerar ventana y abrir login
            LoginForm loginForm = null;
            try {
                loginForm = new LoginForm();
            } catch (ClassNotFoundException | InstantiationException |
                     IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
            loginForm.setVisible(true);
            setVisible(false);

        });
        //Fin menu de dashboard


        //Establezco la funcionalidad de los submenus para mover entre paneles

        //Listado de Usuarios
        listarUsuarios.addActionListener(e -> {
            try {
                changePanel(new UsuarioGUI().getPanel());
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        });
        //Registrar Usuario
        registrarUsuario.addActionListener(e -> {
            try {
                changePanel(new UsuarioRegistroGUI().getPanel());
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Listado de Equipos
        listarEquipos.addActionListener(e -> {
            try {
                changePanel(new EquiposGUI().getPanel());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        //Listado de Ubicaciones
        listarUbicaciones.addActionListener(e -> {
            changePanel(new ListadoDeUbicacionesGUI().getPanel());
        });
        //Registrar Ubicacion
        registrarUbicacion.addActionListener(e -> {
            try {
                changePanel(new IngresarUbicacionGUI().getPanel());
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Listado de Intervenciones
        listarIntervenciones.addActionListener(e -> {
            try {
                changePanel(new ListadoDeIntervencionesGUI().getPanel());
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        });
        //Registrar Intervencion
        registrarIntervencion.addActionListener(e -> {
            try {
                changePanel(new IntervencionGUI().getPanel());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        //Liestado de PErfiles
        listarPerfiles.addActionListener(e -> {
            changePanel(new PerfilesGUI().getPanel());
        });

        //Fin establecimiento de funcionalidad de submenus

        //Agrego los submenus a los menus

        //Inicio
        InicioPrincipal.add(modificarDatosPropios);
        InicioPrincipal.add(cerrarSesion);

        //Gestion de Usuarios
        gestionUsuarios.add(listarUsuarios);
        gestionUsuarios.add(registrarUsuario);

        //Gestion de Equipos
        gestionEquipos.add(listarEquipos);
        gestionEquipos.add(registrarEquipo);

        //Gestion de Ubicaciones
        gestionUbicaciones.add(listarUbicaciones);
        gestionUbicaciones.add(registrarUbicacion);

        //Gestion de Intervenciones
        gestionIntervenciones.add(listarIntervenciones);
        gestionIntervenciones.add(registrarIntervencion);

        //Gestion de Perfiles
        gestionPerfiles.add(listarPerfiles);
        //Fin agregado de submenus a los menus

        //Agrego los menus a la barra de menu
        miMenuBar.add(InicioPrincipal);
        miMenuBar.add(gestionUsuarios);
        miMenuBar.add(gestionEquipos);
        miMenuBar.add(gestionUbicaciones);
        miMenuBar.add(gestionIntervenciones);
        miMenuBar.add(gestionPerfiles);
        //Fin agregado de menus a la barra de menu
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
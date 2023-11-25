package org.example.controlador;

import codigocreativo.uy.servidorapp.excepciones.ServiciosException;

import org.example.vista.Equipo.EquiposGUI;
import org.example.vista.HomeGUI;
import org.example.vista.Intervencion.IntervencionGUI;
import org.example.vista.Intervencion.TiposDeIntervencionesGUI;
import org.example.vista.Perfil.PerfilesGUI;
import org.example.vista.Ubicacion.ListadoDeUbicacionesGUI;
import org.example.vista.Usuario.*;

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
        setLocationRelativeTo(null);
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
        //Fin declaracion submenu de Gestion de Usuarios

        //Declaro el submenu de Gestion de Equipos
        JMenuItem listarEquipos =       new JMenuItem("Listar Equipos");
        JMenuItem fotosEquipos =        new JMenuItem("Fotos de Equipos");
        //Fin declaracion submenu de Gestion de Equipos

        //Declaro el submenu de Gestion de Ubicaciones
        JMenuItem listarUbicaciones =   new JMenuItem("Listar Ubicaciones");
        //Fin declaracion submenu de Gestion de Ubicaciones

        JMenuItem registrarIntervencion =new JMenuItem("Registrar Intervencion");
        JMenuItem listarTiposDeIntervenciones =new JMenuItem("Tipos de Intervenciones");
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
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException ex) {
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

        //Listado de Equipos
        listarEquipos.addActionListener(e -> {
            try {
                changePanel(new EquiposGUI().getPanel());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        fotosEquipos.addActionListener(e -> {
            try {
                changePanel(new HomeGUI().getPanel());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        //Listado de Ubicaciones
        listarUbicaciones.addActionListener(e -> {
            try {
                changePanel(new ListadoDeUbicacionesGUI().getPanel());
            } catch (NamingException | ServiciosException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Tipos de Intervenciones

        listarTiposDeIntervenciones.addActionListener(e -> {
            try {
                 changePanel(new TiposDeIntervencionesGUI().getPanel());
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


        //Gestion de Equipos
        gestionEquipos.add(listarEquipos);
        gestionEquipos.add(fotosEquipos);

        //Gestion de Ubicaciones
        gestionUbicaciones.add(listarUbicaciones);

        //Gestion de Intervenciones
        gestionIntervenciones.add(registrarIntervencion);
        gestionIntervenciones.add(listarTiposDeIntervenciones);

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
package org.example.controlador;

import codigocreativo.uy.servidorapp.excepciones.ServiciosException;

import org.example.vista.Equipo.EquiposGUI;
import org.example.vista.HomeGUI;
import org.example.vista.Intervencion.IntervencionGUI;
import org.example.vista.Intervencion.TiposDeIntervencionesGUI;
import org.example.vista.Perfil.PerfilesGUI;
import org.example.vista.Ubicacion.ListadoDeUbicacionesGUI;
import org.example.vista.Ubicacion.MovimientoEquiposGUI;
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
        // Fuente por defecto para Menús
        Font f = new Font("sans-serif", Font.PLAIN, 12);
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f);

        //iniciar menu
        JMenuBar menuBar=initMenuBar();
        setJMenuBar(menuBar);

        setVisible(true);
        setBounds(10,10,800,800);
        // Panel de ventana inicio
        changePanel(new HomeGUI().getPanel(), "Inicio");
    }


    JMenuBar initMenuBar(){

        JMenuBar miMenuBar = new JMenuBar();
        // Declaro la barra de menu
        JMenu InicioPrincipal =         new JMenu("");
        InicioPrincipal.setIcon(resizeIcon("Aplicacion/ClienteApp/src/main/recursos/menu/workshop-icon.png",60,60));
        JMenu gestionUsuarios =         new JMenu("");
        gestionUsuarios.setIcon(resizeIcon("Aplicacion/ClienteApp/src/main/recursos/menu/usuarios.png",60,60));
        JMenu gestionEquipos =          new JMenu("");
        gestionEquipos.setIcon(resizeIcon("Aplicacion/ClienteApp/src/main/recursos/menu/equipos.png",60,60));
        JMenu gestionUbicaciones =      new JMenu("");
        gestionUbicaciones.setIcon(resizeIcon("Aplicacion/ClienteApp/src/main/recursos/menu/ubicaciones.png",60,60));
        JMenu gestionIntervenciones =   new JMenu("");
        gestionIntervenciones.setIcon(resizeIcon("Aplicacion/ClienteApp/src/main/recursos/menu/intervenciones.png",60,60));
        JMenu gestionPerfiles =         new JMenu("");
        gestionPerfiles.setIcon(resizeIcon("Aplicacion/ClienteApp/src/main/recursos/menu/perfiles.png",60,60));
        // Fin declaración barra de menú

        // Declaro los submenús de Inicio
        JMenuItem fotosEquipos =        new JMenuItem("Inicio");
        JMenuItem modificarDatosPropios = new JMenuItem("Modificar mis datos");
        modificarDatosPropios.setIcon(resizeIcon("Aplicacion/ClienteApp/src/main/recursos/menu/editarMisDatos.png",30,30));
        JMenuItem cerrarSesion =          new JMenuItem("Cerrar Sesion");
        cerrarSesion.setIcon(resizeIcon("Aplicacion/ClienteApp/src/main/recursos/menu/logout.png",30,30));

        // Declaro el submenú de Gestión de Usuarios
        JMenuItem listarUsuarios =      new JMenuItem("Gestionar usuarios");
        // Fin declaración submenú de Gestión de Usuarios

        // Declaro el submenú de Gestión de Equipos
        JMenuItem listarEquipos =       new JMenuItem("Gestionar equipos");

        // Fin declaración submenú de Gestión de Equipos

        // Declaro el submenú de Gestión de Ubicaciones
        JMenuItem listarUbicaciones =   new JMenuItem("Gestionar ubicaciones");
        JMenuItem movimientoEquipos =   new JMenuItem("Movimiento de Equipos");
        // Fin declaración submenú de Gestión de Ubicaciones

        JMenuItem gestionarIntervenciones =new JMenuItem("Gestionar intervenciones");
        JMenuItem listarTiposDeIntervenciones =new JMenuItem("Gestionar tipos de intervenciones");
        // Fin declaración submenú de Gestión de Intervenciones

        // Declaro el submenú de Gestión de Perfiles
        JMenuItem listarPerfiles = new JMenuItem("Gestionar perfiles");

        // Menú Inicio
        // Menú de dashboard
        fotosEquipos.addActionListener(e -> {
            try {
                changePanel(new HomeGUI().getPanel(), "Inicio");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        modificarDatosPropios.addActionListener(e -> {
            changePanel(new ModificarDatosPropiosGUI().getPanel(), "Modificar mis datos");
        });
        cerrarSesion.addActionListener(e -> {
            Sesion.getInstancia(null);
            // Cerrar ventana y abrir login
            LoginForm loginForm = null;
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                loginForm = new LoginForm();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException ex) {
                throw new RuntimeException(ex);
            }
            loginForm.setVisible(true);
            setVisible(false);

        });
        // Fin menú de dashboard


        // Establezco la funcionalidad de los submenús para mover entre paneles

        // Listado de Usuarios
        listarUsuarios.addActionListener(e -> {
            try {
                changePanel(new UsuarioGUI().getPanel(), "Gestión de Usuarios");
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Listado de Equipos
        listarEquipos.addActionListener(e -> {
            try {
                changePanel(new EquiposGUI().getPanel(), "Gestión de Equipos");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        // Listado de Ubicaciones
        listarUbicaciones.addActionListener(e -> {
            try {
                changePanel(new ListadoDeUbicacionesGUI().getPanel(), "Gestión de Ubicaciones");
            } catch (NamingException | ServiciosException ex) {
                throw new RuntimeException(ex);
            }
        });

        movimientoEquipos.addActionListener(e -> {
            try {
                changePanel(new MovimientoEquiposGUI().getPanel(), "Movimiento de Equipos");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Tipos de Intervenciones

        listarTiposDeIntervenciones.addActionListener(e -> {
            try {
                 changePanel(new TiposDeIntervencionesGUI().getPanel(), "Gestión de Tipos de Intervenciones");
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
                });

        // Registrar Intervención
        gestionarIntervenciones.addActionListener(e -> {
            try {
                changePanel(new IntervencionGUI().getPanel(), "Gestión de Intervenciones");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        // Listado de Perfiles
        listarPerfiles.addActionListener(e -> {
            changePanel(new PerfilesGUI().getPanel(), "Gestión de Perfiles");
        });

        // Fin establecimiento de funcionalidad de submenús

        // Agrego los submenús a los menús

        // Inicio
        InicioPrincipal.add(fotosEquipos);
        InicioPrincipal.add(modificarDatosPropios);
        InicioPrincipal.add(cerrarSesion);


        // Gestión de Usuarios
        gestionUsuarios.add(listarUsuarios);


        // Gestión de Equipos
        gestionEquipos.add(listarEquipos);


        // Gestión de Ubicaciones
        gestionUbicaciones.add(listarUbicaciones);
        gestionUbicaciones.add(movimientoEquipos);

        // Gestión de Intervenciones
        gestionIntervenciones.add(gestionarIntervenciones);
        gestionIntervenciones.add(listarTiposDeIntervenciones);

        // Gestión de Perfiles
        gestionPerfiles.add(listarPerfiles);
        // Fin agregado de submenús a los menús

        // Agrego los menús a la barra de menú
        miMenuBar.add(InicioPrincipal);
        miMenuBar.add(gestionUsuarios);
        miMenuBar.add(gestionEquipos);
        miMenuBar.add(gestionUbicaciones);
        miMenuBar.add(gestionIntervenciones);
        miMenuBar.add(gestionPerfiles);
        // Fin agregado de menús a la barra de menú
        return miMenuBar;
    }

    public void changePanel(JPanel jPanel, String title){
    Container c = getContentPane();
    // Remover contenido actual
    c.removeAll();
    // Crear y agregar el título
    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(JLabel.CENTER);
    c.add(titleLabel, BorderLayout.NORTH);
    // Agregar el nuevo panel
    c.add(jPanel, BorderLayout.CENTER);
    // Setear el título de la ventana
    setTitle(title + " - Gestión de mantenimiento- Código Creativo");
    // Redibujar
    c.revalidate();
    c.repaint();
}

    public ImageIcon resizeIcon(String imagePath, int width, int height) {
    ImageIcon originalIcon = new ImageIcon(imagePath);
    Image originalImage = originalIcon.getImage();
    Image resizedImage = originalImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
    return new ImageIcon(resizedImage);
}

}
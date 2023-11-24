package org.example.vista.Usuario;

import codigocreativo.uy.servidorapp.DTO.*;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.controlador.AplicacionVentana;
import org.example.controlador.Sesion;
import org.example.modelo.Conexion;
import org.example.modelo.Utilidades;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class LoginForm extends JFrame {
    private JButton loginButton;
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton cancelarButton;
    private JLabel crearCuenta;
    private JButton soyUnBotonQueButton;
    private JLabel logo;
    private JPanel formularioLogo;
    private JLabel userText;
    private JLabel claveText;

    public LoginForm() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel1);

        setSize(500, 700);
        setBackground(Color.WHITE);
        ImageIcon imagen = new ImageIcon("Aplicacion/ClienteApp/src/main/recursos/ccblanco.jpg");
        Image img = imagen.getImage();
        Image imgRedimensionada = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon imagenRedimensionada = new ImageIcon(imgRedimensionada);
        logo.setIcon(imagenRedimensionada);
        Font f = new Font("Roboto", Font.PLAIN, 14);
        userText.setFont(f);
        claveText.setFont(f);
        textField1.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        textField1.setFont(f);
        passwordField1.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        passwordField1.setFont(f);
        loginButton.setFont(f);
        loginButton.setBackground(Color.decode("#2F9C95"));
        cancelarButton.setFont(f);
        cancelarButton.setBackground(Color.decode("#e06666"));
        pack();
        loginButton.addActionListener(e -> {
            try {
                login();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        cancelarButton.addActionListener(e -> {
            System.exit(0);
        });
        crearCuenta.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    new RegistroUsuarioNuevo("CodigoCreativo - Registro de Usuario");
                } catch (NamingException | UnsupportedLookAndFeelException | ClassNotFoundException |
                         InstantiationException | IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
                setVisible(false);
            }
        });

        //Al pulsar enter en el campo de contraseña se ejecuta el boton de login
        passwordField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButton.doClick();
            }
        });

        soyUnBotonQueButton.setVisible(false); // comentar esta linea para usar el boton de datos de prueba
        //Este boton sirve para generar datos de prueba, descomentar para usar

        /*soyUnBotonQueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Cree algunos datos para funcionar, admin admin");

                try {
                    InstitucionDto institucion = new InstitucionDto();
                    institucion.setNombre("CodigoCreativo");
                    Conexion.obtenerInstitucionBean().agregarInstitucion(institucion);
                    ///////////////////////
                    PerfilDto perfil = new PerfilDto();
                    perfil.setNombrePerfil("Administrador");
                    perfil.setEstado(Estados.ACTIVO);
                    Conexion.obtenerPerfilBean().crearPerfil(perfil);
                    //////////////////////////
                    UsuarioDto usuario = new UsuarioDto();
                    usuario.setFechaNacimiento(LocalDate.now());
                    usuario.setNombre("Administrador");
                    usuario.setApellido("Administrador");
                    usuario.setCedula("12345678");
                    usuario.setEmail("algo@algo.com");
                    usuario.setContrasenia(Utilidades.hashClave("admin"));
                    usuario.setEstado(Estados.ACTIVO);
                    usuario.setNombreUsuario("admin");
                    institucion.setId(1L);
                    usuario.setIdInstitucion(institucion);
                    perfil.setId(1L);
                    usuario.setIdPerfil(perfil);
                    Conexion.obtenerUsuarioBean().crearUsuario(usuario);
                    //////////////////////////
                    UbicacionDto ubicacion = new UbicacionDto();
                    institucion.setId(1L);
                    ubicacion.setIdInstitucion(institucion);
                    ubicacion.setNumero(2L);
                    ubicacion.setPiso(1L);
                    ubicacion.setNombre("CTI");
                    ubicacion.setSector("Sector");
                    Conexion.obtenerUbicacionBean().crearUbicacion(ubicacion);
                    //////////////////////////
                    ProveedoresEquipoDto proveedoresEquipo = new ProveedoresEquipoDto();
                    proveedoresEquipo.setNombre("Proveedor");
                    Conexion.obtenerProveedoresEquipoBean().CrearProveedoresEquipo(proveedoresEquipo);
                    //////////////////////////////
                    PaisDto pais = new PaisDto();
                    pais.setNombre("Uruguay");
                    Conexion.obtenerPaisBean().crearPais(pais);
                    /////////////////////////////////////////////////////
                    MarcasModeloDto marcasModelos = new MarcasModeloDto();
                    marcasModelos.setNombre("Marca");
                    Conexion.obtenerMarcaBean().crearMarcasModelo(marcasModelos);
                    ////////////////////////////////////////
                    ModelosEquipoDto modelosEquipo = new ModelosEquipoDto();
                    modelosEquipo.setNombre("Modelo");
                    marcasModelos.setId(1L);
                    modelosEquipo.setIdMarca(marcasModelos);
                    Conexion.obtenerModeloBean().crearModelosEquipo(modelosEquipo);
                    ///////////////////////////////////////////////////////////////


                } catch (NamingException | ServiciosException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });*/
    }

    private void login() throws Exception {
        String username = textField1.getText();
        String password = new String(passwordField1.getPassword());
        password = Utilidades.hashClave(password);

        UsuarioDto usuario = Conexion.obtenerUsuarioBean().login(username,password);

        if (usuario == null) {
            throw new Exception("Usuario o contraseña incorrectos");
        } else if (usuario.getEstado().equals(Estados.SIN_VALIDAR)) {
            throw new Exception("Usuario aun no validado, consulte a un administrador para conocer su estado");
        } else if (usuario.getEstado().equals(Estados.INACTIVO)) {
            throw new Exception("Usuario desactivado, consulte a un administrador para conocer su estado");
        } else {
            Sesion sesion = Sesion.getInstancia(usuario);
            JOptionPane.showMessageDialog(null, "Bienvenido "+usuario.getNombre()+"!");
            setVisible(false);
            new AplicacionVentana("CodigoCreativo - Sistema de gestion de mantenimiento");
        }
    }

}
package org.example;

import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.controlador.AplicacionVentana;
import org.example.vista.EquiposGUI;

import javax.naming.NamingException;
import javax.swing.*;


/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args) throws NamingException, ServiciosException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        /*// Configuración del contexto inicial
        // Configura las propiedades para el contexto de JNDI
        java.util.Properties jndiProps = new java.util.Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,"org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        // Cambia la URL según tu configuración
        // Crea el contexto de JNDI
        Context context = new InitialContext(jndiProps);
        UsuarioBean usuarioBean = (UsuarioBean) context.lookup("ejb:/ServidorApp-1.0-SNAPSHOT/UsuarioBean!codigocreativo.uy.servidorapp.servicios.UsuarioRemote");

        // Crear un usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setEmail("juanperez@gmail.com");
        usuario.setCedula("12345678");
        //LocalDate fechaNac = LocalDate.of(1990,3,13);
        //usuario.setFechaNacimiento(fechaNac);
        usuario.setContrasenia(BCrypt.hashpw("Test123",BCrypt.gensalt()));
        usuario.setEstado("ACTIVO");
        //usuario.setIdPerfil();*/

        /*DefaultEntidad d = new DefaultEntidad();
        d.setCampoDefaultString("Hola");
        Conexion.obtenerDefaultBean().create(d);
        for (DefaultEntidad de : Conexion.obtenerDefaultBean().list()) {
            System.out.println(de.getCampoDefaultString());
        }*/

        //ControllerVentanaBase controlaBase = ControllerVentanaBase.getInstancia();
        //controlaBase.mostrarVentana();

        //new AplicacionVentana("CodigoCreativo - Sistema de gestion de mantenimiento");
        try {
            new EquiposGUI();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

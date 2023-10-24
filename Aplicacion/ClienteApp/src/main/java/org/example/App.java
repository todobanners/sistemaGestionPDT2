package org.example;

import codigocreativo.uy.servidorapp.entidades.DefaultEntidad;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.DefaultRemote;
import codigocreativo.uy.servidorapp.servicios.UsuarioBean;
import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.time.LocalDate;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args) throws NamingException {
        // Configuración del contexto inicial
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
        //usuario.setIdPerfil();

    }
}

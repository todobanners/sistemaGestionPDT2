package org.example;

import codigocreativo.uy.servidorapp.servicios.DefaultRemote;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Properties;

public class Conexion {
    private static Properties jndiProps; // Propiedades para el contexto de JNDI
    private static Context ctx; // Contexto de JNDI
    //
    private static final String EJB_DEFAULT = "ejb:/ServidorApp-1.0-SNAPSHOT/DefaultBean!codigocreativo.uy.servidorapp.servicios.DefaultRemote"; // Nombre del EJB por defecto

    static { // Inicialización de las propiedades para el contexto de JNDI
        jndiProps = new Properties(); // Crea una instancia de las propiedades
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,"org.wildfly.naming.client.WildFlyInitialContextFactory");// Configura la factoría de contexto inicial
        jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");// Configura la URL del servidor de aplicaciones
        try {
            ctx = new javax.naming.InitialContext(jndiProps);// Crea el contexto de JNDI
        } catch (Exception e) {// Si hay un error
            e.printStackTrace(); // Imprime el error
        }
    }

    public static DefaultRemote obtenerDefaultBean() throws NamingException { // Obtiene el EJB por defecto
        return (DefaultRemote) ctx.lookup(EJB_DEFAULT); // Devuelve el EJB por defecto
    }
}

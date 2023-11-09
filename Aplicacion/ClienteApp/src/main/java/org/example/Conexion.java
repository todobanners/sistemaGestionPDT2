package org.example;

import codigocreativo.uy.servidorapp.servicios.*;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Properties;

public class Conexion {
    private static Properties jndiProps; // Propiedades para el contexto de JNDI
    private static Context ctx; // Contexto de JNDI
    //
    private static final String EJB_DEFAULT = "ejb:/ServidorApp-1.0-SNAPSHOT/DefaultBean!codigocreativo.uy.servidorapp.servicios.DefaultRemote"; // Nombre del EJB por defecto
    private static final String EJB_USUARIO = "ejb:/ServidorApp-1.0-SNAPSHOT/UsuarioBean!codigocreativo.uy.servidorapp.servicios.UsuarioRemote"; // Nombre del EJB de usuario
    private static final String EJB_PERFIL = "ejb:/ServidorApp-1.0-SNAPSHOT/PerfilBean!codigocreativo.uy.servidorapp.servicios.PerfilRemote"; // Nombre del EJB de perfil
    private static final String EJB_INSTITUCION = "ejb:/ServidorApp-1.0-SNAPSHOT/InstitucionBean!codigocreativo.uy.servidorapp.servicios.InstitucionRemote"; // Nombre del EJB de institucion
    private static final String EJB_EQUIPO = "ejb:/ServidorApp-1.0-SNAPSHOT/EquipoBean!codigocreativo.uy.servidorapp.servicios.EquipoRemote"; // Nombre del EJB de equipo
    private static final String EJB_UBICACION = "ejb:/ServidorApp-1.0-SNAPSHOT/UbicacionBean!codigocreativo.uy.servidorapp.servicios.UbicacionRemote"; // Nombre del EJB de ubicacion
    private static final String EJB_INTERVENCION = "ejb:/ServidorApp-1.0-SNAPSHOT/IntervencionBean!codigocreativo.uy.servidorapp.servicios.IntervencionRemote"; // Nombre del EJB de Intervención


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
    public static UsuarioRemote obtenerUsuarioBean() throws NamingException { // Obtiene el EJB de usuario
        return (UsuarioRemote) ctx.lookup(EJB_USUARIO); // Devuelve el EJB de usuario
    }
    public static PerfilRemote obtenerPerfilBean() throws NamingException { // Obtiene el EJB de perfil
        return (PerfilRemote) ctx.lookup(EJB_PERFIL); // Devuelve el EJB de perfil
    }
    public static InstitucionRemote obtenerInstitucionBean() throws NamingException { // Obtiene el EJB de institucion
        return (InstitucionRemote) ctx.lookup(EJB_INSTITUCION); // Devuelve el EJB de institucion
    }
    public static EquipoRemote obtenerEquipoBean() throws NamingException { // Obtiene el EJB de equipo
        return (EquipoRemote) ctx.lookup(EJB_EQUIPO); // Devuelve el EJB de equipo
    }
    public static UbicacionRemote obtenerUbicacionBean() throws NamingException { // Obtiene el EJB de ubicacion
        return (UbicacionRemote) ctx.lookup(EJB_UBICACION); // Devuelve el EJB de ubicacion
    }
    public static IntervencionRemote obtenerIntervencionBean() throws NamingException { // Obtiene el EJB de Intervencion
        return (IntervencionRemote) ctx.lookup(EJB_INTERVENCION); //Devuelve el EJB de intervención
         }
}

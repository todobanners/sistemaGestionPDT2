package org.example.modelo;

import codigocreativo.uy.servidorapp.servicios.*;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Properties;

/*
    Clase para obtener los EJBs, utilizando JNDI
*/

public class Conexion {
    // Contexto de JNDI
    private static Context ctx;

    // Inicialización de las propiedades para el contexto de JNDI
    static {
        // Propiedades para el contexto de JNDI
        Properties jndiProps = new Properties(); // Crea una instancia de las propiedades
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,"org.wildfly.naming.client.WildFlyInitialContextFactory");// Configura la factoría de contexto inicial
        jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");// Configura la URL del servidor de aplicaciones
        try {
            ctx = new javax.naming.InitialContext(jndiProps);// Crea el contexto de JNDI
        } catch (Exception e) {// Si hay un error
            e.printStackTrace(); // Imprime el error
        }
    }

    //Se definen los nombres de los EJBs
    // Nombre del EJB de usuario
    private static final String EJB_USUARIO = "ejb:/ServidorApp-1.0-SNAPSHOT/UsuarioBean!codigocreativo.uy.servidorapp.servicios.UsuarioRemote";
    // Nombre del EJB de perfil
    private static final String EJB_PERFIL = "ejb:/ServidorApp-1.0-SNAPSHOT/PerfilBean!codigocreativo.uy.servidorapp.servicios.PerfilRemote";
    // Nombre del EJB de perfil
    private static final String EJB_PERMISO = "ejb:/ServidorApp-1.0-SNAPSHOT/PermisoBean!codigocreativo.uy.servidorapp.servicios.PermisoRemote";
    // Nombre del EJB de institucion
    private static final String EJB_INSTITUCION = "ejb:/ServidorApp-1.0-SNAPSHOT/InstitucionBean!codigocreativo.uy.servidorapp.servicios.InstitucionRemote";
    // Nombre del EJB de equipo
    private static final String EJB_EQUIPO = "ejb:/ServidorApp-1.0-SNAPSHOT/EquipoBean!codigocreativo.uy.servidorapp.servicios.EquipoRemote";
    // Nombre del EJB de ubicacion
    private static final String EJB_UBICACION = "ejb:/ServidorApp-1.0-SNAPSHOT/UbicacionBean!codigocreativo.uy.servidorapp.servicios.UbicacionRemote";
    // Nombre del EJB de Intervención
    private static final String EJB_INTERVENCION = "ejb:/ServidorApp-1.0-SNAPSHOT/IntervencionBean!codigocreativo.uy.servidorapp.servicios.IntervencionRemote";
    // Nombre del EJB de Intervención
    private static final String EJB_TIPO_INTERVENCION = "ejb:/ServidorApp-1.0-SNAPSHOT/TipoIntervencioneBean!codigocreativo.uy.servidorapp.servicios.TipoIntervencioneRemote";
    // Nombre del EJB de usuario telefono
    private static final String EJB_USUARIO_TELEFONO = "ejb:/ServidorApp-1.0-SNAPSHOT/UsuariosTelefonoBean!codigocreativo.uy.servidorapp.servicios.UsuariosTelefonoRemote";
    // Nombre del EJB de proveedores equipo
    private static final String EJB_PROVEEDORES_EQUIPO = "ejb:/ServidorApp-1.0-SNAPSHOT/ProveedoresEquipoBean!codigocreativo.uy.servidorapp.servicios.ProveedoresEquipoRemote";
    // Nombre del EJB de pais
    private static final String EJB_PAIS =   "ejb:/ServidorApp-1.0-SNAPSHOT/PaisBean!codigocreativo.uy.servidorapp.servicios.PaisRemote";
    // Nombre del EJB de modelo
    private static final String EJB_MODELO = "ejb:/ServidorApp-1.0-SNAPSHOT/ModelosEquipoBean!codigocreativo.uy.servidorapp.servicios.ModelosEquipoRemote";
    // Nombre del EJB de marcas
    private static final String EJB_MARCAS = "ejb:/ServidorApp-1.0-SNAPSHOT/MarcasModeloBean!codigocreativo.uy.servidorapp.servicios.MarcasModeloRemote";
    // Nombre del EJB de tpos de equipo
    private static final String EJB_TIPOS_EQUIPO = "ejb:/ServidorApp-1.0-SNAPSHOT/TiposEquipoBean!codigocreativo.uy.servidorapp.servicios.TiposEquipoRemote";
    // Nombre del EJB de Baja de Ubicaciones
    private static final String EJB_BAJA_UBICACION = "ejb:/ServidorApp-1.0-SNAPSHOT/BajaUbicacionBean!codigocreativo.uy.servidorapp.servicios.BajaUbicacionRemote";
    // Nombre del EJB de Baja de Equipos
    private static final String EJB_BAJA_EQUIPO = "ejb:/ServidorApp-1.0-SNAPSHOT/BajaEquipoBean!codigocreativo.uy.servidorapp.servicios.BajaEquipoRemote";
// Nombre del EJB de Baja de Equipos
    private static final String EJB_EQUIPO_UBICACION = "ejb:/ServidorApp-1.0-SNAPSHOT/EquiposUbicacioneBean!codigocreativo.uy.servidorapp.servicios.EquiposUbicacioneRemote";

    // Métodos para obtener los EJBs
    public static UsuarioRemote obtenerUsuarioBean() throws NamingException {
        return (UsuarioRemote) ctx.lookup(EJB_USUARIO);
    }
    public static PerfilRemote obtenerPerfilBean() throws NamingException {
        return (PerfilRemote) ctx.lookup(EJB_PERFIL);
    }
    public static PermisoRemote obtenerPermisoBean() throws NamingException {
        return (PermisoRemote) ctx.lookup(EJB_PERMISO);
    }
    public static InstitucionRemote obtenerInstitucionBean() throws NamingException {
        return (InstitucionRemote) ctx.lookup(EJB_INSTITUCION);
    }
    public static EquipoRemote obtenerEquipoBean() throws NamingException {
        return (EquipoRemote) ctx.lookup(EJB_EQUIPO);
    }
    public static UbicacionRemote obtenerUbicacionBean() throws NamingException {
        return (UbicacionRemote) ctx.lookup(EJB_UBICACION);
    }
    public static IntervencionRemote obtenerIntervencionBean() throws NamingException {
        return (IntervencionRemote) ctx.lookup(EJB_INTERVENCION);
         }
    public static TipoIntervencioneRemote obtenerTiposIntervencionBean() throws NamingException {
        return (TipoIntervencioneRemote) ctx.lookup(EJB_TIPO_INTERVENCION);
    }
    public static UsuariosTelefonoRemote obtenerUsuariosTelefonoBean() throws NamingException {
        return (UsuariosTelefonoRemote) ctx.lookup(EJB_USUARIO_TELEFONO);
    }
    public static ProveedoresEquipoRemote obtenerProveedoresEquipoBean() throws NamingException {
        return (ProveedoresEquipoRemote) ctx.lookup(EJB_PROVEEDORES_EQUIPO);
    }
    public static PaisRemote obtenerPaisBean() throws NamingException {
        return (PaisRemote) ctx.lookup(EJB_PAIS);
    }
    public static ModelosEquipoRemote obtenerModeloBean() throws NamingException {
        return (ModelosEquipoRemote) ctx.lookup(EJB_MODELO);
    }
    public static MarcasModeloRemote obtenerMarcaBean() throws NamingException {
        return (MarcasModeloRemote) ctx.lookup(EJB_MARCAS);
    }
    public static TiposEquipoRemote obtenerTipoBean() throws NamingException {
        return (TiposEquipoRemote) ctx.lookup(EJB_TIPOS_EQUIPO);
    }
    public static BajaUbicacionRemote obtenerBajaUbicacionBean() throws NamingException {
        return (BajaUbicacionRemote) ctx.lookup(EJB_BAJA_UBICACION);
    }

    public static BajaEquipoRemote obtenerBajaEquipoBean() throws NamingException {
        return (BajaEquipoRemote) ctx.lookup(EJB_BAJA_EQUIPO);
    }
    public static EquiposUbicacioneRemote obtenerEquipoUbicacionBean() throws NamingException {
        return (EquiposUbicacioneRemote) ctx.lookup(EJB_EQUIPO_UBICACION);
    }
}

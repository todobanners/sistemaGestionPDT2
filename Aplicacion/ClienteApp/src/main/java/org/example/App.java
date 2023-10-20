package org.example;

import codigocreativo.uy.servidorapp.entidades.DefaultEntidad;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.DefaultRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
        DefaultRemote defaultBean = (DefaultRemote) context.lookup("ejb:/ServidorApp-1.0-SNAPSHOT/DefaultBean!codigocreativo.uy.servidorapp.servicios.DefaultRemote");
        //MateriasRemote materiasBean = (MateriasRemote) context.lookup("EjemploJPA/MateriasBean!uy.edu.utec.servicios.MateriasRemote");
        //CarrerasRemote carrerasBean = (CarrerasRemote) InitialContext.doLookup("EjemploJPA/CarrerasBean!uy.edu.utec.servicios.CarrerasRemote");

        DefaultEntidad d = new DefaultEntidad();
        d.setCampoDefaultString("PruebaString");
        try {
            defaultBean.create(d);
            System.out.println("Se creó exitosamente el default");

            for (DefaultEntidad defaultEntidad : defaultBean.list()) {
                System.out.println(defaultEntidad.getCampoDefaultString());
            }

        } catch (ServiciosException e) {
            System.out.println(e.getMessage());
        }
    }
}

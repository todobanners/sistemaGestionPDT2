package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.DefaultEntidad;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import org.example.Conexion;

import javax.naming.NamingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HomeGUI {
    private JPanel panel1;
    private JButton enviarButton;
    private JList list1;
    private JTextField textField1;

    public JPanel getPanel(){
    return panel1;
}
    public HomeGUI() throws NamingException, ServiciosException {
        list1.setListData(Conexion.obtenerDefaultBean().list().toArray());//Actualizo la lista
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultEntidad defaulValor = new DefaultEntidad();//Defino el objeto entidad para agregar el campo string
                    defaulValor.setCampoDefaultString(textField1.getText());//Agrego el valor del campo string
                    Conexion.obtenerDefaultBean().create(defaulValor);//Agrego el objeto a la base de datos
                    list1.setListData(Conexion.obtenerDefaultBean().list().toArray());//Actualizo la lista

                } catch (ServiciosException | NamingException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}

package org.example.vista;

import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.entidades.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IngresarUbicacionGUI extends JPanel {
    private JTextField Institucion;
    private JComboBox Sector;
    private JTextField Nombre;
    private JTextField Número;
    private JTextField Piso;
    private JTextField Cama;
    private JLabel InstitucionLabel;
    private JLabel SectorLabel;
    private JLabel NombreLabel;
    private JLabel PisoLabel;
    private JLabel CamaLabel;
    private JPanel IngresoUbicacionNueva;
    private JButton enviarButton;
    private JButton cancelarButton;


    public JPanel getPanel(){
        return IngresoUbicacionNueva;
    }
    private void createUIComponents() {
        Sector.addItem("cti");
    }

    public IngresarUbicacionGUI() {
      createUIComponents();
        enviarButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
        }
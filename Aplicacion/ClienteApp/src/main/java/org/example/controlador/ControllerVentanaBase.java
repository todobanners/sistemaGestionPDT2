package org.example.controlador;

import org.example.vista.DefaultGUI;

public class ControllerVentanaBase {
    private static ControllerVentanaBase instancia;
    private final DefaultGUI defaultGUI;

    private ControllerVentanaBase(){
        this.defaultGUI = DefaultGUI.getInstancia();
        this.abrirVentanaDefault();
    }
    static {
        ControllerVentanaBase.instancia = new ControllerVentanaBase();
    }
    public void mostrarVentana(){
        this.defaultGUI.setVisible(true);
    }

    public static ControllerVentanaBase getInstancia(){
        return ControllerVentanaBase.instancia;
    }

    public void setControladorPanel(ControlPanel controladorPanel){
        this.defaultGUI.cambiarPanelContenido(controladorPanel.getPanel());
    }

    public void abrirVentanaDefault(){
        this.setControladorPanel(new ControlPanelPrincipal());
    }
}

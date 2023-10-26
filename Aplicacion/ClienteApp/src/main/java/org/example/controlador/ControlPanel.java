package org.example.controlador;

import org.example.vista.DefaultGUI;

import javax.swing.*;

public abstract class ControlPanel {
    protected DefaultGUI defaultGUI;

    public ControlPanel(){
        this.defaultGUI = DefaultGUI.getInstancia();
    }
    public abstract JPanel getPanel();
}

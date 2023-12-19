package org.example.controlador;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.io.IOException;
import java.util.Properties;

public class CustomFlatLaf extends FlatLightLaf {
    public CustomFlatLaf() {
        super();

        try {
            Properties props = new Properties();
            props.load(getClass().getResourceAsStream("/FlatLightLaft.properties"));
            for (String key : props.stringPropertyNames()) {
                UIManager.put(key, props.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
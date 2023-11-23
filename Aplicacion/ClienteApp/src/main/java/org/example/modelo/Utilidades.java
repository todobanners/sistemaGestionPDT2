package org.example.modelo;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilidades {
    public static String hashClave(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatePicker createCustomDatePicker() {
        // Crear un objeto DatePickerSettings.
        DatePickerSettings dateSettings = new DatePickerSettings();

        // Crear el DatePicker con los ajustes.
        DatePicker datePicker = new DatePicker(dateSettings);

        // Obtener la URL de la imagen.
        URL dateImageURL = Utilidades.class.getResource("/calendar-128-16.png");

        // Crear la imagen y el ícono.
        Image dateExampleImage = Toolkit.getDefaultToolkit().getImage(dateImageURL);
        ImageIcon dateExampleIcon = new ImageIcon(dateExampleImage);

        // Obtener el botón del DatePicker y establecer el ícono.
        JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
        datePickerButton.setText("");
        datePickerButton.setIcon(dateExampleIcon);

        return datePicker;
    }

    public static DateTimePicker createCustomDateTimePicker() {
        // Crear un objeto DatePickerSettings.
        DatePickerSettings dateSettings = new DatePickerSettings();

        // Crear un objeto TimePickerSettings.
        TimePickerSettings timeSettings = new TimePickerSettings();

        // Crear el DateTimePicker con los ajustes.
        DateTimePicker dateTimePicker = new DateTimePicker(dateSettings, timeSettings);

        // Obtener la URL de la imagen.
        URL tiempoImageURL = Utilidades.class.getResource("/datetime-16.png");
        URL calendarioImageURL = Utilidades.class.getResource("/calendar-128-16.png");

        // Crear la imagen y el ícono.
        Image tiempoImage = Toolkit.getDefaultToolkit().getImage(tiempoImageURL);
        ImageIcon tiempoIcon = new ImageIcon(tiempoImage);

        Image calendarioImage = Toolkit.getDefaultToolkit().getImage(calendarioImageURL);
        ImageIcon calendarioIcon = new ImageIcon(calendarioImage);

        // Obtener el botón del DateTimePicker y establecer el ícono.
        JButton datePickerButton = dateTimePicker.getDatePicker().getComponentToggleCalendarButton();
        datePickerButton.setText("");
        datePickerButton.setIcon(calendarioIcon);

        JButton timePickerButton = dateTimePicker.getTimePicker().getComponentToggleTimeMenuButton();
        timePickerButton.setText("");
        timePickerButton.setIcon(tiempoIcon);

        return dateTimePicker;
    }
}

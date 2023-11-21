package org.example.modelo;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class DatePickerUtil {

    public static DatePicker createCustomDatePicker() {
        // Crear un objeto DatePickerSettings.
        DatePickerSettings dateSettings = new DatePickerSettings();

        // Crear el DatePicker con los ajustes.
        DatePicker datePicker = new DatePicker(dateSettings);

        // Obtener la URL de la imagen.
        URL dateImageURL = DatePickerUtil.class.getResource("/calendar-128-16.png");

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
        URL tiempoImageURL = DatePickerUtil.class.getResource("/datetime-16.png");
        URL calendarioImageURL = DatePickerUtil.class.getResource("/calendar-128-16.png");

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
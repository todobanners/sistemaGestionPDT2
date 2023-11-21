package org.example.modelo;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
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
}
package org.example.modelo;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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

    private static final String API_KEY = "c06165e921ae196cb18e841cc45031c4";
    private static final String URL_UPLOAD = "https://api.imgbb.com/1/upload?key=" + API_KEY;

    public static String subirImagen(File imageFile) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost uploadFile = new HttpPost(URL_UPLOAD);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("image", new FileBody(imageFile));
            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);
            CloseableHttpResponse response = httpClient.execute(uploadFile);
            try {
                HttpEntity responseEntity = response.getEntity();
                String responseContent = EntityUtils.toString(responseEntity);
                System.out.println("Response status: " + response.getStatusLine());
                System.out.println("Response content: " + responseContent);

                // Parsear la respuesta JSON
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(responseContent);
            // Obtener la URL de la imagen
            JSONObject data = (JSONObject) jsonResponse.get("data");
            String imageUrl = (String) data.get("url");

            System.out.println("Image URL: " + imageUrl);
            return imageUrl;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }
}

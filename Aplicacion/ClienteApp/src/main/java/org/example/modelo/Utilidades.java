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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.UUID;
import java.util.Vector;

import static java.lang.System.getProperty;

public class Utilidades {
    public static void exportarAExcel(Vector<String> cabezales, Vector<Vector<Object>> datos, String nombreHoja) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);

        // Limitar la selección a archivos de Excel
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo Excel (*.xlsx)", "xlsx");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showSaveDialog(null);

        File archivoAGuardar = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            archivoAGuardar = fileChooser.getSelectedFile();
            // Asegurarse de que el archivo tenga la extensión .xlsx
            if (!archivoAGuardar.getName().toLowerCase().endsWith(".xlsx")) {
                archivoAGuardar = new File(archivoAGuardar.getAbsolutePath() + ".xlsx");
            }

        } else {
            JOptionPane.showMessageDialog(null, "No se seleccionó la ruta de guardado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Se crea el libro de trabajo
        HSSFWorkbook workbook = new HSSFWorkbook();
        //Se crea una hoja dentro del libro
        HSSFSheet sheet = workbook.createSheet(nombreHoja);
        //Se crea la fila que contiene la cabecera
        HSSFRow rowhead = sheet.createRow((short) 0);
        for (int i = 0; i < cabezales.size(); i++) {
            rowhead.createCell(i).setCellValue(cabezales.get(i));
        }
        //Se crean las filas que contienen los datos
        for (int i = 0; i < datos.size(); i++) {
            HSSFRow row = sheet.createRow((short) i + 1);
            for (int j = 0; j < datos.get(i).size(); j++) {
                row.createCell(j).setCellValue(datos.get(i).get(j).toString());
            }
        }
        try {
            //Se crea el archivo
            FileOutputStream fileOut = new FileOutputStream(archivoAGuardar);
            workbook.write(fileOut);
            fileOut.close();
            Boolean abrirArchivo = JOptionPane.showConfirmDialog(null, "Archivo guardado exitosamente. ¿Desea abrirlo?", "Abrir archivo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
            if (abrirArchivo) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().open(archivoAGuardar);
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al intentar abrir el archivo", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No es posible abrir el archivo automáticamente", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (FileAlreadyExistsException e) {
            JOptionPane.showMessageDialog(null, "El archivo ya existe", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al crear el archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

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
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost uploadFile = new HttpPost(URL_UPLOAD);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("image", new FileBody(imageFile));
            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);
            try (CloseableHttpResponse response = httpClient.execute(uploadFile)) {
                HttpEntity responseEntity = response.getEntity();
                String responseContent = EntityUtils.toString(responseEntity);

                // Parsear la respuesta JSON
                JSONParser parser = new JSONParser();
                JSONObject jsonResponse = (JSONObject) parser.parse(responseContent);
                // Obtener la URL de la imagen
                JSONObject data = (JSONObject) jsonResponse.get("data");
                JSONObject medium = (JSONObject) data.get("medium");
                String imageUrl = (String) medium.get("url");

                return imageUrl;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static LocalDate convertirStringADate(String s) {
        String[] fecha = s.split("-");
        return LocalDate.of(Integer.parseInt(fecha[0]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[2]));
    }
}

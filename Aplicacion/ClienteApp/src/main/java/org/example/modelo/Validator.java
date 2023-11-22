package org.example.modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    // Método para validar que una cadena no esté vacía y tenga al menos una longitud mínima
    public static boolean validarMinimoCaracteres(String texto, int longitudMinima) {
        return texto != null && texto.length() >= longitudMinima;
    }

    // Método para validar que una cadena no exceda una longitud máxima
    public static boolean validarMaximoCaracteres(String texto, int longitudMaxima) {
        return texto != null && texto.length() <= longitudMaxima;
    }

    // Método para validar una dirección de correo electrónico
    public static boolean validarEmail(String email) {
        //Expresion regular para validar email obenida de https://www.freeformatter.com/java-regex-tester.html
        String regex = "^[-a-z0-9~!$%^&*_=+}{'?]+(\\.[-a-z0-9~!$%^&*_=+}{'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*(\\.aero|\\.arpa|\\.biz|\\.com|\\.coop|\\.edu|\\.gov|\\.info|\\.int|\\.mil|\\.museum|\\.name|\\.net|\\.org|\\.pro|\\.travel|\\.mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(\\:[0-9]{1,5})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * @param texto Cadena de texto a validar
     * @return true si la cadena contiene solo letras, false en caso contrario
     */
    // Método para validar que una cadena contenga solo letras
    public static boolean contieneSoloLetras(String texto) {
        return texto == null || !texto.matches("^[a-zA-Z ]+$");
    }

    // Método para validar que una cadena contenga solo números
    public static boolean contieneSoloNumeros(String numero) {
        return numero == null || !numero.matches("^[0-9]+$");
    }

    // Método para validar una contraseña (requiere al menos 8 caracteres, una letra y un número)
    public static boolean validarContrasena(String contrasena) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contrasena);
        return matcher.matches();
    }

    public static boolean validarCedula(String cedula) {
        if (cedula.length() != 8 || !cedula.matches("\\d{8}")) {
            return false;
        }

        int[] coeficientes = {2, 9, 8, 7, 6, 3, 4};
        int suma = 0;

        for (int i = 0; i < coeficientes.length; i++) {
            suma += coeficientes[i] * Character.getNumericValue(cedula.charAt(i));
        }

        int verificadorCalculado = 10 - (suma % 10);

        if (verificadorCalculado == 10) {
            verificadorCalculado = 0;
        }

        int verificadorIngresado = Character.getNumericValue(cedula.charAt(7));

        return verificadorCalculado == verificadorIngresado;
    }
}
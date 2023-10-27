package org.example.controlador;

public class ApplicationController {
    private static ApplicationController instance;

    // Otros atributos y métodos necesarios para la clase

    private ApplicationController() {
        // Constructor privado para evitar la creación de instancias externas
    }

    public static ApplicationController getInstance() {
        if (instance == null) {
            instance = new ApplicationController();
        }
        return instance;
    }

    // Otros métodos para controlar la lógica de la aplicación
}

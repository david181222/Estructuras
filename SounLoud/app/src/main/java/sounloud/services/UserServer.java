package sounloud.services;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import sounloud.model.User;

public class UserServer {
    private static final Logger logger = LogManager.getLogger(UserServer.class);
    /*private List<User> usersInServer;*/
    private static final String RUTA_ARCHIVO_USER = "logs/";
    private final Gson gson;

    public UserServer() {
        this.gson = new Gson();
        /*this.usersInServer = new ArrayList<>(); */
    }

    /*public void showUsers() {
        try {
            int i = 1;
            for (User user : this.usersInServer) {
                System.out.println(i + ". " + user.getNameUser());
                i++;
            }
        } catch (Exception e) {
            logger.error("Error al mostrar usuarios: {}", e.getMessage());
            throw e;
        }
    } */

    /*
     * public User getUser(String name) {
     * boolean condition = true;
     * int index = 0;
     * 
     * try {
     * while (condition) {
     * if (this.usersInServer.get(index).getNameUser().equals(name)) {
     * condition = false;
     * } else {
     * index++;
     * if (index >= this.usersInServer.size()) {
     * throw new IndexOutOfBoundsException("Usuario no encontrado: " + name);
     * }
     * }
     * }
     * return usersInServer.get(index);
     * } catch (Exception e) {
     * logger.error("Error al obtener usuario '{}': {}", name, e.getMessage());
     * return null;
     * }
     * }
     */

    public void addUser(User user) {
    String nombreArchivo = RUTA_ARCHIVO_USER + "user_" + user.getNameUser() + ".txt";
    File archivo = new File(nombreArchivo);

    if (archivo.exists()) {
        logger.warn("El usuario '{}' ya existe. No se guardó.", user.getNameUser());
        return;
    }

    try (FileWriter writer = new FileWriter(archivo)) {
        gson.toJson(user, writer);
        logger.info("Usuario '{}' guardado exitosamente en {}", user.getNameUser(), nombreArchivo);
    } catch (IOException e) {
        logger.error("Error al guardar el usuario: {}", e.getMessage());
    }
}


    public User getUser(String nameUser) {
        String nombreArchivo = RUTA_ARCHIVO_USER + "user_" + nameUser
                + ".txt";
        try (FileReader reader = new FileReader(nombreArchivo)) {
            return gson.fromJson(reader, User.class);
        } catch (IOException e) {
            logger.error("No se obtuvo el usuario {}", nameUser);
            e.printStackTrace();
            return null;
        }
    }
}

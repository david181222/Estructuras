package sounloud.services;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.reflect.TypeToken;

import sounloud.model.Song;

import java.lang.reflect.Type;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;


public class ReadSongs {
    private Gson gson;
    private static final String fileName = "C:\\Users\\david\\Documents\\E.Datos\\RecursosSoundLoud\\Songs.txt";
   private static List<Song> PlaylistReadSongs = new ArrayList<>();
    private static boolean fileAlreadyRead = false;
    private static final Logger logger = LogManager.getLogger(ReadSongs.class);

    public ReadSongs() { //En el constructor de la clase instanciamos el gson
        gson = new Gson();
    }

    public void leerArchivo() {
    if (fileAlreadyRead) return;

    try (FileReader fileReader = new FileReader(fileName)) {
        logger.info("Leyendo el archivo de canciones: {}", fileName);
        
        Type listType = new TypeToken<List<Song>>() {}.getType();
        PlaylistReadSongs = gson.fromJson(fileReader, listType);  // Se aplica la clave
        fileAlreadyRead = true;

        logger.info("Archivo de canciones leído exitosamente.");
    } catch (IOException e) {
        logger.error("Error al leer el archivo: {}", e.getMessage());
        throw new RuntimeException("Error al leer el archivo de canciones: " + e.getMessage());
    }
}

    public List<Song> getReadSongs() {
        return PlaylistReadSongs;
    }
}


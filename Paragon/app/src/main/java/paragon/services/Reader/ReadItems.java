package paragon.services.Reader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import paragon.model.Items.Item;

//Es una clase que lee el txt de habilidades que estan en una lista en formato JSON, por eso se importa el reflect.Type
public class ReadItems {

    private Gson gson;
    private static final String fileName = "C:\\Users\\david\\Documents\\E.Datos\\Paragon\\app\\src\\main\\resources\\Items.txt";
    private static List<Item> items = new ArrayList<>();
    private static boolean fileAlreadyRead = false;
    private static final Logger logger = LogManager.getLogger(ReadItems.class);

    public ReadItems() {
        gson = new Gson();
    }

    public void leerArchivo() {
        if (fileAlreadyRead) return;
        
        try (FileReader fileReader = new FileReader(fileName)) {
            logger.info("Leyendo el archivo de items: {}", fileName);
            
            Type listType = new TypeToken<List<Item>>() {}.getType(); //Con esto le diremos a gson que va a mapear en una lista los objetos Item
            items = gson.fromJson(fileReader, listType); //En vez de colocar Item.class, colocamos el Type definido, pues es una lista de este tipo de objeto
        fileAlreadyRead = true;
            fileAlreadyRead = true;
            
            logger.info("Archivo de items leído exitosamente.");

        } catch (IOException e) {
            logger.error("Error al leer el archivo: {}", e.getMessage());
            throw new RuntimeException("Error al leer el archivo de items: " + e.getMessage());
        }
    }

    public static List<Item> getItems() {
        return items;
    }
}
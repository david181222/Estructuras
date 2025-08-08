package paragon.services.Reader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import paragon.model.Habilities.Ability;

//Es una clase que lee el txt de habilidades que estan en una lista en formato JSON, por eso se importa el reflect.Type
public class ReadAbilities {

    private Gson gson;
    private static final String fileName = "C:\\Users\\david\\Documents\\E.Datos\\Paragon\\app\\src\\main\\resources\\Abilities.txt";
    private static List<Ability> habilidades = new ArrayList<>();
    private static boolean fileAlreadyRead = false;
    private static final Logger logger = LogManager.getLogger(ReadAbilities.class);

    public ReadAbilities() { //En el constructor de la clase instanciamos el gson
        gson = new Gson();
    }

    public void leerArchivo() {
    if (fileAlreadyRead) return;

    try (FileReader fileReader = new FileReader(fileName)) {
        logger.info("Leyendo el archivo de habilidades: {}", fileName);
        
        Type listType = new TypeToken<List<Ability>>() {}.getType(); //Con esto le diremos a gson que va a mapear en una lista los objetos Ability
        habilidades = gson.fromJson(fileReader, listType);  //En vez de colocar Ability.class, colocamos el Type definido, pues es una lista de este tipo de objeto
        fileAlreadyRead = true;

        logger.info("Archivo de habilidades leído exitosamente.");
    } catch (IOException e) {
        logger.error("Error al leer el archivo: {}", e.getMessage());
        throw new RuntimeException("Error al leer el archivo de habilidades: " + e.getMessage());
    }
}

    public static List<Ability> getHabilidades() {
        return habilidades;
    }
}


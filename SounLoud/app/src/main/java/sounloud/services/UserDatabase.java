package sounloud.services;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import sounloud.model.User;
import sounloud.model.Profile;
import sounloud.model.Playlist;
import sounloud.model.Song;

//Esta clase permite gestionar los usuarios del sistema. Elejimos un formato de JSON (vease en resources/users.txt) que es donde se van a guardar
//los usuarios con sus datos, esto para poder manejar una validación e implementar el menú del aplicativo.
public class UserDatabase {
    private static final Logger logger = LogManager.getLogger(UserDatabase.class);
    private static final String USERS_FILE_PATH = "app/src/main/resources/users.txt";
    private final Gson gson;

    public UserDatabase() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    // Leer desde el txt formato JSON los usuarios
    public List<User> loadUsers() {
        try (FileReader reader = new FileReader(USERS_FILE_PATH)) {
            User[] usersArray = gson.fromJson(reader, User[].class);
            if (usersArray == null) {
                logger.warn("No se encontraron usuarios en el archivo");
                return new ArrayList<>();
            }
            logger.info("Se cargaron {} usuarios desde el archivo", usersArray.length);
            return new ArrayList<>(Arrays.asList(usersArray));
        } catch (IOException e) {
            logger.error("Error al leer el archivo de usuarios: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    // Guardar los usuarios en el archivo JSON
    public void saveUsers(List<User> users) {
        try (FileWriter writer = new FileWriter(USERS_FILE_PATH)) {
            gson.toJson(users, writer);
            logger.info("Se guardaron {} usuarios en el archivo", users.size());
        } catch (IOException e) {
            logger.error("Error al guardar el archivo de usuarios: {}", e.getMessage());
        }
    }

    // Buscar un usuario por nombre
    public User searchUserByName(List<User> users, String userName) {
        for (User user : users) {
            if (user.getNameUser().equalsIgnoreCase(userName)) {
                return user;
            }
        }
        return null;
    }

    // Lo siguiente se le pidió a la IA (Se tuvo que recurrir cuando se quizo
    // implementar el sistema de menú), anteriormente la idea fue trabajar con
    // listas,
    // sin embargo, para hacer las validaciones en el sistema de menú se requerían
    // estos métodos adicionales para hacer las validaciones.

    // Remover una canción de una playlist
    public boolean removeSongFromPlaylist(Playlist playlist, Song song) {
        for (int i = 0; i < playlist.getSongs().size(); i++) {
            Song existingSong = playlist.getSongs().get(i);

            // Comparar por ID solo si ambos tienen ID válido
            boolean sameId = false;
            if (existingSong.getIdSong() != null && song.getIdSong() != null) {
                sameId = existingSong.getIdSong().equals(song.getIdSong());
            }

            // Comparar por nombre y autor como alternativa
            boolean sameNameAndAuthor = existingSong.getName().equalsIgnoreCase(song.getName()) &&
                    existingSong.getAuthor().equalsIgnoreCase(song.getAuthor());

            if (sameId || sameNameAndAuthor) {
                playlist.getSongs().remove(i);
                logger.info("Canción '{}' removida de la playlist '{}'", song.getName(), playlist.getNamePlaylist());
                return true;
            }
        }

        logger.warn("No se pudo remover la canción '{}' de la playlist '{}'", song.getName(),
                playlist.getNamePlaylist());
        return false;
    }



    // Buscar un perfil por nombre dentro de un usuario
    public Profile findProfileByName(User user, String profileName) {
        for (Profile profile : user.getUserProfiles()) {
            if (profile.getName().equalsIgnoreCase(profileName)) {
                return profile;
            }
        }
        return null;
    }

    // Buscar una playlist por nombre dentro de un perfil
    public Playlist findPlaylistByName(Profile profile, String playlistName) {
        for (Playlist playlist : profile.getPlaylist()) {
            if (playlist.getNamePlaylist().equalsIgnoreCase(playlistName)) {
                return playlist;
            }
        }
        return null;
    }

    // Agregar una nueva canción a una playlist validando que no existe
    public boolean addSongToPlaylist(Playlist playlist, Song song) {
        // Verificar si la canción ya existe (por ID o combinación de nombre y autor)
        for (Song existingSong : playlist.getSongs()) {
            // Comparar por ID solo si ambos tienen ID válido
            boolean sameId = false;
            if (existingSong.getIdSong() != null && song.getIdSong() != null) {
                sameId = existingSong.getIdSong().equals(song.getIdSong());
            }

            // Comparar por nombre y autor (siempre)
            boolean sameNameAndAuthor = existingSong.getName().equalsIgnoreCase(song.getName()) &&
                    existingSong.getAuthor().equalsIgnoreCase(song.getAuthor());

            if (sameId || sameNameAndAuthor) {
                // Canción ya existe
                logger.warn("La canción '{}' ya existe en la playlist '{}'", song.getName(),
                        playlist.getNamePlaylist());
                return false;
            }
        }

        // Si llegamos aquí, la canción no existe
        playlist.addSong(song);
        logger.info("Canción '{}' agregada a la playlist '{}'", song.getName(), playlist.getNamePlaylist());
        return true;
    }

}

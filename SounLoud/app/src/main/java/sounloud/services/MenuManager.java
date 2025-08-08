package sounloud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sounloud.model.Playlist;
import sounloud.model.Profile;
import sounloud.model.Queueplay;
import sounloud.model.Song;
import sounloud.model.User;
import sounloud.services.ReadSongs;

//Esta clase fue implementada por la IA, se le pidió que creara un sistema de menús donde el usuario pudiese navegar con la siguiente estructura:
//Cada usuario tiene la opción de crear y elegir un perfil; luego, si elije un perfil existente,
//podrá crear para él una playlist y seleccionar una existente; al seleccionar una playlist puede añadir canciones (ya sea manual o cargando el Songs.txt
//a modo de "base de datos" y seleccione una de ahí), esto implementando nuestra clase ReadSongs. Además, puede seleccionar añadir playlists
//a la cola, escuchar una aleatoria o elegir una canción especifica, esto es implementando la lógica creada en la clase Queueplay.

public class MenuManager {
    private static final Logger logger = LogManager.getLogger(MenuManager.class);
    private final Scanner scanner;
    private final UserDatabase userManager;
    private final ReadSongs songCatalog;
    private List<User> users;

    public MenuManager() {
        this.scanner = new Scanner(System.in);
        this.userManager = new UserDatabase();
        this.songCatalog = new ReadSongs();
        this.users = userManager.loadUsers();
        
        // Cargar el catálogo de canciones al inicializar
        try {
            songCatalog.leerArchivo();
            logger.info("Catálogo de canciones cargado exitosamente");
        } catch (Exception e) {
            logger.warn("No se pudo cargar el catálogo de canciones: {}", e.getMessage());
        }
    }

    /**
     * Método principal que inicia el sistema de menús
     */
    public void start() {
        System.out.println("=== Bienvenido a SounLoud ===");
        
        while (true) {
            try {
                User currentUser = loginUser();
                if (currentUser != null) {
                    userMainMenu(currentUser);
                } else {
                    System.out.println("¿Desea intentar nuevamente? (s/n):");
                    String retry = scanner.nextLine().trim().toLowerCase();
                    if (!retry.equals("s") && !retry.equals("si")) {
                        break;
                    }
                }
            } catch (Exception e) {
                logger.error("Error en el menú principal: {}", e.getMessage());
                System.out.println("Error inesperado. Intente nuevamente.");
            }
        }
        
        System.out.println("¡Gracias por usar SounLoud!");
    }

    /**
     * Maneja el login del usuario
     */
    private User loginUser() {
        System.out.println("\n=== Iniciar Sesión ===");
        System.out.println("Usuarios disponibles:");
        
        if (users.isEmpty()) {
            System.out.println("No hay usuarios registrados en el sistema.");
            return null;
        }
        
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i).getNameUser());
        }
        
        System.out.print("Ingrese el nombre del usuario: ");
        String userName = scanner.nextLine().trim();
        
        User foundUser = userManager.searchUserByName(users, userName);
        if (foundUser != null) {
            System.out.println("¡Bienvenido, " + userName + "!");
            return foundUser;
        } else {
            System.out.println("Usuario no encontrado.");
            return null;
        }
    }

    /**
     * Menú principal del usuario después del login
     */
    private void userMainMenu(User user) {
        while (true) {
            System.out.println("\n=== Menú Principal - " + user.getNameUser() + " ===");
            System.out.println("1. Gestionar Perfiles");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                int option = Integer.parseInt(scanner.nextLine().trim());
                
                switch (option) {
                    case 1:
                        profileMenu(user);
                        break;
                    case 2:
                        System.out.println("Cerrando sesión...");
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
            }
        }
    }

    /**
     * Menú de gestión de perfiles
     */
    private void profileMenu(User user) {
        while (true) {
            System.out.println("\n=== Gestión de Perfiles ===");
            System.out.println("1. Crear nuevo perfil");
            System.out.println("2. Seleccionar perfil existente");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            try {
                int option = Integer.parseInt(scanner.nextLine().trim());
                
                switch (option) {
                    case 1:
                        createProfile(user);
                        break;
                    case 2:
                        selectProfile(user);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
            }
        }
    }

    /**
     * Crear un nuevo perfil
     */
    private void createProfile(User user) {
        System.out.println("\n=== Crear Nuevo Perfil ===");
        System.out.print("Nombre del perfil: ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        
        // Verificar si ya existe un perfil con ese nombre
        Profile existingProfile = userManager.findProfileByName(user, name);
        if (existingProfile != null) {
            System.out.println("Ya existe un perfil con ese nombre.");
            return;
        }
        
        System.out.print("Descripción del perfil: ");
        String description = scanner.nextLine().trim();
        
        if (description.isEmpty()) {
            System.out.println("La descripción no puede estar vacía.");
            return;
        }
        
        System.out.print("Género favorito: ");
        String favoriteGenre = scanner.nextLine().trim();
        
        if (favoriteGenre.isEmpty()) {
            System.out.println("El género favorito no puede estar vacío.");
            return;
        }
        
        try {
            Profile newProfile = new Profile(name, description, favoriteGenre);
            user.setProfile(newProfile);
            saveUsers();
            System.out.println("Perfil creado exitosamente.");
        } catch (Exception e) {
            logger.error("Error al crear perfil: {}", e.getMessage());
            System.out.println("Error al crear el perfil.");
        }
    }

    /**
     * Seleccionar un perfil existente
     */
    private void selectProfile(User user) {
        if (user.getUserProfiles().isEmpty()) {
            System.out.println("No tiene perfiles creados. Cree uno primero.");
            return;
        }
        
        System.out.println("\n=== Seleccionar Perfil ===");
        System.out.println("Perfiles disponibles:");
        
        for (int i = 0; i < user.getUserProfiles().size(); i++) {
            Profile profile = user.getUserProfiles().get(i);
            System.out.println((i + 1) + ". " + profile.getName() + " - " + profile.getDescription());
        }
        
        System.out.print("Seleccione un perfil (número): ");
        
        try {
            int profileIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            
            if (profileIndex >= 0 && profileIndex < user.getUserProfiles().size()) {
                Profile selectedProfile = user.getUserProfiles().get(profileIndex);
                playlistMenu(user, selectedProfile);
            } else {
                System.out.println("Número de perfil inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un número válido.");
        }
    }

    /**
     * Menú de gestión de playlists
     */
    private void playlistMenu(User user, Profile profile) {
        while (true) {
            System.out.println("\n=== Gestión de Playlists - " + profile.getName() + " ===");
            System.out.println("1. Crear nueva playlist");
            System.out.println("2. Seleccionar playlist existente");
            System.out.println("3. Volver al menú de perfiles");
            System.out.print("Seleccione una opción: ");
            
            try {
                int option = Integer.parseInt(scanner.nextLine().trim());
                
                switch (option) {
                    case 1:
                        createPlaylist(user, profile);
                        break;
                    case 2:
                        selectPlaylist(user, profile);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
            }
        }
    }

    /**
     * Crear una nueva playlist
     */
    private void createPlaylist(User user, Profile profile) {
        System.out.println("\n=== Crear Nueva Playlist ===");
        System.out.print("Nombre de la playlist: ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        
        // Verificar si ya existe una playlist con ese nombre
        Playlist existingPlaylist = userManager.findPlaylistByName(profile, name);
        if (existingPlaylist != null) {
            System.out.println("Ya existe una playlist con ese nombre.");
            return;
        }
        
        System.out.print("Descripción de la playlist: ");
        String description = scanner.nextLine().trim();
        
        if (description.isEmpty()) {
            System.out.println("La descripción no puede estar vacía.");
            return;
        }
        
        try {
            List<Song> emptySongList = new ArrayList<>();
            Playlist newPlaylist = new Playlist(name, description, emptySongList);
            profile.setPlaylist(newPlaylist);
            saveUsers();
            System.out.println("Playlist creada exitosamente.");
        } catch (Exception e) {
            logger.error("Error al crear playlist: {}", e.getMessage());
            System.out.println("Error al crear la playlist.");
        }
    }

    /**
     * Seleccionar una playlist existente
     */
    private void selectPlaylist(User user, Profile profile) {
        if (profile.getPlaylist().isEmpty()) {
            System.out.println("No tiene playlists creadas. Cree una primero.");
            return;
        }
        
        System.out.println("\n=== Seleccionar Playlist ===");
        System.out.println("Playlists disponibles:");
        
        for (int i = 0; i < profile.getPlaylist().size(); i++) {
            Playlist playlist = profile.getPlaylist().get(i);
            System.out.println((i + 1) + ". " + playlist.getNamePlaylist() + " - " + 
                             playlist.getDescriptionPlaylist() + " (" + playlist.getSongs().size() + " canciones)");
        }
        
        System.out.print("Seleccione una playlist (número): ");
        
        try {
            int playlistIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            
            if (playlistIndex >= 0 && playlistIndex < profile.getPlaylist().size()) {
                Playlist selectedPlaylist = profile.getPlaylist().get(playlistIndex);
                songMenu(user, profile, selectedPlaylist);
            } else {
                System.out.println("Número de playlist inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un número válido.");
        }
    }

    /**
     * Menú de gestión de canciones y reproducción
     */
    private void songMenu(User user, Profile profile, Playlist playlist) {
        while (true) {
            System.out.println("\n=== Gestión de Canciones - " + playlist.getNamePlaylist() + " ===");
            System.out.println("1. Añadir canción");
            System.out.println("2. Remover canción");
            System.out.println("3. Ver canciones de la playlist");
            System.out.println("4. Añadir playlist a cola de reproducción");
            System.out.println("5. Escuchar canción aleatoria");
            System.out.println("6. Navegar a canción específica");
            System.out.println("7. Volver al menú de playlists");
            System.out.print("Seleccione una opción: ");
            
            try {
                int option = Integer.parseInt(scanner.nextLine().trim());
                
                switch (option) {
                    case 1:
                        addSongToPlaylist(user, playlist);
                        break;
                    case 2:
                        removeSongFromPlaylist(user, playlist);
                        break;
                    case 3:
                        showSongsInPlaylist(playlist);
                        break;
                    case 4:
                        addPlaylistToQueue(profile, playlist);
                        break;
                    case 5:
                        playRandomSong(profile);
                        break;
                    case 6:
                        navigateToSpecificSong(profile);
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
            }
        }
    }

    /**
     * Añadir una nueva canción a la playlist
     */
    private void addSongToPlaylist(User user, Playlist playlist) {
        System.out.println("\n=== Añadir Canción ===");
        System.out.println("¿Cómo desea añadir la canción?");
        System.out.println("1. Crear una canción nueva");
        System.out.println("2. Seleccionar del catálogo de canciones");
        System.out.print("Seleccione una opción: ");
        
        try {
            int option = Integer.parseInt(scanner.nextLine().trim());
            
            switch (option) {
                case 1:
                    createNewSong(user, playlist);
                    break;
                case 2:
                    selectFromCatalog(user, playlist);
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un número válido.");
        }
    }

    /**
     * Crear una canción nueva
     */
    private void createNewSong(User user, Playlist playlist) {
        System.out.println("\n=== Crear Nueva Canción ===");
        System.out.print("Nombre de la canción: ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        
        System.out.print("Autor/Artista: ");
        String author = scanner.nextLine().trim();
        
        if (author.isEmpty()) {
            System.out.println("El autor no puede estar vacío.");
            return;
        }
        
        System.out.print("Duración en minutos (ej: 3.45): ");
        double duration;
        try {
            duration = Double.parseDouble(scanner.nextLine().trim());
            if (duration <= 0) {
                System.out.println("La duración debe ser mayor a 0.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un número válido para la duración.");
            return;
        }
        
        System.out.print("URL de YouTube: ");
        String url = scanner.nextLine().trim();
        
        if (url.isEmpty()) {
            System.out.println("La URL no puede estar vacía.");
            return;
        }
        
        try {
            Song newSong = new Song(name, author, duration, url);
            boolean added = userManager.addSongToPlaylist(playlist, newSong);
            
            if (added) {
                saveUsers();
                System.out.println("Canción creada y añadida exitosamente.");
            } else {
                System.out.println("La canción ya existe en la playlist.");
            }
        } catch (Exception e) {
            logger.error("Error al crear canción: {}", e.getMessage());
            System.out.println("Error al crear la canción: " + e.getMessage());
        }
    }

    /**
     * Seleccionar una canción del catálogo
     */
    private void selectFromCatalog(User user, Playlist playlist) {
        System.out.println("\n=== Seleccionar del Catálogo ===");
        
        List<Song> catalogSongs = songCatalog.getReadSongs();
        
        if (catalogSongs == null || catalogSongs.isEmpty()) {
            System.out.println("El catálogo de canciones está vacío o no se pudo cargar.");
            System.out.println("¿Desea crear una canción nueva? (s/n):");
            String createNew = scanner.nextLine().trim().toLowerCase();
            if (createNew.equals("s") || createNew.equals("si")) {
                createNewSong(user, playlist);
            }
            return;
        }
        
        System.out.println("Catálogo de canciones disponibles:");
        System.out.println("(Total: " + catalogSongs.size() + " canciones)");
        System.out.println();
        
        for (int i = 0; i < catalogSongs.size(); i++) {
            Song song = catalogSongs.get(i);
            System.out.println((i + 1) + ". " + song.getName() + " - " + song.getAuthor() + 
                             " (" + song.getDurationMinutes() + " min)");
        }
        
        System.out.print("\nSeleccione una canción por su número (0 para cancelar): ");
        
        try {
            int songIndex = Integer.parseInt(scanner.nextLine().trim());
            
            if (songIndex == 0) {
                System.out.println("Operación cancelada.");
                return;
            }
            
            if (songIndex > 0 && songIndex <= catalogSongs.size()) {
                Song selectedSong = catalogSongs.get(songIndex - 1);
                
                // Mostrar información de la canción seleccionada
                System.out.println("\nCanción seleccionada:");
                System.out.println("Nombre: " + selectedSong.getName());
                System.out.println("Autor: " + selectedSong.getAuthor());
                System.out.println("Duración: " + selectedSong.getDurationMinutes() + " minutos");
                System.out.println("URL: " + selectedSong.getUrlYoutube());
                
                System.out.print("¿Confirma añadir esta canción a la playlist? (s/n): ");
                String confirm = scanner.nextLine().trim().toLowerCase();
                
                if (confirm.equals("s") || confirm.equals("si")) {
                    try {
                        boolean added = userManager.addSongToPlaylist(playlist, selectedSong);
                        
                        if (added) {
                            saveUsers();
                            System.out.println("Canción añadida exitosamente desde el catálogo.");
                        } else {
                            System.out.println("La canción ya existe en la playlist.");
                        }
                    } catch (Exception e) {
                        logger.error("Error al añadir canción del catálogo: {}", e.getMessage(), e);
                        System.out.println("Error al añadir la canción: " + e.getMessage());
                    }
                } else {
                    System.out.println("Operación cancelada.");
                }
            } else {
                System.out.println("Número de canción inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un número válido.");
        }
    }

    /**
     * Remover una canción de la playlist
     */
    private void removeSongFromPlaylist(User user, Playlist playlist) {
        if (playlist.getSongs().isEmpty()) {
            System.out.println("La playlist está vacía.");
            return;
        }
        
        System.out.println("\n=== Remover Canción ===");
        System.out.println("Canciones en la playlist:");
        
        for (int i = 0; i < playlist.getSongs().size(); i++) {
            Song song = playlist.getSongs().get(i);
            System.out.println((i + 1) + ". " + song.getName() + " - " + song.getAuthor());
        }
        
        System.out.print("Seleccione la canción a remover (número): ");
        
        try {
            int songIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            
            if (songIndex >= 0 && songIndex < playlist.getSongs().size()) {
                Song songToRemove = playlist.getSongs().get(songIndex);
                boolean removed = userManager.removeSongFromPlaylist(playlist, songToRemove);
                
                if (removed) {
                    saveUsers();
                    System.out.println("Canción removida exitosamente.");
                } else {
                    System.out.println("No se pudo remover la canción.");
                }
            } else {
                System.out.println("Número de canción inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un número válido.");
        }
    }

    /**
     * Mostrar las canciones de una playlist
     */
    private void showSongsInPlaylist(Playlist playlist) {
        System.out.println("\n=== Canciones en " + playlist.getNamePlaylist() + " ===");
        
        if (playlist.getSongs().isEmpty()) {
            System.out.println("La playlist está vacía.");
            return;
        }
        
        for (int i = 0; i < playlist.getSongs().size(); i++) {
            Song song = playlist.getSongs().get(i);
            System.out.println((i + 1) + ". " + song.getName() + " - " + song.getAuthor() + 
                             " (" + song.getDurationMinutes() + " min)");
        }
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Añadir playlist a la cola de reproducción
     */
    private void addPlaylistToQueue(Profile profile, Playlist playlist) {
        if (playlist.getSongs().isEmpty()) {
            System.out.println("La playlist está vacía. Añada canciones primero.");
            return;
        }
        
        System.out.println("\n=== Añadir a Cola de Reproducción ===");
        Queueplay queueplay = new Queueplay();
        queueplay.addPlaylist(playlist);
        
        System.out.println("¿Desea añadir más playlists a la cola? (s/n):");
        String addMore = scanner.nextLine().trim().toLowerCase();
        
        if (addMore.equals("s") || addMore.equals("si")) {
            addMultiplePlaylistsToQueue(profile, queueplay);
        }
        
        System.out.println("¿Desea iniciar la reproducción? (s/n):");
        String startPlaying = scanner.nextLine().trim().toLowerCase();
        
        if (startPlaying.equals("s") || startPlaying.equals("si")) {
            System.out.println("Iniciando reproducción de la cola...");
            queueplay.startListening();
        }
    }

    /**
     * Añadir múltiples playlists a la cola
     */
    private void addMultiplePlaylistsToQueue(Profile profile, Queueplay queueplay) {
        while (true) {
            System.out.println("\nPlaylists disponibles:");
            
            for (int i = 0; i < profile.getPlaylist().size(); i++) {
                Playlist pl = profile.getPlaylist().get(i);
                System.out.println((i + 1) + ". " + pl.getNamePlaylist() + " (" + pl.getSongs().size() + " canciones)");
            }
            
            System.out.print("Seleccione una playlist para añadir (0 para terminar): ");
            
            try {
                int playlistIndex = Integer.parseInt(scanner.nextLine().trim());
                
                if (playlistIndex == 0) {
                    break;
                } else if (playlistIndex > 0 && playlistIndex <= profile.getPlaylist().size()) {
                    Playlist selectedPlaylist = profile.getPlaylist().get(playlistIndex - 1);
                    if (!selectedPlaylist.getSongs().isEmpty()) {
                        queueplay.addPlaylist(selectedPlaylist);
                        System.out.println("Playlist añadida a la cola.");
                    } else {
                        System.out.println("La playlist está vacía y no se puede añadir.");
                    }
                } else {
                    System.out.println("Número de playlist inválido.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
            }
        }
    }

    /**
     * Reproducir una canción aleatoria
     */
    private void playRandomSong(Profile profile) {
        // Recopilar todas las playlists no vacías
        List<Playlist> nonEmptyPlaylists = new ArrayList<>();
        for (Playlist pl : profile.getPlaylist()) {
            if (!pl.getSongs().isEmpty()) {
                nonEmptyPlaylists.add(pl);
            }
        }
        
        if (nonEmptyPlaylists.isEmpty()) {
            System.out.println("No hay playlists con canciones para reproducir.");
            return;
        }
        
        System.out.println("\n=== Reproducción Aleatoria ===");
        Queueplay queueplay = new Queueplay();
        
        // Añadir todas las playlists no vacías a la cola
        for (Playlist pl : nonEmptyPlaylists) {
            queueplay.addPlaylist(pl);
        }
        
        System.out.println("Reproduciendo canción aleatoria...");
        queueplay.startListeningRandom();
    }

    /**
     * Navegar a una canción específica
     */
    private void navigateToSpecificSong(Profile profile) {
        if (profile.getPlaylist().isEmpty()) {
            System.out.println("No tiene playlists creadas.");
            return;
        }
        
        // Mostrar todas las playlists
        System.out.println("\n=== Navegar a Canción Específica ===");
        System.out.println("Seleccione una playlist:");
        
        for (int i = 0; i < profile.getPlaylist().size(); i++) {
            Playlist pl = profile.getPlaylist().get(i);
            System.out.println((i + 1) + ". " + pl.getNamePlaylist() + " (" + pl.getSongs().size() + " canciones)");
        }
        
        System.out.print("Seleccione una playlist (número): ");
        
        try {
            int playlistIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            
            if (playlistIndex >= 0 && playlistIndex < profile.getPlaylist().size()) {
                Playlist selectedPlaylist = profile.getPlaylist().get(playlistIndex);
                
                if (selectedPlaylist.getSongs().isEmpty()) {
                    System.out.println("La playlist seleccionada está vacía.");
                    return;
                }
                
                // Mostrar canciones de la playlist seleccionada
                System.out.println("\nCanciones en " + selectedPlaylist.getNamePlaylist() + ":");
                
                for (int i = 0; i < selectedPlaylist.getSongs().size(); i++) {
                    Song song = selectedPlaylist.getSongs().get(i);
                    System.out.println((i + 1) + ". " + song.getName() + " - " + song.getAuthor());
                }
                
                System.out.print("Seleccione una canción (número): ");
                
                try {
                    int songIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
                    
                    if (songIndex >= 0 && songIndex < selectedPlaylist.getSongs().size()) {
                        Song selectedSong = selectedPlaylist.getSongs().get(songIndex);
                        
                        Queueplay queueplay = new Queueplay();
                        System.out.println("Reproduciendo canción específica...");
                        queueplay.startListeningSelectedSong(selectedSong);
                    } else {
                        System.out.println("Número de canción inválido.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Por favor ingrese un número válido.");
                }
            } else {
                System.out.println("Número de playlist inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un número válido.");
        }
    }

    /**
     * Guarda la lista de usuarios actualizada en el archivo JSON
     */
    private void saveUsers() {
        userManager.saveUsers(users);
        logger.info("Usuarios guardados en el archivo JSON");
    }
}

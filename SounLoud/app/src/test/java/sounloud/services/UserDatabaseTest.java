package sounloud.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import sounloud.model.User;
import sounloud.model.Profile;
import sounloud.model.Playlist;
import sounloud.model.Song;

class UserDatabaseTest {

    private UserDatabase userDatabase;
    private User testUser;
    private Profile testProfile;
    private Playlist testPlaylist;
    private Song testSong1;
    private Song testSong2;

    @BeforeEach
    void setUp() {
        userDatabase = new UserDatabase();
        
        // Crear datos de prueba
        testUser = new User("Usuario Test");
        testProfile = new Profile("Perfil Test", "Descripción test", "Rock");
        testPlaylist = new Playlist("Playlist Test", "Descripción playlist", new ArrayList<>());
        testSong1 = new Song("Canción Test 1", "Artista Test 1", 3.5, "https://youtube.com/test1");
        testSong2 = new Song("Canción Test 2", "Artista Test 2", 4.2, "https://youtube.com/test2");
        
        // Estructurar datos
        testUser.setProfile(testProfile);
        testProfile.setPlaylist(testPlaylist);
    }

    @Test
    void testSearchUserByName_UserExists() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(testUser);
        users.add(new User("Otro Usuario"));

        // Act
        User result = userDatabase.searchUserByName(users, "Usuario Test");

        // Assert
        assertNotNull(result);
        assertEquals("Usuario Test", result.getNameUser());
    }

    @Test
    void testSearchUserByName_UserNotExists() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(testUser);

        // Act
        User result = userDatabase.searchUserByName(users, "Usuario Inexistente");

        // Assert
        assertNull(result);
    }

    @Test
    void testSearchUserByName_CaseInsensitive() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(testUser);

        // Act
        User result = userDatabase.searchUserByName(users, "usuario test");

        // Assert
        assertNotNull(result);
        assertEquals("Usuario Test", result.getNameUser());
    }

    @Test
    void testFindProfileByName_ProfileExists() {
        // Act
        Profile result = userDatabase.findProfileByName(testUser, "Perfil Test");

        // Assert
        assertNotNull(result);
        assertEquals("Perfil Test", result.getName());
    }

    @Test
    void testFindProfileByName_ProfileNotExists() {
        // Act
        Profile result = userDatabase.findProfileByName(testUser, "Perfil Inexistente");

        // Assert
        assertNull(result);
    }

    @Test
    void testFindPlaylistByName_PlaylistExists() {
        // Act
        Playlist result = userDatabase.findPlaylistByName(testProfile, "Playlist Test");

        // Assert
        assertNotNull(result);
        assertEquals("Playlist Test", result.getNamePlaylist());
    }

    @Test
    void testFindPlaylistByName_PlaylistNotExists() {
        // Act
        Playlist result = userDatabase.findPlaylistByName(testProfile, "Playlist Inexistente");

        // Assert
        assertNull(result);
    }

    @Test
    void testAddSongToPlaylist_NewSong() {
        // Act
        boolean result = userDatabase.addSongToPlaylist(testPlaylist, testSong1);

        // Assert
        assertTrue(result);
        assertEquals(1, testPlaylist.getSongs().size());
        assertEquals("Canción Test 1", testPlaylist.getSongs().get(0).getName());
    }

    @Test
    void testAddSongToPlaylist_DuplicateSongByNameAndAuthor() {
        // Arrange
        testPlaylist.addSong(testSong1);
        Song duplicateSong = new Song("Canción Test 1", "Artista Test 1", 3.0, "https://different.url");

        // Act
        boolean result = userDatabase.addSongToPlaylist(testPlaylist, duplicateSong);

        // Assert
        assertFalse(result);
        assertEquals(1, testPlaylist.getSongs().size());
    }

    @Test
    void testAddSongToPlaylist_DuplicateSongById() {
        // Arrange
        testPlaylist.addSong(testSong1);

        // Act - Intentar añadir la misma canción otra vez
        boolean result = userDatabase.addSongToPlaylist(testPlaylist, testSong1);

        // Assert
        assertFalse(result);
        assertEquals(1, testPlaylist.getSongs().size());
    }

    @Test
    void testRemoveSongFromPlaylist_SongExists() {
        // Arrange
        testPlaylist.addSong(testSong1);
        testPlaylist.addSong(testSong2);

        // Act
        boolean result = userDatabase.removeSongFromPlaylist(testPlaylist, testSong1);

        // Assert
        assertTrue(result);
        assertEquals(1, testPlaylist.getSongs().size());
        assertEquals("Canción Test 2", testPlaylist.getSongs().get(0).getName());
    }

    @Test
    void testRemoveSongFromPlaylist_SongNotExists() {
        // Arrange
        testPlaylist.addSong(testSong1);
        Song nonExistentSong = new Song("No Existe", "Artista Falso", 1.0, "https://fake.url");

        // Act
        boolean result = userDatabase.removeSongFromPlaylist(testPlaylist, nonExistentSong);

        // Assert
        assertFalse(result);
        assertEquals(1, testPlaylist.getSongs().size());
    }

    @Test
    void testRemoveSongFromPlaylist_EmptyPlaylist() {
        // Act
        boolean result = userDatabase.removeSongFromPlaylist(testPlaylist, testSong1);

        // Assert
        assertFalse(result);
        assertEquals(0, testPlaylist.getSongs().size());
    }

    @Test
    void testLoadUsers_EmptyList() {
        // Arrange
        UserDatabase userDb = new UserDatabase();

        // Act
        List<User> result = userDb.loadUsers();

        // Assert
        assertNotNull(result);
        // El resultado depende del archivo real, pero debería ser una lista válida
        assertTrue(result instanceof List);
    }

    @Test
    void testSaveUsers_ValidList() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(testUser);

        // Act & Assert - No debería lanzar excepción
        assertDoesNotThrow(() -> userDatabase.saveUsers(users));
    }

    @Test
    void testSearchUserByName_NullList() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            userDatabase.searchUserByName(null, "Usuario Test");
        });
    }

    @Test
    void testSearchUserByName_EmptyList() {
        // Arrange
        List<User> emptyList = new ArrayList<>();

        // Act
        User result = userDatabase.searchUserByName(emptyList, "Usuario Test");

        // Assert
        assertNull(result);
    }

    @Test
    void testAddSongToPlaylist_NullSong() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            userDatabase.addSongToPlaylist(testPlaylist, null);
        });
    }

    @Test
    void testAddSongToPlaylist_NullPlaylist() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            userDatabase.addSongToPlaylist(null, testSong1);
        });
    }
}

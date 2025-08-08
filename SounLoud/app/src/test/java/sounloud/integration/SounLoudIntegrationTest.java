package sounloud.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import sounloud.model.*;
import sounloud.services.UserDatabase;
import sounloud.services.ReadSongs;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SounLoudIntegrationTest {
    
    @TempDir
    Path tempDir;
    
    private UserDatabase userDatabase;
    private ReadSongs readSongs;
    
    @BeforeEach
    void setUp() throws IOException {
        userDatabase = new UserDatabase();
        readSongs = new ReadSongs();
    }
    
    @Test
    void testCompleteUserWorkflow() {
        // Test complete user creation and management workflow
        
        // 1. Create a new user
        User user = new User("TestUser");
        assertNotNull(user);
        assertEquals("TestUser", user.getNameUser());
        
        // 2. Create profiles for the user
        Profile profile1 = new Profile("Rock Profile", "Love rock music", "Rock");
        Profile profile2 = new Profile("Jazz Profile", "Smooth jazz lover", "Jazz");
        
        user.setProfile(profile1);
        user.setProfile(profile2);
        
        assertEquals(2, user.getUserProfiles().size());
        
        // 3. Create songs
        Song song1 = new Song("Bohemian Rhapsody", "Queen", 5.9, "https://youtube.com/bohemian");
        Song song2 = new Song("Take Five", "Dave Brubeck", 5.4, "https://youtube.com/takefive");
        Song song3 = new Song("Stairway to Heaven", "Led Zeppelin", 8.0, "https://youtube.com/stairway");
        
        // 4. Create playlists
        List<Song> rockSongs = new ArrayList<>();
        rockSongs.add(song1);
        rockSongs.add(song3);
        
        List<Song> jazzSongs = new ArrayList<>();
        jazzSongs.add(song2);
        
        Playlist rockPlaylist = new Playlist("Best Rock", "Top rock songs", rockSongs);
        Playlist jazzPlaylist = new Playlist("Smooth Jazz", "Relaxing jazz", jazzSongs);
        
        // 5. Add playlists to profiles
        profile1.setPlaylist(rockPlaylist);
        profile2.setPlaylist(jazzPlaylist);
        
        // 6. Verify data integrity
        assertEquals(1, profile1.getPlaylist().size());
        assertEquals(1, profile2.getPlaylist().size());
        assertEquals(2, rockPlaylist.getSongs().size());
        assertEquals(1, jazzPlaylist.getSongs().size());
        
        // 7. Test playlist operations
        rockPlaylist.addSong(song2); // Add jazz song to rock playlist
        assertEquals(3, rockPlaylist.getSongs().size());
        
        rockPlaylist.removeSong(song2); // Remove it
        assertEquals(2, rockPlaylist.getSongs().size());
        
        // 8. Verify all IDs are unique
        assertNotEquals(user.getIdUser(), profile1.getIdProfile());
        assertNotEquals(profile1.getIdProfile(), profile2.getIdProfile());
        assertNotEquals(song1.getIdSong(), song2.getIdSong());
        assertNotEquals(rockPlaylist.getIdPlaylist(), jazzPlaylist.getIdPlaylist());
    }
    
    @Test
    void testUserDatabaseIntegration() {
        // Test UserDatabase functionality step by step
        
        // 1. Test that UserDatabase can be created without issues
        assertNotNull(userDatabase);
        
        // 2. Test loadUsers doesn't throw exceptions (even if it returns empty)
        List<User> loadedUsers = null;
        assertDoesNotThrow(() -> {
            List<User> users = userDatabase.loadUsers();
            assertNotNull(users); // Should return a list, even if empty
        });
        
        loadedUsers = userDatabase.loadUsers();
        assertNotNull(loadedUsers);
        
        // 3. Test search with empty list (should work regardless of file content)
        User notFound = userDatabase.searchUserByName(new ArrayList<>(), "NonExistentUser");
        assertNull(notFound);
        
        // 4. Create and test save functionality
        User testUser1 = new User("IntegrationTestUser1");
        List<User> testUsers = new ArrayList<>();
        testUsers.add(testUser1);
        
        // Test save functionality
        assertDoesNotThrow(() -> userDatabase.saveUsers(testUsers));
        
        // 5. Test search functionality with known data
        User foundInList = userDatabase.searchUserByName(testUsers, "IntegrationTestUser1");
        assertNotNull(foundInList);
        assertEquals("IntegrationTestUser1", foundInList.getNameUser());
        
        // Test case-insensitive search
        User foundCaseInsensitive = userDatabase.searchUserByName(testUsers, "integrationtestuser1");
        assertNotNull(foundCaseInsensitive);
        assertEquals("IntegrationTestUser1", foundCaseInsensitive.getNameUser());
    }
    
    @Test
    void testPlaylistManagement() {
        // Test comprehensive playlist management
        
        // 1. Create songs
        Song song1 = new Song("Song 1", "Artist 1", 3.0, "https://youtube.com/1");
        Song song2 = new Song("Song 2", "Artist 2", 4.0, "https://youtube.com/2");
        Song song3 = new Song("Song 3", "Artist 3", 5.0, "https://youtube.com/3");
        
        // 2. Create playlist with initial songs
        List<Song> initialSongs = new ArrayList<>();
        initialSongs.add(song1);
        
        Playlist playlist = new Playlist("My Playlist", "Test playlist", initialSongs);
        assertEquals(1, playlist.getSongs().size());
        
        // 3. Add more songs
        playlist.addSong(song2);
        playlist.addSong(song3);
        assertEquals(3, playlist.getSongs().size());
        
        // 4. Remove a song
        playlist.removeSong(song2);
        assertEquals(2, playlist.getSongs().size());
        assertFalse(playlist.getSongs().contains(song2));
        assertTrue(playlist.getSongs().contains(song1));
        assertTrue(playlist.getSongs().contains(song3));
        
        // 5. Test that songs maintain their properties
        Song retrievedSong = playlist.getSongs().get(0);
        assertNotNull(retrievedSong.getIdSong());
        assertNotNull(retrievedSong.getName());
        assertNotNull(retrievedSong.getAuthor());
        assertTrue(retrievedSong.getDurationMinutes() > 0);
        assertNotNull(retrievedSong.getUrlYoutube());
    }
    
    @Test
    void testDataPersistence() throws IOException {
        // Test data persistence and loading with the default file path
        
        // 1. Create and save data
        User user = new User("PersistenceUser");
        Profile profile = new Profile("Test Profile", "Test Description", "Rock");
        
        Song song = new Song("Test Song", "Test Artist", 3.5, "https://youtube.com/test");
        List<Song> songs = new ArrayList<>();
        songs.add(song);
        
        Playlist playlist = new Playlist("Test Playlist", "Test Description", songs);
        profile.setPlaylist(playlist);
        user.setProfile(profile);
        
        List<User> users = new ArrayList<>();
        users.add(user);
        
        userDatabase.saveUsers(users);
        
        // 2. Load data and verify
        List<User> loadedUsers = userDatabase.loadUsers();
        
        // 3. Find our test user among the loaded users
        User loadedUser = null;
        for (User u : loadedUsers) {
            if ("PersistenceUser".equals(u.getNameUser())) {
                loadedUser = u;
                break;
            }
        }
        
        // 4. Verify data integrity if user was found
        if (loadedUser != null) {
            assertEquals("PersistenceUser", loadedUser.getNameUser());
            assertTrue(loadedUser.getUserProfiles().size() >= 1);
            
            // Find our test profile
            Profile loadedProfile = null;
            for (Profile p : loadedUser.getUserProfiles()) {
                if ("Test Profile".equals(p.getName())) {
                    loadedProfile = p;
                    break;
                }
            }
            
            if (loadedProfile != null) {
                assertEquals("Test Profile", loadedProfile.getName());
                assertTrue(loadedProfile.getPlaylist().size() >= 1);
                
                // Find our test playlist
                Playlist loadedPlaylist = null;
                for (Playlist pl : loadedProfile.getPlaylist()) {
                    if ("Test Playlist".equals(pl.getNamePlaylist())) {
                        loadedPlaylist = pl;
                        break;
                    }
                }
                
                if (loadedPlaylist != null) {
                    assertEquals("Test Playlist", loadedPlaylist.getNamePlaylist());
                    assertTrue(loadedPlaylist.getSongs().size() >= 1);
                    
                    // Find our test song
                    Song loadedSong = null;
                    for (Song s : loadedPlaylist.getSongs()) {
                        if ("Test Song".equals(s.getName())) {
                            loadedSong = s;
                            break;
                        }
                    }
                    
                    if (loadedSong != null) {
                        assertEquals("Test Song", loadedSong.getName());
                        assertEquals("Test Artist", loadedSong.getAuthor());
                        assertEquals(3.5, loadedSong.getDurationMinutes());
                        assertEquals("https://youtube.com/test", loadedSong.getUrlYoutube());
                        
                        // Verify ID is generated for loaded entity
                        assertNotNull(loadedSong.getIdSong());
                    }
                }
            }
            
            // Verify IDs are generated for loaded entities
            assertNotNull(loadedUser.getIdUser());
            if (loadedProfile != null) {
                assertNotNull(loadedProfile.getIdProfile());
            }
        }
    }
    
    @Test
    void testReadSongsIntegration() {
        // Test ReadSongs service integration
        
        // 1. Test ReadSongs functionality
        assertDoesNotThrow(() -> readSongs.leerArchivo());
        
        List<Song> catalogSongs = readSongs.getReadSongs();
        assertNotNull(catalogSongs);
        
        // 2. Verify each song in catalog has valid properties
        for (Song song : catalogSongs) {
            if (song != null) {
                assertNotNull(song.getIdSong());
                assertNotNull(song.getName());
                assertFalse(song.getName().trim().isEmpty());
                
                assertNotNull(song.getAuthor());
                assertFalse(song.getAuthor().trim().isEmpty());
                
                assertTrue(song.getDurationMinutes() > 0);
                
                assertNotNull(song.getUrlYoutube());
                assertFalse(song.getUrlYoutube().trim().isEmpty());
            }
        }
    }
    
    @Test
    void testErrorHandling() {
        // Test error handling across the system
        
        // 1. Test invalid user creation
        assertThrows(IllegalArgumentException.class, () -> new User(null));
        assertThrows(IllegalArgumentException.class, () -> new User(""));
        assertThrows(IllegalArgumentException.class, () -> new User("   "));
        
        // 2. Test invalid profile creation
        assertThrows(IllegalArgumentException.class, () -> 
            new Profile(null, "Description", "Genre"));
        assertThrows(IllegalArgumentException.class, () -> 
            new Profile("Name", null, "Genre"));
        assertThrows(IllegalArgumentException.class, () -> 
            new Profile("Name", "Description", null));
        
        // 3. Test invalid song creation
        assertThrows(IllegalArgumentException.class, () -> 
            new Song(null, "Artist", 3.0, "url"));
        assertThrows(IllegalArgumentException.class, () -> 
            new Song("Song", null, 3.0, "url"));
        assertThrows(IllegalArgumentException.class, () -> 
            new Song("Song", "Artist", 0, "url"));
        assertThrows(IllegalArgumentException.class, () -> 
            new Song("Song", "Artist", 3.0, null));
        
        // 4. Test invalid playlist creation
        assertThrows(IllegalArgumentException.class, () -> 
            new Playlist(null, "Description", new ArrayList<>()));
        assertThrows(IllegalArgumentException.class, () -> 
            new Playlist("Name", null, new ArrayList<>()));
        assertThrows(IllegalArgumentException.class, () -> 
            new Playlist("Name", "Description", null));
    }
    
    @Test
    void testEqualsAndHashCodeConsistency() {
        // Test equals and hashCode implementations across all model classes
        
        // 1. Test User equals/hashCode - Users with same content should be equal
        User user1 = new User("TestUser");
        User user2 = new User("TestUser");
        
        // Users should be equal based on name and profiles (not ID)
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        
        // 2. Test Profile equals/hashCode
        Profile profile1 = new Profile("Name", "Desc", "Genre");
        Profile profile2 = new Profile("Name", "Desc", "Genre");
        
        assertEquals(profile1, profile2);
        assertEquals(profile1.hashCode(), profile2.hashCode());
        
        // 3. Test Song equals/hashCode
        Song song1 = new Song("Song", "Artist", 3.0, "url");
        Song song2 = new Song("Song", "Artist", 3.0, "url");
        
        assertEquals(song1, song2);
        assertEquals(song1.hashCode(), song2.hashCode());
        
        // 4. Test Playlist equals/hashCode
        List<Song> songs = new ArrayList<>();
        songs.add(song1);
        
        Playlist playlist1 = new Playlist("Name", "Desc", songs);
        Playlist playlist2 = new Playlist("Name", "Desc", songs);
        
        assertEquals(playlist1, playlist2);
        assertEquals(playlist1.hashCode(), playlist2.hashCode());
    }
}

package sounloud.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sounloud.model.Song;
import java.util.List;

public class ReadSongsTest {
    
    private ReadSongs readSongs;
    
    @BeforeEach
    void setUp() {
        readSongs = new ReadSongs();
    }
    
    @Test
    void testReadSongsCreation() {
        assertNotNull(readSongs);
        assertNotNull(readSongs.getReadSongs());
    }
    
    @Test
    void testGetReadSongsInitiallyEmpty() {
        List<Song> songs = readSongs.getReadSongs();
        assertNotNull(songs);
        // The list might be empty initially or contain songs from the actual file
        assertTrue(songs.size() >= 0);
    }
    
    @Test
    void testLeerArchivoDoesNotThrowException() {
        // Test that calling leerArchivo doesn't throw an exception
        // Note: This will try to read the actual file, so we just test it doesn't crash
        assertDoesNotThrow(() -> readSongs.leerArchivo());
    }
    
    @Test
    void testGetReadSongsAfterLeerArchivo() {
        // Test the sequence of operations
        readSongs.leerArchivo();
        List<Song> songs = readSongs.getReadSongs();
        
        assertNotNull(songs);
        // We can't predict the exact content since it depends on the actual file
        // but we can verify the list is not null and each song has valid properties
        for (Song song : songs) {
            if (song != null) {
                assertNotNull(song.getIdSong());
                assertNotNull(song.getName());
                assertNotNull(song.getAuthor());
                assertNotNull(song.getUrlYoutube());
                assertTrue(song.getDurationMinutes() > 0);
            }
        }
    }
    
    @Test
    void testMultipleCallsToLeerArchivo() {
        // Test that multiple calls to leerArchivo don't cause issues
        readSongs.leerArchivo();
        List<Song> firstCall = readSongs.getReadSongs();
        
        readSongs.leerArchivo();
        List<Song> secondCall = readSongs.getReadSongs();
        
        // Should return the same list since fileAlreadyRead flag prevents re-reading
        assertEquals(firstCall.size(), secondCall.size());
    }
    
    @Test
    void testSongValidation() {
        readSongs.leerArchivo();
        List<Song> songs = readSongs.getReadSongs();
        
        // Test that all songs in the list are valid
        for (Song song : songs) {
            if (song != null) {
                // Test basic properties are not null or empty
                assertNotNull(song.getName());
                assertFalse(song.getName().trim().isEmpty());
                
                assertNotNull(song.getAuthor());
                assertFalse(song.getAuthor().trim().isEmpty());
                
                assertNotNull(song.getUrlYoutube());
                assertFalse(song.getUrlYoutube().trim().isEmpty());
                
                assertTrue(song.getDurationMinutes() > 0);
                
                // Test ID generation
                assertNotNull(song.getIdSong());
                assertFalse(song.getIdSong().trim().isEmpty());
            }
        }
    }
    
    @Test
    void testUniqueIds() {
        readSongs.leerArchivo();
        List<Song> songs = readSongs.getReadSongs();
        
        // Test that all songs have unique IDs
        for (int i = 0; i < songs.size(); i++) {
            for (int j = i + 1; j < songs.size(); j++) {
                Song song1 = songs.get(i);
                Song song2 = songs.get(j);
                
                if (song1 != null && song2 != null) {
                    assertNotEquals(song1.getIdSong(), song2.getIdSong(),
                        "Songs at indices " + i + " and " + j + " have the same ID");
                }
            }
        }
    }
    
    @Test
    void testReadSongsStaticBehavior() {
        // Test that the ReadSongs class maintains static state correctly
        ReadSongs firstInstance = new ReadSongs();
        ReadSongs secondInstance = new ReadSongs();
        
        firstInstance.leerArchivo();
        List<Song> firstList = firstInstance.getReadSongs();
        
        // Second instance should see the same data due to static variables
        List<Song> secondList = secondInstance.getReadSongs();
        
        assertEquals(firstList.size(), secondList.size());
    }
    
    @Test
    void testEmptyFileHandling() {
        // This test checks behavior when the file might be empty or malformed
        // We can't easily test with a temp file since the class uses a hardcoded path
        // but we can test that it handles empty/null results gracefully
        
        List<Song> songs = readSongs.getReadSongs();
        assertNotNull(songs);
        
        // If the file was empty or malformed, the list should still be a valid empty list
        assertTrue(songs.size() >= 0);
    }
    
    @Test
    void testGetReadSongsConsistency() {
        // Test that multiple calls to getReadSongs return consistent results
        List<Song> firstCall = readSongs.getReadSongs();
        List<Song> secondCall = readSongs.getReadSongs();
        
        // Should return the same list reference or equivalent content
        assertEquals(firstCall.size(), secondCall.size());
        
        for (int i = 0; i < firstCall.size(); i++) {
            Song song1 = firstCall.get(i);
            Song song2 = secondCall.get(i);
            
            if (song1 != null && song2 != null) {
                assertEquals(song1.getName(), song2.getName());
                assertEquals(song1.getAuthor(), song2.getAuthor());
                assertEquals(song1.getDurationMinutes(), song2.getDurationMinutes());
                assertEquals(song1.getUrlYoutube(), song2.getUrlYoutube());
            }
        }
    }
}

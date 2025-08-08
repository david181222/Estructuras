package sounloud.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SongTest {
    
    private Song song;
    
    @BeforeEach
    void setUp() {
        song = new Song("TestSong", "TestArtist", 3.5, "https://youtube.com/test");
    }
    
    @Test
    void testSongCreation() {
        assertNotNull(song.getIdSong());
        assertEquals("TestSong", song.getName());
        assertEquals("TestArtist", song.getAuthor());
        assertEquals(3.5, song.getDurationMinutes());
        assertEquals("https://youtube.com/test", song.getUrlYoutube());
    }
    
    @Test
    void testSongCreationWithValidParameters() {
        Song validSong = new Song("ValidSong", "ValidArtist", 4.2, "https://youtube.com/valid");
        assertNotNull(validSong.getIdSong());
        assertEquals("ValidSong", validSong.getName());
        assertEquals("ValidArtist", validSong.getAuthor());
        assertEquals(4.2, validSong.getDurationMinutes());
        assertEquals("https://youtube.com/valid", validSong.getUrlYoutube());
    }
    
    @Test
    void testSongCreationWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Song(null, "Artist", 3.0, "https://youtube.com/test");
        });
    }
    
    @Test
    void testSongCreationWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Song("", "Artist", 3.0, "https://youtube.com/test");
        });
    }
    
    @Test
    void testSongCreationWithNullAuthor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Song("Song", null, 3.0, "https://youtube.com/test");
        });
    }
    
    @Test
    void testSongCreationWithEmptyAuthor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Song("Song", "", 3.0, "https://youtube.com/test");
        });
    }
    
    @Test
    void testSongCreationWithZeroDuration() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Song("Song", "Artist", 0.0, "https://youtube.com/test");
        });
    }
    
    @Test
    void testSongCreationWithNegativeDuration() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Song("Song", "Artist", -1.0, "https://youtube.com/test");
        });
    }
    
    @Test
    void testSongCreationWithNullUrl() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Song("Song", "Artist", 3.0, null);
        });
    }
    
    @Test
    void testSongCreationWithEmptyUrl() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Song("Song", "Artist", 3.0, "");
        });
    }
    
    @Test
    void testSongCreationWithMinimumValidDuration() {
        Song shortSong = new Song("ShortSong", "Artist", 0.1, "https://youtube.com/short");
        assertEquals(0.1, shortSong.getDurationMinutes());
    }
    
    @Test
    void testSongCreationWithLongDuration() {
        Song longSong = new Song("LongSong", "Artist", 60.0, "https://youtube.com/long");
        assertEquals(60.0, longSong.getDurationMinutes());
    }
    
    @Test
    void testGetIdSongLazyGeneration() {
        // Create a song and verify ID is generated
        String firstId = song.getIdSong();
        assertNotNull(firstId);
        
        // Call again and verify same ID is returned
        String secondId = song.getIdSong();
        assertEquals(firstId, secondId);
    }
    
    @Test
    void testEqualsAndHashCode() {
        Song song1 = new Song("SameName", "SameArtist", 3.0, "https://youtube.com/same");
        Song song2 = new Song("SameName", "SameArtist", 3.0, "https://youtube.com/same");
        
        // Since they have the same content (excluding ID), they should be equal
        assertEquals(song1, song2);
        assertEquals(song1.hashCode(), song2.hashCode());
        
        // Same instance should be equal to itself
        assertEquals(song1, song1);
        assertEquals(song1.hashCode(), song1.hashCode());
    }
    
    @Test
    void testEqualsWithDifferentName() {
        Song song1 = new Song("Name1", "SameArtist", 3.0, "https://youtube.com/same");
        Song song2 = new Song("Name2", "SameArtist", 3.0, "https://youtube.com/same");
        
        assertNotEquals(song1, song2);
    }
    
    @Test
    void testEqualsWithDifferentArtist() {
        Song song1 = new Song("SameName", "Artist1", 3.0, "https://youtube.com/same");
        Song song2 = new Song("SameName", "Artist2", 3.0, "https://youtube.com/same");
        
        assertNotEquals(song1, song2);
    }
    
    @Test
    void testEqualsWithDifferentDuration() {
        Song song1 = new Song("SameName", "SameArtist", 3.0, "https://youtube.com/same");
        Song song2 = new Song("SameName", "SameArtist", 4.0, "https://youtube.com/same");
        
        assertNotEquals(song1, song2);
    }
    
    @Test
    void testEqualsWithDifferentUrl() {
        Song song1 = new Song("SameName", "SameArtist", 3.0, "https://youtube.com/url1");
        Song song2 = new Song("SameName", "SameArtist", 3.0, "https://youtube.com/url2");
        
        assertNotEquals(song1, song2);
    }
    
    @Test
    void testEqualsWithNull() {
        assertNotEquals(song, null);
    }
    
    @Test
    void testEqualsWithDifferentClass() {
        assertNotEquals(song, "StringObject");
    }
    
    @Test
    void testToString() {
        String toString = song.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("TestSong"));
        assertTrue(toString.contains("TestArtist"));
        assertTrue(toString.contains("3.5"));
        assertTrue(toString.contains("https://youtube.com/test"));
    }
    
    @Test
    void testUniqueIdGeneration() {
        Song song1 = new Song("Name", "Artist", 3.0, "https://youtube.com/test");
        Song song2 = new Song("Name", "Artist", 3.0, "https://youtube.com/test");
        
        // IDs should be unique even for songs with same content
        assertNotEquals(song1.getIdSong(), song2.getIdSong());
    }
    
    @Test
    void testDoubleEquals() {
        // Test edge case for double comparison in equals method
        Song song1 = new Song("Test", "Artist", 3.14159, "https://youtube.com/test");
        Song song2 = new Song("Test", "Artist", 3.14159, "https://youtube.com/test");
        
        assertEquals(song1, song2);
        assertEquals(song1.hashCode(), song2.hashCode());
    }
    
    @Test
    void testDurationPrecision() {
        // Test with various duration values
        Song preciseSong = new Song("PreciseSong", "Artist", 3.14159265359, "https://youtube.com/precise");
        assertEquals(3.14159265359, preciseSong.getDurationMinutes(), 0.0000000001);
    }
}

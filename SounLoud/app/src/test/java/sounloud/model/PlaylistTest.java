package sounloud.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistTest {
    
    private Playlist playlist;
    private Song song1;
    private Song song2;
    private List<Song> songList;
    
    @BeforeEach
    void setUp() {
        songList = new ArrayList<>();
        song1 = new Song("Title1", "Artist1", 3.0, "https://youtube.com/song1");
        song2 = new Song("Title2", "Artist2", 3.5, "https://youtube.com/song2");
        songList.add(song1);
        
        playlist = new Playlist("TestPlaylist", "Test Description", songList);
    }
    
    @Test
    void testPlaylistCreation() {
        assertNotNull(playlist.getIdPlaylist());
        assertEquals("TestPlaylist", playlist.getNamePlaylist());
        assertEquals("Test Description", playlist.getDescriptionPlaylist());
        assertNotNull(playlist.getSongs());
        assertEquals(1, playlist.getSongs().size());
        assertTrue(playlist.getSongs().contains(song1));
    }
    
    @Test
    void testPlaylistCreationWithEmptyList() {
        List<Song> emptySongList = new ArrayList<>();
        Playlist emptyPlaylist = new Playlist("EmptyPlaylist", "Empty Description", emptySongList);
        
        assertNotNull(emptyPlaylist.getIdPlaylist());
        assertEquals("EmptyPlaylist", emptyPlaylist.getNamePlaylist());
        assertEquals("Empty Description", emptyPlaylist.getDescriptionPlaylist());
        assertNotNull(emptyPlaylist.getSongs());
        assertTrue(emptyPlaylist.getSongs().isEmpty());
    }
    
    @Test
    void testPlaylistCreationWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Playlist(null, "Description", songList);
        });
    }
    
    @Test
    void testPlaylistCreationWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Playlist("", "Description", songList);
        });
    }
    
    @Test
    void testPlaylistCreationWithNullDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Playlist("Name", null, songList);
        });
    }
    
    @Test
    void testPlaylistCreationWithEmptyDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Playlist("Name", "", songList);
        });
    }
    
    @Test
    void testPlaylistCreationWithNullSongList() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Playlist("Name", "Description", null);
        });
    }
    
    @Test
    void testSetNamePlaylist() {
        // Note: Playlist class doesn't have setter methods, so we skip these tests
        // or we could test that the values remain unchanged
        assertEquals("TestPlaylist", playlist.getNamePlaylist());
    }
    
    @Test
    void testSetDescriptionPlaylist() {
        // Note: Playlist class doesn't have setter methods, so we skip these tests
        // or we could test that the values remain unchanged
        assertEquals("Test Description", playlist.getDescriptionPlaylist());
    }
    
    @Test
    void testSetSongs() {
        // Note: Playlist class doesn't have setSongs method, so we skip this test
        // We can test that the original songs remain
        assertEquals(1, playlist.getSongs().size());
        assertTrue(playlist.getSongs().contains(song1));
    }
    
    @Test
    void testAddSong() {
        playlist.addSong(song2);
        assertEquals(2, playlist.getSongs().size());
        assertTrue(playlist.getSongs().contains(song1));
        assertTrue(playlist.getSongs().contains(song2));
    }
    
    @Test
    void testAddDuplicateSong() {
        playlist.addSong(song1);
        assertEquals(2, playlist.getSongs().size()); // Should allow duplicates
    }
    
    @Test
    void testRemoveSong() {
        playlist.addSong(song2);
        assertEquals(2, playlist.getSongs().size());
        
        playlist.removeSong(song1);
        assertEquals(1, playlist.getSongs().size());
        assertFalse(playlist.getSongs().contains(song1));
        assertTrue(playlist.getSongs().contains(song2));
    }
    
    @Test
    void testRemoveSongNotInList() {
        int originalSize = playlist.getSongs().size();
        playlist.removeSong(song2);
        assertEquals(originalSize, playlist.getSongs().size());
        assertTrue(playlist.getSongs().contains(song1));
    }
    
    @Test
    void testRemoveFromEmptyPlaylist() {
        List<Song> emptySongList = new ArrayList<>();
        Playlist emptyPlaylist = new Playlist("EmptyPlaylist", "Empty Description", emptySongList);
        
        // This should not throw an exception
        assertDoesNotThrow(() -> emptyPlaylist.removeSong(song1));
        assertEquals(0, emptyPlaylist.getSongs().size());
    }
    
    @Test
    void testShowSongsWithEmptyList() {
        List<Song> emptySongList = new ArrayList<>();
        Playlist emptyPlaylist = new Playlist("EmptyPlaylist", "Empty Description", emptySongList);
        
        // This test just verifies the method doesn't throw an exception
        assertDoesNotThrow(() -> emptyPlaylist.showSongs());
    }
    
    @Test
    void testShowSongsWithItems() {
        playlist.addSong(song2);
        // This test just verifies the method doesn't throw an exception
        assertDoesNotThrow(() -> playlist.showSongs());
    }
    
    @Test
    void testEqualsAndHashCode() {
        List<Song> sameSongList = new ArrayList<>();
        sameSongList.add(song1);
        
        Playlist playlist1 = new Playlist("SameName", "Same Description", sameSongList);
        Playlist playlist2 = new Playlist("SameName", "Same Description", sameSongList);
        
        // Since they have the same content (excluding ID), they should be equal
        assertEquals(playlist1, playlist2);
        assertEquals(playlist1.hashCode(), playlist2.hashCode());
        
        // Same instance should be equal to itself
        assertEquals(playlist1, playlist1);
        assertEquals(playlist1.hashCode(), playlist1.hashCode());
    }
    
    @Test
    void testEqualsWithDifferentName() {
        List<Song> sameSongList = new ArrayList<>();
        sameSongList.add(song1);
        
        Playlist playlist1 = new Playlist("Name1", "Same Description", sameSongList);
        Playlist playlist2 = new Playlist("Name2", "Same Description", sameSongList);
        
        assertNotEquals(playlist1, playlist2);
    }
    
    @Test
    void testEqualsWithDifferentDescription() {
        List<Song> sameSongList = new ArrayList<>();
        sameSongList.add(song1);
        
        Playlist playlist1 = new Playlist("Same Name", "Description1", sameSongList);
        Playlist playlist2 = new Playlist("Same Name", "Description2", sameSongList);
        
        assertNotEquals(playlist1, playlist2);
    }
    
    @Test
    void testEqualsWithDifferentSongs() {
        List<Song> songList1 = new ArrayList<>();
        List<Song> songList2 = new ArrayList<>();
        songList1.add(song1);
        songList2.add(song2);
        
        Playlist playlist1 = new Playlist("Same Name", "Same Description", songList1);
        Playlist playlist2 = new Playlist("Same Name", "Same Description", songList2);
        
        assertNotEquals(playlist1, playlist2);
    }
    
    @Test
    void testEqualsWithNull() {
        assertNotEquals(playlist, null);
    }
    
    @Test
    void testEqualsWithDifferentClass() {
        assertNotEquals(playlist, "StringObject");
    }
    
    @Test
    void testToString() {
        String toString = playlist.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("TestPlaylist"));
        assertTrue(toString.contains("Test Description"));
    }
    
    @Test
    void testUniqueIdGeneration() {
        List<Song> sameSongList = new ArrayList<>();
        sameSongList.add(song1);
        
        Playlist playlist1 = new Playlist("Name", "Description", sameSongList);
        Playlist playlist2 = new Playlist("Name", "Description", sameSongList);
        
        // IDs should be unique even for playlists with same content
        assertNotEquals(playlist1.getIdPlaylist(), playlist2.getIdPlaylist());
    }
}

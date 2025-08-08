package sounloud.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class ProfileTest {
    
    private Profile profile;
    private Playlist playlist1;
    private Playlist playlist2;
    
    @BeforeEach
    void setUp() {
        profile = new Profile("TestProfile", "Test Description", "Rock");
        playlist1 = new Playlist("Playlist1", "Description1", new ArrayList<>());
        playlist2 = new Playlist("Playlist2", "Description2", new ArrayList<>());
    }
    
    @Test
    void testProfileCreation() {
        assertNotNull(profile.getIdProfile());
        assertEquals("TestProfile", profile.getName());
        assertEquals("Test Description", profile.getDescription());
        assertEquals("Rock", profile.getFavoriteGenre());
        assertNotNull(profile.getPlaylist());
        assertTrue(profile.getPlaylist().isEmpty());
    }
    
    @Test
    void testProfileCreationWithValidParameters() {
        Profile validProfile = new Profile("ValidName", "Valid Description", "Jazz");
        assertNotNull(validProfile.getIdProfile());
        assertEquals("ValidName", validProfile.getName());
        assertEquals("Valid Description", validProfile.getDescription());
        assertEquals("Jazz", validProfile.getFavoriteGenre());
        assertNotNull(validProfile.getPlaylist());
    }
    
    @Test
    void testProfileCreationWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Profile(null, "Description", "Genre");
        });
    }
    
    @Test
    void testProfileCreationWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Profile("", "Description", "Genre");
        });
    }
    
    @Test
    void testProfileCreationWithNullDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Profile("Name", null, "Genre");
        });
    }
    
    @Test
    void testProfileCreationWithEmptyDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Profile("Name", "", "Genre");
        });
    }
    
    @Test
    void testProfileCreationWithNullGenre() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Profile("Name", "Description", null);
        });
    }
    
    @Test
    void testProfileCreationWithEmptyGenre() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Profile("Name", "Description", "");
        });
    }
    
    @Test
    void testSetPlaylist() {
        profile.setPlaylist(playlist1);
        assertEquals(1, profile.getPlaylist().size());
        assertTrue(profile.getPlaylist().contains(playlist1));
    }
    
    @Test
    void testSetMultiplePlaylists() {
        profile.setPlaylist(playlist1);
        profile.setPlaylist(playlist2);
        assertEquals(2, profile.getPlaylist().size());
        assertTrue(profile.getPlaylist().contains(playlist1));
        assertTrue(profile.getPlaylist().contains(playlist2));
    }
    
    @Test
    void testSetDuplicatePlaylist() {
        profile.setPlaylist(playlist1);
        profile.setPlaylist(playlist1);
        assertEquals(2, profile.getPlaylist().size()); // Should allow duplicates
    }
    
    @Test
    void testShowPlaylistsWithEmptyList() {
        // This test just verifies the method doesn't throw an exception
        assertDoesNotThrow(() -> profile.showPlaylists());
    }
    
    @Test
    void testShowPlaylistsWithItems() {
        profile.setPlaylist(playlist1);
        profile.setPlaylist(playlist2);
        // This test just verifies the method doesn't throw an exception
        assertDoesNotThrow(() -> profile.showPlaylists());
    }
    
    @Test
    void testEqualsAndHashCode() {
        Profile profile1 = new Profile("SameName", "Same Description", "Same Genre");
        Profile profile2 = new Profile("SameName", "Same Description", "Same Genre");
        
        // Since they have the same content (excluding ID), they should be equal
        assertEquals(profile1, profile2);
        assertEquals(profile1.hashCode(), profile2.hashCode());
        
        // Same instance should be equal to itself
        assertEquals(profile1, profile1);
        assertEquals(profile1.hashCode(), profile1.hashCode());
    }
    
    @Test
    void testEqualsWithDifferentName() {
        Profile profile1 = new Profile("Name1", "Same Description", "Same Genre");
        Profile profile2 = new Profile("Name2", "Same Description", "Same Genre");
        
        assertNotEquals(profile1, profile2);
    }
    
    @Test
    void testEqualsWithDifferentDescription() {
        Profile profile1 = new Profile("Same Name", "Description1", "Same Genre");
        Profile profile2 = new Profile("Same Name", "Description2", "Same Genre");
        
        assertNotEquals(profile1, profile2);
    }
    
    @Test
    void testEqualsWithDifferentGenre() {
        Profile profile1 = new Profile("Same Name", "Same Description", "Genre1");
        Profile profile2 = new Profile("Same Name", "Same Description", "Genre2");
        
        assertNotEquals(profile1, profile2);
    }
    
    @Test
    void testEqualsWithNull() {
        assertNotEquals(profile, null);
    }
    
    @Test
    void testEqualsWithDifferentClass() {
        assertNotEquals(profile, "StringObject");
    }
    
    @Test
    void testToString() {
        String toString = profile.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("TestProfile"));
        assertTrue(toString.contains("Test Description"));
        assertTrue(toString.contains("Rock"));
    }
    
    @Test
    void testUniqueIdGeneration() {
        Profile profile1 = new Profile("Name", "Description", "Genre");
        Profile profile2 = new Profile("Name", "Description", "Genre");
        
        // IDs should be unique even for profiles with same content
        assertNotEquals(profile1.getIdProfile(), profile2.getIdProfile());
    }
}

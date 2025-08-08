package sounloud.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Usuario Test");
    }

    @Test
    void testUserCreation_ValidName() {
        // Assert
        assertNotNull(user);
        assertEquals("Usuario Test", user.getNameUser());
        assertNotNull(user.getIdUser());
        assertNotNull(user.getUserProfiles());
        assertTrue(user.getUserProfiles().isEmpty());
    }

    @Test
    void testUserCreation_NullName() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new User(null);
        });
    }

    @Test
    void testUserCreation_EmptyName() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new User("");
        });
    }

    @Test
    void testUserCreation_WhitespaceOnlyName() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new User("   ");
        });
    }

    @Test
    void testSetProfile_ValidProfile() {
        // Arrange
        Profile profile = new Profile("Perfil Test", "Descripción", "Rock");

        // Act
        user.setProfile(profile);

        // Assert
        assertEquals(1, user.getUserProfiles().size());
        assertEquals("Perfil Test", user.getUserProfiles().get(0).getName());
    }

    @Test
    void testSetProfile_MultipleProfiles() {
        // Arrange
        Profile profile1 = new Profile("Perfil 1", "Descripción 1", "Rock");
        Profile profile2 = new Profile("Perfil 2", "Descripción 2", "Jazz");

        // Act
        user.setProfile(profile1);
        user.setProfile(profile2);

        // Assert
        assertEquals(2, user.getUserProfiles().size());
        assertEquals("Perfil 1", user.getUserProfiles().get(0).getName());
        assertEquals("Perfil 2", user.getUserProfiles().get(1).getName());
    }

    @Test
    void testShowProfiles_NoProfiles() {
        // Act & Assert - No debería lanzar excepción
        assertDoesNotThrow(() -> user.showProfiles());
    }

    @Test
    void testShowProfiles_WithProfiles() {
        // Arrange
        Profile profile = new Profile("Perfil Test", "Descripción", "Rock");
        user.setProfile(profile);

        // Act & Assert - No debería lanzar excepción
        assertDoesNotThrow(() -> user.showProfiles());
    }

    @Test
    void testUserIdGeneration_Unique() {
        // Arrange
        User user2 = new User("Usuario 2");

        // Assert
        assertNotEquals(user.getIdUser(), user2.getIdUser());
    }

    @Test
    void testUserIdGeneration_NotNull() {
        // Assert
        assertNotNull(user.getIdUser());
        assertFalse(user.getIdUser().isEmpty());
    }

    @Test
    void testEquals_SameNameAndProfiles() {
        // Arrange
        User user2 = new User("Usuario Test");
        Profile profile = new Profile("Perfil Test", "Descripción", "Rock");
        user.setProfile(profile);
        user2.setProfile(profile);

        // Act & Assert
        assertEquals(user, user2);
    }

    @Test
    void testEquals_DifferentName() {
        // Arrange
        User user2 = new User("Usuario Diferente");

        // Act & Assert
        assertNotEquals(user, user2);
    }

    @Test
    void testHashCode_SameUsers() {
        // Arrange
        User user2 = new User("Usuario Test");

        // Act & Assert
        assertEquals(user.hashCode(), user2.hashCode());
    }
}

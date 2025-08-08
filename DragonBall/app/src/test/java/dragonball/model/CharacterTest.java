package dragonball.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {
    @Test
    void testCharacterGettersAndSetters() {
        Character character = new Character();
        character.setId(1);
        character.setName("Goku");
        character.setKi("9000000");
        character.setMaxKi("10000000");
        character.setRace("Saiyan");
        character.setGender("Male");
        character.setDescription("Main character");
        character.setImage("goku.png");
        character.setAffiliation("Z Fighters");
        character.setDeletedAt(null);

        assertEquals(1, character.getId());
        assertEquals("Goku", character.getName());
        assertEquals("9000000", character.getKi());
        assertEquals("10000000", character.getMaxKi());
        assertEquals("Saiyan", character.getRace());
        assertEquals("Male", character.getGender());
        assertEquals("Main character", character.getDescription());
        assertEquals("goku.png", character.getImage());
        assertEquals("Z Fighters", character.getAffiliation());
        assertNull(character.getDeletedAt());
    }

    @Test
    void testToString() {
        Character character = new Character();
        character.setId(2);
        character.setName("Vegeta");
        character.setKi("8500000");
        character.setMaxKi("9500000");
        character.setRace("Saiyan");
        character.setGender("Male");
        character.setDescription("Prince of Saiyans");
        character.setImage("vegeta.png");
        character.setAffiliation("Z Fighters");
        character.setDeletedAt(null);

        String toString = character.toString();
        assertTrue(toString.contains("Vegeta"));
        assertTrue(toString.contains("8500000"));
        assertTrue(toString.contains("Prince of Saiyans"));
    }
}

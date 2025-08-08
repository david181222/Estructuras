package paragon.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import paragon.model.Character.Character;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Esta clase define a un jugador que posee una lista de personajes, que sería el mazo aleatorio que usará en el combate
public class Player {
    private static final Logger logger = LogManager.getLogger(Player.class);
    private String name;
    private String password;
    private List<Character> characters;

    public Player(String name, String password) {
        try {
            if (name == null || name.isEmpty()) {
                logger.error("El nombre del jugador no puede ser nulo o vacío.");
                throw new IllegalArgumentException("El nombre del jugador no puede ser nulo o vacío.");
            }
            if (password == null || password.isEmpty()) {
                logger.error("La contraseña del jugador no puede ser nula o vacía.");
                throw new IllegalArgumentException("La contraseña del jugador no puede ser nula o vacía.");
            }
            logger.info("Creando al jugador: {}", name);
            this.name = name;
            this.password = password;
            this.characters = new ArrayList<>();
            logger.info("Jugador creado: {}", name);
        } catch (Exception e) {
            logger.error("Error al crear jugador: {}", e.getMessage());
            throw e;
        }
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<Character> getAliveCharacters() {
        return characters.stream()
                .filter(c -> c.getHp() > 0)
                .collect(Collectors.toList());
    }
    public List<Character> getCharacters() {
    return characters; 
}


    public void setCharacters(Character a) {
        this.characters.add(a);

    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", characters=" + characters +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Player))
            return false;
        Player player = (Player) obj;
        if (!name.equals(player.name))
            return false;
        if (!password.equals(player.password))
            return false;
        if (!characters.equals(player.characters))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, characters);
    }
}

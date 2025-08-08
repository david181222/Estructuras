package paragon.model.Character.Inventory;

import java.util.ArrayList;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import paragon.model.Habilities.Ability;
import paragon.model.Items.Item;

//Esta clase define un inventario que poseen los personajes, y es donde se almacenan las habilidades e items cuando se cargan de manera aleatoria
//en el main al simular el combate
public class Inventory {
    private static final Logger logger = LogManager.getLogger(Inventory.class);
    private ArrayList<Ability> abilities;
    private ArrayList<Item> items;

    public Inventory() {
        logger.info("Inicializando inventario");
        this.abilities = new ArrayList<>();
        this.items = new ArrayList<>();
        logger.info("Inventario creado exitosamente.");
    }

    public void addAbility(Ability ability) {
        try {
            if (ability == null) {
                logger.error("La habilidad no puede ser nula.");
                throw new IllegalArgumentException("La habilidad no puede ser nula.");
            }
            abilities.add(ability);
            logger.info("Habilidad {} añadida al inventario.", ability);
        } catch (Exception e) {
            logger.error("Error al añadir habilidad: {}", e.getMessage());
            throw e;
        }

    }

    public void addItem(Item item) {
        try {
            if (item == null) {
                logger.error("El ítem no puede ser nulo.");
                throw new IllegalArgumentException("El ítem no puede ser nulo.");
            }
            items.add(item);
            logger.info("Ítem {} añadido al inventario.", item);
        } catch (Exception e) {
            logger.error("Error al añadir ítem: {}", e.getMessage());
            throw e;
        }
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "abilities=" + abilities +
                ", items=" + items +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Inventory))
            return false;
        Inventory inventory = (Inventory) obj;
        if (!abilities.equals(inventory.abilities))
            return false;
        if (!items.equals(inventory.items))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(abilities, items);
    }
}
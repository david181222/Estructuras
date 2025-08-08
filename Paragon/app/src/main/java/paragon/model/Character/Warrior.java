package paragon.model.Character;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Warrior extends Character {
    private static final Logger logger = LogManager.getLogger(Warrior.class);

    public Warrior(String name) {
        super(name);

        logger.info("Creando guerrero llamado: {}", name);
        this.strength = 25 + (int) (Math.random() * 16); // Fuerza entre 25 y 40
        this.hp = 100 + (int) (Math.random() * 71); // Salud de 100 a 170
        this.mp = 30 + (int) (Math.random() * 21); // Mana de 30 a 50
        this.intelligence = 5 + (int) (Math.random() * 10); // Habilidad entre 5 y 15
        this.ability = 10 + (int) (Math.random() * 11); // Habilidad entre 10 y 20
        logger.info("Guerrero creado exitosamente: {}", this);
    }

    @Override
    public int totalPower() {
        int power = (getStrength() * getLevel()) + getHp();
        logger.info("Poder total del guerrero {}: {}", this.name, power);
        return power;
    }

    
}

package paragon.model.Character;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Wizard extends Character {
    private static final Logger logger = LogManager.getLogger(Wizard.class);

    public Wizard(String name) {
        super(name);
        logger.info("Creando mago llamado: {}", name);
        this.intelligence = 25 + (int) (Math.random() * 16); // Habilidad entre 25 y 40
        this.hp = 60 + (int) (Math.random() * 61); // Salud de 60 a 120
        this.mp = 50 + (int) (Math.random() * 51); // Mana de 50 a 100
        this.ability = 5 + (int) (Math.random() * 11); // Habilidad entre 5 y 15
        this.strength = 5 + (int) (Math.random() * 11); // Fuerza entre 5 y 15
        logger.info("Mago creado exitosamente: " + this);
    }
     @Override
    public int totalPower() {
        int power = (getAbility() * getLevel()) + getHp();
        logger.info("Poder total del mago {}: {}", this.name, power);
        return power;
    }
   
}

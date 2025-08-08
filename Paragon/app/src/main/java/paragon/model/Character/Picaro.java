package paragon.model.Character;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Picaro extends Character {
    private static final Logger logger = LogManager.getLogger(Picaro.class);

    public Picaro(String name) {
        super(name);
        logger.info("Creando pícaro llamado: {}", name);
        this.strength = 5 + (int) (Math.random() * 11); // Fuerza entre 5 y 15
        this.hp = 80 + (int) (Math.random() * 51); // Salud de 80 a 130
        this.mp = 40 + (int) (Math.random() * 31); // Mana de 40 a 70
        this.intelligence = 5 + (int) (Math.random() * 16); // Inteligencia entre 5 y 20
        this.ability = 25 + (int) (Math.random() * 16); // Habilidad entre 25 y 40
        logger.info("Picaro creado exitosamente: {}", this);
    }

     @Override
    public int totalPower() {
        int power = (getIntelligence() * getLevel()) + getHp();
        logger.info("Poder total del pícaro {}: {}", this.name, power);
        return power;
    }

}

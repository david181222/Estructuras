package paragon.model.Character;

import java.util.Objects;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import paragon.model.Actions.Interface.IActions;
import paragon.model.Character.Inventory.Inventory;
import paragon.model.Habilities.Ability;

//Definimos la clase Character con sus atributos, además implementa la interfaz de acciones IActions
public abstract class Character implements IActions {
  private static final Logger logger = LogManager.getLogger(Character.class);
  protected UUID id;
  protected String name;
  protected int level;
  protected int hp;
  protected int mp;
  protected int strength;
  protected int intelligence;
  protected int ability;
  protected Inventory inventory;

  public Character(String name) {
    try {
      logger.info("Creando personaje llamado: {}", name);
      if (name == null || name.isEmpty()) {
        throw new IllegalArgumentException("El nombre del personaje no puede ser nulo o vacío.");
      }
      this.id = UUID.randomUUID();
      this.name = name;
      this.level = 1;
      this.inventory = new Inventory();
      logger.info("Personaje creado exitosamente: {}", this);
    } catch (Exception e) {
      logger.error("Error al crear personaje: {}", e.getMessage());
      throw e;
    }
  }

  @Override
  public void attack(Character target) {
    try {
      if (target == null) {
        logger.error("El objetivo del ataque no puede ser nulo.");
        throw new IllegalArgumentException("El objetivo del ataque no puede ser nulo.");
      }
      logger.info("{} ataca a {}", this.name, target.getName());

      int damage = this.strength + 10; 
      target.hp -= damage;
      logger.info("{} inflige {} puntos de daño a {}", this.name, damage, target.getName());
      
    } catch (Exception e) {
      logger.error("Error al atacar: {}", e.getMessage());
      throw e;
    }
  }

  @Override
  public void useHability(Ability ability, Character target) {
    try {
      logger.info("{} utiliza una habilidad", this.name);
      if (ability == null) {
        logger.error("La habilidad no puede ser nula.");
        throw new IllegalArgumentException("La habilidad no puede ser nula.");
      }
      if (this.mp < ability.getManaCost()) {
        logger.warn("{} no tiene suficiente mana para usar la habilidad {}", this.name, ability.getName());
        return;
      }
      this.mp -= ability.getManaCost();
      target.hp -= ability.getDamage();
      logger.info("{} usa la habilidad {} en {}", this.name, ability.getName(), target.getName());
    } catch (Exception e) {
      logger.error("Error al usar habilidad: {}", e.getMessage());
      throw e;
    }
  }

  @Override
  public int totalPower() {
    int power = 0;
    return power;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getLevel() {
    return level;
  }

  public int getHp() {
    return hp;
  }
  public void increaseHp(int n){
    this.hp += n;
  }

  public int getMp() {
    return mp;
  }
   public void increaseMp(int n){
    this.mp += n;
  }

  public int getStrength() {
    return strength;
  }

  public int getIntelligence() {
    return intelligence;
  }

  public int getAbility() {
    return ability;
  }
  public Inventory getInventory(){
    return inventory;
  }


  

  @Override
  public String toString() {
    return "Character{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", level=" + level +
        ", hp=" + hp +
        ", mp=" + mp +
        ", strength=" + strength +
        ", intelligence=" + intelligence +
        ", ability=" + ability +
        '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Character))
      return false;
    Character character = (Character) obj;
    if (!id.equals(character.id))
      return false;
    if (!name.equals(character.name))
      return false;
    if (level != character.level)
      return false;
    if (hp != character.hp)
      return false;
    if (mp != character.mp)
      return false;
    if (strength != character.strength)
      return false;
    if (intelligence != character.intelligence)
      return false;
    return ability == character.ability;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, level, hp, mp, strength, intelligence, ability);
  }

}


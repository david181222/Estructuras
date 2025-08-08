
package paragon.model.Actions.Interface;


import paragon.model.Character.Character;
import paragon.model.Habilities.Ability;

//Interfaz de acciones para los personajes
public interface IActions {

    void attack(Character target);

    void useHability(Ability ability, Character target);

    int totalPower();

}

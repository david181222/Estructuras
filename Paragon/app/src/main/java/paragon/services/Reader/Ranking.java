package paragon.services.Reader;

import java.util.ArrayList;
import java.util.List;

import paragon.model.Player.Player;
import paragon.model.Character.Character;

//Es una clase que tiene por objetivo hacer un top de los personajes más poderosos que estan en batalla
public class Ranking {
    private Player p1;
    private Player p2;

    public Ranking(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public String topCharacters() {
        List<Character> characters = new ArrayList<>();

        characters.addAll(p1.getCharacters());
        characters.addAll(p2.getCharacters());

        if (characters.isEmpty()) {
            return "No hay personajes disponibles.";
        }

        characters.sort((c1, c2) -> Integer.compare(c2.totalPower(), c1.totalPower())); //Ordenamos la lista comparando los poderes totales 

        //Usando programación funcional podemos mapear los objetos de la lista de personajes y devolver el string con un formato
        return characters.stream()
                .map(r -> "Nombre: " + r.getName() + ", Poder total: " + Integer.toString(r.totalPower()))
                .reduce("", (a, b) -> a + "\n------------------------------------------------\n" + b
                        + "\n------------------------------------------------\n");
    }
}

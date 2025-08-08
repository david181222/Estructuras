
package paragon;

import paragon.model.Character.Picaro;
import paragon.model.Character.Warrior;
import paragon.model.Habilities.Ability;
import paragon.model.Match.Match;
import paragon.model.Player.Player;
import paragon.services.Reader.Ranking;
import paragon.services.Reader.ReadAbilities;
import paragon.services.Reader.ReadItems;
import paragon.model.Items.Item;

import java.util.List;

public class App {

    public static void main(String[] args) {
        //Vamos a simular un combate creando y probando nuestras clases y métodos:
        // Creo el jugador 1, luego añado las habilidades y items
        // Luego creo el jugador 2, añado sus personajes, habilidades y items (Todo se asigna con Math.random)
        // Luego creo el combate, añado los jugadores y enemigos


        ReadAbilities lectorHabilidades = new ReadAbilities();
        lectorHabilidades.leerArchivo();

        ReadItems lectorItems = new ReadItems();
        lectorItems.leerArchivo();

        List<Ability> habilidades = ReadAbilities.getHabilidades();
        List<Item> items = ReadItems.getItems();

        Player p1 = new Player("david", "123");
        Player p2 = new Player("jose", "1213");

        Warrior c1 = new Warrior("Solid Snake");
        Picaro c2 = new Picaro("Kazuhira Miller");
        Warrior c22 = new Warrior("Higgs");
        Picaro c12 = new Picaro("Fragile");

        //Añadimos habilidades
        c1.getInventory().addAbility(habilidades.get((int) (Math.random() * habilidades.size())));
        c22.getInventory().addAbility(habilidades.get((int) (Math.random() * habilidades.size())));
        c2.getInventory().addAbility(habilidades.get((int) (Math.random() * habilidades.size())));
        c12.getInventory().addAbility(habilidades.get((int) (Math.random() * habilidades.size())));
        //Añadimos items
        c1.getInventory().addItem(items.get((int) (Math.random() * items.size())));
        c22.getInventory().addItem(items.get((int) (Math.random() * items.size())));
        c2.getInventory().addItem(items.get((int) (Math.random() * items.size())));
        c12.getInventory().addItem(items.get((int) (Math.random() * items.size())));
       

        p1.setCharacters(c1);
        p1.setCharacters(c12);
        p2.setCharacters(c2);
        p2.setCharacters(c22);

        Ranking ranking = new Ranking(p1, p2);
        System.out.println(ranking.topCharacters());

        Match match = new Match(p2, p1);
        System.out.println(match.playMatch(p2, p1));

    }
}

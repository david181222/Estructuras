package paragon.model.Match;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import paragon.model.Habilities.Ability;
import paragon.model.Items.Item;
import paragon.model.Player.Player;
import paragon.services.Reader.ReadItems;

//En esta clase de implementa toda la lógica de combate para una partida entre dos jugadores
public class Match {

    private final static Logger logger = LogManager.getLogger(Match.class);

    Player p1;
    Player p2;

    public Match(Player p1, Player p2) {
        try {
            if (p1 == null || p2 == null) {
                logger.info("No pueden jugar participantes nulos");
                throw new IllegalArgumentException("Jugador nulo");
            }
            this.p1 = p1;
            this.p2 = p2;
        } catch (Exception e) {
            logger.error("Jugador nulo");
            throw e;
        }
    }

    public String playMatch(Player p1, Player p2) {
        // Inicializar la lectura de items
        ReadItems readItems = new ReadItems();
        readItems.leerArchivo();

        int livesP1 = p1.getCharacters().size();
        int livesP2 = p2.getCharacters().size();
        int decisionP1, decisionP2;
        int decisionP1c = 0;
        int decisionP2c = 0;
        int decisionP1a = 0;
        int decisionP2a = 0;
        int decisionP1i = 0;
        int decisionP2i = 0;
        Ability abi;
        Item item;
        boolean condition;

        Scanner sc = new Scanner(System.in);

        if (livesP1 <= 0 || livesP2 <= 0) {
            logger.error("Los jugadores no tienen personajes");
            throw new IllegalArgumentException("No hay suficientes personajes");
            
        } else {
            while (livesP1 > 0 && livesP2 > 0) {

                // Jugador 1 elige personaje
                logger.info("Jugador 1 eligiendo personaje...");
                condition = true;
                // Ciclo para que se elija un personaje válido
                while (condition) {
                    System.out.println("Jugador 1 elige tu personaje");
                    System.out.println(p1.getAliveCharacters());
                    decisionP1c = Integer.parseInt(sc.nextLine()) - 1;

                    if (decisionP1c >= 0 && decisionP1c < p1.getCharacters().size()) {
                        condition = false;
                    } else {
                        logger.error("Jugador 1 debe elegir un personaje válido");
                        System.out.println("Elige un número válido");
                    }
                }

                // Jugador 2 elige personaje
                logger.info("Jugador 2 eligiendo personaje...");
                condition = true;
                // Ciclo para que se elija un personaje válido
                while (condition) {
                    System.out.println("Jugador 2 elige tu personaje");
                    System.out.println(p2.getAliveCharacters());
                    decisionP2c = Integer.parseInt(sc.nextLine()) - 1;

                    if (decisionP2c >= 0 && decisionP2c < p2.getCharacters().size()) {
                        condition = false;
                    } else {
                        logger.error("Jugador 2 debe elegir un personaje válido");
                        System.out.println("Elige un número válido");
                    }
                }

                condition = true;
                // Ciclo para que se elija una acción válida
                while (condition) {
                    // Jugador 1 elige acción
                    logger.info("Jugador 1 eligiendo acción");
                    System.out.println("Jugador 1 elige tu acción:\n1.Atacar\n2.Usar habilidad\n3.Usar item");
                    decisionP1 = Integer.parseInt(sc.nextLine());

                    if (decisionP1 == 1) {
                        // Lógica de ataque
                        logger.info("{} atacando a {}", decisionP1c, decisionP2c);
                        p1.getCharacters().get(decisionP1c).attack(p2.getCharacters().get(decisionP2c));
                        condition = false;
                    } else if (decisionP1 == 2) {
                        // Lógica de habilidades
                        if (p1.getCharacters().get(decisionP1c).getInventory().getAbilities().size() > 0) {
                            System.out.println("Elige la habilidad:");
                            System.out.println(p1.getCharacters().get(decisionP1c).getInventory().getAbilities());
                            decisionP1a = Integer.parseInt(sc.nextLine()) - 1;
                            logger.info("Buscando habilidad con índice {}", decisionP1a);
                           if(decisionP1a >= 0 && decisionP1a < p1.getCharacters().get(decisionP1c).getInventory().getAbilities().size()){
                            abi = p1.getCharacters().get(decisionP1c).getInventory().getAbilities().get(decisionP1a);
                            logger.info("{} usa habilidad {} contra {}", decisionP1c, abi.getName(), decisionP2c);
                            p1.getCharacters().get(decisionP1c).useHability(abi, p2.getCharacters().get(decisionP2c));
                            p1.getCharacters().get(decisionP1c).getInventory().getAbilities().remove(abi);
                            condition = false;
                            } else {
                                System.out.println("Número de habilidad inválido");
                                continue;
                            }
                        } else {
                            System.out.println("No hay habilidades disponibles");
                        }
                    } else if (decisionP1 == 3) {
                        // Lógica de items
                        if (p1.getCharacters().get(decisionP1c).getInventory().getItems().size() > 0) {
                            System.out.println("Elige el item:");
                            System.out.println(p1.getCharacters().get(decisionP1c).getInventory().getItems());
                            decisionP1i = Integer.parseInt(sc.nextLine()) - 1;
                            if (decisionP1i >= 0 && decisionP1i < p1.getCharacters().get(decisionP1c).getInventory()
                                    .getItems().size()) {
                                item = p1.getCharacters().get(decisionP1c).getInventory().getItems().get(decisionP1i);
                                logger.info("{} usa item {}", decisionP1c, item.getName());

                                // Aplicar efecto del item
                                if (item.getHeal() > 0) {
                                    p1.getCharacters().get(decisionP1c).increaseHp(item.getHeal());
                                    System.out.println(p1.getCharacters().get(decisionP1c).getName() + " se curó "
                                            + item.getHeal() + " HP");
                                    p1.getCharacters().get(decisionP1c).getInventory().getItems().remove(item);
                                }
                                if (item.getPlus() > 0) {
                                    p1.getCharacters().get(decisionP1c).increaseMp(item.getPlus());
                                    System.out.println(p1.getCharacters().get(decisionP1c).getName() + " aumentó "
                                            + item.getPlus() + " MP");
                                    p1.getCharacters().get(decisionP1c).getInventory().getItems().remove(item);
                                }

                                // Continuar con el combate
                                System.out.println("Ahora elige tu acción de combate:");
                                continue;
                            } else {
                                System.out.println("Número de item inválido");
                                continue;
                            }
                        } else {
                            System.out.println("No hay items disponibles");
                        }
                    } else {
                        logger.error("Selección inválida");
                        System.out.println("Elija una opción válida");
                    }
                }

                // Verificar si el jugador 2 perdió al personaje
                if (p2.getCharacters().get(decisionP2c).getHp() <= 0) {
                    System.out.println(p2.getCharacters().get(decisionP2c).getName() + " ha muerto.");
                    p2.getCharacters().remove(decisionP2c);
                    livesP2--;

                    // Sube hp y mp al personaje de jugador 1
                    logger.info("Jugador 1 recibe boost para {}", p1.getCharacters().get(decisionP1c).getName());
                    p1.getCharacters().get(decisionP1c).increaseHp(25);
                    p1.getCharacters().get(decisionP1c).increaseMp(30);
                    System.out.println(
                            p1.getCharacters().get(decisionP1c).getName()
                                    + " ha incrementado su salud y mana por kill");

                    // Jugador 2 elige nuevo personaje para seguir jugando
                    logger.info("Jugador 2 eligiendo personaje...");
                    condition = true;
                    while (condition) {
                        if (livesP2 <= 0) {
                            break;
                        }
                        System.out.println("\nJugador 2 elige tu personaje");
                        System.out.println(p2.getAliveCharacters());
                        decisionP2c = Integer.parseInt(sc.nextLine()) - 1;

                        if (decisionP2c >= 0 && decisionP2c < p2.getCharacters().size()) {
                            condition = false;
                        } else {
                            System.out.println("Elige un número válido");
                        }
                    }

                    if (livesP2 <= 0)
                        continue;
                }

                condition = true;
                while (condition) {
                    // Jugador 2 elige acción
                    System.out.println("Jugador 2 elige tu acción:\n1. Atacar\n2. Usar habilidad\n3. Usar item");
                    decisionP2 = Integer.parseInt(sc.nextLine());

                    if (decisionP2 == 1) {
                        //Lógica de ataque
                        p2.getCharacters().get(decisionP2c).attack(p1.getCharacters().get(decisionP1c));
                        condition = false;
                    } else if (decisionP2 == 2) {
                        // Lógica de habilidad
                        logger.info("{} eligiendo habilidad", decisionP2c);
                        if (p2.getCharacters().get(decisionP2c).getInventory().getAbilities().size() > 0) {
                            System.out.println("Elige la habilidad:");
                            System.out.println(p2.getCharacters().get(decisionP2c).getInventory().getAbilities());
                            decisionP2a = Integer.parseInt(sc.nextLine()) - 1;
                            logger.info("Buscando habilidad con índice {}", decisionP2a);
                            if(decisionP2a >= 0 && decisionP2a < p2.getCharacters().get(decisionP2c).getInventory().getAbilities().size()){
                            abi = p2.getCharacters().get(decisionP2c).getInventory().getAbilities().get(decisionP2a);
                            logger.info("{} usa habilidad {} contra {}", decisionP2c, abi.getName(), decisionP1c);
                            p2.getCharacters().get(decisionP2c).useHability(abi, p1.getCharacters().get(decisionP1c));
                            p2.getCharacters().get(decisionP2c).getInventory().getAbilities().remove(abi);
                            condition = false;
                            } else {
                                System.out.println("Número de habilidad inválido");
                                continue;
                            }
                        } else {
                            System.out.println("No hay habilidades disponibles");
                        }
                    } else if (decisionP2 == 3) {
                        // Lógica de items
                        if (p2.getCharacters().get(decisionP2c).getInventory().getItems().size() > 0) {
                            System.out.println("Elige el item:");
                            System.out.println(p2.getCharacters().get(decisionP2c).getInventory().getItems());
                            decisionP2i = Integer.parseInt(sc.nextLine()) - 1;
                            if (decisionP2i >= 0 && decisionP2i < p2.getCharacters().get(decisionP2c).getInventory()
                                    .getItems().size()) {
                                item = p2.getCharacters().get(decisionP2c).getInventory().getItems().get(decisionP2i);
                                logger.info("{} usa item {}", decisionP2c, item.getName());

                                // Aplicar efecto del item
                                if (item.getHeal() > 0) {
                                    p2.getCharacters().get(decisionP2c).increaseHp(item.getHeal());
                                    System.out.println(p2.getCharacters().get(decisionP2c).getName() + " se curó "
                                            + item.getHeal() + " HP");
                                    p2.getCharacters().get(decisionP2c).getInventory().getItems().remove(item);
                                }
                                if (item.getPlus() > 0) {
                                    p2.getCharacters().get(decisionP2c).increaseMp(item.getPlus());
                                    System.out.println(p2.getCharacters().get(decisionP2c).getName() + " aumentó "
                                            + item.getPlus() + " MP");
                                    p2.getCharacters().get(decisionP2c).getInventory().getItems().remove(item);
                                }

                                // Continuar con el combate
                                System.out.println("Ahora elige tu acción de combate:");
                                continue;
                            } else {
                                System.out.println("Número de item inválido");
                                continue;
                            }
                        } else {
                            System.out.println("No hay items disponibles");
                        }
                    } else {
                        logger.error("Selección inválida");
                        System.out.println("Elija una opción válida.");
                    }
                }

                // Verificar si el jugador 1 perdió al personaje
                if (p1.getCharacters().get(decisionP1c).getHp() <= 0) {
                    System.out.println(p1.getCharacters().get(decisionP1c).getName() + " ha muerto.");
                    p1.getCharacters().remove(decisionP1c);
                    livesP1--;
                    // Sube hp y mp al personaje de jugador 2
                    logger.info("Jugador 2 recibe boost para {}", p2.getCharacters().get(decisionP2c).getName());
                    p2.getCharacters().get(decisionP2c).increaseHp(25);
                    p2.getCharacters().get(decisionP2c).increaseMp(30);
                    System.out.println(
                            p2.getCharacters().get(decisionP2c).getName()
                                    + " ha incrementado su salud y mana por kill");

                    // reinicia el ciclo de juego

                    if (livesP1 <= 0)
                        continue;
                }
            }
           

            if (livesP1 > livesP2) {
                return "\nThe winner is " + p1.getName();
            } else if (livesP2 > livesP1) {
                return "\nThe winner is " + p2.getName();
            } else {
                return "\nEmpate";
            }
        }
    }

}

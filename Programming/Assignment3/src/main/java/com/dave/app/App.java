/*
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package src.main.java.com.dave.app;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import src.main.java.com.dave.exceptions.InvalidLocalDateException;
import src.main.java.com.dave.exceptions.InvalidUIDException;

public class App {

        public static void main(String[] args) throws InvalidLocalDateException, InvalidUIDException {
                try {

                        // Create player object and 3 achievements and add the achievements to the
                        // player
                        Player playerObject = new Player("Dawid Szczesny", Country.Ireland, LocalDate.of(2022, 2, 2));

                        Achievement achievement1 = new Achievement("Award for Bravery",
                                        "Awarded for Bravery during WW2",
                                        LocalDate.of(2012, 10, 20), playerObject.getID());
                        Achievement achievement2 = new Achievement("Citizens Award", "Awarded for being a good citizen",
                                        LocalDate.of(2012, 10, 20), playerObject.getID());
                        Achievement achievement3 = new Achievement("Award for Bravery",
                                        "Awarded for Bravery during WW2",
                                        LocalDate.of(2012, 10, 20), playerObject.getID());

                        playerObject.addAchievement(achievement1);
                        playerObject.addAchievement(achievement2);
                        playerObject.addAchievement(achievement3);

                        // Serialize player object
                        Player.writeObject(playerObject);

                        // Deserialize player object
                        List<Player> readInPlayerList = Player.readObject();
                        for (Player player : readInPlayerList) {
                                System.out.println(player);
                        }

                } catch (InvalidLocalDateException | IOException | InvalidUIDException e) {
                        e.printStackTrace();
                }
        }

}

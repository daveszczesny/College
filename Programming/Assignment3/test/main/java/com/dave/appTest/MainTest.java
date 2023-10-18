/*
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package test.main.java.com.dave.appTest;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import src.main.java.com.dave.app.Achievement;
import src.main.java.com.dave.app.Country;
import src.main.java.com.dave.app.Player;
import src.main.java.com.dave.exceptions.InvalidLocalDateException;
import src.main.java.com.dave.exceptions.InvalidUIDException;

public class MainTest {

    /**
     * Test to check correct Achievement creation
     * including null checks
     */

    @Test
    public void TestFor_ValidAchievementCreation() {

        // Checks for invalid achievement name
        assertThrows(NullPointerException.class, () -> {
            new Achievement(null, "description", LocalDate.now(), "00000000");
        });

        // Checks for invalid description of achievement
        assertThrows(NullPointerException.class, () -> {
            new Achievement("Royal Award", null, LocalDate.now(), "00000000");
        });

        // Checks for invalid date of award
        assertThrows(NullPointerException.class, () -> {
            new Achievement("An Award", "description", null, "00000000");
        });

        // Checks for invalid date of award (Awarded in the future)
        assertThrows(InvalidLocalDateException.class, () -> {
            new Achievement("An Award", "description", LocalDate.of(2025, 12, 10), "00000000");
        });

        // Checks for invalid player UID
        assertThrows(InvalidUIDException.class, () -> {
            new Achievement("An Award", "description", LocalDate.of(2022, 12, 10), "123");
        });

        // Checks for invalid player UID
        assertThrows(IllegalArgumentException.class, () -> {
            new Achievement("An Award", "description", LocalDate.of(2022, 12, 10), "");
        });

    }

    /**
     * Test to check correct player creation,
     * including null checks
     */
    @Test
    public void TestFor_ValidPlayerCreation() {
        // Checks for null username
        assertThrows(IllegalArgumentException.class, () -> {
            new Player(null, Country.Ireland, LocalDate.now());
        });

        // Checks for null country
        assertThrows(NullPointerException.class, () -> {
            new Player("Dawid Szczesny", null, LocalDate.now());
        });

        // Checks for invalid joining date
        assertThrows(NullPointerException.class, () -> {
            new Player("Dawid Szczesny", Country.Ireland, null);
        });

        // Checks for invalid joing date (Join date in the future)
        assertThrows(InvalidLocalDateException.class, () -> {
            new Player("Dawid Szczesny", Country.Ireland, LocalDate.of(2025, 12, 10));
        });

    }

    /**
     * Test to check that a player can add an achievement after creation, and can retrieve it
     */
    @Test
    public void TestFor_SuccesfulCreationAndAdditionOfAchievement() {
        try {
            Player player = new Player("Dawid Szczesny", Country.Ireland, LocalDate.of(2023, 2, 2));

            // Check to ensure player has no achievements
            assertTrue(player.getAchievements().size()==0);

            // Adding an achievement to player
            Achievement achievement = new Achievement("Royal Award", "Awarded by King Charles III",
                    LocalDate.of(2023, 2, 10), player.getID());
            player.addAchievement(achievement);

            // Checking that player now has one achievement
            assertTrue(player.getAchievements().size() == 1);

            // Checking that that achievement can be read correctly
            assertTrue(player.getAchievements().get(0).toString()
                    .equals("Achievement: Royal Award, Awarded by King Charles III - Awarded on 2023-02-10"));
            assertTrue(player.getAchievements().get(0).getPlayerUID().equals(player.getID()));

        } catch (InvalidLocalDateException | InvalidUIDException e) {
            e.printStackTrace();
        }

    }

    /**
     * Test to check that player class when serialized creates plauer.ser file
     * and to check that it reads the player back succesfully
     */
    @Test
    public void TestFor_SuccessfulFileSerialization() {
        try {
            Player player = new Player("Dawid Szczesny", Country.Ireland, LocalDate.of(2023, 2, 2));

            Player.writeObject(player);

            // Checks if player.ser file exists
            String fileName = "player.ser";
            File file = new File(fileName);
            assertTrue(file.exists());

            Player player2 = Player.readObject().get(0);
            
            // Checks that the player object is equal before serialization and after deserizalization
            assertTrue(player.getID().equals(player2.getID()));
            assertTrue(player.getCountry().equals(player2.getCountry()));
            assertTrue(player.getJoinDate().equals(player2.getJoinDate()));

        } catch (InvalidLocalDateException | IOException | InvalidUIDException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test to check that multiple player objects can be wrote and read to
     */
    @Test
    public void TestFor_MultiplePlayerObjectSerialization_AND_Deserialization() {
        try {
            // Create 5 player objects
            Player playerObject1 = new Player("Dawid Szczesny", Country.Ireland, LocalDate.of(2022, 2, 2));
            Player playerObject2 = new Player("Tom Smith", Country.Spain, LocalDate.of(2022, 2, 2));
            Player playerObject3 = new Player("Craigh Tim", Country.America, LocalDate.of(2021, 5, 2));
            Player playerObject4 = new Player("Faith Szczesna", Country.Ireland, LocalDate.of(2002, 2, 2));
            Player playerObject5 = new Player("Winosa Wino", Country.Ireland, LocalDate.of(2012, 2, 22));

            // Create achievements for the player objects
            Achievement achievement1 = new Achievement("Award for Bravery", "Awarded for Bravery during WW2",
                    LocalDate.of(2012, 10, 20), playerObject1.getID());
            Achievement achievement2 = new Achievement("Citizens Award", "Awarded for being a good citizen",
                    LocalDate.of(2012, 10, 20), playerObject1.getID());
            Achievement achievement3 = new Achievement("Award for Bravery", "Awarded for Bravery during WW2",
                    LocalDate.of(2012, 10, 20), playerObject1.getID());
            Achievement achievement4 = new Achievement("Stewardship award", "Awarded for being a steward",
                    LocalDate.of(2012, 10, 20), playerObject1.getID());
            Achievement achievement5 = new Achievement("Citizens Award", "Awarded for being a good citizen",
                    LocalDate.of(2012, 10, 20), playerObject1.getID());
            Achievement achievement6 = new Achievement("Citizens Award", "Awarded for being a good citizen",
                    LocalDate.of(2010, 10, 20), playerObject1.getID());

            // Add achievements to the player objects
            playerObject1.addAchievement(achievement1);
            playerObject2.addAchievement(achievement2);
            playerObject2.addAchievement(achievement3);
            playerObject3.addAchievement(achievement4);
            playerObject4.addAchievement(achievement5);
            playerObject5.addAchievement(achievement6);

            // Add player objects to a list
            List<Player> playerList = new ArrayList<>();
            playerList.add(playerObject1);
            playerList.add(playerObject2);
            playerList.add(playerObject3);
            playerList.add(playerObject4);
            playerList.add(playerObject5);

            // Serializes list
            Player.writeObject(playerList);

            // read list
            List<Player> readInPlayerList = Player.readObject();

            for (int i = 0; i < readInPlayerList.size(); i++) {
                assertTrue(readInPlayerList.get(i).toString().equals(playerList.get(i).toString()));

                System.out.println(readInPlayerList.get(i).getAchievements().size() + " - "
                        + playerList.get(i).getAchievements().size());

                for (int j = 0; j < readInPlayerList.get(i).getAchievements().size(); j++) {
                    assertTrue(
                            readInPlayerList.get(i).getAchievements().get(j).toString().equals(
                                    playerList.get(i).getAchievements().get(j).toString()));

                }
            }

        } catch (InvalidLocalDateException | IOException | InvalidUIDException e) {
            e.printStackTrace();
        }

    }

}

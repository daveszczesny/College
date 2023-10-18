/*
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package src.main.java.com.dave.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import src.main.java.com.dave.exceptions.InvalidLocalDateException;
import src.main.java.com.dave.exceptions.InvalidUIDException;

/**
 * The Player class represents a player with attributes, username, country, join
 * date, id, and achievements
 * The Player class is custom serializable to write its attributes (except its
 * achievements)
 * The achievements are instead wrote into an achievements.csv file in the
 * format [player id, achievement name, desc, date of award]
 * Deserialization then reads all the attributes (except achievements).
 * Achievements are read from a Scanner, reading each line in the csv as a
 * different achievement
 * Achievements are matched from player id, and then are assigned to the player
 * 
 */
public class Player implements Serializable {
    private String id;
    private String username;
    private Country country;
    private LocalDate joinDate;
    // transient making it unserializable
    private transient List<Achievement> achievements = new ArrayList<>();

    public Player() {
    }

    public Player(String username, Country country, LocalDate joinDate, List<Achievement> achievements)
            throws InvalidLocalDateException {
        setUID();
        setUsername(username);
        setCountry(country);
        setJoinDate(joinDate);
        setAchievements(achievements);
    }

    public Player(String username, Country country, LocalDate joinDate)
            throws InvalidLocalDateException {
        setUID();
        setUsername(username);
        setCountry(country);
        setJoinDate(joinDate);
    }

    // Getters and Setter methods
    public String getID() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Country getCountry() {
        return country;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    // Sets a random unique ID to the player
    public void setUID() {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString().replaceAll("-", "").substring(0, 8);
    }

    /**
     * Sets username attribute
     * @param username
     * @throws IllegalArgumentException
     */
    public void setUsername(String username) {
        if (username == null || username.equals("")) {
            throw new IllegalArgumentException("Username cannot be null or empty!");
        }

        this.username = username;
    }

    /**
     * Sets the country enum attribute
     * @param country
     * @throws NullPointerException
     */
    public void setCountry(Country country) {
        if (country == null) {
            throw new NullPointerException("Country cannot be null!");
        }

        this.country = country;
    }

    /**
     * Sets the date attribute
     * 
     * @param joinDate
     * @throws NullPointerException
     * @throws InvalidLocalDateException
     */
    public void setJoinDate(LocalDate joinDate) throws InvalidLocalDateException {
        if (joinDate == null) {
            throw new NullPointerException("Join date cannot be null!");
        }

        if (joinDate.isAfter(LocalDate.now())) {
            throw new InvalidLocalDateException("Join date cannot be in the future");
        }

        this.joinDate = joinDate;
    }

    /**
     * Sets a List of achievements, allows for null allocation
     * 
     * @param achievements
     * @throws NullPointerException
     */
    public void setAchievements(List<Achievement> achievements) {
        if (achievements == null) {
            throw new NullPointerException();
        }
        this.achievements = new ArrayList<>(achievements);
    }

    /**
     * Adds an achievement
     * 
     * @param achievement
     * @throws NullPointerException
     */
    public void addAchievement(Achievement achievement) {
        if (achievement == null) {
            throw new NullPointerException("Cannot add a null achievement!");
        }

        this.achievements.add(achievement);
    }

    /**
     * Writes a single player object as a list to serialzed file player.ser
     * Then writes the player objects achievements into a csv
     * 
     * @param player
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void writeObject(Player player) throws FileNotFoundException, IOException {
        String fileName = "player.ser";
        FileOutputStream file = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(file);

        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        out.writeObject(playerList);

        try (PrintWriter writer = new PrintWriter("achievements.csv")) {
            for (Achievement achievement : player.getAchievements()) {
                writer.println(player.getID() + "," + achievement.toCSVString());
            }
        }

        out.flush();
        out.close();
        file.close();

    }

    /**
     * Writes a list of player objects to serialized file player.ser
     * Then writes each of their achievements into an achievements csv file (Comma separated value)
     * 
     * @param players
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void writeObject(List<Player> players) throws FileNotFoundException, IOException {
        String fileName = "player.ser";
        FileOutputStream file = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(file);

        out.writeObject(players);

        try (PrintWriter writer = new PrintWriter("achievements.csv")) {

            for (Player player : players) {
                for (Achievement achievement : player.getAchievements()) {
                    writer.println(player.getID() + "," + achievement.toCSVString());
                }
            }

        }

        out.flush();
        out.close();
        file.close();
    }

    /**
     * Reads the list of players from the serialized file player.ser
     * Then it reads the achievements file, and assigns each achievement to a player given their unique ID
     * 
     * @return List<Player>
     * @throws InvalidLocalDateException
     * @throws InvalidUIDException
     */
    public static List<Player> readObject() throws InvalidLocalDateException, InvalidUIDException {
        String fileName = "player.ser";
        List<Player> players = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(fileName); ObjectInputStream in = new ObjectInputStream(file)) {

            players = (List<Player>) in.readObject();
            for (Player player : players) {
                player.achievements = new ArrayList<>();
                try (Scanner scanner = new Scanner(new File("achievements.csv"))) {
                    while (scanner.hasNextLine()) {
                        // Each achievement is wrote as a CSV per each line
                        // We divide each line into its separate sections
                        String[] parts = scanner.nextLine().split(",");

                        // First section is the owner of that achievement's ID
                        if (parts[0].equals(player.getID())) {
                            // We format the date, as when written its in YYYY-MM-DD,
                            // We format it to [YYYY], [MM], [DD]
                            String[] dateFormat = parts[3].replaceAll(" ", "").split("-");
                            Achievement achievement = new Achievement(parts[1], parts[2],
                                    LocalDate.of(Integer.parseInt(dateFormat[0]), Integer.parseInt(dateFormat[1]),
                                            Integer.parseInt(dateFormat[2])),
                                    parts[0]);
                            player.addAchievement(achievement);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return players;
    }       

    @Override
    public String toString()
    {
        return String.format("Player: UID - %s, Username - %s is from %s, and has %d achievements", 
                getID(), getUsername(), getCountry().toString(), getAchievements().size());
    }

}
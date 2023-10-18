/*
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package src.main.java.com.dave.app;
import java.time.LocalDate;

import src.main.java.com.dave.exceptions.InvalidLocalDateException;
import src.main.java.com.dave.exceptions.InvalidUIDException;

public class Achievement
{

    private String achievementName;
    private String description;
    private LocalDate dateOfAward;
    private String playerUID;


    /**
     * 
     * @param achievementName
     * @param description
     * @param dateOfAward
     * @throws InvalidLocalDateException
     */
    public Achievement(String achievementName, String description, LocalDate dateOfAward, String playerUID)
        throws InvalidLocalDateException, InvalidUIDException
    {
        setAchievementName(achievementName);
        setDescription(description);
        setDateOfAward(dateOfAward);
        setPlayerUID(playerUID);
    }


    /**
     * 
     * Sets the achievement name 
     * 
     * @param name
     * @thorws NullPointerException
     */
    public void setAchievementName(String name)
    {
        if(name == null || name.equals(""))
        {
            throw new NullPointerException("Achievement name cannot be null!");
        }

        this.achievementName = name;
    }

    /**
     * Sets description of achievement
     * 
     * @param desc
     * @throws NullPointerExceptiom
     */
    public void setDescription(String desc)
    {
        if(desc == null || desc.equals(""))
        {
            throw new NullPointerException("Description of achievement cannot be null!");
        }

        this.description = desc;
    }

    /**
     * Sets the date awarded 
     * 
     * @param date
     * @throws NullPointerException
     * @throws InvalidLocalDateException
     */
    public void setDateOfAward(LocalDate date) throws InvalidLocalDateException
    {
        if(date == null)
        {
            throw new NullPointerException("Date of Award cannot be null!");
        }
        if(date.isAfter(LocalDate.now()))
        {
            throw new InvalidLocalDateException("Date of Award cannot be in the future!");
        
        }

        this.dateOfAward = date;
    }

    /**
     * Sets the player UID to an achievement
     * 
     * @param uid
     * @throws IllegalArgumentException
     * @throws InvalidUIDException
     */
    public void setPlayerUID(String uid) throws InvalidUIDException
    {
        if(uid == null || uid.equals(""))
        {
            throw new IllegalArgumentException("Player UID cannot be null or empty!");
        }

        if(uid.length() < 8){
            throw new InvalidUIDException("Player UID is not valid!");
        }


        this.playerUID = uid;
    }

    /**
     * 
     * @return playerUID
     */
    public String getPlayerUID()
    {
        return this.playerUID;
    }

    /**
     * 
     * @return achievementName
     */
    public String getAchievementName()
    {
        return achievementName;
    }

    /**
     * 
     * @return description
     */
    public String getAchievementDescription()
    {
        return description;
    }

    /**
     * 
     * @return dateOfAward
     */
    public LocalDate getDateOfAward()
    {
        return dateOfAward;
    }


    @Override
    public String toString()
    {
        return String.format("Achievement: %s, %s - Awarded on %s", 
                getAchievementName(), getAchievementDescription(), getDateOfAward().toString());
    }

    // To string method for CSV files.
    public String toCSVString()
    {
        String output = getAchievementName()+","+getAchievementDescription()+","+getDateOfAward();
        return output;
    }

}
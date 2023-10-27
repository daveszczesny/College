/**
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package com.dave.album;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Song {

    // Attributes
    private int index;
    private String name;
    private String length;

    /**
     * Constructor
     * 
     * @param index
     * @param name
     * @param length
     */
    public Song(int index, String name, String length) {
        setIndex(index);
        setName(name);
        setLength(length);
    }

    /**
     * Method to set name of song
     * 
     * @param name
     * @throws IllegalArgumentException
     */
    public void setName(String name) {

        if (name == null || name.equals("")) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    /**
     * Method to set the length of song
     * 
     * @param name
     * @throws IllegalArgumentException
     */
    public void setLength(String length) {

        if (length == null || length.equals("")) {
            throw new IllegalArgumentException();
        }

        this.length = length;
    }


    /**
     * Method to set the index
     * @param index
     * @throws IllegalArgumentException
     */
    public void setIndex(int index){
     
        if(index <= 0){
            throw new IllegalArgumentException("Index cannot be 0 or a negative");
        }

        this.index = index;
    }

    // Getter methods
    /**
     * Method to get the name of song
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method to get the length of song
     * @return
     */
    public String getLength() {
        return this.length;
    }


    /**
     * Method to get index of song
     * @return
     */
    public int getIndex(){
        return this.index;
    }

    /*
     * To string method override,
     * Print song in NAME, MIN:SEC
     */
    @Override
    public String toString() {
        return String.format("%d, %s, %s",getIndex(), getName(), getLength());
    }

    /**
     * Static method to retrieve all the songs from a given file
     * 
     * @param file
     * @return List<Song>
     * @throws IOException
     */
    public static List<Song> retrieveSongsFromFile(File file) throws IOException {
        List<Song> songs = new ArrayList<>();
        int counter = 1;
        if (!file.exists()) {
            throw new FileNotFoundException("Songs file not found");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] fileData = line.split(",");

                // Format time so its in minutes : seconds
                String[] time = fileData[1].replaceAll(" ", "").split(":");
                LocalTime temp = LocalTime.of(0, Integer.parseInt(time[0]), Integer.parseInt(time[1]));
                String formattedTime = DateTimeFormatter.ofPattern("mm:ss").format(temp);

                // Create song instance, increase counter, and add to list
                Song song = new Song(counter, fileData[0], formattedTime);
                counter++;
                songs.add(song);
            }
        }

        return songs;
    }
}

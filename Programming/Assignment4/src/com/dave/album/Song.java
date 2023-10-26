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

    private int index;
    private String name;
    private String length;

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

    // Getter methods
    public String getName() {
        return this.name;
    }

    public String getLength() {
        return this.length;
    }


    public void setIndex(int index){
        this.index = index;
    }
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
     * @return
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

                Song song = new Song(counter, fileData[0], formattedTime);
                counter++;
                songs.add(song);
            }
        }

        return songs;
    }
}

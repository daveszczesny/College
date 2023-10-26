package com.dave.album;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.StringBuilder;

/*
 * Class to store the albums from music library
 */
public class Album {

    private static final Dimension IMAGE_SIZE = new Dimension(200, 200);

    private List<Song> songsList = new ArrayList<>();
    private String artist;
    private String albumName;
    private ImageIcon albumImage;

    public Album() {

    }

    public Album(String artist) {
        setArtist(artist);
    }

    public Album(String artist, String albumName) {
        setArtist(artist);
        setAlbumName(albumName);
    }

    public Album(String artist, String albumName, ImageIcon albumIcon) {
        setArtist(artist);
        setAlbumName(albumName);
        setAlbumImage(albumIcon);
    }

    public static List<Album> LoadAlbumsFromFile(File file) throws IOException {
        List<Album> albums = new ArrayList<>();

        // If file does not exist
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        // music library exists, read file
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                // split file by csv splitter
                String[] fileData = line.split(",");

                // create new instance of album and populate
                Album album = new Album();
                album.setArtist(fileData[0]); // artist name
                album.setAlbumName(fileData[1]); // album name
                ImageIcon image = retrieveSizedImageIcon(new File(fileData[2].replaceAll(" ", ""))); // album cover image
                album.setAlbumImage(image);
                album.setSongsList(Song.retrieveSongsFromFile(new File(fileData[3].replaceAll(" ", "")))); // Songs list
                albums.add(album); // add album to album list
            }
        }

        return albums;
    }

    /**
     * Method to take file for album image and create a scaled image icon for it
     * 
     * @param file
     * @return ImageIcon
     * @throws IOException
     */
    public static ImageIcon retrieveSizedImageIcon(File file) throws IOException {
        if (!file.exists()) {
            System.err.println("Can't find file: " + file.getAbsolutePath());
            throw new FileNotFoundException("Image icon file not found!");
        }

        BufferedImage bi = ImageIO.read(file);
        Image dimg = bi.getScaledInstance((int) IMAGE_SIZE.getWidth(), (int) IMAGE_SIZE.getHeight(),
                Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);
    }

    /**
     * Set artist for album
     * 
     * @param artist
     * @throws IllegalArgumentException
     */
    public void setArtist(String artist) {
        if (artist == null || artist.equals("")) {
            throw new IllegalArgumentException();
        }

        this.artist = artist;
    }

    /**
     * Get artist name
     * 
     * @return
     */
    public String getArtist() {
        return this.artist;
    }

    /**
     * Sets the name of the album
     * 
     * @param albumName
     * @thorws IllegalArgumentException
     */
    public void setAlbumName(String albumName) {
        if (albumName == null || albumName.equals("")) {
            throw new IllegalArgumentException();
        }
        this.albumName = albumName;
    }

    public String getAlbumName() {
        return this.albumName;
    }

    /**
     * Set the icon image for the album
     * 
     * @param albumImage
     * @throws NullPointerExceptiom
     */
    public void setAlbumImage(ImageIcon albumImage) {
        if (albumImage == null) {
            throw new NullPointerException();
        }

        this.albumImage = albumImage;
    }

    public ImageIcon getAlbumImage() {
        return this.albumImage;
    }

    /**
     * Add song to album list
     * 
     * @param song
     * @throws IllegalArgumentException
     */
    public void addSong(Song song) {
        if (song == null) {
            throw new IllegalArgumentException();

        }
        songsList.add(song);
    }

    /**
     * Overrides songs list
     * 
     * @param songs
     * @throws NullPointerException
     */
    public void setSongsList(List<Song> songs) {

        if (songs == null) {
            throw new NullPointerException();
        }

        this.songsList = songs;
    }

    /**
     * Return album songs
     * 
     * @return
     */
    public List<Song> getAlbumSongs() {
        return this.songsList;
    }

    @Override
    public String toString() {
        // Concatenates all the songs in the album to print
        StringBuilder sb = new StringBuilder();
        sb.append(getAlbumName()).append(" by ").append(getArtist()).append("\n");
        for (int i = 0; i < songsList.size(); i++) {
            sb.append((i + 1)).append(",. ").append(songsList.get(i)).append("\n");
        }
        sb.append("\n\n");

        return sb.toString();
    }

}

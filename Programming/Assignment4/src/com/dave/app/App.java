/**
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package com.dave.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.dave.album.Album;
import com.dave.album.Song;

/**
 * Starting point of program
 * Creates Window and different panels
 */
public class App extends JFrame {

    // Window size constant to define the window dimension
    private final Dimension WINDOW_SIZE = new Dimension(800, 800);

    // Album list used to display for music library
    private List<Album> albumList = new ArrayList<>();
    private JPanel buttonPanel = null;

    public App() {

        loadAlbums();
        setupWindow();

    }

    /**
     * Loads albums from path
     */
    private void loadAlbums() {
        // Relative path to music library file
        String path = "Resources/music_library.csv";
        try {
            // retrieve albums from file
            albumList = Album.LoadAlbumsFromFile(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method Sets up the albums in music library
     * Each album is loaded as a button
     * Each button has an OnClick event, so that it unloads the previous panel from
     * the
     * display and loads the albums song panel to screen
     * Method takes a reference to the main panel container
     * 
     * @param panelContainer
     */
    private void setupAlbumButtons(Container panelContainer) {
        // Loops through each Album
        for (Album album : albumList) {

            // Create an album panel for each album
            JPanel albumPanel = new JPanel(new BorderLayout());

            // Create a button for each album panel and set bg color to white
            JButton button = new JButton("", album.getAlbumImage());
            button.setBackground(Color.white);

            // Text label for album name and artist
            JLabel label = new JLabel(album.getAlbumName() + " by " + album.getArtist());
            label.setFont(new Font("ariial", 0, 18));
            JPanel labelPanel = new JPanel(new BorderLayout());
            labelPanel.add(label);
            albumPanel.add(labelPanel, BorderLayout.NORTH); // add to top of panel

            // Create the table for each album
            JTable table = createTable(album);
            table.setEnabled(false); // make non-editible

            // Set the size of the table, and turn of grids
            table.setPreferredScrollableViewportSize(
                    new Dimension((int) WINDOW_SIZE.getWidth() - 25, (int) WINDOW_SIZE.getHeight() - 100));
            table.setFillsViewportHeight(true);
            table.setShowGrid(false);

            // Create a panel for table and set it to cemter
            JPanel tablePanel = new JPanel();
            tablePanel.add(new JScrollPane(table));
            albumPanel.add(tablePanel, BorderLayout.CENTER);

            // Create a button back to return to main music library
            // Set it to the bottom of the display
            JButton backButton = new JButton("Back");
            albumPanel.add(backButton, BorderLayout.SOUTH);

            // Set the default view for the album panel to invisible
            albumPanel.setVisible(false);

            /**
             * Button onclick event
             * When button pressed
             * Main container will remove previous panel
             * Panel of the album clicked becomes visible and is added to container
             * the main container is then repainted
             */
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    panelContainer.removeAll();
                    albumPanel.setVisible(true);
                    panelContainer.add(albumPanel);
                    panelContainer.revalidate();
                    panelContainer.repaint();
                }

            });

            /*
             * Back button onclick event
             * When back button is clicked
             * Main container will remove previous panel
             * and it will add the default album button panel that was originally loaded at
             * the start
             * it will then repaint to show the user
             */
            backButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    panelContainer.removeAll();
                    buttonPanel.setVisible(true);
                    panelContainer.add(buttonPanel);
                    panelContainer.revalidate();
                    panelContainer.repaint();
                }

            });

            // We add the button to button panel
            buttonPanel.add(button);
        }
    }

    /**
     * Method to create table, takes in album and retrieves from it the index of
     * song, song name and length
     * 
     * @param album
     * @return JTable
     */
    private JTable createTable(Album album) {
        String[] colNames = {
                "No.", "Track name", "Length"
        };

        List<Song> songs = album.getAlbumSongs();

        String[][] data = new String[songs.size()][3];
        for (int i = 0; i < songs.size(); i++) {
            data[i][0] = String.valueOf(songs.get(i).getIndex());
            data[i][1] = songs.get(i).getName();
            data[i][2] = songs.get(i).getLength();
        }

        return new JTable(data, colNames);
    }

    /**
     * Method to set up JFrame window
     */
    private void setupWindow() {

        // Creates main container for window amd assign grid layout to it
        Container container = getContentPane();
        container.setLayout(new GridLayout(1, 1));

        // Create a panel for the album buttons with a grid layout
        buttonPanel = new JPanel(new GridLayout(0, 3, 50, 15));
        buttonPanel.setVisible(true);

        // Sets up the albumbuttoms (buttonPanel)
        setupAlbumButtons(container);
        container.add(buttonPanel);

        // Set basic window attributes
        setTitle("Music Library");
        setSize((int) WINDOW_SIZE.getWidth(), (int) WINDOW_SIZE.getHeight());
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new App();
    }
}

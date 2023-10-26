package com.dave.app;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;

import com.dave.album.Album;
import com.dave.album.Song;

public class App extends JFrame {

    private List<Album> alubmList = new ArrayList<>();

    private JPanel buttonPanel = null;

    public App() {

        loadAlbums();

        setupWindow();

    }

    private void loadAlbums() {
        String path = "Resources/music_library.csv";
        try {
            alubmList = Album.LoadAlbumsFromFile(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setupAlbumButtons(Container panelContainer)
    {
        for(Album album : alubmList){
            JButton button = new JButton("", album.getAlbumImage());
            button.setBackground(Color.white);
            String[] colNames = {
                "No.", "Track name", "Length"
            };

            List<Song> songs = album.getAlbumSongs();
            
            String[][] data = new String[songs.size()][3];

            for(int i = 0; i < songs.size(); i++){
                data[i][0] = String.valueOf(songs.get(i).getIndex());
                data[i][1] = songs.get(i).getName();
                data[i][2] = songs.get(i).getLength();
            }

            JTable table = new JTable(data, colNames);
            JPanel panel = new JPanel();
            
            panel.add(new JScrollPane(table));
            
            JButton backButton = new JButton("Back");
            panel.add(backButton);

            panel.setVisible(false);

            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    panelContainer.removeAll();
                    panel.setVisible(true);
                    panelContainer.add(panel);
                    panelContainer.revalidate();
                    panelContainer.repaint();
                }
                
            });


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

            buttonPanel.add(button);
        }
    }

    public void actionPerformed(ActionEvent event){

    }

    private void setupWindow() {

        Container container = getContentPane();

        container.setLayout(new GridLayout(1, 1));

        buttonPanel = new JPanel(new GridLayout(0, 3, 50,15 ));
        buttonPanel.setVisible(true);

        setupAlbumButtons(container);
        container.add(buttonPanel);

        setSize(800, 800);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new App();
    }
}

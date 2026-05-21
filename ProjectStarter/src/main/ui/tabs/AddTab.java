package ui.tabs;

import javax.swing.*;

import model.Category;
import model.Music;
import ui.PlayListAppUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the add tab that the user can add a new music to the playlist
public class AddTab extends Tab {
    private static final String STATUS_READY = "Enter a music info and press Add";
    private JLabel status;
    private JTextField songField;
    private JTextField singerField;
    private JComboBox<Category> categoryCombo;

    // MODIFIES: this
    // EFFECTS: constructs the add tab and initializes each components
    public AddTab(PlayListAppUI controller) {
        super(controller);
        setLayout(new BorderLayout());

        status = new JLabel(STATUS_READY, JLabel.CENTER);
        add(status, BorderLayout.NORTH);

        add(makeForm(), BorderLayout.CENTER);
        add(makeButtons(), BorderLayout.SOUTH);
    }

    // EFFECTS: creates and returns the form panel for each inputs
    private JPanel makeForm() {
        JPanel form = new JPanel(new GridLayout(3, 3,9, 9));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        songField = new JTextField();
        singerField = new JTextField();
        categoryCombo = new JComboBox<>(Category.values());

        form.add(new JLabel("Song:"));
        form.add(songField);
        form.add(new JLabel("Singer:"));
        form.add(singerField);
        form.add(new JLabel("Category:"));
        form.add(categoryCombo);

        return form;
    }

    // EFFECTS: creates and returns the button row for adding music 
    private JPanel makeButtons() {
        JButton addButton = new JButton("Add to playlist");

        JPanel row = formatButtonRow(addButton);

        addButton.addActionListener(e -> addMusic());

        return row;
    }

    // EFFECTS: if any input is blank, updates status with an error message,
    //          otherwise adds the entered music to the playlist
    private void addMusic() {
        String song = songField.getText().trim();
        String singer = singerField.getText().trim();
        Category category = (Category) categoryCombo.getSelectedItem();

        if (song.isEmpty() || singer.isEmpty() || category == null) {
            status.setText("Song, singer, and category cannot be blank.");
            return;
        }

        getController().getPlayList().addMusic(new Music(song, singer, category));
        status.setText("Added: " + category + " - " + song + " by " + singer);

        getController().getTabbedPane().setSelectedIndex(PlayListAppUI.VIEW_TAB_INDEX);
    }
}

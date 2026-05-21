package ui.tabs;

import javax.swing.*;

import ui.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the home tab 
public class HomeTab extends Tab {
    private static final String INIT_GREETING = "Create your own playlist!";
    private JLabel greeting;

    // MODIFIES: this
    // EFFECTS: constructs the home tab and places all UI components
    public HomeTab(PlayListAppUI controller) {
        super(controller);

        setLayout(new GridLayout(3, 1));

        placeGreeting();
        placeHomeButtons();
        placeAddButton();
        placeViewButton();
    }

    //EFFECTS: creates greeting at top of console
    private void placeGreeting() {
        greeting = new JLabel(INIT_GREETING, JLabel.CENTER);
        greeting.setSize(WIDTH, HEIGHT / 3);
        this.add(greeting);
    }

    // EFFECTS: creates and adds the save and load button row to this tab
    private void placeHomeButtons() {
        JButton saveButton = new JButton(ButtonNames.SAVE.getValue());
        JButton loadButton = new JButton(ButtonNames.LOAD.getValue());

        JPanel buttonRow = formatButtonRow(saveButton);
        buttonRow.add(loadButton);
        buttonRow.setSize(WIDTH, HEIGHT / 6);

        saveButton.addActionListener(e -> {
            boolean ok = getController().savePlayList();
            greeting.setText(ok ? "Playlist saved successfully" : "Unable to save playlist");
        });

        loadButton.addActionListener(e -> {
            boolean ok = getController().loadPlayList();
            greeting.setText(ok ? "Playlist loaded successfully" : "Unable to load playlist");
        });

        this.add(buttonRow);
    }

    // EFFECTS: creates and adds the "Add" button to this tab
    private void placeAddButton() {
        JPanel statusBlock = new JPanel();
        JButton statusButton = new JButton(ButtonNames.ADD.getValue());
        statusBlock.add(formatButtonRow(statusButton));

        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.ADD.getValue())) {
                    getController().getTabbedPane().setSelectedIndex(PlayListAppUI.ADD_TAB_INDEX);
                }
            }
        });

        this.add(statusBlock);
    }

    // EFFECTS: creates and adds the "View" button to this tab
    private void placeViewButton() {
        JPanel statusBlock = new JPanel();
        JButton statusButton = new JButton(ButtonNames.VIEW.getValue());
        statusBlock.add(formatButtonRow(statusButton));

        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.VIEW.getValue())) {
                    getController().getTabbedPane().setSelectedIndex(PlayListAppUI.VIEW_TAB_INDEX);
                }
            }
        });

        this.add(statusBlock);
    }
}

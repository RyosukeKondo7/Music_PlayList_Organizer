package ui.tabs;

import javax.swing.*;
import java.awt.*;

import ui.PlayListAppUI;

// Represents a tab in the UI
public class Tab extends JPanel {

    private final PlayListAppUI controller;

    // MODIFIES: this
    // EFFECTS: creates a tab and stores a reference to controller
    public Tab(PlayListAppUI controller) {
        this.controller = controller;
    }

    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    //EFFECTS: returns the PlayListUi controller for this tab
    public PlayListAppUI getController() {
        return controller;
    }
}

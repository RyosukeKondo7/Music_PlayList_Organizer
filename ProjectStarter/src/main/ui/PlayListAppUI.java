package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.HomeTab;
import ui.tabs.AddTab;
import ui.tabs.ViewTab;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Represents playList App's UI
public class PlayListAppUI extends JFrame {
    public static final int HOME_TAB_INDEX = 0;
    public static final int ADD_TAB_INDEX = 1;
    public static final int VIEW_TAB_INDEX = 2;

    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/playList.json";
    
    private JTabbedPane topbar;
    private PlayList playList;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    // EFFECTS: constructs playlist app with save and load 
    public PlayListAppUI() {
        super("PlayList");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        playList = new PlayList("My Playlist");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        topbar = new JTabbedPane();
        topbar.setTabPlacement(JTabbedPane.TOP);

        loadTabs();
        add(topbar);

        loadOnStartup();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveOnExit();
            }
        });

        setVisible(true);
    }

    //EFFECTS: returns SmartHome object controlled by this UI
    public PlayList getPlayList() {
        return playList;
    }

    // MODIFIES: this
    // EFFECTS: sets playlist to the given playlist
    public void setPlayList(PlayList playList) {
        this.playList = playList;
    }

    //MODIFIES: this
    //EFFECTS: adds home tab, add tab and view tab to this UI
    private void loadTabs() {
        JPanel homeTab = new HomeTab(this);
        JPanel addTab = new AddTab(this);
        JPanel viewTab = new ViewTab(this);

        topbar.add(homeTab, HOME_TAB_INDEX);
        topbar.setTitleAt(HOME_TAB_INDEX, "Home");
        topbar.add(addTab, ADD_TAB_INDEX);
        topbar.setTitleAt(ADD_TAB_INDEX, "Add");
        topbar.add(viewTab, VIEW_TAB_INDEX);
        topbar.setTitleAt(VIEW_TAB_INDEX, "View");
    }

    //EFFECTS: returns topbar of this UI
    public JTabbedPane getTabbedPane() {
        return topbar;
    }

    // EFFECTS: saves the current playlist to file
    public boolean savePlayList() {
        try {
            jsonWriter.open();
            jsonWriter.write(playList);
            jsonWriter.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads playlist from file (replacing current playlist)
    public boolean loadPlayList() {
        try {
            setPlayList(jsonReader.read());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // EFFECTS: load playlist when this app starts
    private void loadOnStartup() {
        if (JOptionPane.showConfirmDialog(this, "Load playlist from file?", "Load",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (!loadPlayList()) {
                JOptionPane.showMessageDialog(this, "Unable to load from file.", "Load",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // EFFECTS: ask if save any changes to the playlist when close this app
    private void saveOnExit() {
        if (JOptionPane.showConfirmDialog(this, "Save playlist to file before exiting?", "Save",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (!savePlayList()) {
                JOptionPane.showMessageDialog(this, "Unable to save to file.", "Save",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        printEventLog();
    }

    // EFFECTS: prints all logged events to console
    private void printEventLog() {
        EventLog eventLog = EventLog.getInstance();
        for (Event event : eventLog) {
            System.out.println(event.toString());
            System.out.println();
        }
    }
}

package ui.tabs;

import javax.swing.*;

import model.Music;
import ui.PlayListAppUI;

import java.awt.*;
import java.util.List;

// Represents the view tab that displays playlist and chart 
public class ViewTab extends Tab {
    private DefaultListModel<Music> listModel;
    private JList<Music> list;
    private JTextField singerFilterField;
    private JLabel header;
    private CategoryChartPanel chartPanel;

    // MODIFIES: this
    // EFFECTS: constructs the view tab and displays 
    public ViewTab(PlayListAppUI controller) {
        super(controller);
        setLayout(new BorderLayout());

        header = new JLabel("", JLabel.CENTER);
        add(header, BorderLayout.NORTH);

        add(makeCenter(), BorderLayout.CENTER);
        add(makeControls(), BorderLayout.SOUTH);

        refreshAll();
    }

    // EFFECTS: creates and returns the center panel containing the list view and chart view
    private JPanel makeCenter() {
        JTabbedPane views = new JTabbedPane();

        JPanel listView = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        listView.add(new JScrollPane(list), BorderLayout.CENTER);
        views.addTab("List", listView);

        chartPanel = new CategoryChartPanel(getController());
        views.addTab("Chart", chartPanel);

        views.addChangeListener(e -> chartPanel.repaint());

        JPanel center = new JPanel(new BorderLayout());
        center.add(views, BorderLayout.CENTER);
        return center;
    }

    // EFFECTS: creates and returns the controls panel 
    private JPanel makeControls() {
        JPanel controls = new JPanel(new FlowLayout());

        controls.add(new JLabel("Sort by singer:"));
        singerFilterField = new JTextField(12);
        controls.add(singerFilterField);

        JButton filterButton = new JButton("Show");
        JButton showAllButton = new JButton("Show all");
        JButton refreshButton = new JButton("Refresh");
        JButton removeButton = new JButton("Remove selected");

        controls.add(filterButton);
        controls.add(showAllButton);
        controls.add(refreshButton);
        controls.add(removeButton);

        filterButton.addActionListener(e -> refreshFiltered());
        showAllButton.addActionListener(e -> refreshAll());
        refreshButton.addActionListener(e -> refreshAll());
        removeButton.addActionListener(e -> removeSelected());


        return controls;
    }

    // EFFECTS: updates the header and list to show all musics currently in the playlist
    private void refreshAll() {
        List<Music> musics = getController().getPlayList().getPlayList();
        header.setText("All musics in playlist (" + musics.size() + ")");
        setListFromMusics(musics);
    }

    // EFFECTS: if singer filter is blank, shows all musics,
    //          otherwise updates the header and list to show only musics by the given singer
    private void refreshFiltered() {
        String singer = singerFilterField.getText().trim();
        if (singer.isEmpty()) {
            refreshAll();
            return;
        }
        List<Music> selected = getController().getPlayList().sortBySinger(singer);
        header.setText("Musics by \"" + singer + "\" (" + selected.size() + ")");
        setListFromMusics(selected);
    }

    // EFFECTS: replaces the list contents with musics and repaints the chart
    private void setListFromMusics(List<Music> musics) {
        listModel.clear();
        for (Music m : musics) {
            listModel.addElement(m);
        }
    }

     // EFFECTS: removes the selected song from playlist and refreshes view
    private void removeSelected() {
        int index = list.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Please select a song to remove.",
                    "No selection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Music toRemove = listModel.getElementAt(index);
        getController().getPlayList().removeMusic(toRemove);

        if (singerFilterField.getText().trim().isEmpty()) {
            refreshAll();
        } else {
            refreshFiltered();
        }
    }
}

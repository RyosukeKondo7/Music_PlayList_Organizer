package ui.tabs;

import model.Category;
import model.Music;
import ui.PlayListAppUI;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

// Represents a panel that draws a bar chart of category counts in the playlist
public class CategoryChartPanel extends JPanel {
    private final PlayListAppUI controller;
    private static final Color TEXT_COLOR = new Color(25, 25, 25);
    private static final Color BAR_COLOR = new Color(70, 130, 180);
    private static final int MAX_LABEL_LENGTH = 8;

    // MODIFIES: this
    // EFFECTS: constructs the chart panel with a white background and padding
    public CategoryChartPanel(PlayListAppUI controller) {
        this.controller = controller;
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
    }

    // MODIFIES: this
    // EFFECTS: draws bar chart showing the number of music in each music category
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Map<Category, Integer> counts = buildCategoryCounts();
        int max = computeMax(counts);

        drawTitle(g2);
        drawBars(g2, counts, max);
    }

    // EFFECTS: returns a map from each Category to how many songs belong to it
    private Map<Category, Integer> buildCategoryCounts() {
        EnumMap<Category, Integer> counts = new EnumMap<>(Category.class);
        for (Category c : Category.values()) {
            counts.put(c, 0);
        }
        for (Music m : controller.getPlayList().getPlayList()) {
            counts.merge(m.getCategory(), 1, Integer::sum);
        }
        return counts;
    }

    // EFFECTS: returns the maximum count across all categories
    private int computeMax(Map<Category, Integer> counts) {
        return Math.max(Collections.max(counts.values()), 1);
    }

    // EFFECTS: draws the chart title at the top of the panel
    private void drawTitle(Graphics2D g2) {
        g2.setColor(TEXT_COLOR);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
        g2.drawString("Musics by category", 0, 18);
    }

    // EFFECTS: draws one bar per category, with its count label above and name below
    private void drawBars(Graphics2D g2, Map<Category, Integer> counts, int max) {
        int top = 30;
        int bottom = getHeight() - 30;
        int left = 10;
        int right = getWidth() - 10;
        int availableH = Math.max(60, bottom - top);

        int n = Category.values().length;
        int gap = 10;
        int barW = Math.max(18, (right - left - gap * (n - 1)) / n);

        int x = left;
        for (Category c : Category.values()) {
            int count = counts.get(c);
            int barH = (int) Math.round((count / (double) max) * (availableH - 20));
            int barTop = bottom - barH;

            drawBar(g2, x, barTop, barW, barH);
            drawCountLabel(g2, x, barTop, count);
            drawCategoryLabel(g2, x, c.name());

            x += barW + gap;
        }
    }

    // EFFECTS: fills a single bar rectangle with the bar colour
    private void drawBar(Graphics2D g2, int x, int barTop, int barW, int barH) {
        g2.setColor(BAR_COLOR);
        g2.fillRect(x, barTop, barW, barH);
    }

    // EFFECTS: draws the count just above the bar
    private void drawCountLabel(Graphics2D g2, int x, int barTop, int count) {
        g2.setColor(TEXT_COLOR);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 12f));
        g2.drawString(String.valueOf(count), x + 4, barTop - 4);
    }

    // EFFECTS: draws the category name below the chart area
    private void drawCategoryLabel(Graphics2D g2, int x, String name) {
        String label = name.length() > MAX_LABEL_LENGTH ? name.substring(0, MAX_LABEL_LENGTH) : name;
        g2.setColor(TEXT_COLOR);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 12f));
        g2.drawString(label, x, getHeight() - 10);
    }
}

package ui;

public enum ButtonNames {
    GREET("Hello!"),
    VIEW("View Playlist"),
    ADD("Add music"),
    SAVE("Save"),
    LOAD("Load");

    private final String name;

    ButtonNames(String name) {
        this.name = name;
    }

    //EFFECTS: returns name value of this button
    public String getValue() {
        return name;
    }
}

package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents an account having a song name and a singer name
public class Music implements Writable {
    private String song;
    private String singer;
    private Category category;

    // EFFECTS: set given song name and singer name
    public Music(String song, String singer, Category category) {
        this.song = song;
        this.singer = singer;
        this.category = category;
    }

    public String getSong() {
        return song;
    }
    
    public String getSinger() {
        return singer;
    }

    public Category getCategory() {
        return category;
    }

    // EFFECTS: returns string representation of this music
    public String toString() {
        return song + "  :  " + singer + "  :  " + category;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("song", song);
        json.put("singer", singer);
        json.put("category", category);
        return json;
    }
}

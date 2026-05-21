package model;

import model.Music;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents having list of music and playlist title
public class PlayList implements Writable {
    private List<Music> playList;
    private String title;

    // EFFECTS: having empty list of music and set given title name
    public PlayList(String title) {
        playList = new ArrayList<>();
        this.title = title;
    }

    // MODIFIES: this
    // EFFECTS: add music to playList
    public void addMusic(Music music) {
        this.playList.add(music);
        EventLog.getInstance().logEvent(new Event("Added music: "  + music.getSong() + " (" + music.getSinger() + ")"));
    }

    // MODIFIES: this
    // EFFECTS: reset the playList
    public void reset() {
        this.playList.clear();
        EventLog.getInstance().logEvent(new Event("Cleared playlist"));
    }

    // MODIFIES: this
    // EFFECTS: removes the given music from playlist if present
    public void removeMusic(Music music) {
        playList.remove(music);
        EventLog.getInstance().logEvent(new Event("Removed " + music.getSong() + " (" + music.getSinger() + ")"));
    }

    // REQUIRES: playlist is not empty
    // EFFECTS: get list of music by given singer
    public List<Music> sortBySinger(String singer) {
        List<Music> selected = new ArrayList<>();
        for (Music m : playList) {
            if (m.getSinger().equals(singer)) {
                selected.add(m);
            }
        }
        EventLog.getInstance().logEvent(new Event("Filtered playlist by: " +  singer));
        return selected;
    }

    public List<Music> getPlayList() {
        return playList;
    }

    public String getTitle() {
        return title;
    }

    // EFFECTS: returns number of musics in this playlist
    public int numMusics() {
        return playList.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("musics", musicsToJson());
        return json;
    }

    // EFFECTS: returns muisc in this playlist as a JSON array
    private JSONArray musicsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Music m : playList) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }
}

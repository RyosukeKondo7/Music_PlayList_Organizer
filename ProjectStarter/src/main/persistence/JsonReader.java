package persistence;

import model.Category;
import model.Music;
import model.PlayList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Used as reference; github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo 
// Represents a reader that reads playlist from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads playlist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PlayList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses playlist from JSON object and returns it
    private PlayList parsePlayList(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        PlayList pl = new PlayList(title);
        addMusics(pl, jsonObject);
        return pl;
    }

    // MODIFIES: pl
    // EFFECTS: parses music from JSON object and adds them to playlist
    private void addMusics(PlayList pl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("musics");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addMusic(pl, nextThingy);
        }
    }

    // MODIFIES: pl
    // EFFECTS: parses music from JSON object and adds it to playlist
    private void addMusic(PlayList pl, JSONObject jsonObject) {
        String song = jsonObject.getString("song");
        String singer = jsonObject.getString("singer");
        Category category = Category.valueOf(jsonObject.getString("category"));
        Music music = new Music(song, singer, category);
        pl.addMusic(music);
    }
}


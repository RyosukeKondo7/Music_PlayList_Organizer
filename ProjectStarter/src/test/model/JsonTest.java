package model;

import model.Category;
import model.Music;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMusic(String song, String singer, Category category, Music music) {
        assertEquals(song, music.getSong());
        assertEquals(singer, music.getSinger());
        assertEquals(category, music.getCategory());
        assertEquals(category + ": " + song + " by: " + singer, music.toString());
    }
}

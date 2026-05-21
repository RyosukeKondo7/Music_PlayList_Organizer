package model;

import model.Category;
import model.Music;
import model.PlayList;
import persistence.JsonReader;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PlayList pl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlayList.json");
        try {
            PlayList pl = reader.read();
            assertEquals("My playlist", pl.getTitle());
            assertEquals(0, pl.numMusics());
            assertTrue(pl.sortBySinger("A").isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlayList.json");
        try {
            PlayList pl = reader.read();
            assertEquals("My playlist", pl.getTitle());
            List<Music> sorted = pl.sortBySinger("The Weekend");
            assertEquals(1, sorted.size());
            List<Music> playlist = pl.getPlayList();
            assertEquals(2, playlist.size());
            checkMusic("Starboy", "The Weekend", Category.HIPHOP, playlist.get(0));
            checkMusic("Wonderwall", "Oasis", Category.POP, playlist.get(1));

            pl.reset();
            assertTrue(pl.getPlayList().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

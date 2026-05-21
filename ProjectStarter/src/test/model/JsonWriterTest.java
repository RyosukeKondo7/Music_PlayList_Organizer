package model;

import model.Category;
import model.Music;
import model.PlayList;
import persistence.JsonReader;
import persistence.JsonWriter;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            PlayList pl = new PlayList("My playlist");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlayList.json");
            writer.open();
            writer.write(pl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPlayList.json");
            pl = reader.read();
            assertEquals("My playlist", pl.getTitle());
            assertEquals(0, pl.numMusics());
            assertTrue(pl.sortBySinger("A").isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            PlayList pl = new PlayList("My playlist");
            pl.addMusic(new Music("Apple", "B", Category.JAZZ));
            pl.addMusic(new Music("Banana", "C", Category.CLASSICAL));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPlayList.json");
            writer.open();
            writer.write(pl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPlayList.json");
            pl = reader.read();
            assertEquals("My playlist", pl.getTitle());
            List<Music> sorted = pl.sortBySinger("C");
            assertEquals(1, sorted.size());
            List<Music> playList = pl.getPlayList();
            assertEquals(2, playList.size());
            checkMusic("Apple", "B", Category.JAZZ, playList.get(0));
            checkMusic("Banana", "C", Category.CLASSICAL, playList.get(1));

            pl.reset();
            assertTrue(pl.getPlayList().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

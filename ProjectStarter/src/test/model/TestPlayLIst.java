package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPlayLIst {
    private PlayList testPlayList;
    private Music m1;
    private Music m2;
    private Music m3;
    
    @BeforeEach
    public void runBefore() {
        testPlayList = new PlayList("My favorite");
        m1 = new Music("A", "Alex", Category.POP);
        m2 = new Music("B", "Ben", Category.JAZZ);
        m3 = new Music("C", "Alex", Category.POP);
    }

    @Test
    public void testConstructor() {
        assertEquals("My favorite", testPlayList.getTitle());
        assertEquals(0, testPlayList.getPlayList().size());
        assertEquals("A", m1.getSong());
        assertEquals("Alex", m1.getSinger());
        assertEquals(Category.POP, m1.getCategory());
    }

    @Test
    public void testAddSingleMusic() {
        testPlayList.addMusic(m1);
        List<Music> musics = testPlayList.getPlayList();
        assertEquals(1, musics.size());
        assertEquals(m1, musics.get(0));
    }

    @Test
    public void testAddSingleMusicTwice() {
        testPlayList.addMusic(m1);
        testPlayList.addMusic(m1);
        List<Music> musics = testPlayList.getPlayList();
        assertEquals(2, musics.size());
        assertEquals(m1, musics.get(0));
        assertEquals(m1, musics.get(1));
    }

    
    @Test
    public void testAddMultipleMusic() {
        testPlayList.addMusic(m1);
        testPlayList.addMusic(m2);
        testPlayList.addMusic(m3);
        List<Music> musics = testPlayList.getPlayList();
        assertEquals(3, musics.size());
        assertEquals(m1, musics.get(0));
        assertEquals(m2, musics.get(1));
        assertEquals(m3, musics.get(2));
    }

    @Test
    public void testSortByNosuchSinger() {
        testPlayList.addMusic(m1);
        testPlayList.addMusic(m2);
        testPlayList.addMusic(m3);
        List<Music> sorted = testPlayList.sortBySinger("A");
        assertEquals(0, sorted.size());
    }

    @Test
    public void testSortBySinger() {
        testPlayList.addMusic(m1);
        testPlayList.addMusic(m2);
        testPlayList.addMusic(m3);
        List<Music> sorted = testPlayList.sortBySinger("Alex");
        assertEquals(2, sorted.size());
        assertEquals(m1, sorted.get(0));
        assertEquals(m3, sorted.get(1));
    }

    @Test
    public void testReset() {
        testPlayList.addMusic(m1);
        testPlayList.addMusic(m2);
        List<Music> musics = testPlayList.getPlayList();
        assertEquals(2, musics.size());
        assertEquals(m1, musics.get(0));
        assertEquals(m2, musics.get(1));
        testPlayList.reset();
        List<Music> afterReset = testPlayList.getPlayList();
        assertEquals(0, afterReset.size());
    }
}

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BackendTests {

    private Backend backend;
    private Tree_Placeholder treePlaceholder;

    @BeforeEach
    void setUp() {
        treePlaceholder = new Tree_Placeholder();
        backend = new Backend(treePlaceholder);
    }

    @Test
    void roleTest1() {
        // Test reading a sample data file
        assertDoesNotThrow(() -> backend.readData("src/songs.csv"));

        // Check if the tree contains songs by calling getRange with no filters (null)
        List<String> songTitles = backend.getRange(null, null);
        Assertions.assertFalse(songTitles.isEmpty(), "The song list should not be empty after reading data.");

        // Check if the specific song is in the list (for future tester)
        // Assertions.assertTrue(songTitles.contains("Kills You Slowly"), "Kills You Slowly should be in the list.");
        // Verify that the tree contains the last song added (from the mock/placeholder behavior)
        assertNotNull(treePlaceholder.lastAddedSong);
    }

    /**
     * Tester method for the getRange method in Backend class
     */
    @Test
    void roleTest2() {
        // Songs are preloaded in the Tree_Placeholder
        List<String> result = backend.getRange(-6, -4); // Using loudness values that match the provided songs
        assertEquals(3, result.size());
        assertTrue(result.contains("A L I E N S"));
        assertTrue(result.contains("BO$$"));
        assertTrue(result.contains("Cake By The Ocean"));


        // test GetRange with another LastAddedSong
        // Insert a song with a loudness of 6 and then filter for it
        Song newSong = new Song("NewSong", "Artist", "Genre", 2022, 150, 90, 80, 6, 20);
        treePlaceholder.insert(newSong);
        // Filter the songs loudness over 5
        result = backend.getRange(5, null);
        assertEquals(1, result.size());
        assertTrue(result.contains("NewSong"));

        // Test if getrange set the min and max all to 6 works
        result = backend.getRange(6, 6);
        assertEquals(1, result.size());
        assertTrue(result.contains("NewSong"));

        // Test if getRange set to null and null works.
        result = backend.getRange(null, null);
        assertEquals(4, result.size());
        assertTrue(result.contains("NewSong"));
        assertTrue(result.contains("A L I E N S"));
        assertTrue(result.contains("BO$$"));
        assertTrue(result.contains("Cake By The Ocean"));
    }

    /**
     * Tester method for the setFilter method in Backend class
     */
    @Test
    void roleTest3() {
        // Insert a song with BPM 150 (above the threshold)
        Song newSong = new Song("New Song", "Artist", "Genre", 2022, 150, 90, 80, -5, 20);
        treePlaceholder.insert(newSong);

        // Set speed filter at 140 BPM
        List<String> result = backend.setFilter(140);
        assertEquals(2, result.size());
        assertTrue(result.contains("New Song"));
        assertTrue(result.contains("A L I E N S"));

        // Set speed filter at 160 BPM (no songs should pass)
        result = backend.setFilter(160);
        assertTrue(result.isEmpty());
    }

    /**
     * Tester method for the fiveMost method in Backend class
     */
    @Test
    void roleTest4() {
        // Placeholder songs include:
        // A L I E N S: 43
        // BO$$: 81
        // Cake By The Ocean: 77
        // Song1: 90
        // Song1 > BO$$ > Cake By The Ocean > A L I E N S

        // New songs added
        Song newSong1 = new Song("Song1", "Artist 1", "Genre 1", 2020, 120, 50, 90, -5, 25);  // Highest danceability
        treePlaceholder.insert(newSong1);

        List<String> insertCheck = backend.getRange(null, null);

        // Filter five most danceable songs
        List<String> result = backend.fiveMost();
        assertEquals(4, result.size());  // Should return the top 4, since there are 4 songs in the tree
        // See if its is ordered correctly
        List<String> expectedList = Arrays.asList("Song1", "BO$$", "Cake By The Ocean", "A L I E N S");
        assertEquals(result, expectedList);

        assertTrue(result.contains("Song1"));
        assertTrue(result.contains("A L I E N S"));
        assertTrue(result.contains("BO$$"));
        assertTrue(result.contains("Cake By The Ocean"));

        // Filter five most danceable songs after set the filter of bpm 120


    }
}
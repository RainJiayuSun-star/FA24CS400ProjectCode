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
    void readData() {
        /*
        // Since the readData method requires a CSV file and file operations are involved,
        // we'll mock the process or focus on internal logic if possible. However, without I/O,
        // a direct test might be difficult. Focus on insertion into the tree.
        assertDoesNotThrow(() -> backend.readData("src/songs.csv"));

        System.out.println("-----------Read successful-------------");

        // Verify that the tree contains the last song added (from the mock/placeholder behavior)
        assertNotNull(treePlaceholder.lastAddedSong);

         */
            // Test reading a sample data file
            assertDoesNotThrow(() -> backend.readData("src/songs.csv"));

            // Check if the tree contains songs by calling getRange with no filters (null)
            List<String> songTitles = backend.getRange(null, null);
            Assertions.assertFalse(songTitles.isEmpty(), "The song list should not be empty after reading data.");
            
            // Check if a specific song is in the list
            Assertions.assertTrue(songTitles.contains("Love The Way You Lie"), "Love The Way You Lie should be in the list.");
            // Verify that the tree contains the last song added (from the mock/placeholder behavior)
            assertNotNull(treePlaceholder.lastAddedSong);
    }

    @Test
    void getRange() {

    }

    @Test
    void setFilter() {
    }

    @Test
    void fiveMost() {
    }
}
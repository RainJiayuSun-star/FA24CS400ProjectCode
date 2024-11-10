import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class BackendTests {

    private Backend backend;
    private Tree_Placeholder treePlaceholder;

    @BeforeEach
    void setUp() {
        treePlaceholder = new Tree_Placeholder();
        backend = new Backend(treePlaceholder);
    }

    /**
     * Tester method that try to read the sample data file and see if the song has been correctly updated
     */
    @Test
    void roleTest1() {
        // Test reading a sample data file
        assertDoesNotThrow(() -> backend.readData("songs.csv"));

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
    }

    /**
     * The first test after integration. Testing "Load Song File". If the file 
     * correctly loaded, the menu should print out "success". Otherwise, the menu
     * should print out "error".
     */
    @Test 
    void test5_Integration() {
        // ---------------- Valid Filename --------------------//
        String userInput = "songs.csv\nQ"; // User input filename
        TextUITester UITest = new TextUITester(userInput);

        IterableSortedCollection<Song> songCollection = new IterableRedBlackTree<>();
        Backend backend = new Backend(songCollection);
        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(scanner, backend);

        try {
            frontend.loadFile();
        } catch (Exception e) {
            assertTrue(false);
        }
        String menuOutput = UITest.checkOutput();
        assertTrue((menuOutput.toLowerCase()).contains("success"));
        
        // ---------------- Invalid filename ------------------//
        String userInput1 = "afddajdfjkh.csv\nQ"; // user input new filename
        TextUITester UITest_1 = new TextUITester(userInput1);

        IterableSortedCollection<Song> songCollection_1 = new IterableRedBlackTree<>();
        Backend backend_1 = new Backend(songCollection_1);
        Scanner scanner_1 = new Scanner(System.in);
        Frontend frontend_1 = new Frontend(scanner_1, backend_1);

        try {
            frontend_1.loadFile();
        } catch (Exception e) {
            assertTrue(false);
        }
        String menuOutput_1 = UITest_1.checkOutput();        
        assertTrue((menuOutput_1.toLowerCase()).contains("error"));
    }

    /**
     * This tester tests get songs by loudness functionality. Assume user set the range 10 to 50 and two songs
     * with 10 and 50 loudness should be included, since based on the backend function description the filtering 
     * mechanism should be inclusive
     */
    @Test
    void test6_Integration() {
        String userInput = "10\n50\nQ"; // User set the range of getSongs by 0 and 100
        TextUITester UITest = new TextUITester(userInput);

        IterableSortedCollection<Song> songCollection = new IterableRedBlackTree<>();
        Backend backend = new Backend(songCollection);
        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(scanner, backend);

        Song newSong1 = new Song("Song1", "Artist 1", "Genre 1", 2020, 120, 70, 90, 10, 25);  // Highest danceability
        Song newSong2 = new Song("Song2", "Artist 2", "Genre 2", 2020, 100, 30, 90, 50, 25);  // Highest danceability
        Song newSong3 = new Song("Song3", "Artist 3", "Genre 3", 2020, 50, 40, 90, -5, 25);  // Highest danceability

        songCollection.insert(newSong1);
        songCollection.insert(newSong2);
        songCollection.insert(newSong3);
        
        try {
            frontend.getSongs();;
        } catch (Exception e){
            assertTrue(false);
        }
        String menuOutput = UITest.checkOutput();
        assertTrue(menuOutput.contains(newSong1.getTitle())); // loudness 10, within range
        assertTrue(menuOutput.contains(newSong2.getTitle())); // loudness 50, within range
        assertTrue(!menuOutput.contains(newSong3.getTitle())); // loudness -5, outside the range
    }

    /**
     * This tester tests filter songs by speed functionality. Based on the backend function description, the filter
     * range should be exclusive. 
     */
    @Test
    void test7_Integration() {
        String userInput = "-5\nQ"; // User set the speed greater then 5
        TextUITester UITest = new TextUITester(userInput);

        IterableSortedCollection<Song> songCollection = new IterableRedBlackTree<>();
        Backend backend = new Backend(songCollection);
        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(scanner, backend);

        Song newSong1 = new Song("Song1", "Artist 1", "Genre 1", 2020, 120, 70, 90, 10, 25);  // Highest danceability
        Song newSong2 = new Song("Song2", "Artist 2", "Genre 2", 2020, -5, 30, 90, 50, 25);  // Highest danceability
        Song newSong3 = new Song("Song3", "Artist 3", "Genre 3", 2020, 50, 40, 90, -5, 25);  // Highest danceability

        songCollection.insert(newSong1);
        songCollection.insert(newSong2);
        songCollection.insert(newSong3);
        
        try {
            frontend.setFilter(); // setfilter for speed > 5
        } catch (Exception e){
            assertTrue(false);
        }
        String menuOutput = UITest.checkOutput();
        assertTrue(menuOutput.contains(newSong1.getTitle())); 
        assertTrue(!menuOutput.contains(newSong2.getTitle())); 
        assertTrue(menuOutput.contains(newSong3.getTitle())); 
    }


    /**
     * This tester tests Display five most Danceable functionality. Set filter first then display top 5 danceable songs
     */
    @Test
    void test8_Integration() {
        String userInput = "40\nQ"; // User set the range of getSongs by 10 and 50
        TextUITester UITest = new TextUITester(userInput);

        IterableSortedCollection<Song> songCollection = new IterableRedBlackTree<>();
        Backend backend = new Backend(songCollection);
        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(scanner, backend);

        Song newSong1 = new Song("Song1", "Artist 1", "Genre 1", 2020, 120, 70, 10, 10, 25);  
        Song newSong2 = new Song("Song2", "Artist 2", "Genre 2", 2020, -5, 30, 5, 50, 25);  
        Song newSong3 = new Song("Song3", "Artist 3", "Genre 3", 2020, 50, 40, 29, -5, 25);  
        Song newSong4 = new Song("Song4", "Artist 4", "Genre 4", 2020, 60, 40, 15, -5, 25);  
        Song newSong5 = new Song("Song5", "Artist 5", "Genre 5", 2020, 70, 40, 90, -5, 25);  
        Song newSong6 = new Song("Song6", "Artist 6", "Genre 6", 2020, 75, 40, 60, -5, 25);  
        Song newSong7 = new Song("Song7", "Artist 7", "Genre 7", 2020, 75, 40, 5, -5, 25);  

        songCollection.insert(newSong1); // danceability 10
        songCollection.insert(newSong2); // danceability 5
        songCollection.insert(newSong3); // danceability 20
        songCollection.insert(newSong4); // danceability 15
        songCollection.insert(newSong5); // danceability 90
        songCollection.insert(newSong6); // danceability 60
        songCollection.insert(newSong7); // danceability 5
        
        try {
            frontend.displayTopFive();
        } catch (Exception e){
            assertTrue(false);
        }
        String menuOutput = UITest.checkOutput();
        assertTrue(menuOutput.contains(newSong1.getTitle())); 
        assertTrue(!menuOutput.contains(newSong2.getTitle())); // get filtered out by danceability
        assertTrue(menuOutput.contains(newSong3.getTitle()));
        assertTrue(menuOutput.contains(newSong4.getTitle()));
        assertTrue(menuOutput.contains(newSong5.getTitle())); 
        assertTrue(menuOutput.contains(newSong6.getTitle())); 
        assertTrue(!menuOutput.contains(newSong7.getTitle())); // get filtered out by danceability
    }
}
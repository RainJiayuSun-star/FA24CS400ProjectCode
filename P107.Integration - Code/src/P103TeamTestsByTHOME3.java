import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * Tests team members Backend.java classes
 *
 * @author brianthome
 */
public class P103TeamTestsByTHOME3 {
    /**
     * Tests readData & getRange methods of Backend
     */
    @Test
    public void test1() {
        IterableSortedCollection<Song> tree = new Tree_Placeholder();
        Backend backend = new Backend(tree);

        try { // Tests if method can properly open a file that shouldn't exist
            backend.readData("blank");
            Assertions.fail("Should have returned an error on a non-existent file but didn't");
        } catch (IOException ignore) {
        }

        try { // Tests if method can properly open a file that should exist
            backend.readData("songs.csv");
            if (tree.isEmpty()) {
                Assertions.fail("Opened the file but didn't insert any items");
            }

        } catch (IOException e) {
            Assertions.fail("Should have successfully opened songs.csv but didn't");
        }


        List<String> list = backend.getRange(0, 0);
        if (!list.isEmpty()) {
            Assertions.fail("getRange(0,0) should have returned an empty list but didn't");
        }

        list = backend.getRange(-5, -4);
        String actual = list.toString();
        String expected = "[A L I E N S, BO$$, Cake By The Ocean]";
        if (!actual.equals(expected)) {
            Assertions.fail("getRange(-5,-4) should have returned all songs, but didn't");
        }
    }

    /**
     * Tests setFilter method of Backend
     */
    @Test
    public void test2() {
        IterableSortedCollection<Song> tree = new Tree_Placeholder();
        Backend backend = new Backend(tree);

        List<String> list = backend.setFilter(null);
        String actual = list.toString();
        String expected = "[A L I E N S, BO$$, Cake By The Ocean]";
        if (!actual.equals(expected)) { // Tests all values should return in correct order
            Assertions.fail("Tests whether all songs return with a null filter value");
        }

        list = backend.setFilter(50);
        actual = list.toString();
        expected = "[A L I E N S, BO$$, Cake By The Ocean]";
        if (!actual.equals(expected)) { // Tests all values should return in correct order
            Assertions.fail("Tests whether low filter value filters no songs");
        }

        list = backend.setFilter(200);
        actual = list.toString();
        expected = "[]"; // Should not return any of the values
        if (!actual.equals(expected)) {
            Assertions.fail("Tests whether all songs filtered out when bpm > all song values");
        }

        list = backend.setFilter(115);
        actual = list.toString();
        expected = "[A L I E N S, Cake By The Ocean]"; // Test some values should return in 
        // correct order
        if (!actual.equals(expected)) {
            Assertions.fail("Test to check whether filter works to filter out a song below 115 " +
                    "bpm");
        }
    }

    /**
     * Tests fiveMost method of Backend
     */
    @Test
    public void test3() {
        IterableSortedCollection<Song> tree = new Tree_Placeholder();
        Backend backend = new Backend(tree);
        List<String> list;

        list = backend.fiveMost();
        String expected = list.get(0);

        if ((!expected.equals("A L I E N S") && !expected.equals("Cake By The Ocean") && !expected.equals("BO$$"))) {
            Assertions.fail("Tests whether all values in placeholder are returned with no filters" +
                    " present"); // At least one of the values wasn't returned
        }
        backend.getRange(-4, 0);
        if (!backend.fiveMost().isEmpty()) {
            Assertions.fail("Check whether, when the bounds don't include any song in the list, " +
                    "the method returns no songs ");
        }

        backend.getRange(null, null); // Effectively resets range
        backend.setFilter(115);
        list = backend.fiveMost();
        String actual = list.toString();
        if (!actual.equals("[Cake By The Ocean, A L I E N S]")) {
            Assertions.fail("Tests whether fiveMost follows current setFilter() value. Actual " +
                    "returned value was: " + actual + ", but expected: [Cake By The Ocean, A L I " +
                    "E N S]");
        }
    }
}

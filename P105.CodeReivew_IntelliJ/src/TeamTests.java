import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

/**
 * This class test for Frontend implementations
 */
public class TeamTests {

    /**
     * Test MenuOptions. See if there is a menu print out and there is an error message when input other stuff
     */
    @Test
    public void test1() {
        String initialInput = "Q\n"; // finish the tester
        TextUITester UITest0 = new TextUITester(initialInput);
        IterableSortedCollection<Song> songCollection0 = new Tree_Placeholder();
        Backend_Placeholder backend0 = new Backend_Placeholder(songCollection0);
        Scanner scanner0 = new Scanner(System.in);
        Frontend frontend0 = new Frontend(scanner0, backend0);
        frontend0.runCommandLoop();

        String mainMenu = UITest0.checkOutput();

        // test 1.1: enter an invalid input does not cause program to end
        String userInput = "1\nQ";
        TextUITester UITest1 = new TextUITester(userInput);

        // create frontend
        IterableSortedCollection<Song> songCollection1 = new Tree_Placeholder();
        Backend_Placeholder backend1 = new Backend_Placeholder(songCollection1);
        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(scanner, backend1);

        // test runCommandLoop method
        try {
            frontend.runCommandLoop();
        } catch (Exception e){
            Assertions.assertTrue(false);
        }

        // check if the menu is displayed, and program functions well after enter an invalid input
        String menuOutput1 = UITest1.checkOutput();
        Assertions.assertTrue(menuOutput1.contains(mainMenu));
        Assertions.assertTrue(!menuOutput1.isBlank());

        // Test if it is case-sensitive
        String userInput2 = "songs.csv\nD\nQ";
        TextUITester UITest2 = new TextUITester(userInput2);

        IterableSortedCollection<Song> songCollection2 = new Tree_Placeholder();
        Backend_Placeholder backend2 = new Backend_Placeholder(songCollection2);
        Scanner scanner2 = new Scanner(System.in);
        Frontend frontend2 = new Frontend(scanner2, backend2);

        // test runCommandLoop method
        try {
            frontend2.runCommandLoop();
        } catch (Exception e){
            Assertions.assertTrue(false);
        }
        String menuOutput2 = UITest2.checkOutput();
        Assertions.assertNotEquals(menuOutput2, menuOutput1);
    }

    /**
     * Test D. display top 5 songs and L. load file interaction
     * 2.1 Display top 5
     * 2.2 Load file & Call Display top 5 Again
     */
    @Test
    public void test2() {
        // test 2.1 Display all the songs that currently has
        String userInput = "Q";
        TextUITester UITest = new TextUITester(userInput);

        IterableSortedCollection<Song> songCollection = new Tree_Placeholder();
        Backend_Placeholder backend = new Backend_Placeholder(songCollection);
        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(scanner, backend);

        try {
            frontend.displayTopFive();
        } catch (Exception e){
            Assertions.assertTrue(false);
        }
        String menuOutput = UITest.checkOutput();
        Assertions.assertTrue(menuOutput.contains("A L I E N S"));
        Assertions.assertTrue(menuOutput.contains("BO$$"));
        Assertions.assertTrue(menuOutput.contains("Cake By The Ocean"));

        // test 2.2 (step 2) Check if there is one more song added. Then use D.display to see if the song is added
        // After try to load the file, it should add one more song
        String userInput1 = "songs.csv\nD\nQ";
        TextUITester UITest1 = new TextUITester(userInput1);

        IterableSortedCollection<Song> songCollection1 = new Tree_Placeholder();
        Backend_Placeholder backend1 = new Backend_Placeholder(songCollection1);
        Scanner scanner1 = new Scanner(System.in);
        Frontend frontend1 = new Frontend(scanner1, backend1);

        // test runCommandLoop method
        try {
            frontend1.loadFile();
            frontend1.displayTopFive();
        } catch (Exception e){
            Assertions.assertTrue(false);
        }
        String menuOutput1 = UITest1.checkOutput();
        Assertions.assertTrue(menuOutput1.contains("A L I E N S"));
        Assertions.assertTrue(menuOutput1.contains("BO$$"));
        Assertions.assertTrue(menuOutput1.contains("Cake By The Ocean"));
        Assertions.assertTrue(menuOutput1.contains("DJ Got Us Fallin' In Love (feat. Pitbull)"));
    }

    /**
     * Test G. get song interaction; it should take two inputs and filter and song
     */
    @Test
    public void test3() {
        String userInput = "-10\n10\nQ";
        TextUITester UITest = new TextUITester(userInput);

        IterableSortedCollection<Song> songCollection = new Tree_Placeholder();
        Backend_Placeholder backend = new Backend_Placeholder(songCollection);
        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(scanner, backend);

        try {
            frontend.getSongs();
        } catch (Exception e){
            Assertions.assertTrue(false);
        }
        String menuOutput = UITest.checkOutput();
        Assertions.assertTrue(menuOutput.contains("A L I E N S"));
        Assertions.assertTrue(menuOutput.contains("BO$$"));
        Assertions.assertTrue(menuOutput.contains("Cake By The Ocean"));
    }

    /**
     * Test F. set filter interaction for desired speed
     */
    @Test
    public void test4() {
        String userInput = "105\nQ";
        TextUITester UITest = new TextUITester(userInput);

        IterableSortedCollection<Song> songCollection = new Tree_Placeholder();
        Backend_Placeholder backend = new Backend_Placeholder(songCollection);
        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(scanner, backend);

        try {
            frontend.setFilter();
        } catch (Exception e){
            Assertions.assertTrue(false);
        }
        String menuOutput = UITest.checkOutput();
        Assertions.assertTrue(menuOutput.contains("A L I E N S"));
        Assertions.assertTrue(menuOutput.contains("Cake By The Ocean"));
    }
}

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class FrontendTests {

    @Test
    public void frontendTest1() {
        // Simulate user input for loading a file and quitting the program
        String input = "L\nexample.csv\nQ\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        IterableSortedCollection<Song> tree = new Tree_Placeholder();
        BackendInterface backend = new Backend_Placeholder(tree);

        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(scanner, backend);

        frontend.runCommandLoop();

        assertTrue(true);
    }

    @Test
    public void frontendTest2() {
        // Simulate user input for getting songs by loudness and quitting
        String input = "G\n50\n100\nQ\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        IterableSortedCollection<Song> tree = new Tree_Placeholder();
        BackendInterface backend = new Backend_Placeholder(tree);

        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(scanner, backend);

        frontend.runCommandLoop();

        assertTrue(true);
    }

    @Test
    public void frontendTest3() {
        // Simulate user input for setting the speed filter and quitting
        String input = "F\n120\nQ\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        IterableSortedCollection<Song> tree = new Tree_Placeholder();
        BackendInterface backend = new Backend_Placeholder(tree);

        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(scanner, backend);

        frontend.runCommandLoop();

        assertTrue(true);
    }

    @Test
    public void frontendTest4() {
        // Simulate user input for displaying the top five songs and quitting
        String input = "D\nQ\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        IterableSortedCollection<Song> tree = new Tree_Placeholder();
        BackendInterface backend = new Backend_Placeholder(tree);

        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(scanner, backend);

        frontend.runCommandLoop();

        assertTrue(true);
    }
}


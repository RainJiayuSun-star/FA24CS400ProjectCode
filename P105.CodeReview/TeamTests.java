
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import JTAN94.App;
import JTAN94.Frontend;

/**
 * TeamTests class contains JUnit tests for testing the App and Frontend classes using opaque-box testing.
 */
public class TeamTests {

    /**
     * Tests the interaction flow of starting the app and receiving the welcome message.
     * This is an opaque-box test that simulates the initial run of the application.
     */
    @Test
    public void testAppStartupFlow() {
        // Simulate no input initially to see the startup flow
        TextUITester tester = new TextUITester("");

        // Set up the app and start the frontend
        new Frontend(new App()).run();

        // Capture and verify the output
        String output = tester.getOutput();
        assertTrue(output.contains("Welcome to the App"),
                "Expected to see a welcome message at the start.");
    }

    /**
     * Tests a scenario where a user lists songs through the frontend.
     * This is an opaque-box test that checks the interaction result.
     */
    @Test
    public void testListSongsInteraction() {
        // Simulate user input to list songs and then exit
        TextUITester tester = new TextUITester("list songs\nexit\n");

        // Set up the app and start the frontend
        new Frontend(new App()).run();

        // Capture and verify the output contains song listing
        String output = tester.getOutput();
        assertTrue(output.contains("Song List:"),
                "Expected the output to include a list of songs when 'list songs' command is given.");
    }

    /**
     * Tests the exit command through user interaction with the frontend.
     * This is an opaque-box test that checks the application's response to user input.
     */
    @Test
    public void testExitInteraction() {
        // Simulate user input to directly exit the application
        TextUITester tester = new TextUITester("exit\n");

        // Set up the app and start the frontend
        new Frontend(new App()).run();

        // Capture and verify the output contains the exit message
        String output = tester.getOutput();
        assertTrue(output.contains("Goodbye"),
                "Expected to see a goodbye message when the user exits.");
    }
}

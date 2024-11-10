import java.io.IOException;
import java.util.Scanner;
import java.util.List;

public class Frontend implements FrontendInterface{

    private Scanner in;
    private BackendInterface backend;

    public Frontend(Scanner in, BackendInterface backend) {
        this.in = in;
        this.backend = backend;
    }

    /**
     * Repeatedly gives the user an opportunity to issue new commands until
     * they select Q to quit. Uses the scanner passed to the constructor to
     * read user input.
     */
    @Override
    public void runCommandLoop() {
        String command;
        boolean keepRunning = true;
        displayMainMenu();
        while(keepRunning){
            System.out.print("Enter Command: ");
            command = in.nextLine();
            System.out.print("\n");
            switch(command.toLowerCase()) {
                case "l":
                    loadFile();
                    break;
                case "g":
                    getSongs();
                    break;
                case "f":
                    setFilter();
                    break;
                case "d":
                    displayTopFive();
                    break;
                case "q":
                    keepRunning = false;
		    in.close();
                    break;
                default:
                    System.out.println("That is not a valid command");
            }
        }
    }

    /**
     * Displays the menu of command options to the user.  Giving the user the
     * instructions of entering L, G, F, D, or Q (case insensitive) to load a
     * file, get songs, set filter, display the top five, or quit.
     */
    @Override
    public void displayMainMenu() {
        System.out.println("Command Options(Case Insensitive):");
        System.out.println("- [L]oad Song File");
        System.out.println("- [G]et Songs by Loudness");
        System.out.println("- [F]ilter Songs by Speed");
        System.out.println("- [D]isplay Five Most Danceable");
        System.out.println("- [Q]uit");
    }

    /**
     * Provides text-based user interface for prompting the user to select
     * the csv file that they would like to load, provides feedback about
     * whether this is successful vs any errors are encountered.
     * [L]oad Song File
     *
     * When the user enters a valid filename, the file with that name
     * should be loaded.
     * Uses the scanner passed to the constructor to read user input and
     * the backend passed to the constructor to load the file provided
     * by the user. If the backend indicates a problem with finding or
     * reading the file by throwing an IOException, a message is displayed
     * to the user, and they will be asked to enter a new filename.
     */
    @Override
    public void loadFile() {
        String fileName;
        while(true) {
            System.out.print("Please Enter Filename: ");
            fileName = in.nextLine();
            System.out.print("\n");
            try {
                backend.readData(fileName);
                System.out.println("File Successfully Loaded");
                break;
            } catch (IOException e) {
                System.out.println("Error While Loading, Please Try Again");
            }
        }
    }

    /**
     * Provides text-based user interface and error handling for retrieving a
     * list of song titles that are sorted by Loudness.  The user should be
     * given the opportunity to optionally specify a minimum and/or maximum
     * Loudness to limit the number of songs displayed to that range.
     * [G]et Songs by Loudness
     *
     * When the user enters only two numbers (pressing enter after each), the
     * first of those numbers should be interpreted as the minimum, and the
     * second as the maximum Loudness.
     * Uses the scanner passed to the constructor to read user input and
     * the backend passed to the constructor to retrieve the list of sorted
     * songs.
     */
    @Override
    public void getSongs() {
        Integer max = null;
        Integer min = null;
        String input;
        boolean haveMin = false;
        boolean haveMax = false;
        while (!(haveMax && haveMin)) {

	    // Checks for minimum loudness
            if (!haveMin) {
                System.out.print("Please Enter Min Loudness: ");
                input = in.nextLine();
                System.out.print("\n");

		// Checks for null case
                if (input.isBlank()) {
                    haveMin = true;
                } else {
                    try {
                        min = Integer.parseInt(input);
                        haveMin = true;
                    } catch (Exception e) {
                        System.out.println("Incorrect Input, Please Try Again");
                        continue;
                    }
                }
            }

	    // Check for maximum loudness
            if (!haveMax) {
                System.out.print("Please Enter Max Loudness: ");
                input = in.nextLine();
                System.out.print("\n");

		// Checks for null case
                if (input.isBlank()) {
                    haveMax = true;
                } else {
                    try {
                        max = Integer.parseInt(input);
                        haveMax = true;
                    } catch (Exception e) {
                        System.out.println("Incorrect Input, Please Try Again");
                    }
                }
            }
        }
        System.out.println(backend.getRange(min, max));
    }

    /**
     * Provides text-based user interface and error handling for setting a
     * filter threshold.  This and future requests to retrieve songs will
     * will only return the titles of songs that are larger than the
     * user specified Speed.  The user should also be able to clear
     * any previously specified filters.
     * [F]ilter Songs by Speed
     *
     * When the user enters only a single number, that number should be used
     * as the new filter threshold.
     * Uses the scanner passed to the constructor to read user input and
     * the backend passed to the constructor to set the filters provided by
     * the user and retrieve songs that maths the filter criteria.
     */
    @Override
    public void setFilter() {
        Integer speed = null;
        String input;
        while(true) {
            System.out.print("Please Enter Desired Speed: ");
            input = in.nextLine();
            System.out.print("\n");

	    // Checks for null case
            if (input.isBlank()) {
                break;
            } else {
                try {
                    speed = Integer.parseInt(input);
                    break;
                } catch (Exception e) {
                    System.out.println("Incorrect Input, Please Try Again");
                }
            }
        }
        System.out.println(backend.setFilter(speed));
    }

    /**
     * Displays the titles of up to five of the most Danceable songs within the
     * previously set Loudness range and larger than the specified
     * Speed.  If there are no such songs, then this method should
     * indicate that and recommend that the user change their current range or
     * filter settings.
     * [D]isplay five most Danceable
     *
     * The user should not need to enter any input when running this command.
     * Uses the backend passed to the constructor to retrieve the list of up
     * to five songs.
     */
    @Override
    public void displayTopFive() {
        List<String> topFive = backend.fiveMost();
        if (topFive.isEmpty()) {
            System.out.println("Speed and Loudness Filter are Too Restrictive, Please Try Again " +
                    "With Different Presets");
        } else {
            System.out.println(topFive);
        }
    }
}

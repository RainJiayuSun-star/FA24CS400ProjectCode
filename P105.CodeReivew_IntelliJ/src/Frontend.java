import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation of a user interface for app iSongly
 * @author brianthome 
 */
public class Frontend implements FrontendInterface {

    /**
     * Scanner for each Frontend instance
     */
    private final Scanner scanner;
    /**
     * Backend to interface with for each Frontend object
     */
    private final BackendInterface backendInterface;

    /**
     * Creates a new frontend object
     *
     * @param scanner Scanner in
     * @param backend Backend interface implementation
     */
    public Frontend(Scanner scanner, BackendInterface backend) {
        this.scanner = scanner;
        this.backendInterface = backend;
    }

    /**
     * Repeatedly gives the user an opportunity to issue new commands until
     * they select Q to quit. Uses the scanner passed to the constructor to
     * read user input.
     */
    @Override
    public void runCommandLoop() {
        while (scanner.hasNext()) {
            String current = scanner.next();
            if (current.equalsIgnoreCase("Q")) {
                break;
            } else if (current.equalsIgnoreCase("O")) {
                displayMainMenu();
                scanner.nextLine();
                System.out.println("Please enter another command or 'Q' to quit");
            } else if (current.equalsIgnoreCase("L")) {
                loadFile();
                scanner.nextLine();
                System.out.println("Please enter another command or 'Q' to quit");
            } else if (current.equalsIgnoreCase("G")) {
                getSongs();
                System.out.println("Please enter another command or 'Q' to quit");
            } else if (current.equalsIgnoreCase("F")) {
                setFilter();
                scanner.nextLine();
                System.out.println("Please enter another command or 'Q' to quit");
            } else if (current.equalsIgnoreCase("D")) {
                displayTopFive();
                scanner.nextLine();
                System.out.println("Please enter another command or 'Q' to quit");
            } else {
                System.out.println("Please enter a valid command");
                scanner.nextLine();
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
        System.out.println("Enter 'L' to load a file\nEnter 'G' to get songs\nEnter 'F' to set " +
                "filter\nEnter 'D' to display the top five\nEnter 'Q' to quit ");
    }

    /**
     * Provides text-based user interface for prompting the user to select
     * the csv file that they would like to load, provides feedback about
     * whether this is successful vs any errors are encountered.
     * [L]oad Song File
     * <p>
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
        System.out.println("Please enter the name of a CSV file to load");
        while (scanner.hasNextLine()) {
            String current = scanner.next();
            try {
                String[] currentSplit = current.split("\\.");
                if (currentSplit.length > 1 && !currentSplit[1].equals("csv")) {
                    // Test if string ends in csv file format
                    throw new IOException("csv");
                }
                this.backendInterface.readData(current); // Backend call
                System.out.println("CSV file " + current + " successfully loaded");
                return; // Only reached without an error
            } catch (IOException e) {
                if (current.equalsIgnoreCase("Q")) {
                    return;
                }
                if (e.getMessage().equals("csv")) {
                    System.out.println("File must end in .csv. Please enter another file name");
                } else {
                    System.out.println("There was a problem when trying to find the file. Please " +
                            "enter a new file name to try again, or enter 'Q' to quit to main " +
                            "menu");
                }
            }
        }
    }

    /**
     * Provides text-based user interface and error handling for retrieving a
     * list of song titles that are sorted by Loudness.  The user should be
     * given the opportunity to optionally specify a minimum and/or maximum
     * Loudness to limit the number of songs displayed to that range.
     * [G]et Songs by Loudness
     * <p>
     * When the user enters only two numbers (pressing enter after each), the
     * first of those numbers should be interpreted as the minimum, and the
     * second as the maximum Loudness.
     * Uses the scanner passed to the constructor to read user input and
     * the backend passed to the constructor to retrieve the list of sorted
     * songs.
     */
    @Override
    public void getSongs() {
        boolean hasNoMin = true;
        boolean toBreak = false;
        int minDecibels = 0;
        int maxDecibels = -1000000;
        System.out.println("Please enter the minimum loudness (in dB) for each song as a negative" +
                " number, or enter 1 for no limit");
        while (scanner.hasNext()) {
            while (scanner.hasNextInt() && hasNoMin) {
                int input = scanner.nextInt();
                if (input <= 0) {
                    System.out.println("Minimum loudness is " + input);
                    minDecibels = input;
                    System.out.println("Please enter the maximum loudness (in dB) for each song " +
                            "as a negative number, or 1 for no limit");
                    hasNoMin = false; // Makes it so loop doesn't come back to min if enter
                    // non-numeric for second input
                    break;
                } else if (input == 1) {
                    System.out.println("No limit for minimum loudness");
                    hasNoMin = false;
                    break;
                } else {
                    System.out.println("Value cannot be positive for loudness in dB");
                }
            }
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input <= 0 && input < minDecibels) {
                    System.out.println("Maximum loudness is " + input);
                    maxDecibels = input;
                    toBreak = true;
                    break;
                } else if (input == 1) {
                    toBreak = true;
                    System.out.println("No limit on maximum volume");
                    break;
                } else {
                    System.out.println("Value cannot be positive for loudness in dB and needs to " +
                            "be less than " + minDecibels);
                }
            }
            if (toBreak) {
                break; // Break if done with input
            } else {
                System.out.println("PLease enter a number");
                scanner.next(); // Very important to move to next input, loops otherwise
            }
        }
        List<String> list = this.backendInterface.getRange(minDecibels, maxDecibels);
        if (maxDecibels == -1000000 && minDecibels == 0) {
            System.out.println("Songs that match no decibel range:");
            for (String out : list) {
                System.out.println(out);
            }
            // All songs, nicer formatting
        } else {
            System.out.println("Songs that match decibel range " + minDecibels + " to " + maxDecibels + ": ");
            for (String out : list) {
                System.out.println(out);
            }
        }

    }


    /**
     * Provides text-based user interface and error handling for setting a
     * filter threshold.  This and future requests to retrieve songs will
     * only return the titles of songs that are larger than the
     * user specified Speed.  The user should also be able to clear
     * any previously specified filters.
     * [F]ilter Songs by Speed
     * <p>
     * When the user enters only a single number, that number should be used
     * as the new filter threshold.
     * Uses the scanner passed to the constructor to read user input and
     * the backend passed to the constructor to set the filters provided by
     * the user and retrieve songs that match the filter criteria.
     */
    @Override
    public void setFilter() {
        System.out.println("Would you like to clear current filters for decibel range and speed? " +
                "Type 'Y' or 'N'");
        while (scanner.hasNext()) {
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                System.out.println("Filter has been cleared");
                this.backendInterface.setFilter(0); // Set to zero to clear filter
                this.backendInterface.getRange(0, -100000); // Reset decibel range
                break;
            } else if (input.equalsIgnoreCase("n")) {
                break;
            } else {
                System.out.println("Please type either 'Y' or 'N'");
            }
        }
        boolean toBreak = false;
        System.out.println("Please enter the minimum speed (in BPM, range 0-250) for the range of" +
                " songs you would like to filter");
        while (scanner.hasNext()) {
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= 0 && input <= 250) {
                    System.out.println("Minimum speed is now " + input + " BPM");
                    toBreak = true; // Break out of second original loop
                    List<String> list = this.backendInterface.setFilter(input);
                    System.out.println("List of songs that match filters: ");
                    for (String out : list) {
                        System.out.println(out);
                    }
                    break;
                } else {
                    System.out.println("Value must be in range 0-250");
                }
            }
            if (toBreak) {
                break; // Break if done with input
            } else {
                System.out.println("PLease enter a number");
                scanner.next(); // Very important to move to next input, loops otherwise
            }
        }
    }

    /**
     * Displays the titles of up to five of the most Danceable songs within the
     * previously set Loudness range and larger than the specified
     * Speed.  If there are no such songs, then this method should
     * indicate that and recommend that the user change their current range or
     * filter settings.
     * [D]isplay five most Danceable
     * <p>
     * The user should not need to enter any input when running this command.
     * Uses the backend passed to the constructor to retrieve the list of up
     * to five songs.
     */
    @Override
    public void displayTopFive() {
        System.out.println("Searching for most danceable songs that match filters...\n");
        List<String> list = this.backendInterface.fiveMost();
        if (list.isEmpty()) {
            System.out.println("There are no songs that match the selected filters. Please change" +
                    " the filters for decibel range and speed");
            return;
        }
        System.out.println("Most danceable songs are: ");
        for (String out : list) {
            System.out.println(out);
        }
    }
}


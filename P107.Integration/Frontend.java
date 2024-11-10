import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class Frontend implements FrontendInterface {
    private Scanner in;
    private BackendInterface backend;

    // Constructor as specified in the interface
    public Frontend(Scanner in, BackendInterface backend) {
        this.in = in;
        this.backend = backend;
    }

    @Override
    public void runCommandLoop() {
        displayMainMenu();
        String command = in.nextLine().trim().toUpperCase();
        while (!command.equals("Q")) {  // Loop until the user enters 'Q' to quit
            switch (command) {
                case "L":
                    loadFile();
                    break;
                case "G":
                    getSongs();
                    break;
                case "F":
                    setFilter();
                    break;
                case "D":
                    displayTopFive();
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
            displayMainMenu();
            command = in.nextLine().trim().toUpperCase();
        }
        System.out.println("Goodbye!");
    }

    @Override
    public void displayMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("[L]oad Song File");
        System.out.println("[G]et Songs by Loudness");
        System.out.println("[F]ilter Songs by Speed");
        System.out.println("[D]isplay five most Danceable");
        System.out.println("[Q]uit");
        System.out.print("Enter a command: ");
    }

    @Override
    public void loadFile() {
        System.out.print("Enter the filename to load: ");
        String filename = in.nextLine().trim();
        try {
            backend.readData(filename);
            System.out.println("File loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading file. Please try again.");
        }
    }

    @Override
    public void getSongs() {
        System.out.print("Enter minimum Loudness: ");
        String minLoudnessStr = in.nextLine().trim();
        System.out.print("Enter maximum Loudness: ");
        String maxLoudnessStr = in.nextLine().trim();

        try {
            int minLoudness = Integer.parseInt(minLoudnessStr);
            int maxLoudness = Integer.parseInt(maxLoudnessStr);

            // Use the placeholder backend's getRange method to get the songs
            List<String> songs = backend.getRange(minLoudness, maxLoudness);

            if (songs.isEmpty()) {
                System.out.println("No songs found in the specified range.");
            } else {
                System.out.println("Songs in the specified loudness range:");
                for (String song : songs) {
                    System.out.println(song);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values.");
        }
    }

    @Override
    public void setFilter() {
        System.out.print("Enter the speed filter threshold: ");
        String speedStr = in.nextLine().trim();

        try {
            int speed = Integer.parseInt(speedStr);

            // Use the placeholder backend's setFilter method to apply the filter
            List<String> filteredSongs = backend.setFilter(speed);

            if (filteredSongs.isEmpty()) {
                System.out.println("No songs match the current filter.");
            } else {
                System.out.println("Filtered songs:");
                for (String song : filteredSongs) {
                    System.out.println(song);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value.");
        }
    }

    @Override
    public void displayTopFive() {
        // Use the placeholder backend's fiveMost method to get the top five danceable songs
        List<String> topFive = backend.fiveMost();

        if (topFive.isEmpty()) {
            System.out.println("No songs found. Consider adjusting your filters or ranges.");
        } else {
            System.out.println("Top five danceable songs:");
            for (int i = 0; i < topFive.size(); i++) {
                System.out.println((i + 1) + ". " + topFive.get(i));
            }
        }
    }
}


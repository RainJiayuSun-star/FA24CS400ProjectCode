import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Frontend implements FrontendInterface {

  BackendInterface backend = null;
  Scanner scan;
  public Frontend(Scanner scan, BackendInterface backend) {
    this.scan = new Scanner(System.in);
    this.backend = backend;

  }
  // Your constructor must have the signature above. All methods below must
  // rely only on the provided Scanner to read input from the user, and only
  // on the provided BackendInterface to compute the results of different
  // user queries by calling the methods of BackendInterface.

  /**
   * Repeatedly gives the user an opportunity to issue new commands until
   * they select Q to quit. Uses the scanner passed to the constructor to
   * read user input.
   */
  public void runCommandLoop() {
      boolean quit = true;
      String input = null;
	while (quit) {
	    displayMainMenu();
	    System.out.println("Enter a letter");
	    if (scan.hasNextLine()) {
	    input = scan.nextLine().toUpperCase();
	    }
	
	switch(input) {
	case "L":
	    loadFile();
	    System.out.println("File loaded");
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
	case "Q":
	    quit = false;
	    System.out.println("Quiting the program");
	    break;
	default:
	    System.out.println("Invalid input.");
	}    
	}
    
  }

  /**
   * Displays the menu of command options to the user.  Giving the user the
   * instructions of entering L, G, F, D, or Q (case insensitive) to load a
   * file, get songs, set filter, display the top five, or quit.
   */
  public void displayMainMenu() {
    System.out.println("L: Load file");
    System.out.println("G: get songs");
    System.out.println("F: set filter");
    System.out.println("D: display the top five");
    System.out.println("Q: quit");

   
  }

  // Each of the following commands are designed to handle the user
  // interaction associated with a different Main Menu command.

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
  public void loadFile() {

    try {
      System.out.println("Enter file name: ");
      if (scan.hasNextLine()) {
	  String input = scan.nextLine();
	  backend.readData(input);
      }
    } catch (IOException e) {
      System.out.println("File not found, enter a new filename: ");
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
  public void getSongs() {
    Integer minimum;
    Integer maximum;
    System.out.println("Enter the minimum value: ");
    System.out.println("Enter the maximum value: ");
    minimum = scan.nextInt();
    maximum = scan.nextInt();
    

   
    List<String> songs = backend.getRange(minimum, maximum);
    if (songs.isEmpty()) {
	System.out.println("No songs were found");
    } else {
	System.out.println("Songs within the specified requirements:");
      for (int i = 0; i < songs.size(); i++) {
        System.out.println(songs.get(i));
       }
    }
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
  public void setFilter() {
    int filter = 0;
    System.out.println("Enter threshold: ");
    filter = scan.nextInt();
    List<String> songs;

    songs = backend.setFilter(filter);
    if (songs.isEmpty()) { 
	System.out.println("List is empty");
    } else {
	for (int i = 0; i < songs.size(); i++) {
	    System.out.println("List of songs");
	    System.out.println(songs.get(i));
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
   *
   * The user should not need to enter any input when running this command.
   * Uses the backend passed to the constructor to retrieve the list of up
   * to five songs.
   */
  public void displayTopFive() {
    List<String> songs;

    songs = backend.fiveMost();
    if (songs.isEmpty()) {
	System.out.println("No songs were found, change filter or range settings");
    } else {
      for (int i = 0; i < songs.size(); i++) {
	  System.out.println("List of songs:");
	  System.out.println(songs.get(i));
      }
    }
  }

}

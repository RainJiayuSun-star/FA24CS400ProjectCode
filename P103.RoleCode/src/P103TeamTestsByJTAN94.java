import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
   * This class checks for the functionality of getRange(), setFilter and fiveMost() 
   */

public class P103TeamTestsByJTAN94 {
  /**
   * This method checks that only the songs within the specified
   * range is printed by the getRange() method
   */
// this method tests if IOException is thrown when the wrong file is used
@Test
  public void wrongFileTest() {
    IterableSortedCollection<Song> tree = new Tree_Placeholder();
    Backend backend = new Backend(tree);

    // Expecting an IOException when trying to read from a wrong file.
    Throwable throwable = assertThrows(IOException.class, () ->
    {
      backend.readData("wrong_file.csv");
    });
    assertEquals(IOException.class, throwable.getClass());
  }

  @Test
  public void getRange() {
    // creates a new tree 
    IterableSortedCollection<Song> tree = new Tree_Placeholder();
    Backend backend = new Backend(tree);
    StringBuilder output = new StringBuilder();

    try {
      // read data from songs.csv
      backend.readData("songs.csv");
      
      // get songs that have loudness between -5 and 0 
      List<String> songs = backend.getRange(-5,0);
      for (int i = 0; i < songs.size(); i++) {
        output.append(songs.get(i));
      }
      // expected songs 
      String expectedOutput = "A L I E N SBO$$Cake By The Ocean";
      Assertions.assertEquals(expectedOutput, output.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  /**
   * This method checks that only the specified songs that satisfy getRange
   * and setFilter(the speed is greater than the specified integer) are returned
   */
  @Test
  public void setFilter() {
    IterableSortedCollection<Song> tree = new Tree_Placeholder();
    Backend backend = new Backend(tree);
    StringBuilder output = new StringBuilder();

    List<String> songs = backend.getRange(-5,0);
    // sets the speed to a minimum of 120 
    List<String> songTitles = backend.setFilter(120);
    for (int i = 0; i < songTitles.size(); i++) {
      output.append(songs.get(i));
    }
    for (String song: songTitles) {
      String expectedOutput = "A L I E N S";
      Assertions.assertEquals(expectedOutput, output.toString());
    }
  }

  /**
   * This method checks that the returned songs satisfy the setFilter, getRange
   * and prints out the five most danceable songs
   */
  @Test
  public void fiveMost() {
    IterableSortedCollection<Song> tree = new Tree_Placeholder();
    Backend backend = new Backend(tree);
    StringBuilder output = new StringBuilder();

    List<String> songs = backend.getRange(-5,0);
    backend.setFilter(120);
    // songs that pass both getRange and setFilter
    List<String> songList = backend.fiveMost();
    for (int i = 0; i < songList.size(); i++) {
      output.append(songs.get(i));
    }
    for (String song: songList) {
      String expectedOutput = "A L I E N S";
      Assertions.assertEquals(expectedOutput, output.toString());
    }
  }
}

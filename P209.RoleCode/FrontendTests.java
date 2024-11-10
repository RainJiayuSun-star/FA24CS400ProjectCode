import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

/**
 * The FrontendTests class test for Frondend class. Each of the test methods should create 
 * an instance of the class along any needed placeholder instances. Be sure to provide
 * descriptive comments for each of your test methods, which describe the high-level
 * functionality that they are designed to check for
 */
class FrontendTests{

    /**
     * Test that checks the correctness of the HTML generated for the shortest path prompt.
     * It verifies that the HTML contains fields for "start" and "end" locations and a button
     */
    @Test
    void roleTest1() {
        BackendInterface backend = (BackendInterface) new Backend_Placeholder(new Graph_Placeholder());
        Frontend frontend = new Frontend(backend);

        // Check the generated HTML for shortest path prompt
        String generatedHTML = frontend.generateShortestPathPromptHTML();
        System.out.println(generatedHTML);
        assertTrue(generatedHTML.contains("id=\"start\"")); // HTML should contain a start location input
        assertTrue(generatedHTML.contains("id=\"end\"")); // HTML should contain an end location input
        assertTrue(generatedHTML.contains("Find Shortest Path")); // HTML should contain a button to find the shortest path

    }

    /**
     * roleTest2 tests generateShortestPathResponseHTML method. It checks the correctness of the HTML 
     * response for a given shortest path. It verifies that the HTML displays the correct start and end 
     * locations, an ordered list of locations, and the total travel time.
     */
    @Test 
    void roleTest2() {
        BackendInterface backend = (BackendInterface) new Backend_Placeholder(new Graph_Placeholder());
        Frontend frontend = new Frontend(backend);

        // Check the generated HTML for a shortest path response
        String generatedHTML = frontend.generateShortestPathResponseHTML("Union South", "Atmospheric, Oceanic and Space Sciences");
        assertTrue(generatedHTML.contains("<ol>")); // HTML should contain an ordered list for the path
        assertTrue(generatedHTML.contains("<li>Union South</li>")); // HTML should list the starting location
        assertTrue(generatedHTML.contains("<li>Atmospheric, Oceanic and Space Sciences</li>")); // it should list the end location
        // assertFalse(generatedHTML.contains("<li>Computer Sciences and Statistics</li>")); // It shouldn't contain CS department since it would not be the shortest path
        assertTrue(generatedHTML.contains("Total travel time:")); // HTML should display the total travel time
        
        //------------------if the starting element does not exist in the graph---------------------
        generatedHTML = frontend.generateShortestPathResponseHTML("Van Hise Hall", "Union South");
        assertTrue(generatedHTML.contains("No path found"));

        // -----------------if the ending element in the does not exist in the graph
        // For integration test
        //generatedHTML = frontend.generateShortestPathResponseHTML("Union South", "Van Hise Hall");
        //System.out.println(generatedHTML);
        //assertTrue(generatedHTML.contains("No path found"));
    }

    /**
     * roleTest3 tests generateLongestLocationListFromPromptHTML method. It test that checks the correctness 
     * of the HTML generated for the longest location list prompt
     */
    @Test
    void roleTest3() {
        BackendInterface backend = (BackendInterface) new Backend_Placeholder(new Graph_Placeholder());
        Frontend frontend = new Frontend(backend);

        // Check the generated HTML for longest location list prompt
        String generatedHTML = frontend.generateLongestLocationListFromPromptHTML();
        assertTrue(generatedHTML.contains("id=\"from\"")); // HTML should contain a 'from' location input
        assertTrue(generatedHTML.contains("Longest Location List From")); // HTML should contain a button to find the longest location list
    }

    /**
     * roleTest4 tests generateLongestLocationListFromResponseHTML method. It test that checks the correctness of the HTML response for the 
     * longest location list. It verifies that the HTML includes a paragraph with the start location, an ordered list of locations,
     * and a paragraph showing the total number of locations.
     */
    @Test
    void roleTest4() {
        BackendInterface backend = (BackendInterface) new Backend_Placeholder(new Graph_Placeholder());
        Frontend frontend = new Frontend(backend);

        // check that generatedHTML contains expected elements and information
        String startLocation = "Union South";
        // It should contain 3 places which are Union South, Computer Sciences and Statistics, and Atmospheric, Oceanic and Space Sciences for the longest path
        String generatedHTML = frontend.generateLongestLocationListFromResponseHTML(startLocation);       
        assertTrue(generatedHTML.contains("<p>Longest list of locations from " + startLocation + ":</p>"));
        assertTrue(generatedHTML.contains("<ol>"));
        assertTrue(generatedHTML.contains("<li>Union South</li>"));
        // assertTrue(generatedHTML.contains("<li>Computer Sciences and Statistics</li>"));
        // assertTrue(generatedHTML.contains("<li>Atmospheric, Oceanic and Space Sciences</li>"));
        assertTrue(generatedHTML.contains("Total number of locations:"));

        //------------------if the starting element not exist in the graph---------------------
        startLocation = "Noland Hall";
        generatedHTML = frontend.generateLongestLocationListFromResponseHTML(startLocation);
        assertTrue(generatedHTML.contains("No locations found from starting location"));
    }
}
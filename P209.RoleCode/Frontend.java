import java.util.List;

public class Frontend implements FrontendInterface{

    private BackendInterface backend; 
    
    /**
     * Frontend Constructor
     * @param backend is used for shortest path computations
     */
    public Frontend(BackendInterface backend) {
        this.backend = backend;
    };
    
    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a text input field with the id="start", for the start location
     * - a text input field with the id="end", for the destination
     * - a button labelled "Find Shortest Path" to request this computation
     * Ensure that these text fields are clearly labelled, so that the user
     * can understand how to use them.
     * @return an HTML string that contains input controls that the user can
     *         make use of to request a shortest path computation
     */
    @Override
    public String generateShortestPathPromptHTML() {
        // HTML form for shortest path input
        String htmlString = """
            <div>
                <label for="start">Start Location:</label>
                <input type="text" id="start" name="start">
                <br>
                <label for="end">End Location:</label>
                <input type="text" id="end" name="end">
                <br>
                <button>Find Shortest Path</button>
            </div>
            """;
        return htmlString;
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a paragraph (p) that describes the path's start and end locations
     * - an ordered list (ol) of locations along that shortest path
     * - a paragraph (p) that includes the total travel time along this path
     * Or if there is no such path, the HTML returned should instead indicate 
     * the kind of problem encountered.
     * @param start is the starting location to find a shortest path from
     * @param end is the destination that this shortest path should end at
     * @return an HTML string that describes the shortest path between these
     *         two locations
     */
    @Override
    public String generateShortestPathResponseHTML(String start, String end) {
        String htmlString = null;
        try {
            List<String> path = backend.findLocationsOnShortestPath(start, end);
            List<Double> times = backend.findTimesOnShortestPath(start, end);

            if (path.isEmpty()) {
                htmlString = "<p>No path found from " + start + " to " + end + ".</p>";
            } else {
                StringBuilder pathHTML = new StringBuilder("<p>Shortest path from " + start + " to " + end + ":</p><ol>");
                for (String location : path) {
                    pathHTML.append("<li>").append(location).append("</li>");
                }
                pathHTML.append("</ol><p>Total travel time: ").append(times.get(times.size() - 1)).append(" minutes.</p>");
                htmlString = pathHTML.toString();
            }
        } catch (Exception e) {
            htmlString = "<p>Error: Could not calculate shortest path from " + start + " to " + end + ".</p>";
        }
        return htmlString;
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a text input field with the id="from", for the start location
     * - a button labelled "Longest Location List From" to submit this request
     * Ensure that this text field is clearly labelled, so that the user
     * can understand how to use it.
     * @return an HTML string that contains input controls that the user can
     *         make use of to request a longest location list calculation
     */
    @Override
    public String generateLongestLocationListFromPromptHTML() {
        String htmlString = """
            <div>
                <label for="from">Starting Location:</label>
                <input type="text" id="from" name="from">
                <br>
                <button>Longest Location List From</button>
            </div>  
            """;;
        return htmlString;
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a paragraph (p) that describes the path's start and end locations
     * - an ordered list (ol) of locations along that shortest path
     * - a paragraph (p) that includes the total number of locations on path
     * Or if no such path can be found, the HTML returned should instead
     * indicate the kind of problem encountered.
     * @param start is the starting location to find the longest list from
     * @return an HTML string that describes the longest list of locations 
     *        along a shortest path starting from the specified location
     */
    @Override
    public String generateLongestLocationListFromResponseHTML(String start) {
        String htmlString = null;
        try {
            List<String> path = backend.getLongestLocationListFrom(start);

            if (path.isEmpty()) {
                htmlString = "<p>No locations found from starting location " + start + ".</p>";
            } else {
                StringBuilder pathHTML = new StringBuilder("<p>Longest list of locations from " + start + ":</p><ol>");
                for (String location : path) {
                    pathHTML.append("<li>").append(location).append("</li>");
                }
                pathHTML.append("</ol><p>Total number of locations: ").append(path.size()).append(".</p>");
                htmlString = pathHTML.toString();
            }
        } catch (Exception e) {
            htmlString = "<p>Error: Could not calculate longest location list from " + start + ".</p>";
        }
        return htmlString;
    }
}
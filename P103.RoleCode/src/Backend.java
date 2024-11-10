import java.io.IOException;
import java.sql.Array;
import java.util.*;
import java.io.File;


/**
 * The class Backend that implements the BackendInterface
 */
public class Backend implements BackendInterface {

    private IterableSortedCollection<Song> tree;
    private Integer low;
    private Integer high;
    private Integer speedThreshold = null;

    //public Backend(IterableSortedCollection<Song> tree)
    // Your constructor must have the signature above. All methods below must
    // use the provided tree to store, sort, and iterate through songs. This
    // will enable you to create some tests that use the placeholder tree, and
    // others that make use of a working tree.

    // Comparator that compares songs based on their loudness
    private Comparator<Song> loudnessComparator = new Comparator<Song>() {
        @Override
        public int compare(Song s1, Song s2) {
            // Compare the two songs by their loudness
            return Integer.compare(s1.getLoudness(), s2.getLoudness());
        }
    };

    /**
     * Constructor for Backend
     *
     * @param tree the tree being added
     */
    public Backend(IterableSortedCollection<Song> tree) {
        this.tree = tree;
    }

    /**
     * Loads data from the .csv file referenced by filename.  You can rely
     * on the exact headers found in the provided songs.csv, but you should
     * not rely on them always being presented in this order or on there
     * not being additional columns describing other song qualities.
     * After reading songs from the file, the songs are inserted into
     * the tree passed to the constructor.
     *
     * @param filename is the name of the csv file to load data from
     * @throws IOException when there is trouble finding/reading file
     */
    @Override
    public void readData(String filename) throws IOException {
        if (filename == null) {throw new IOException();}; // if the filename section is null, return to exit
        try (Scanner fileReader = new Scanner(new File(filename))) {
            Map<String, Integer> songHeaderMap = new HashMap<>(); // Creating a map for the songHeaders

            if (fileReader.hasNextLine()) {
                String songHeaders = fileReader.nextLine();
                String[] songHeadersList = songHeaders.split(","); // Turn songHeadersList into a list of strings
                ArrayList<Integer> songHeaderNumber = new ArrayList<>(); // ArrayList
                for (int i = 0; i < songHeadersList.length; i++) {
                    songHeaderMap.put(songHeadersList[i], i);
                }
            }
            // Create a list for testing
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine(); // Read the lines
                // String[] songValues = line.split(","); // Turn the one line into the list
                // Handle quoted fields correctly (fields with commas inside quotes)
                String[] songValues = parseCSVLine(line);

                // System.out.println(songValues[songHeaderMap.get("title")]);
                // System.out.println(songValues[songHeaderMap.get("artist")]);
                // System.out.println(songValues[songHeaderMap.get("top genre")]);

                // Handle missing or extra columns by using the map
                Song aSong = new Song(
                        songValues[songHeaderMap.get("title")],
                        songValues[songHeaderMap.get("artist")],
                        songValues[songHeaderMap.get("top genre")],
                        Integer.parseInt(songValues[songHeaderMap.get("year")]),
                        Integer.parseInt(songValues[songHeaderMap.get("bpm")]),
                        Integer.parseInt(songValues[songHeaderMap.get("nrgy")]),
                        Integer.parseInt(songValues[songHeaderMap.get("dnce")]),
                        Integer.parseInt(songValues[songHeaderMap.get("dB")]),
                        Integer.parseInt(songValues[songHeaderMap.get("live")]),
                        loudnessComparator
                );
                tree.insert(aSong); // insert the song object
                // tree.iterator();
            }
        } catch (IOException e) {
            throw new IOException("Cannot open file correctly");
        }
    }

    /**
     * This method splits a CSV line while respecting commas within quoted fields.
     * It handles values enclosed in double quotes where commas might appear.
     *
     * @param line The line of the CSV file to be parsed.
     * @return An array of strings representing the individual fields.
     */
    private String[] parseCSVLine(String line) {
        // Regex to split by commas outside quotes
        String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        String[] values = line.split(regex);

        // Remove surrounding quotes from any field if present
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].replaceAll("^\"|\"$", "").trim();
        }

        return values;
    }

    /**
     * Retrieves a list of song titles from the tree passed to the contructor.
     * The songs should be ordered by the songs' Loudness, and that fall within
     * the specified range of Loudness values.  This Loudness range will
     * also be used by future calls to the setFilter and getmost Danceable methods.
     * <p>
     * If a Speed filter has been set using the setFilter method
     * below, then only songs that pass that filter should be included in the
     * list of titles returned by this method.
     * <p>
     * When null is passed as either the low or high argument to this method,
     * that end of the range is understood to be unbounded.  For example, a null
     * high argument means that there is no maximum Loudness to include in
     * the returned list.
     *
     * @param low  is the minimum Loudness of songs in the returned list
     * @param high is the maximum Loudness of songs in the returned list
     * @return List of titles for all songs from low to high, or an empty
     * list when no such songs have been loaded
     */
    @Override
    public List<String> getRange(Integer low, Integer high) {
        this.low = low;
        this.high = high;

        List<Song> songs = new ArrayList<>(); // Initialize a list that store all the filtered songs
        for (Song song : tree) {
            if ((low == null || song.getLoudness() >= low) && (high == null || song.getLoudness() <= high)) {
                songs.add(song);
            }
        }
        // Sort the filtered songs by their loudness level using Collections.sort
        Collections.sort(songs, (s1, s2) -> Integer.compare(s1.getLoudness(), s2.getLoudness()));
        List<String> songTitles = new ArrayList<>();
        for (Song song : songs) {
            songTitles.add(song.getTitle());
        }
        return songTitles;
    }

    /**
     * Retrieves a list of song titles that have a Speed that is
     * larger than the specified threshold.  Similar to the getRange
     * method: this list of song titles should be ordered by the songs'
     * Loudness, and should only include songs that fall within the specified
     * range of Loudness values that was established by the most recent call
     * to getRange. If getRange has not previously been called, then no low
     * or high Loudness bound should be used.  The filter set by this method
     * will be used by future calls to the getRange and getmost Danceable methods.
     * <p>
     * When null is passed as the threshold to this method, then no Speed
     * threshold should be used.  This effectively clears the filter.
     *
     * @param threshold filters returned song titles to only include songs that
     *                  have a Speed that is larger than this threshold.
     * @return List of titles for songs that meet this filter requirement, or
     * an empty list when no such songs have been loaded
     */
    @Override
    public List<String> setFilter(Integer threshold) {
        this.speedThreshold = threshold;
        List<Song> songs = new ArrayList<>(); // Initialize a list that store all the filtered songs
        // Order the song by the loudness using hashmap
        for (Song song : tree) {
            if (speedThreshold == null || song.getBPM() > speedThreshold) { // The speed threshold filter
                songs.add(song);
            }
        }
        // Sort the filtered songs by their loudness level using Collections.sort
        Collections.sort(songs, (s1, s2) -> Integer.compare(s1.getLoudness(), s2.getLoudness()));
        List<String> songTitles = new ArrayList<>();
        for (Song song : songs) {
            songTitles.add(song.getTitle());
        }
        return songTitles;
    }

    /**
     * Top Danceable Songs
     * This method returns a list of song titles representing the five
     * most Danceable songs that both fall within any attribute range specified
     * by the most recent call to getRange, and conform to any filter set by
     * the most recent call to setFilter.  The order of the song titles in this
     * returned list is up to you.
     * <p>
     * If fewer than five such songs exist, return all of them.  And return an
     * empty list when there are no such songs.
     *
     * @return List of five most Danceable song titles
     */
    @Override
    public List<String> fiveMost() {
        // Initialize the song list to store the reordered songs
        List<Song> reorderedSongs = new ArrayList<>();
        // Apply both filter and add songs
        for (Song song : tree) {
            // Apply the loudness range filter
            boolean filterLoudness = (low == null || song.getLoudness() >= low) && (high == null || song.getLoudness() <= high);
            // Apply the speed threshold filter (if speedThreshold is not null)
            boolean filterSpeed = (speedThreshold == null || song.getBPM() > speedThreshold);

            // Add songs to the list if both filters match
            if (filterLoudness && filterSpeed) {
            reorderedSongs.add(song);
            }
        }

        // Sort by danceability in descending order
        reorderedSongs.sort((s1, s2) -> Integer.compare(s2.getDanceability(), s1.getDanceability()));

        // Initialized the song title list
        List<String> topFiveDanceableSongs = new ArrayList<>();
        // Add number of songs either less than 5 or 5
        for (int i = 0; i < Math.min(5, reorderedSongs.size()); i++) {
            topFiveDanceableSongs.add(reorderedSongs.get(i).getTitle());
        }

        return topFiveDanceableSongs;
    }
}




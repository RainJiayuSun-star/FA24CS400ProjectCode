// === CS400 File Header Information ===
// Name: <Rain Jiayu Sun>
// Email: <jsun424 @wisc.edu email address>
// Group and Team: <your group name: two letters, and team color>
// Group TA: <name of your group's ta>
// Lecturer: <name of your lecturer>
// Notes to Grader: <optional extra notes>

import java.util.PriorityQueue;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     */
    public DijkstraGraph() {
        super(new PlaceholderMap<>());
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        // implement in step 5.3
        return null;
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        // implement in step 5.4
        return null;
	}

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        // implement in step 5.4
        return Double.NaN;
    }

    // TODO: implement 3+ tests in step 4.1

    /**
     * testClassExample one: Create a test that makes use of an example that you traced through in lecture, and 
     * confirm that the results of your implementation match what you previously computed by hand.
     * This tester implements the example traced through in lecture, and confirm results of the implementation 
     * match what previously computed by hand. 
     * Test on shortestPathData and shortestPathCost methods
     * Here D->G->H->I is the shortest path from D->I
     */
    @Test
    public void testClassExample_1() {
        DijkstraGraph<String, Integer> testGraph = new DijkstraGraph<>();

        // Insert Nodes
        testGraph.insertNode("D");
    	testGraph.insertNode("G");
    	testGraph.insertNode("F");
    	testGraph.insertNode("A");
    	testGraph.insertNode("B");
    	testGraph.insertNode("L");
    	testGraph.insertNode("M");
    	testGraph.insertNode("H");
    	testGraph.insertNode("I");
    	testGraph.insertNode("E");

        // Insert Edges with Weights
    	testGraph.insertEdge("D", "F", 4); // D->4->F
    	testGraph.insertEdge("D", "A", 7); // D->7->A
    	testGraph.insertEdge("D", "G", 2); // D->2->G
    	testGraph.insertEdge("F", "G", 9); // F->9->G
    	testGraph.insertEdge("G", "H", 9); // G->9->H
    	testGraph.insertEdge("G", "L", 7); // G->7->L
    	testGraph.insertEdge("G", "A", 4); // G->4->A
    	testGraph.insertEdge("A", "H", 7); // A->7->H
    	testGraph.insertEdge("A", "B", 1); // A->1->B
    	testGraph.insertEdge("A", "M", 5); // A->5->M
    	testGraph.insertEdge("B", "M", 3); // B->3->M
    	testGraph.insertEdge("H", "L", 2); // H->2->L
    	testGraph.insertEdge("H", "B", 6); // H->6->B
    	testGraph.insertEdge("H", "I", 2); // H->2->I
    	testGraph.insertEdge("M", "I", 4); // M->4->I
    	testGraph.insertEdge("M", "E", 3); // M->3->E
    	testGraph.insertEdge("M", "F", 4); // M->4->F
    	testGraph.insertEdge("I", "D", 2); // I->2->D
    	testGraph.insertEdge("I", "H", 2); // I->2->H

        List<String> expectedPath = Arrays.asList("D", "G", "H", "I"); // 2 + 9 + 2 = 13
        int expectedCost = 13;

        List<String> actualPath = testGraph.shortestPathData("D", "I");
        double actualCost = testGraph.shortestPathCost("D", "I");

        assertEquals(expectedPath, actualPath);
        assertEquals(expectedCost, actualCost, 0.001);
    }

    /**
     * testClassExample two: Create another test using the same graph as you did for the test above, but 
     * check the cost and sequence of data along the shortest path between a different start and end node.
     * This tester implements the example traced through in lecture, and confirm results of the implementation 
     * match what previously computed by hand. 
     * Test on shortestPathData and shortestPathCost methods
     * Here from D to B is D(0,-)G(4,D)A(4,G)B(1,A) | D->G->A->B 2+4+1 
     */
    @Test
    public void testClassExample_2() {
        DijkstraGraph<String, Integer> testGraph = new DijkstraGraph<>();

        // Insert Nodes
        testGraph.insertNode("D");
    	testGraph.insertNode("G");
    	testGraph.insertNode("F");
    	testGraph.insertNode("A");
    	testGraph.insertNode("B");
    	testGraph.insertNode("L");
    	testGraph.insertNode("M");
    	testGraph.insertNode("H");
    	testGraph.insertNode("I");
    	testGraph.insertNode("E");

        // Insert Edges with Weights
    	testGraph.insertEdge("D", "F", 4); // D->4->F
    	testGraph.insertEdge("D", "A", 7); // D->7->A
    	testGraph.insertEdge("D", "G", 2); // D->2->G
    	testGraph.insertEdge("F", "G", 9); // F->9->G
    	testGraph.insertEdge("G", "H", 9); // G->9->H
    	testGraph.insertEdge("G", "L", 7); // G->7->L
    	testGraph.insertEdge("G", "A", 4); // G->4->A
    	testGraph.insertEdge("A", "H", 7); // A->7->H
    	testGraph.insertEdge("A", "B", 1); // A->1->B
    	testGraph.insertEdge("A", "M", 5); // A->5->M
    	testGraph.insertEdge("B", "M", 3); // B->3->M
    	testGraph.insertEdge("H", "L", 2); // H->2->L
    	testGraph.insertEdge("H", "B", 6); // H->6->B
    	testGraph.insertEdge("H", "I", 2); // H->2->I
    	testGraph.insertEdge("M", "I", 4); // M->4->I
    	testGraph.insertEdge("M", "E", 3); // M->3->E
    	testGraph.insertEdge("M", "F", 4); // M->4->F
    	testGraph.insertEdge("I", "D", 2); // I->2->D
    	testGraph.insertEdge("I", "H", 2); // I->2->H

        List<String> expectedPath = Arrays.asList("D", "G", "A", "B"); // 2 + 4 + 1 = 7
        int expectedCost = 7;

        List<String> actualPath = testGraph.shortestPathData("D", "B");
        double actualCost = testGraph.shortestPathCost("D", "B");

        assertEquals(expectedPath, actualPath);
        assertEquals(expectedCost, actualCost, 0.001);
    }

    /**
     * testClassExample three: Create a test that checks the behavior of your implementation 
     * when the nodes that you are searching for a path between existing nodes in the graph, but 
     * there is no sequence of directed edges that connects them from the start to the end.
     * Test on computeShortestPath method
     */
    @Test
    public void testClassExample_3() {
        DijkstraGraph<String, Integer> testGraph = new DijkstraGraph<>();

        // Insert Nodes
        testGraph.insertNode("D");
    	testGraph.insertNode("G");
    	testGraph.insertNode("F");
    	testGraph.insertNode("A");
    	testGraph.insertNode("B");
    	testGraph.insertNode("L");
    	testGraph.insertNode("M");
    	testGraph.insertNode("H");
    	testGraph.insertNode("I");
    	testGraph.insertNode("E");

        // Insert Edges with Weights
    	testGraph.insertEdge("D", "F", 4); // D->4->F
    	testGraph.insertEdge("D", "A", 7); // D->7->A
    	testGraph.insertEdge("D", "G", 2); // D->2->G
    	testGraph.insertEdge("F", "G", 9); // F->9->G
    	testGraph.insertEdge("G", "H", 9); // G->9->H
    	testGraph.insertEdge("G", "L", 7); // G->7->L
    	testGraph.insertEdge("G", "A", 4); // G->4->A
    	testGraph.insertEdge("A", "H", 7); // A->7->H
    	testGraph.insertEdge("A", "B", 1); // A->1->B
    	testGraph.insertEdge("A", "M", 5); // A->5->M
    	testGraph.insertEdge("B", "M", 3); // B->3->M
    	testGraph.insertEdge("H", "L", 2); // H->2->L
    	testGraph.insertEdge("H", "B", 6); // H->6->B
    	testGraph.insertEdge("H", "I", 2); // H->2->I
    	testGraph.insertEdge("M", "I", 4); // M->4->I
    	testGraph.insertEdge("M", "E", 3); // M->3->E
    	testGraph.insertEdge("M", "F", 4); // M->4->F
    	testGraph.insertEdge("I", "D", 2); // I->2->D
    	testGraph.insertEdge("I", "H", 2); // I->2->H

        // ShortestPathData method should throw NoSuchElementException if no correct edges found
        try {
            // No path from L to D but there could be paths from D to L
            testGraph.shortestPathData("L", "D");
            System.out.println("Expected NoSuchElementException to be thrown");
            assertFalse(true);
        } catch (NoSuchElementException e){
            assertTrue(true); // Test passes if NoSuchElementException is thrown
        } catch (Exception e) {
            // If other exceptions caught, test fails
            assertFalse(true);
        }

        // ShortestPathData method should throw NoSuchElementException if no correct edges found
        try {
            // No path from L to D but there could be paths from D to L
            testGraph.shortestPathCost("L", "D");
            System.out.println("Expected NoSuchElementException to be thrown");
            assertFalse(true);
        } catch (NoSuchElementException e){
            assertTrue(true); // Test passes if NoSuchElementException is thrown
        } catch (Exception e) {
            // If other exceptions caught, test fails
            assertFalse(true);
        }

        // ComputeShortestPath should throw NoSuchElementException if no correct edges found
        try {
            // No path from L to D but there could be paths from D to L
            testGraph.computeShortestPath("L", "D"); 
            System.out.println("Expected NoSuchElementException to be thrown");
            assertFalse(true);
        } catch (NoSuchElementException e){
            assertTrue(true); // Test passes if NoSuchElementException is thrown
        } catch (Exception e) {
            // If other exceptions caught, test fails
            assertFalse(true);
        }
    }

    /**
     * Test1 tests a known shortest path. It uses a simple graph structure and compares
     * the computed shortest path and cost to a manually calculated example
     */
    @Test
    public void test1() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertEdge("A", "B", 2);
        graph.insertEdge("A", "C", 4);
        graph.insertEdge("B", "C", 1);

        List<String> expectedPath = Arrays.asList("A", "B", "C");
        double expectedCost = 3; // Path A -> B -> C, cost = 2 + 1

        assertEquals(expectedPath, graph.shortestPathData("A", "C"));
        assertEquals(expectedCost, graph.shortestPathCost("A", "C"), 0.001);
    }
    
    /**
     * Test2 tests a more complicated known shortest path. It uses a simple graph structure and compares
     * the computed shortest path and cost to a manually calculated example
     */
    @Test
    public void test2() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
        // Insert nodes
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");

        // Insert edges with weights
        graph.insertEdge("A", "B", 2); // A-2-B 
        graph.insertEdge("A", "B", 4); // A-4-C
        graph.insertEdge("B","C", 1); // B-1-C 
        graph.insertEdge("B", "D", 7); // B-7-D
        graph.insertEdge("C", "D", 3); // C-3-D
        graph.insertEdge("C", "E", 5); // C-5-E
        graph.insertEdge("D", "E", 1); // D-1-E
        graph.insertEdge("D", "F", 2); // D-2-F
        graph.insertEdge("E", "F", 3); // E-3-F

        // Expected shotest path from A to F: A -> B -> C -> D -> F
        List<String> expectedPath = Arrays.asList("A","B","C","D","F");
        double expectedCost = 8; 
        
        assertEquals(expectedPath, graph.shortestPathData("A", "F"));
        assertEquals(expectedCost, graph.shortestPathCost("A", "F"), 0.001);
    }

    /**
     * Test3 tests path between different start and end nodes. It checks the shortest path and cost between 
     * two difference nodes in the same
     */
    public void test3() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
        // Insert nodes
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");

        // Insert edges with weights
        graph.insertEdge("A", "B", 2);
        graph.insertEdge("B", "C", 1);
        graph.insertEdge("C", "D", 3);
        graph.insertEdge("A", "D", 7);

        List<String> expectedPath = Arrays.asList("A", "B", "C", "D");
        double expectedCost = 6; // Path A -> B -> C -> D, cost = 2 + 1 + 3

        assertEquals(expectedPath, graph.shortestPathData("A", "D"));
        assertEquals(expectedCost, graph.shortestPathCost("A", "D"), 0.001);
    }

    /**
     * Test3 tests for the case that no path between nodes. It confirms that when there is 
     * no path between two nodes, an exception is triggered
     */
    @Test
    public void test4() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertEdge("A", "B", 2);

        try {
            graph.computeShortestPath("A", "C");
            System.out.println("Expected NoSuchElementException to be thrown");
            assertFalse(true);
        } catch (NoSuchElementException e){
            assertTrue(true); // Test passes if NoSuchElementException is thrown
        } catch (Exception e) {
            // If other exceptions caught, test fails
            assertFalse(true);
        }
    }
}

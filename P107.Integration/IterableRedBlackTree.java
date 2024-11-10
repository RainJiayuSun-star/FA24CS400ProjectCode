import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Stack;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * This class extends RedBlackTree into a tree that supports iterating over the values it
 * stores in sorted, ascending order.
 */
public class IterableRedBlackTree<T extends Comparable<T>>
                extends RedBlackTree<T> implements IterableSortedCollection<T> {

    private Comparable<T> iteratorMin = null; // store the min for the iterator, null if not set
    private Comparable<T> iteratorMax = null; // store the max for the iterator, null if not set

    /**
     * Allows setting the start (minimum) value of the iterator. When this method is called,
     * every iterator created after it will use the minimum set by this method until this method
     * is called again to set a new minimum value.
     * @param min the minimum for iterators created for this tree, or null for no minimum
     */
    public void setIteratorMin(Comparable<T> min) {this.iteratorMin = min;}

    /**
     * Allows setting the stop (maximum) value of the iterator. When this method is called,
     * every iterator created after it will use the maximum set by this method until this method
     * is called again to set a new maximum value.
     * @param max the maximum for iterators created for this tree, or null for no maximum
     */
    public void setIteratorMax(Comparable<T> max) {this.iteratorMax = max;}

    /**
     * Returns an iterator over the values stored in this tree. The iterator uses the
     * start (minimum) value set by a previous call to setIteratorMin, and the stop (maximum)
     * value set by a previous call to setIteratorMax. If setIteratorMin has not been called
     * before, or if it was called with a null argument, the iterator uses no minimum value
     * and starts with the lowest value that exists in the tree. If setIteratorMax has not been
     * called before, or if it was called with a null argument, the iterator uses no maximum
     * value and finishes with the highest value that exists in the tree.
     */
    public Iterator<T> iterator() {
        return new RBTIterator<>(this.root, this.iteratorMin, this.iteratorMax);
    }

    /**
     * Nested class for Iterator objects created for this tree and returned by the iterator method.
     * This iterator follows an in-order traversal of the tree and returns the values in sorted,
     * ascending order.
     */
    protected static class RBTIterator<R extends Comparable<R>> implements Iterator<R> {

         // stores the start point (minimum) for the iterator
         Comparable<R> min = null;
         // stores the stop point (maximum) for the iterator
         Comparable<R> max = null;
         // stores the stack that keeps track of the inorder traversal
         Stack<BSTNode<R>> stack = null;

        /**
         * Constructor for a new iterator if the tree with root as its root node, and
         * min as the start (minimum) value (or null if no start value) and max as the
         * stop (maximum) value (or null if no stop value) of the new iterator.
         * @param root root node of the tree to traverse
         * @param min the minimum value that the iterator will return
         * @param max the maximum value that the iterator will return 
         */
        public RBTIterator(BSTNode<R> root, Comparable<R> min, Comparable<R> max) {
            this.min = min; // stores the start (min) value
            this.max = max; // stores the stop (max) value
            this.stack = new Stack<>(); // initializes empty stack object
            buildStackHelper(root); // initialize the stack
        }

        /**
         * Helper method for initializing and updating the stack. This method both
         * - finds the next data value stored in the tree (or subtree) that is bigger
         *   than or equal to the specified start point (maximum), and
         * - builds up the stack of ancestor nodes that contain values larger than or
         *   equal to the start point so that those nodes can be visited in the future.
         * @param node the root node of the subtree to process
         */
        private void buildStackHelper(BSTNode<R> node) {
            // base case: if node is null, stop by return
            if (node == null) {
                return;
            }
            // recursive case 1: if the value in the argument node is smaller than
            // the start (min) point. min.compareTo(node.data) > 0
            if (min != null && node.getData().compareTo((R) min) < 0) {
                buildStackHelper(node.right);
            }
            // recursive case 2: pushes the argument node onto the stack and calls itself recursively
            // on the argument node's left subtree if the value in the argument node is larger than or
            // equal to the start (min) point
            else {
                stack.push(node);
                buildStackHelper(node.left);
            }
        }

        /**
         * This method try to see if there is next node to put on stack. It returns true if the iterator has another
         * value to return, and false otherwise.
         * @return true if the iterator has another value to return, false otherwise
         */
        public boolean hasNext() {
            // returns true if there are more nodes to visit with values smaller than or equal to the set
            // stop point (maximum), and false otherwise.

            // Check if the stack has any elements
            while (!stack.isEmpty()) {
                // Peek at the top of the stack without removing it
                BSTNode<R> topNode = stack.peek();

                // If a maximum is set and the current top node exceeds maximum, skip
                if (max != null && topNode.data.compareTo((R) max) > 0) {
                    stack.pop(); // remove the top node from the stack
                    if (topNode.right != null) {
                        buildStackHelper(topNode.right); // go right subtree
                    }
                } else {
                    return true; //
                }
            }
            return false;
        }

        /**
         * Returns the next value of the iterator.
         * @throws NoSuchElementException if the iterator has no more values to return
         * @return nextValue of stack
         */
        public R next() {
            // If the iterator has no more values to return,  throw NoSuchElementException
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            // Pop the top node from the stack to get the next value
            BSTNode<R> currentNode = stack.pop();
            R nextValue = currentNode.data;

            // If the right subtree exists, push nodes from the right subtree to the stack
            if (currentNode.right != null) {
                buildStackHelper(currentNode.right);
            }
            return nextValue;
        }
    }

    /**
     * This tester iterableRBT_test1
     * Use integers as comparable data;
     * includes duplicates;
     * Minimum (start point) and Maximum (end point) for the iterator are set;
     * The iterator should be able to return values within a range
     */
    @Test
    public void iterableRBT_test1() {
        IterableRedBlackTree<Integer> rbtIntTree = new IterableRedBlackTree<>();
        rbtIntTree.insert(10);
        rbtIntTree.insert(25);
        rbtIntTree.insert(5);
        rbtIntTree.insert(15);
        rbtIntTree.insert(15); // duplicate 15

        // set minimum and maximum for the iterator
        rbtIntTree.setIteratorMin(10);
        rbtIntTree.setIteratorMax(20);

        // Create an iterator with specified start and stop point
        Iterator<Integer> iterator = rbtIntTree.iterator();

        // Check if the iterator returns the correct values in the range in the ascending order
        assertTrue(iterator.hasNext());
        assertEquals((Integer) 10, iterator.next());
        assertEquals((Integer) 15, iterator.next());
        assertEquals((Integer) 15, iterator.next());
        assertFalse(iterator.hasNext());
    }

    /**
     * This tester iterableRBT_test2
     * Use integers as comparable data;
     * does not include duplicates;
     * Minimum (start point) for the iterator is set but Maximum (end point) is not set;
     * The iterator should be able to return values within a range
     */
    @Test
    public void iterableRBT_test2() {
        IterableRedBlackTree<Integer> rbtIntTree = new IterableRedBlackTree<>();
        rbtIntTree.insert(10);
        rbtIntTree.insert(25);
        rbtIntTree.insert(5);
        rbtIntTree.insert(15);
        rbtIntTree.insert(15); // duplicate 15

        // set minimum for the iterator
        rbtIntTree.setIteratorMin(15);

        // Create an iterator
        Iterator<Integer> iterator = rbtIntTree.iterator();

        // Check if the iterator returns the correct values in the range in the ascending order
        assertTrue(iterator.hasNext());
        assertEquals((Integer) 15, iterator.next());
        assertEquals((Integer) 15, iterator.next());
        assertEquals((Integer) 25, iterator.next());
        assertFalse(iterator.hasNext());
    }

    /**
     * This tester iterableRBT_test3
     * Use string as comparable data;
     * does not include duplicates;
     * Maximum (end point) for the iterator is set but Minimum (start point) is not set;
     * The iterator should be able to return values within a range
     */
    @Test
    public void iterableRBT_test3() {
        IterableRedBlackTree<String> rbtStrTree = new IterableRedBlackTree<>();
        rbtStrTree.insert("h");
        rbtStrTree.insert("b");
        rbtStrTree.insert("k");
        rbtStrTree.insert("a");
        rbtStrTree.insert("c");
        rbtStrTree.insert("j");
        rbtStrTree.insert("z");

        // set maximum for the iterator
        rbtStrTree.setIteratorMax("k");

        // Create an iterator
        Iterator<String> iterator = rbtStrTree.iterator();

        // Check if the iterator returns the correct values in the range in the ascending order
        assertTrue(iterator.hasNext());
        assertEquals((String) "a", iterator.next());
        assertEquals((String) "b", iterator.next());
        assertEquals((String) "c", iterator.next());
        assertEquals((String) "h", iterator.next());
        assertEquals((String) "j", iterator.next());
        assertEquals((String) "k", iterator.next());
        assertFalse(iterator.hasNext());
    }

    /**
     * This tester iterableRBT_test3
     * Use string as comparable data;
     * does not include duplicates;
     * Maximum (end point) for the iterator is set but Minimum (start point) is not set;
     * The iterator should be able to return values within a range
     */
    @Test
    public void iterableRBT_test4() {
        IterableRedBlackTree<String> rbtStrTree = new IterableRedBlackTree<>();
        rbtStrTree.insert("n");
        rbtStrTree.insert("a");
        rbtStrTree.insert("z");
        rbtStrTree.insert("k");
        rbtStrTree.insert("m");

        // set both minimum and maximum for the iterator
        rbtStrTree.setIteratorMin("k");
        rbtStrTree.setIteratorMax("n");

        // Create an iterator
        Iterator<String> iterator = rbtStrTree.iterator();

        // Check if the iterator returns the correct values in the range in the ascending order
        assertTrue(iterator.hasNext());
        assertEquals((String) "k", iterator.next());
        assertEquals((String) "m", iterator.next());
        assertEquals((String) "n", iterator.next());
        assertFalse(iterator.hasNext());
    }
}

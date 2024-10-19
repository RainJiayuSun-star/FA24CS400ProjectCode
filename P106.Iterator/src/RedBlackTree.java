import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * RedBlackTree class that implements a red-black tree
 */
public class RedBlackTree <T extends Comparable<T>> extends BSTRotation<T> {

    /**
     * Checks if a new red node in the RedBlackTree causes a red property violation
     * by having a red parent. If this is not the case, the method terminates without
     * making any changes to the tree. If a red property violation is detected, then
     * the method repairs this violation and any additional red property violations
     * that are generated as a result of the applied repair operation.
     * @param newRedNode a newly inserted red node, or a node turned red by previous repair
     */
    protected void ensureRedProperty(RBTNode<T> newRedNode) {
        RBTNode<T> child = newRedNode;
        RBTNode<T> parent = child.getUp(); // Initialize parent to null
        RBTNode<T> grandparent = null;

        // Assign grandparent
        if (parent != null) {
            grandparent = parent.getUp();
        }

        // If the node is the root, flip color of the node to black
        if (parent == null) {
            child.flipColor();
            return;
        }

        // If the parent is black, no need to repair
        if (!parent.isRed()) {
            return;
        }

        // Check for violation {
        if (parent.isRed()) { // If parent is red, then there must exist a grandparent
            RBTNode<T> uncle; // Initialize uncle
            if (grandparent.getLeft() == parent) { // assign uncle
                uncle = grandparent.getRight();
            } else {
                uncle = grandparent.getLeft();
            }

            // Case 1: parent is red and uncle is red: switch color for parent, uncle, and grandparent
            if (uncle != null && uncle.isRed()) {
                parent.flipColor();
                uncle.flipColor();
                grandparent.flipColor();
                ensureRedProperty(grandparent); // After complete these steps, check if earlier structure need to repair
            }

            // Case 2: uncle is black or null
            else {
                // Case 2.1
                if ((grandparent.getLeft() == parent && parent.getRight() == child) || (grandparent.getRight() == parent
                        && parent.getLeft() == child)) {
                    // rotate child and parent
                    rotate(child, parent);
                    // Reassign child and parent
                    RBTNode<T> temp = parent;
                    parent = child;
                    child = temp;
                }
                // Case 2.2
                // rotate parent and grandparent
                rotate(parent, grandparent);
                // After rotation, switch color of parent and grandparent

                // Flip
                parent.flipColor();
                grandparent.flipColor();
            }
        }
    }

    /**
     * Insert a Red-black tree node into the tree
     * @param data a node that get inserted
     * @throws NullPointerException if the node is null
     */
    @Override
    public void insert(T data) throws NullPointerException {
        // check if the data is null
        if (data == null) {
            throw new NullPointerException();
        }

        // Create a new rbt node
        RBTNode<T> rbtNode = new RBTNode<>(data);

        // If BST is empty
        if (this.root == null) {
            this.root = rbtNode;
        } else { // If BST is not empty
            this.insertHelper(rbtNode, root); // insert data into root tree using method from BST
        }
        this.ensureRedProperty(rbtNode); // check for violation. It fixes the tree if there is a violation
    }

    /**
     * This insertion into an empty RBT. This test check if the node as the root of the tree becomes black after.
     */
    @Test
    public void test101() {
        // Initialize tree for testing
        RedBlackTree<Integer> tree1 = new RedBlackTree<>();
        // Insert integer into the RBT
        tree1.insert(10);
        RBTNode<Integer> root = (RBTNode<Integer>) tree1.root;
        assertFalse(root.isRed(), "Root node should be black after insertion.");
    }

    /**
     * Case 1, where the uncle is red
     * This tester method implement RBT Insert based on the HW3.2 quiz 2nd question.
     */
    @Test
    public void test102() {
        // Initialize tree for testing
        RedBlackTree<String> tree2 = new RedBlackTree<>();
        // Insert strings into the RBT
        tree2.insert("O"); // insert O
        tree2.insert("F"); // insert F
        tree2.insert("V"); // insert V
        tree2.insert("D"); // insert D
        tree2.insert("J"); // insert J
        tree2.insert("S"); // insert S
        tree2.insert("Y"); // insert Y
        tree2.insert("C"); // insert C
        tree2.insert("E"); // insert E
        tree2.insert("P"); // Finally, insert P

        // Set root as the first node inserted
        RBTNode<String> root = (RBTNode<String>) tree2.root;
        // Check "O"
        assertEquals(root.getData(), "O", "Values are not equal: O"); // compare value
        assertNotNull(root.getData(), "should not be null."); // compare null
        assertFalse(root.isRed(), root.getData() + " should remain black."); // "O" should be Black

        // Check "F"
        assertEquals(root.getLeft().getData(), "F", "Values are not equal: F"); // compare value
        assertNotNull(root.getLeft().getData(), "should not be null."); // compare null
        assertFalse(!root.getLeft().isRed(), root.getLeft().getData() + " should remain red."); // "F" should be RED

        // Check "V"
        assertEquals(root.getRight().getData(), "V", "Values are not equal: V"); // compare value
        assertNotNull(root.getRight().getData(), "should not be null."); // compare null
        assertFalse(!root.getRight().isRed(), root.getRight().getData() +" should remain red."); // "V" should be RED

        // Check "D"
        assertEquals(root.getLeft().getLeft().getData(), "D", "Values are not equal: D"); // compare v
        assertNotNull(root.getLeft().getLeft().getData(), "should not be null."); // compare null
        assertFalse(root.getLeft().getLeft().isRed(), root.getLeft().getLeft().getData() + " should remain black."); // "D" should be Black

        // Check "J"
        assertEquals(root.getLeft().getRight().getData(), "J", "Values are not equal: J"); // compare v
        assertNotNull(root.getLeft().getRight().getData(), "should not be null."); // compare null
        assertFalse(root.getLeft().getRight().isRed(), root.getLeft().getRight().getData() + " should remain black."); // "J" should be Black

        // Check "S"
        assertEquals(root.getRight().getLeft().getData(), "S", "Values are not equal: S"); // compare v
        assertNotNull(root.getRight().getLeft().getData(), "should not be null."); // compare null
        assertFalse(root.getRight().getLeft().isRed(), root.getRight().getLeft().getData() + " should remain black."); // "S" should be Black

        // Check "Y"
        assertEquals(root.getRight().getRight().getData(), "Y", "Values are not equal: Y"); // compare v
        assertNotNull(root.getRight().getRight().getData(), "should not be null."); // compare null
        assertFalse(root.getRight().getRight().isRed(), root.getRight().getRight().getData() +" should remain black."); // "Y" should be Black

        // Check "C"
        assertEquals(root.getLeft().getLeft().getLeft().getData(), "C", "Values are not equal: C"); //cv
        assertNotNull(root.getLeft().getLeft().getLeft().getData(), "should not be null."); // compare null
        assertFalse(!root.getLeft().getLeft().getLeft().isRed(), root.getLeft().getLeft().getLeft().getData() +" should remain black."); // "C" should be Red

        // Check "E"
        assertEquals(root.getLeft().getLeft().getRight().getData(), "E", "Values are not equal: E");//cv
        assertNotNull(root.getLeft().getLeft().getRight().getData(), "should not be null."); // compare null
        assertFalse(!root.getLeft().getLeft().getRight().isRed(), root.getLeft().getLeft().getRight().getData() + " should remain red"); // "E" should be Red

        // Check "P"
        assertEquals(root.getRight().getLeft().getLeft().getData(), "P", "Values are not equal: P");//cv
        assertNotNull(root.getRight().getLeft().getLeft().getData(), "should not be null."); // compare null
        assertFalse(!root.getRight().getLeft().getLeft().isRed(), root.getRight().getLeft().getLeft().getData() + " should remain red"); // "P" should be Red
    }

    /**
     * Case 2, parent is red, uncle is black, rotate twice.
     */
    @Test
    public void test103() {
        // Initialize tree for testing
        RedBlackTree<String> tree3 = new RedBlackTree<>();
        // Insert Strings into the RBT
        tree3.insert("L"); // insert L
        tree3.insert("F"); // insert F
        tree3.insert("T"); // insert T
        tree3.insert("I"); // insert I
        tree3.insert("Q"); // insert Q
        tree3.insert("X"); // insert X
        tree3.insert("H"); // Finally insert H

        // Set root as the first node inserted
        RBTNode<String> root = (RBTNode<String>) tree3.root;

        // Check "L"
        assertNotNull(root.getData(), "should not be null."); // compare null
        assertEquals(root.getData(), "L", "Values are not equal: L"); // compare value
        assertFalse(root.isRed(), root.getData() + " should remain black."); // "L" should be Black

        // Check "H"
        assertNotNull(root.getLeft().getData(), "should not be null"); // compare null
        assertEquals(root.getLeft().getData(), "H", "Values are not equal: H"); // compare value
        assertFalse(root.getLeft().isRed(), root.getLeft().getData() + " should remain black"); // "H" should be Black

        // Check "T"
        assertNotNull(root.getRight().getData(), "should not be null"); // compare null
        assertEquals(root.getRight().getData(), "T", "Values are not equal: T"); // compare value
        assertFalse(root.getRight().isRed(), root.getRight().getData() + " should remain red"); // "T" should be black

        // Check "F"
        assertNotNull(root.getLeft().getLeft().getData(), "should not be null"); // compare null
        assertEquals(root.getLeft().getLeft().getData(), "F", "Values are not equal: I"); // compare value
        assertFalse(!root.getLeft().getLeft().isRed(), root.getLeft().getLeft().getData() + " should remain red"); // "F" should be RED

        // Check "I"
        assertNotNull(root.getLeft().getRight().getData(), "should not be null"); // compare null
        assertEquals(root.getLeft().getRight().getData(), "I", "Values are not equal: E"); // compare value
        assertFalse(!root.getLeft().getRight().isRed(), root.getLeft().getRight().getData() + " should remain red"); // "I" should be RED

        // Check "Q"
        assertNotNull(root.getRight().getLeft().getData(), "should not be null"); // compare null
        assertEquals(root.getRight().getLeft().getData(), "Q", "Values are not equal: Q"); // compare value
        assertFalse(!root.getRight().getLeft().isRed(), root.getRight().getLeft().getData() + " should remain black"); // "Q" should be RED

        // Check "X"
        assertNotNull(root.getRight().getRight().getData(), "should not be null"); // compare null
        assertEquals(root.getRight().getRight().getData(), "X", "Values are not equal: X"); // compare value
        assertFalse(!root.getRight().getRight().isRed(), root.getRight().getRight().getData() + " should remain black"); // "X" should be RED
    }
}

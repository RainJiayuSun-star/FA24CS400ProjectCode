import java.lang.ref.SoftReference;

/**
 * The BinarySearchTree class implements a binary search tree (BST) data structure
 * that allows for storing and retrieving elements in a sorted manner. It supports
 * standard BST operations like insertion, searching, checking size, and clearing
 * the tree. Additionally, this class includes tester methods to verify correct
 * functionality, which can be run via the main method.
 *
 * @param <T> the type of elements stored in the tree, which must implement the
 *            Comparable interface to ensure correct sorting order.
 */
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {
    protected BSTNode<T> root = null;

    /**
     * Performs the naive binary search tree insert algorithm to recursively
     * insert the provided newNode (which has already been initialized with a
     * data value) into the provided tree/subtree.  When the provided subtree
     * is null, this method does nothing.
     */
    protected void insertHelper(BSTNode<T> newNode, BSTNode<T> subtree) {
        if (subtree == null) {
            return;
        }
        // Compare newNode's data with the current subtree node's data
        int compare = newNode.getData().compareTo(subtree.getData());

        if (compare <= 0) {
            // Go to the left subtree
            if (subtree.getLeft() == null) {
                subtree.setLeft(newNode);
                newNode.setUp(subtree);
            } else {
                insertHelper(newNode, subtree.getLeft());
            }
        } else {
            // Go to the right subtree
            if (subtree.getRight() == null) {
                subtree.setRight(newNode);
                newNode.setUp(subtree);
            } else {
                insertHelper(newNode, subtree.getRight());
            }
        }
    }

    /**
     * Inserts a new data value into the sorted collection.
     *
     * @param data the new value being insterted
     * @throws NullPointerException if data argument is null, we do not allow
     *                              null values to be stored within a SortedCollection
     */
    @Override
    public void insert(T data) throws NullPointerException {
        if (data == null) { // Data is null
            throw new NullPointerException();
        }
        BSTNode<T> newNode = new BSTNode<>(data);
        if (root == null) {
            root = newNode;
        } else {
            insertHelper(newNode, root);
        }
    }

    /**
     * Check whether data is stored in the tree.
     *
     * @param data the value to check for in the collection
     * @return true if the collection contains data one or more times,
     * and false otherwise
     */
    @Override
    public boolean contains(Comparable<T> data) {
        return containsHelper(root, data);
    }

    /**
     * The containsHelper method helps to find a data if it is stored in the tree
     *
     * @param node the node that contains the data for comparing
     * @param data the value to check for in the collection
     * @return true if the collection contains data one or more times, false otherwise
     */
    protected boolean containsHelper(BSTNode<T> node, Comparable<T> data) {
        if (node == null) { // If the node does not exist, return false
            return false;
        }

        int compare = data.compareTo(node.getData()); // Compare the data if it is the same

        if (compare == 0) { // Data found
            return true;
        } else if (compare < 0) { // Data is less than the current one, go left tree
            return containsHelper(node.getLeft(), data);
        } else { // Data is greater than the current one, go right tree
            return containsHelper(node.getRight(), data);
        }
    }

    /**
     * Counts the number of values in the collection, with each duplicate value
     * being counted separately within the value returned.
     *
     * @return the number of values in the collection, including duplicates
     */
    @Override
    public int size() {
        return sizeHelper(root);
    }

    /**
     * This private method helps to determine the size of the BST
     *
     * @param node the node of the BST
     * @return the size of the BST tree
     */
    private int sizeHelper(BSTNode<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + sizeHelper(node.getLeft()) + sizeHelper(node.getRight());
    }

    /**
     * Checks if the collection is empty.
     *
     * @return true if the collection contains 0 values, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Removes all values and duplicates from the collection.
     */
    @Override
    public void clear() {
        root = null;
    }

    /**
     * Inserting multiple int as both left and right children in different orders to create differently shaped trees1
     * and test if insert(), size(), contains(), isEmpty(), and clear() methods works fine. test1() should have a
     * Perfect Binary Tree
     *
     * @return true if all testers passed, otherwise false.
     */
    public boolean test1() {
        // Create a testBST by inserting multiple values as both left and right children in different orders
        BinarySearchTree<Integer> testBST = new BinarySearchTree<>();
        // Test is empty method is correct 01. In this case, root == null
        if (!testBST.isEmpty()) {
            return false;
        }
        testBST.insert(10);
        testBST.insert(12);
        testBST.insert(8);
        testBST.insert(6);
        testBST.insert(11);
        testBST.insert(15);
        testBST.insert(9);

        // Test is empty method is correct 02. In this case, root == null
        if (testBST.isEmpty()) {
            return false;
        }

        // Test if each of the values are correctly inserted to the correct positions.
        if (Integer.compare(testBST.root.getData(), 10) != 0) { // Test if the root was init and assigned correctly
            System.out.println("root");
            return false;
        }
        if (Integer.compare(testBST.root.getLeft().getData(), 8) != 0) { // Root = 10, goes left, child should be 8
            System.out.println("8");
            return false;
        }
        if (Integer.compare(testBST.root.getRight().getData(), 12) != 0) { // Root = 10, goes right, child should be 12
            System.out.println("12");
            return false;
        }
        if (Integer.compare(testBST.root.left.getLeft().getData(), 6) != 0) { // Root = 8, goes left, child should be 6
            System.out.println("6");
            return false;
        }
        if (Integer.compare(testBST.root.left.getRight().getData(), 9) != 0) { // Root = 8, goes right, child should be 9
            System.out.println("9");
            return false;
        }
        if (Integer.compare(testBST.root.right.getLeft().getData(), 11) != 0) { // Root = 12, goes left, child be 11
            System.out.println("11");
            return false;
        }
        if (Integer.compare(testBST.root.right.getRight().getData(), 15) != 0) { // Root = 12, goes right, child be 15
            System.out.println("15");
            return false;
        }

        // Test the size of BST
        if (testBST.size() != 7) {
            System.out.println("Actual BST size: " + testBST.size());
            return false;
        }

        // See if testBST contains the inserted values by using contains method. Test contains method
        if (!testBST.contains(10) || !testBST.contains(12) || !testBST.contains(8) || !testBST.contains(6) ||
                !testBST.contains(11) || !testBST.contains(15) || !testBST.contains(9)) {
            System.out.println("contains method fails");
            return false;
        }

        // Test if the clear method is correct
        testBST.clear();
        if (!testBST.isEmpty()) {
            return false;
        }

        // If all testers passed
        return true;
    }

    /**
     * Inserting multiple string as both left and right children in different orders to create differently shaped trees1
     * and test if insert(), size(), contains(), isEmpty(), and clear() methods works fine. test2() should have a
     * Complete Binary Tree, left skewed.
     *
     * @return true if all testers passed, otherwise false.
     */
    public boolean test2() {
        // Create a testBST by inserting multiple values as both left and right children in different orders
        BinarySearchTree<String> testBST = new BinarySearchTree<>();
        // Test is empty method is correct 01. In this case, root == null
        if (!testBST.isEmpty()) {
            return false;
        }
        testBST.insert("d");
        testBST.insert("b");
        testBST.insert("a");
        testBST.insert("c");
        testBST.insert("f");

        // Test is empty method is correct 02. In this case, root == null
        if (testBST.isEmpty()) {
            return false;
        }

        // Test if each of the values are correctly inserted to the correct positions.
        if (!testBST.root.getData().equals("d")) { // root = "d"
            System.out.println(testBST.root.getData());
            return false;
        }
        if (!testBST.root.getLeft().getData().equals("b")) { // root = "d", goes left child = "b"
            System.out.println(testBST.root.getLeft().getData());
            return false;
        }
        if (!testBST.root.getRight().getData().equals("f")) { // root = "d", goes right child = "f"
            System.out.println(testBST.root.getRight().getData());
            return false;
        }
        if (!testBST.root.left.getLeft().getData().equals("a")) { // root = "b", goes left child = "a"
            System.out.println(testBST.root.left.getLeft().getData());
            return false;
        }
        if (!testBST.root.left.getRight().getData().equals("c")) { // root = "b", goes right child = "c"
            System.out.println(testBST.root.left.getRight().getData());
            return false;
        }

        // Test the size of BST
        if (testBST.size() != 5) {
            System.out.println("Actual BST size: " + testBST.size());
            return false;
        }

        // See if testBST contains the inserted values by using contains method. Test contains method
        if (!testBST.contains("d") || !testBST.contains("b") || !testBST.contains("a") || !testBST.contains("c") ||
                !testBST.contains("f")) {
            System.out.println("contains method fails");
            return false;
        }

        // Test if the clear method is correct
        testBST.clear();
        if (!testBST.isEmpty()) {
            return false;
        }

        // If all testers passed
        return true;
    }

    /**
     * Inserting multiple string as both left and right children in different orders to create differently shaped trees1
     * and test if insert(), size(), contains(), isEmpty(), and clear() methods works fine. test2() should have a
     * Right Skewed tree
     *
     * @return true if all testers passed, otherwise false.
     */
    public boolean test3() {
        // Create a testBST by inserting multiple values as both left and right children in different orders
        BinarySearchTree<String> testBST = new BinarySearchTree<>();
        // Test is empty method is correct 01. In this case, root == null
        if (!testBST.isEmpty()) {
            return false;
        }
        testBST.insert("d");
        testBST.insert("f");
        testBST.insert("g");
        testBST.insert("z");

        // Test is empty method is correct 02. In this case, root == null
        if (testBST.isEmpty()) {
            return false;
        }

        // Test if each of the values are correctly inserted to the correct positions.
        if (!testBST.root.getData().equals("d")) { // root = "d"
            System.out.println(testBST.root.getData());
            return false;
        }
        if (!testBST.root.getRight().getData().equals("f")) { // root = "d", right child = "f"
            System.out.println(testBST.root.getRight().getData());
            return false;
        }
        if (!testBST.root.right.getRight().getData().equals("g")) { // root = "f", right child = "g"
            System.out.println(testBST.root.right.getRight().getData());
            return false;
        }
        if (!testBST.root.right.right.getRight().getData().equals("z")) { // root = "g", right child = "z"
            System.out.println(testBST.root.right.right.getRight().getData());
            return false;
        }

        // Test the size of BST
        if (testBST.size() != 4) {
            System.out.println("Actual BST size: " + testBST.size());
            return false;
        }

        // See if testBST contains the inserted values by using contains method. Test contains method
        if (!testBST.contains("d") || !testBST.contains("f") || !testBST.contains("g") || !testBST.contains("z")) {
            System.out.println("contains method fails");
            return false;
        }

        // Test if the clear method is correct
        testBST.clear();
        if (!testBST.isEmpty()) {
            return false;
        }

        // If all testers passed
        return true;
    }

    /**
     * Run the tester methods
     *
     * @param args not used
     */
    public static void main(String[] args) {
        BinarySearchTree<Integer> mainBST = new BinarySearchTree<>(); // New BST tree
        System.out.println("The testers of the program:");
        System.out.println("tester1: " + mainBST.test1());
        System.out.println("tester2: " + mainBST.test2());
        System.out.println("tester3: " + mainBST.test3());
    }
}
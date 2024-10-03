/**
 * The BST Rotation class for left and right rotation
 */
public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree<T> {

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a right
     * child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this
     * method will either throw a NullPointerException: when either reference is
     * null, or otherwise will throw an IllegalArgumentException.
     *
     * @param child  is the node being rotated from child to parent position
     * @param parent is the node being rotated from parent to child position
     * @throws NullPointerException     when either passed argument is null
     * @throws IllegalArgumentException when the provided child and parent
     *                                  nodes are not initially (pre-rotation) related that way
     */
    protected void rotate(BSTNode<T> child, BSTNode<T> parent)
            throws NullPointerException, IllegalArgumentException {
        if (child == null || parent == null) {
            throw new NullPointerException("Neither the parent nor the child node can be null.");
        }

        if (parent.getRight() != null && parent.getRight().getData().equals(child.getData()) &&
                child.getUp().getData().equals(parent.getData())) {
            leftRotate(child, parent);
        } else if (parent.getLeft() != null && parent.getLeft().getData().equals(child.getData()) &&
                child.getUp().getData().equals(parent.getData())) {
            rightRotate(child, parent);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Performs a right rotation on the subtree rooted at the specified parent node. This operation promotes the left
     * child to the parent's position in the subtree. The right subtree of the child node becomes the left subtree of
     * the parent.
     *
     * @param child  the left child of the parent node that will be promoted to the parent's position after the rotation
     * @param parent the node currently serving as the root of the subtree to be rotated, which will become the right
     *               child of the child node
     * @throws IllegalArgumentException if the child is not the left child of the parent
     */
    private void rightRotate(BSTNode<T> child, BSTNode<T> parent)  {
        if (child == null || parent == null) {
            throw new NullPointerException("Neither the parent nor the child node can be null");
        }

        // Right Rotation - Adjust the nodes' position
        // step 1
        child.setUp(parent.getUp()); // Set the 'child' as the parent of the 'parent'

        // step 2
        if (parent.getUp() != null) {
            if (parent.isRightChild()) { // If the parent is the right child of the "parent's" parent
                parent.getUp().setRight(child); // The "parent's" parent set right to 'child'
            } else {
                parent.getUp().setLeft(child); // The "parent's" parent set left to 'child'
            }
        }

        // step 3
        parent.setLeft(child.getRight());

        // step 4
        child.setRight(parent);

        // step 5
        if (child.getRight().getLeft() != null) { // Set parent's left
            child.getRight().getLeft().setUp(parent);
        }

        // step 6 setting the parent back to child
        parent.setUp(child);
        if (child.getUp() == null) {
            root = child;
        }
    }

    /**
     * Performs a left rotation on the subtree rooted at the specific parent node. This operation promotes the right
     * child to the parent's position in the subtree. The left subtree of the 'child' node becomes the right subtree of
     * the parent.
     *
     * @param child  the right child of the parent node that will be moved to the parent's position after the rotation
     * @param parent the node currently serving as the root of the subtree to be rotated, which will become the left
     *               child of the child node
     * @throws NullPointerException     if both parents and the child node are null
     * @throws IllegalArgumentException if the child is not the right child of the parent
     */
    private void leftRotate(BSTNode<T> child, BSTNode<T> parent) {
        if (child == null || parent == null) {
            throw new NullPointerException("Neither the parent nor the child node can be null");
        }

        // Left Rotation - Adjust the nodes' position
        // step 1
        child.setUp(parent.getUp()); // Set the 'child' as the parent of the 'parent'

        // step 2
        if (parent.getUp() != null) {
            if (parent.isRightChild()) { // If the parent is the right child of the "parent's" parent
                parent.getUp().setRight(child); // The "parent's" parent set right to 'child'
            } else {
                parent.getUp().setLeft(child); // The "parent's" parent set left to 'child'
            }
        }

        // step 3
        parent.setRight(child.getLeft());

        // step 4
        // BSTNode<T> childLeft = child.getLeft();
        child.setLeft(parent);

        // step 5
        if (child.getLeft().getRight() != null) { // Set parent's left
            child.getLeft().getRight().setUp(parent);
        }

        // step 6 setting the parent back to child
        parent.setUp(child);
        if (child.getUp() == null) {
            root = child;
        }
    }

    /**
     * Perform Right rotation on root and its child. The 2 nodes that are rotated have 3 shared children between them
     * @return true if all tests pass, otherwise return false
     */
    public boolean test1() {
        // Create a testBST by inserting multiple values as both left and right children in different orders
        BSTRotation<Integer> test1BST = new BSTRotation<>();
        // Add nodes
        test1BST.insert(10);
        test1BST.insert(5);
        test1BST.insert(15);
        test1BST.insert(4);
        test1BST.insert(7);
        test1BST.insert(12);
        test1BST.insert(17);

        // Test if the tree is constructed correctly. Test if each of the values are correctly inserted to the correct
        // positions.
        if (!test1BST.root.getData().equals(10)) {
            return false;
        }
        if (!test1BST.root.getLeft().getData().equals(5)) {
            return false;
        }
        if (!test1BST.root.getRight().getData().equals(15)) {
            return false;
        }
        if (!test1BST.root.getLeft().getLeft().getData().equals(4)) {
            return false;
        }
        if (!test1BST.root.getLeft().getRight().getData().equals(7)) {
            return false;
        }
        if (!test1BST.root.getRight().getLeft().getData().equals(12)) {
            return false;
        }
        if (!test1BST.root.getRight().getRight().getData().equals(17)) {
            return false;
        }

        // System.out.println(test1BST.root); // 10
        // System.out.println(test1BST.root.getLeft()); //5

        // Start rotating, right rotate ROOT with CHILD in this case

        test1BST.rotate(test1BST.root.getLeft(), test1BST.root);

        /*
        // Manually check if the outputs are correct
        System.out.println(test1BST.root.getData()); // 5
        System.out.println(test1BST.root.getLeft().getData()); // 4
        System.out.println(test1BST.root.getRight().getData()); // 10
        System.out.println(test1BST.root.getRight().getLeft().getData()); // 7
        System.out.println(test1BST.root.getRight().getRight().getData()); // 15
        System.out.println(test1BST.root.getRight().getRight().getLeft().getData()); // 12
        System.out.println(test1BST.root.getRight().getRight().getRight().getData()); // 17
         */

        // Test if the nodes are in the correct position
        if (!test1BST.root.getData().equals(5)) {
            return false;
        }
        if (!test1BST.root.getLeft().getData().equals(4)) {
            return false;
        }
        if (!test1BST.root.getRight().getData().equals(10)) {
            return false;
        }
        if (!test1BST.root.getRight().getLeft().getData().equals(7)) {
            return false;
        }
        if (!test1BST.root.getRight().getRight().getData().equals(15)) {
            return false;
        }
        if (!test1BST.root.getRight().getRight().getLeft().getData().equals(12)) {
            return false;
        }
        if (!test1BST.root.getRight().getRight().getRight().getData().equals(17)) {
            return false;
        }

        // If all tests pass
        return true;
    }

    /**
     * Perform left rotation on root and its child. The 2 nodes that are rotated have 3 shared children between them
     * @return true if all tests pass, otherwise return false
     */
    public boolean test2() {
        // Create a testBST by inserting multiple values as both left and right children in different orders
        BSTRotation<Integer> test2BST = new BSTRotation<>();

        // Add nodes
        test2BST.insert(10);
        test2BST.insert(5);
        test2BST.insert(15);
        test2BST.insert(4);
        test2BST.insert(7);
        test2BST.insert(12);
        test2BST.insert(17);

        // Rotate left
        test2BST.rotate(test2BST.root.getRight(), test2BST.root);

        /*
        // Manually check if the outputs are correct
        System.out.println(test2BST.root.getData()); // 15
        System.out.println(test2BST.root.getLeft().getData()); // 10
        System.out.println(test2BST.root.getRight().getData()); // 17
        System.out.println(test2BST.root.getLeft().getLeft().getData()); // 5
        System.out.println(test2BST.root.getLeft().getRight().getData()); // 12
        System.out.println(test2BST.root.getLeft().getLeft().getLeft().getData()); // 4
        System.out.println(test2BST.root.getLeft().getLeft().getRight().getData()); // 7
         */

        //
        if (!test2BST.root.getData().equals(15)) {
            return false;
        }
        if (!test2BST.root.getLeft().getRight().getUp().getData().equals(10)){
            return false;
        }

        if (!test2BST.root.getLeft().getData().equals(10)) {
            return false;
        }
        if (!test2BST.root.getRight().getData().equals(17)) {
            return false;
        }
        if (!test2BST.root.getLeft().getLeft().getData().equals(5)) {
            return false;
        }
        if (!test2BST.root.getLeft().getRight().getData().equals(12)) {
            return false;
        }
        if (!test2BST.root.getLeft().getLeft().getLeft().getData().equals(4)) {
            return false;
        }
        if (!test2BST.root.getLeft().getLeft().getRight().getData().equals(7)) {
            return false;
        }

        // If all tests pass
        return true;
    }

    /**
     * Test right rotation on a parent that is not a root and its child. The two nodes that are rotated have 2 shared
     * children between them
     * @return true if all tests pass, otherwise return false
     */
    public boolean test3() {
        // Create a testBST by inserting multiple values as both left and right children in different orders
        BSTRotation<Integer> test3BST = new BSTRotation<>();

        // Add nodes; 2 shared children between parent and child
        test3BST.insert(13);
        test3BST.insert(9);
        test3BST.insert(15);
        test3BST.insert(7);
        test3BST.insert(10);
        test3BST.insert(4);
        test3BST.insert(2);
        test3BST.insert(5);

        // Test if the tree is constructed correctly. Test if each of the values are correctly inserted to the correct
        // positions.
        if (!test3BST.root.getData().equals(13)) {
            System.out.println(test3BST.root.getData());
            return false;
        }
        if (!test3BST.root.getLeft().getData().equals(9)) {
            System.out.println(test3BST.root.getLeft().getData());
            return false;
        }
        if (!test3BST.root.getRight().getData().equals(15)) {
            System.out.println(test3BST.root.getRight().getData());
            return false;
        }
        if (!test3BST.root.getLeft().getLeft().getData().equals(7)) {
            System.out.println(test3BST.root.getLeft().getLeft().getData());
            return false;
        }
        if (!test3BST.root.getLeft().getRight().getData().equals(10)) {
            System.out.println(test3BST.root.getLeft().getRight().getData());
            return false;
        }
        if (!test3BST.root.getLeft().getLeft().getLeft().getData().equals(4)) {
            System.out.println(test3BST.root.getLeft().getLeft().getLeft().getData());
            return false;
        }
        if (!test3BST.root.getLeft().getLeft().getLeft().getLeft().getData().equals(2)) {
            System.out.println(test3BST.root.getLeft().getLeft().getLeft().getLeft().getData());
            return false;
        }
        if (!test3BST.root.getLeft().getLeft().getLeft().getRight().getData().equals(5)) {
            System.out.println(test3BST.root.getLeft().getLeft().getLeft().getRight().getData());
            return false;
        }

        // Rotate right
        test3BST.rotate(test3BST.root.getLeft().getLeft(), test3BST.root.getLeft());

        /*
        // Manually check if it is the new nodes positions are correct
        System.out.println(test3BST.root.getData()); // 13
        System.out.println(test3BST.root.getLeft().getData()); // 7
        System.out.println(test3BST.root.getRight().getData()); // 15
        System.out.println(test3BST.root.getLeft().getLeft().getData()); // 4
        System.out.println(test3BST.root.getLeft().getRight().getData()); // 9
        System.out.println(test3BST.root.getLeft().getLeft().getLeft().getData()); // 2
        System.out.println(test3BST.root.getLeft().getLeft().getRight().getData()); // 5
        System.out.println(test3BST.root.getLeft().getRight().getRight().getData()); // 10
         */

        // Check if the positions updated correctly
        if (!test3BST.root.getData().equals(13)) {
            System.out.println(test3BST.root.getData());
            return false;
        }
        if (!test3BST.root.getLeft().getData().equals(7)) {
            System.out.println(test3BST.root.getLeft().getData());
            return false;
        }
        if (!test3BST.root.getRight().getData().equals(15)) {
            return false;
        }
        if (!test3BST.root.getLeft().getLeft().getData().equals(4)) {
            return false;
        }
        if (!test3BST.root.getLeft().getRight().getData().equals(9)) {
            return false;
        }
        if (!test3BST.root.getLeft().getLeft().getLeft().getData().equals(2)) {
            return false;
        }
        if (!test3BST.root.getLeft().getLeft().getRight().getData().equals(5)) {
            return false;
        }
        if (!test3BST.root.getLeft().getRight().getRight().getData().equals(10)) {
            return false;
        }

        // If all tests pass
        return true;
    }

    /**
     * Test right rotation on a parent that is not a root and its child. The 2 nodes that are rotated have 1 shared
     * children between them
     * @return true if all tests pass, otherwise return false
     */
    public boolean test4() {
        // Create a testBST by inserting multiple values as both left and right children in different orders
        BSTRotation<Integer> test4BST = new BSTRotation<>();

        // Add nodes; 2 shared children between parent and child
        test4BST.insert(10);
        test4BST.insert(5);
        test4BST.insert(15);

        // Test if the tree is constructed correctly. Test if each of the values are correctly inserted to the correct
        // positions.
        if (!test4BST.root.getData().equals(10)) {
            return false;
        }
        if (!test4BST.root.getLeft().getData().equals(5)) {
            return false;
        }
        if (!test4BST.root.getRight().getData().equals(15)) {
            return false;
        }

        // Rotate BST
        test4BST.rotate(test4BST.root.getRight(), test4BST.root);

        // Test if the nodes are in correct positions after the rotation
        if (!test4BST.root.getData().equals(15)) {
            System.out.println(test4BST.root.getData());
            return false;
        }
        if (!test4BST.root.getLeft().getData().equals(10)) {
            return false;
        }
        if (!test4BST.root.getLeft().getLeft().getData().equals(5)) {
            return false;
        }
        if (!test4BST.root.getLeft().getUp().getData().equals(15)) {
            System.out.println(test4BST.root.getLeft().getUp().getData());
            return false;
        }

        return true;
    }

    /**
     * Test right rotation on a parent that is not a root and its child. The 2 nodes that are rotated have 0 shared
     * children between them
     * @return true if all tests pass, otherwise return false
     */
    public boolean test5() {
        // Create a testBST by inserting multiple values as both left and right children in different orders
        BSTRotation<Integer> test5BST = new BSTRotation<>();

        // Add nodes; 2 shared children between parent and child
        test5BST.insert(10);
        test5BST.insert(15);

        // Test if the tree is constructed correctly. Test if each of the values are correctly inserted to the correct
        // positions.
        if (!test5BST.root.getData().equals(10)) {
            return false;
        }
        if (!test5BST.root.getRight().getData().equals(15)) {
            return false;
        }

        // Rotate Left
        test5BST.rotate(test5BST.root.getRight(), test5BST.root);

        // Test if the nodes are in correct positions after the rotation
        if (!test5BST.root.getData().equals(15)) {
            return false;
        }
        if (!test5BST.root.getLeft().getData().equals(10)) {
            return false;
        }
        if (!test5BST.root.getLeft().getUp().getData().equals(15)) {
            return false;
        }

        // If all tests pass
        return true;
    }

    /**
     * Run the tester methods
     *
     * @param args not used
     */
    public static void main(String[] args) {
        BSTRotation<Integer> mainBSTRotate = new BSTRotation<Integer>(); // New BST tree
        System.out.println("The testers of the program:");
        System.out.println("tester1: " + mainBSTRotate.test1());
        System.out.println("tester2: " + mainBSTRotate.test2());
        System.out.println("tester3: " + mainBSTRotate.test3());
        System.out.println("tester4: " + mainBSTRotate.test4());
        System.out.println("tester5: " + mainBSTRotate.test5());
    }
}


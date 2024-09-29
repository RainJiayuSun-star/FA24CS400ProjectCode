/**
 * RedBlackTree class that implements a red-black tree
 */
public class RedBlackTree <T extends Comparable<T>> extends BinarySearchTree<T> {

    /**
     * Checks if a new red node in the RedBlackTree causes a red property violation
     * by having a red parent. If this is not the case, the method terminates without
     * making any changes to the tree. If a red property violation is detected, then
     * the method repairs this violation and any additional red property violations
     * that are generated as a result of the applied repair operation.
     * @param newRedNode a newly inserted red node, or a node turned red by previous repair
     */
    protected void ensureRedProperty(RBTNode<T> newRedNode) {
        // TODO: Implement this method.
    }

    /**
     * Insert a Red-black tree node into the tree
     * @param data
     * @throws NullPointerException
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
}

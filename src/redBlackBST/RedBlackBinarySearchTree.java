package redBlackBST;

import common.Item;
import common.Key;

import java.util.ArrayList;

// TODO(Code is very similar to BST)
// TODO(AN important note on recursion is that the main difference between iteration and recursion is that the total amount of work done in iteration is known while for recursion it is not
//  For recursion we have a number of directions to be followed until we reach the goal where the instruction to be followed states that the work has been done)
public class RedBlackBinarySearchTree <k extends Comparable<Item>>{
    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private Node root;

    public class Node {
        public Item item;
        public Key key;
        public Node left, right;
        public boolean color;
        public int count;

        public Node(Key key,Item item) {
            this.key = key;
            this.item = item;
        }
    }
    public Node root() {return root;}

    // Define the link to parent of a node
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }
    public boolean isEmpty() {
        return root == null;
    }
    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.count = h.count;
        h.count = size(h.left) + size(h.right) + 1;
        return x;
    }
    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.count = h.count;
        h.count = size(h.left) + size(h.right) + 1;
        return x;
    }
    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        // Red = boolean value true
        // Black = boolean value false
        // Whatever color h was will be now be inverted
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }
    public int size() {
        return size(root);
    }
    private Item get(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else
                return x.item;
        }
        return null;
    }
    public Item get(Key key) {
        return get(root,key);
    }

    public void put(Key key,Item item){
        root = put(root,key,item);
        // The color of root should always be black
        root.color = BLACK;
    }

    private Node put(Node h,Key key,Item item){
        if(h == null) {
            h = new Node(key,item);
            ++h.count;
            h.color = RED;
            return h;
        }
        // Same as binary tree
        int cmp = key.compareTo(h.key);
        if      (cmp < 0) h.left  = put(h.left,  key, item);
        else if (cmp > 0) h.right = put(h.right, key, item);
        else              h.item   = item;

        // Set up links so that this red black tree has a 1-1 correspondence to a 2-3 tree
        // if the color of the right node is red and the color of the left node is black rotate left
        if (isRed(h.right) && !isRed(h.left))
            h = rotateLeft(h);
        // If there are 2 successive red links rotate right
        if (isRed(h.left)  &&  isRed(h.left.left))
            h = rotateRight(h);
        // If a temporary 4-node is formed
        if (isRed(h.left)  &&  isRed(h.right))
            flipColors(h);
        h.count = size(h.left) + size(h.right) + 1;
        return h;
    }
    // Deletion
    /*
    Abstractions: We do not want to end up to a 2 node, so we perform color flips,rotations and moving the red characteristic to other nodes on the way down.
    In order to ensure the above abstraction we create the invariant where if we have two black nodes one must be set to red
    We want to end up to a situation where the min value is a red node so that we can just set it to null <- Goal
    During balancing the red color is always pushed towards the parent until it reaches the root, when it reaches the root of the tree balance will be restored by simply setting it to black
    As we search for the node to delete and alter the tree ,we don't care about the right leaning links and four nodes formed since they will be fixed after the node is deleted*
    After we set the value to null we balance the tree until perfect balance is achieved
    * */

    // Deletion helper methods
    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }
    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }
    // restore red-black tree invariant
    private Node balance(Node h) {
        if (isRed(h.right) && !isRed(h.left))    h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.count = size(h.left) + size(h.right) + 1;
        return h;
    }
    // The minimum value in a BST is the last/the deepest node without a left child in the left subtree
    public Node min() {
        return min(root);
    }
    // The minimum value of the tree
    // This same method can be reused within the class to find the minimum value within a specific subtree
    private Node min(Node x) {
        if(x.left == null) return x;
        // Recursively search for the minimum value
        return min(x.left);
    }

    // The maximum value of the tree
    // The maximum value in a BST is the last/the deepest node without a right child in the right subtree
    public Node max() {
        return max(root);
    }
    private Node max(Node x) {
        if(x.right == null) return x;
        // Recursively search fo the max value by checking the right children
        return max(x.right);
    }

    // Delete min
    public void deleteMin() {
        // if both children of root are black, set root to red
        // Having a red root will make it possible to move the red characteristic within the tree
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        // Ensure the root is always returned to black since it might have been changed if it had 2 black children
        if (!isEmpty()) root.color = BLACK;
    }
    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMin(Node h) {
        if (h.left == null)
            return null;

        // If both are black move red towards the left direction
        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        // Fix the right leaning links and four nodes formed when we searched for the key
        return balance(h);
    }

    public void deleteMax() {
        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }
    // delete the key-value pair with the maximum key rooted at h
    private Node deleteMax(Node h) {
        // If the left child is red rotate right so that the red node is now on the right
        if (isRed(h.left))
            h = rotateRight(h);
        if (h.right == null)
            return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }
    public void delete(Key key) {
        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        // TO always ensure root is set back to black
        if (!isEmpty()) root.color = BLACK;
    }
    // delete the key-value pair with the given key rooted at h
    // Changes are made as we search fo the key
    private Node delete(Node h, Key key) {

        if (key.compareTo(h.key) < 0)  {
            // If there are two black nodes following each other turn one to a red node
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        // The key is in the right subtree
        else {
            // If it was true the red will be moved towards right by rotating the tree right since the key we are looking for is on the right subtree and the goal that our key should be red is on course
            if (isRed(h.left))
                h = rotateRight(h);
            // If true means We have reached our final goal of a red node with no children
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            // If true it means that we are currently in a 2-node and to stay on course we need to move the red color towards the right
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            // Means that the node to be deleted has children/is na internal node
            // Remember we do not set internal nodes to null rather we exchange them with the minimum from the right subtree and then we delete the minimum
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.item = x.item;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    // Floor -> This is the largest item that is smaller than a given item
    public String floor(Key key) {
        Node x = floor(root,key);
        if (x == null) return null;
        return x.item.itemName;
    }
    private Node floor(Node x,Key key) {
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        // There are 3 situations we need to consider when looking for the floor of an item
        // If  the parameter item is equal to the current item
        if(cmp == 0) return x;

        // Here we are traversing the left subtree to look for a possible floor item
        // If the parameter item is less than the current item search recursively for the floor in the left subtree
        // In this case null just means that the floor of this key is not located in the left subtree
        if(cmp < 0) return floor(x.left,key);

        // If the parameter item is greater than the current item search recursively for the floor in the right subtree of the current item since the floor can not be lesser than the current item therefore no need for left traversal. If there exists a child in the right subtree return it, otherwise return the current item that resulted in the initial right branch
        Node t = floor(x.right,key);
        // Floor is located in the right subtree
        if(t != null) return t;
            // Floor is not located in the right subtree therefore x is returned
        else return x;
    }

    // ceiling implementation
    // Ceiling is the smallest item greater or equal to
    // It has to be greater
    public String ceiling(Key key) {
        Node x = ceiling(root,key);
        if(x == null) return null;
        return x.item.itemName;
    }
    private Node ceiling(Node x, Key key) {
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        // If the current item is equal to the parameter item given by (key) return the current item
        if(cmp == 0) return x;

        // If the parameter item is less than the current item cpm == -1, search recursively of there is a child item that is greater than the parametrized item in the left subtree since we are looking for the smallest but greatest item
        // If there is return it, if there isn't return the item at the point where it branched to the left subtree
        if(cmp < 0) {
            Node t = ceiling(x.left,key); // The return function at this point will not lead to termination of this method
            if(t != null) return t;
            else return x;
        }
        // If the parameter item is greater than the current item cpm == 1, recursively search for the ceiling in the right subtree
        // The ceiling has to be greater or equal to the parameter item
        return ceiling(x.right,key); // The return function at this point will lead to termination of this method like a chain reaction /*
    }

    // Rank -> This is the number of items that are less than the passed key
    public int rank(Key key) {
        return rank(key,root);
    }

    private int rank(Key key, Node x) {
        if(x == null) return 0;
        int cmp = key.compareTo(x.key);
        // If the passed key called key is smaller than x
        // Check the rank of the node in the left subtree
        if(cmp < 0) return rank(key,x.left);
            // If the key passed is larger
            // 1. Count the current node as part of the rank
            // 2. Count all the children to the left of the current node
            // 3. Finally check if there are any smaller nodes to the right of the current node
        else if (cmp > 0) return 1 + size(x.left) + rank(key,x.right);
            // If they are equal count all the nodes to the left of the current node
        else return size(x.left);
    }

    public Iterable<Item> iteratorOrdered() {
        ArrayList<Item> q = new ArrayList<>();
        inorder(root,q);
        for(Item item : q)
            System.out.print(item.itemName + " ");
        return q;
    }
    private void inorder(Node x,ArrayList<Item> q) {
        if(x == null) return;
        // Recursively iterate through the all left children
        inorder(x.left,q);
        // Place the element in the queue
        q.add(x.item);
        // Recursively iterate through all the right children
        inorder(x.right,q);
    }
    public void iteratorReverse() {
        ArrayList<Item> q = new ArrayList<>();
        inReverse(root,q);
        for(Item item : q)
            System.out.print(item.itemName + " ");
    }
    private void inReverse(Node x,ArrayList<Item> q){
        if(x == null) return;
        inReverse(x.right,q);
        q.add(x.item);
        inReverse(x.left,q);
    }


}

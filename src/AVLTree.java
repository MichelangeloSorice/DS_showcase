
import java.util.LinkedList;
import java.util.Queue;

public class AVLTree<T extends Comparable<T>>  {

    private class Node {
        T data;
        int height;
        // short for BalanceFactor
        int bf;
        Node left, right;

        public Node(T data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    private int nodeCount = 0;
    private Node root;

    public int height(){ return root == null ? 0: root.height; }
    public int size() { return nodeCount; }
    public boolean isEmpty() { return nodeCount == 0; }

    public boolean contains(T value){
        return contains(root, value);
    }

    public boolean insert(T value){
        if(value == null) return false;
        if(contains(value)) return false;
        root = insert(root, value);
        nodeCount++;
        return true;
    }

    public boolean remove(T value){
        if(value == null || !contains(root, value)) return false;
        root = remove(root, value);
        nodeCount--;
        return true;
    }

    private boolean contains(Node node, T value){
        if(node == null) return false;
        int cmp = value.compareTo(node.data);

        if( cmp == 0) return true;
        else if( cmp > 0) return contains( node.right, value );
        else return contains(node.left, value);
    }


    private Node insert(Node node, T value){
        if(node == null) return new Node(value, null, null);

        int cmp = value.compareTo(node.data);

        if(cmp > 0) node.left = insert(node.left, value);
        else node.right = insert(node.right, value);

        // Update balance factors and height values
        updateNode(node);

        return balanceNode(node);

    }

    private Node remove(Node node, T value){
        if(node == null) return null;

        int cmp = value.compareTo(node.data);
        if(cmp < 0){
            node.left = remove(node.left, value);
        }
        else if(cmp > 0){
            node.right = remove(node.right, value);
        }
        else {
            if(node.left == null)
                return node.right;
            else if(node.right == null)
                return node.left;

            // When removing a node with two children we can either replace it
            // with the smallest value in the right subtree or with the largest in the left one
            // As a heuristic we can chose to replace with a node from the highest subtree
            if(node.left.height > node.right.height ){
                T successorValue = findMax(node.left);
                node.data = successorValue;
                node.left = remove(node.left, successorValue);

            }else{
                T successorValue = findMin(node.right);
                node.data = successorValue;
                node.right = remove(node.right, successorValue);
            }
        }

        updateNode(node);
        return balanceNode(node);
    }

    private void updateNode(Node node){
        int rightNodeHeight = (node.right == null) ? -1 : node.right.height;
        int leftNodeHeight = (node.left == null) ? -1 : node.left.height;

        node.height = 1 + Math.max(rightNodeHeight, leftNodeHeight);
        node.bf = rightNodeHeight - leftNodeHeight;
    }

    private Node balanceNode(Node node){
        // Heavy Left subtree
        if(node.bf == -2){
            // Left left case
            if(node.left.bf <= 0)
                return leftLeftCase(node);
            return leftRightCase(node);
        }
        // Heavy right subtree
        else if(node.bf == +2){
            // Right right case
            if(node.right.bf >= 0)
                return rightRightCase(node);
            return rightLeftCase(node);
        }
        return node;
    }

    private Node leftLeftCase(Node node){
        return rightRotation(node);
    }

    private Node leftRightCase(Node node){
        node.left = leftRotation(node.left);
        return rightRotation(node);
    }

    private Node rightRightCase(Node node){
        return leftRotation(node);
    }

    private Node rightLeftCase(Node node){
        node.right = rightRightCase(node.right);
        return leftRotation(node);
    }

    private Node rightRotation(Node node) {
        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        updateNode(node);
        updateNode(newParent);
        return newParent;
    }

    private Node leftRotation(Node node) {
        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        updateNode(node);
        updateNode(newParent);
        return newParent;
    }

    private T findMin(Node node){
        Node trav = node;
        while(trav.left != null){
            trav = trav.left;
        }
        return trav.data;
    }

    private T findMax(Node node){
        Node trav = node;
        while(trav.right != null){
            trav = trav.right;
        }
        return trav.data;
    }
}

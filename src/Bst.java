import java.util.*;
import java.util.Queue;
import java.util.Stack;

/**
 * Basic implementation of a Binary Search Tree
 * @param <T>
 */
public class Bst<T extends Comparable<T>> {

    public enum TreeTraversalOrder{
        PREorder,
        POSTorder,
        INorder,
        LEVELorder;
    }

   private class Node {
       T data;
       Node left, right;

       public Node(T data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
       }
   }

   private int nodeCount = 0;
   private Node root = null;

    public int size(){
        return nodeCount;
    }

    public boolean isEmpty(){
        return nodeCount == 0;
    }

    public boolean contains(T elem){
        return find(elem) != null;
    }

    public Node find(T elem){
        Node curr = root;
        while(curr != null){
            int res = elem.compareTo(curr.data);
            if( res == 0) return curr;
            if( res < 0 ) curr = curr.left;
            if( res > 0 ) curr = curr.right;
        }

        return null;
    }

    public boolean add(T elem){
        if(contains(elem)) return false; // Not handling duplicates

        root = add(root, elem);
        nodeCount++;
        return true;
    }

    private Node add(Node node, T elem){
        if(node == null) return new Node(elem, null, null);

        int res = elem.compareTo(node.data);
        if(res < 0) node.left = add(node.left, elem);
        if(res > 0) node.right = add(node.right, elem);

        return node;
    }

    public boolean remove(T elem){
        Node toRemove = find(elem);
        if(toRemove == null) return false;

        root = remove(root, elem);
        nodeCount--;
        return true;
    }

    private Node remove(Node node, T elem){
        if(node == null ) return null;

        int res = elem.compareTo(node.data);

        if(res < 0) node.left = remove(node.left, elem);
        if(res > 0) node.right = remove(node.right, elem);
        if(res == 0) {
            // Found
            if( node.left == null && node.right == null ) return null; // removing leaf node
            if( node.left == null && node.right != null ) {
                Node dx = node.right;
                node.data = null;
                node.right = null;
                node = null;
                return dx;
            }
            if( node.left != null && node.right == null ) {
                Node sx = node.left;
                node.data = null;
                node.left = null;
                node = null;
                return sx;
            }

            // We have two subtrees we pick the biggest element in left subtree
            Node biggest = node.left;
            while(biggest.right != null) biggest = biggest.right;
            node.data = biggest.data;
            node.left = remove(node.left, node.data);
        }

        return node;
    }

    public int height(){
        return height(root);
    }

    public int height(Node node){
        if(node == null) return 0;
        return Math.max(height(node.left), height(node.right))+1;
    }

    public Iterator<T> traverse(TreeTraversalOrder order){
        switch (order){
            case INorder: return traverseInOrder();
            case PREorder: return traversePreOrder();
            case POSTorder: return traversePostOrder();
            case LEVELorder: return traverseLevelOrder();
            default: return null;
        }
    }

    private Iterator<T> traversePreOrder() {
        int expNodeCount = nodeCount; // to spot concurrent modifications
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if(expNodeCount != nodeCount) throw new ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if(expNodeCount != nodeCount) throw new ConcurrentModificationException();

                Node node = stack.pop();
                if(node.right != null) stack.push(node.right);
                if(node.left != null) stack.push(node.left);

                return node.data;
            }
        };
    }

    private Iterator<T> traversePostOrder() {
        int expNodeCount = nodeCount; // to spot concurrent modifications
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        stack1.push(root);

        while(!stack1.isEmpty()){
            Node node = stack1.pop();

            if(node != null){
                stack2.push(node);
                if(node.left != null) stack1.push(node.left);
                if(node.right != null) stack1.push(node.right);
            }
        }

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if(expNodeCount != nodeCount) throw new ConcurrentModificationException();
                return root != null && !stack2.isEmpty();
            }

            @Override
            public T next() {
                if(expNodeCount != nodeCount) throw new ConcurrentModificationException();

                return stack2.pop().data;
            }
        };
    }

    private Iterator<T> traverseInOrder() {
        int expNodeCount = nodeCount; // to spot concurrent modifications
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        return new Iterator<T>() {
            Node trav = root;
            @Override
            public boolean hasNext() {
                if(expNodeCount != nodeCount) throw new ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if(expNodeCount != nodeCount) throw new ConcurrentModificationException();

                // Going as far left as possible, pushing everything into the stack
                while(trav != null && trav.left != null){
                    stack.push(trav.left);
                    trav = trav.left;
                }

                Node node = stack.pop();

                if(node.right != null){
                    stack.push(node.right);
                    trav = node.right;
                }

                return node.data;
            }
        };
    }

    private Iterator<T> traverseLevelOrder(){
        int expNodeCount = nodeCount; // to spot concurrent modifications
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if(expNodeCount != nodeCount) throw new ConcurrentModificationException();
                return root != null && !queue.isEmpty();
            }

            @Override
            public T next() {
                Node node = queue.poll();
                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);
                return node.data;
            }
        };

    }

}

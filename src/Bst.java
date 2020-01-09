/**
 * Basic implementation of a Binary Search Tree
 * @param <T>
 */
public class Bst<T extends Comparable<T>> {

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


}

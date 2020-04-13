package dataStructures.trees;

/**
 * Tree data structure used for fast search of words (intended as sequences of symbols)
 * Implemented for lowercase english letters
 */
public class Trie {
    final int alphabetSize = 26;
    Node root;

    public Trie(int alphabetSize){
        root = new Node('/');
    }

    public void addWord(String word){
        if(word.length() == 0) {
            root.isEndOfWord = true;
            return;
        }
        add(root, 0, word.toCharArray());
    }

    public boolean search(String word){
        char[] wa = word.toCharArray();
        if(wa.length == 0) return root.isEndOfWord;

        Node trav = root;
        int pos = 0;

        while(trav != null){
            int cidx = wa[pos]-'a';
            if(trav.children[cidx] == null) return false;
            trav = trav.children[cidx];
            pos++;
            if(pos == wa.length-1) return trav.isEndOfWord;
        }

        return false;
    }


    private void add(Node n, int pos, char[] wa){
        int cidx = wa[pos]-'a';
        if(pos == wa.length-1) {
            n.isEndOfWord = true;
            return;
        }
        if(n.children[cidx] == null) n.children[cidx] = new Node(wa[pos]);
        add(n.children[cidx], pos+1, wa);
     }



    private class Node {
        Node[] children;
        char me;
        boolean isEndOfWord;

        Node(char me){
            this.me = me;
            children = new Node[alphabetSize];
        }
    }
}

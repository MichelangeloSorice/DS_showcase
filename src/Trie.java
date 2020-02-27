/**
 * Tree data structure used for fast search of words (intended as sequences of symbols)
 * @param <T>
 */
public class Trie<T extends Comparable<T>> {

    private class Node {
        Node[] children;
        boolean isEndOfWord;

        Node()
    }
}

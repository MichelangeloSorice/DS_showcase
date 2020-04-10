package dataStructures.trees;

/**
 * Simple FenwickTree implementation
 */
public class BinaryIndexedTree {
    // Array containing tree ranges
    private long[] tree;

    // Create empty tree
    public BinaryIndexedTree(int sz){
        tree = new long[sz+1];
    }

    // We have to make sure that the array of values is 1 based
    // this means that values[0] must not be used, O(n) construction time
    public BinaryIndexedTree(long[] values){
        if(values == null)
            throw new IllegalArgumentException("Values array is null!");

        this.tree = values.clone();

        for(int i = 1; i<tree.length; i++){
            int j = i + lsb(i);
            if(j < tree.length) tree[j] += tree[i];
        }
    }

    private int lsb(int i){
        // Get the LSB with some magic complement of 2
        return i & -1;
    }

    // Prefix sum in range [1, i]
    public long prefixSum(int i){
        long sum = 0L;
        while(i != 0){
            sum += tree[i];
            i -= lsb(i);
        }
        return sum;
    }

    // Sum in range [i, j]
    public long rangeSum(int i, int j){
        if(j<i) throw new IllegalArgumentException("invalid range");
        return prefixSum(j) - prefixSum(i-1);
    }

    // Adding 'val' to index i
    public void add(int i, long val){
        while( i < tree.length){
            tree[i] += val;
            i += lsb(i);
        }
    }

    // Setting index i to be equal to k
    public void set(int i, long k){
        long value = rangeSum(i, i);
        add(i, k-value);
    }
}

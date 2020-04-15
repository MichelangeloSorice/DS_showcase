package dataStructures.trees;

/**
 * Simple FenwickTree implementation - SUPPORT for POINT UPDATE, RAGE QUERY
 */
public class BinaryIndexedTree {
    // Array containing tree ranges
    // Each element of the array contains the prefix sum of a range of elements
    // tree[i] = prefixSum (i-lsb(i) , i]
    private long[] tree;

    // NEEDED for RANGE UPDATES and POINT QUERY version
    // private long[] original_tree;

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
        // original_tree = tree.clone();
    }

    private int lsb(int i){
        // Isolates the lowest one bit value
        return i & -i;

        // An alternative method is to use the Java's built in method
        // return Integer.lowestOneBit(i);
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

    // RANGE QUERY - Sum in range [i, j]
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

    // POINT UPDATE  - Setting index i to be equal to k
    public void set(int i, long k){
        long value = rangeSum(i, i);
        add(i, k-value);
    }


    /***
     * RANGE UPDATE AND POINT QUERY version
     * Let the Fenwick tree be initialized with zeros. Suppose that we want to increment the interval [l,r] by x
     * We make two point update operations on Fenwick tree which are add(l, x) and add(r+1, -x).
     *
     * If we want to get the value of A[i],
     * we just need to take the prefix sum using the ordinary range sum method (as original tree sums are all zeros).
     * To see why this is true, we can just focus on the previous increment operation again.
     * If i<l, then the two update operations have no effect on the query and we get the sum 0.
     * If i∈[l,r], then we get the answer x because of the first update operation.
     * And if i>r, then the second update operation will cancel the effect of first one.
     */


    //  The logic behind this method is the
    // same as finding the prefix sum in a Fenwick tree except that you need to
    // take the difference between the current tree and the original to get
    // the point value.
    // public long get(int i) {
         //return prefixSum(i, tree) - prefixSum(i - 1, original_tree);
    // }

    // Updating (adding to) range l to r inclusive with val
    // public void range_add(int l, int r, long val){
        //add(l, val);
        //add(r+1, -val);
    //}
}

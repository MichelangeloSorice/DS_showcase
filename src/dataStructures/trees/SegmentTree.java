package dataStructures.trees;

/**
 * Data structure used to efficiently compute range queries over an array
 * as finding range sum or maximum within range
 *
 * NOTES:
 * -Tree size has 4*N as upper limit:
 *   1 - the height of the tree is log2(N)
 *   2 - we store the tree withig an array root at index 1 - for node i - lc at 2*i, rc at 2*i+1
 *   Thus the rightmost node of a level at depth h is at 2^(h+1)-1
 *   The upper limit is 2^(log2(N)+1)-1 < 2*2^(log2(N)) = 4*2^(log2(N)-1) < 4*2^(log2(N)) == 4*N  :D
 */
public class SegmentTree {
    int size;
    int[] tree;

    /**
     * This implementation is for range sum queries
     * @param arr
     */
    public SegmentTree(int[] arr){
        size = arr.length;
        // Tree size has 4*N as upper limit: see notes;
        tree = new int[4*size];
        buildTree(1, 0, size-1, arr);
    }

    /**
     * @param vidx - node index
     * @param l - segment star
     * @param r - segment end
     * @param arr
     */
    public void buildTree(int vidx, int l, int r, int[] arr){
        if(l > r) return;
        if(l == r){
            tree[vidx] = arr[l];
        }else{
            int mid = l+(r-l)/2;
            // Build left and right subtrees
            buildTree(2*vidx, l, mid, arr);
            buildTree(2*vidx+1, mid+1, r, arr);
            tree[vidx] = tree[2*vidx]+tree[2*vidx+1];
        }
    }

    /**
     * Chacks validity of query and performs it
     * @param l - query start
     * @param r - query end
     * @return
     */
    public int queryTree(int l, int r){
        if(l<0 || r>=size || l>r) return -1;
        return query(1, 0, size-1, l, r);
    }

    /**
     * Performs actual query
     * @param vidx - node index
     * @param tl - node segment start
     * @param tr - node segment end
     * @param l - query start
     * @param r - query end
     * @return
     */
    public int query(int vidx, int tl, int tr, int l, int r){
        if(l > r) return 0;
        // Query is exactly about my segment
        if(l == tl && r == tr) return tree[vidx];
        int mid = tl+(tr-tl)/2;

        // Everything in left or right subtree
        if(r <= mid) return  query(2*vidx, tl, mid, l, r);
        if(l > mid) return query(2*vidx+1, mid+1, tr, l, r);

        // range covers partially both my left and right subtrees
        return query(2*vidx, tl, mid, l, mid) + query(2*vidx+1, mid+1, tr, mid+1, r);
    }

}

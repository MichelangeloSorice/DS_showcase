import dataStructures.trees.UnionFind;

import java.util.*;

public class Algorithms<T extends Comparable> {


    /**
     * Dijkstra's
     * NB - Main advantage wrt to BFS and DFS is being able to deal with weighted graphs
     *
     * Time Complexity O(V*log(V) + E) NB the upper limit for E is V^2 (think to a completely connected graph)
     * Actually if we implement it using a simple binary heap we will end up with O((V+E)log(V))
     * To get to the best time complexity we need a Fibonacci Heap which provides amortized O(1) time for updating keys
     *
     *
     * Steps - Dijkstra(G, W, s)  - Graph, Weight, Source vertex
     * 1. Initialization -->
     *  - d[i] to INFINITE for each vertex rather than s whose dist will be set 0
     *  - p[i] to null for each vertex (will keep predecessor of vertex i in sp)
     *  - S = {} set containing vertex for which SP has been found (already processed)
     *  - Q = {V[G]} set of vertexes to be processed init with the entire set of vertices
     *
     * 2. WHILE( Q is not EMPTY)
     *      u <-- GET_MIN(Q)    // the first we get is of course s - we will perform this V times
     *      S.add(u)
     *      for v in Adj[u]     // vertexes reachable from u
     *          relax(u, v, w)  // if( d[v] > d[u] + W[u][v] ) update d[v] and p[v] as well
     *                          // update op - will be performed at most E times
     */
    public int[] spDijkstra(int[][] edges, int N, int K) {
        // N vertexes 0 based
        List<List<Integer[]>> adj = new ArrayList();

        for(int i = 0; i<N; i++)  adj.add(new ArrayList());
        // Edges[i] = {source, dest, cost}
        for(int[] edg : edges)
            adj.get(edg[0]).add(new Integer[]{edg[1], edg[2]});

        // Initializing distances
        int[] dist = new int[N];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // The queue contains vertexes with their sp so far, may contain same vertex with multiple distances at the same time
        // a[0] vertex dist a[1] vertex id
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0]-b[0]));
        pq.offer(new int[]{0,K});

        int res = 0;
        while(!pq.isEmpty()){
            int[] cur = pq.poll();

            if(dist[cur[1]] != Integer.MAX_VALUE) continue; // already found sp for the node
            // Found sp up to it
            dist[cur[1]] = cur[0];

            // Relaxing
            List<Integer[]> c_adj = adj.get(cur[1]);
            for(Integer[] edg : c_adj){
                pq.offer(new int[]{cur[0] + edg[1], edg[0]});
            }
        }

        return dist;
    }


    /**
     * Bellman-Ford
     * NB - Main advantage is that it can deal/detect negative cycles and negative edges
     *
     * Time Complexity O(V*E) NB the upper limit for E is V^2 (think to a completely connected graph)
     *
     * Steps - Bellman-Ford(E, V, s) - Edges, Vertexes, Source node
     * 1. Initialization -->
     *  - Define an array D of size V containing sp distances for each vertex.
     *  - Set all distance values to +INF except for the source node whose distance is 0
     *
     * 2. Iterate for i = 0; i<V-1; i++:
     *      for e in E:                                         // we can go through edges in any order
     *          if(D[e.from] + e.cost < D[e.to])                // relax each edge
     *              D[e.to] = D[e.from] + e.cost < D[e.to]
     *
     * 3. Negative cycles detections:
     *  Repeat the exact operations of step 2 but in case an edge can be relaxed set
     *  the new distance of the to node to -INF. After v-1 iterations all nodes affected or
     *  directly in a negative cycle will be marked with -INF
     */


    /**
     * BFS - Breadth First Search
     * Graph as: Adjacency List - Adj|V| is an array with as many elements as vertexes in the graph
     *  each element contains a list of vertexes reachable in one step from that vertex.
     *
     * Space Time Complexity - O(V+E) vertices + edges - we search the whole graph
     *
     * NB - Main problems is detecting cycles to avoid exploring again and again the same node
     *  Possible approach is to use an hashmap to track the level of each node, if a node has already been found
     *  during the exploration of a previous level we avoid exploring it again
     * */



    /**
     * DFS - Depth First Search
     *  Recursive exploration of the graph, backtracking as necessary
     *
     * Space Time Complexity - O(V+E) vertices + edges - we search the whole graph
     *
     * Alg - for V in Adj(Start):
     *          if V is not in parent (set of visited nodes)
     *              parent[V] = S
     *              dfs(V, Adj(V))
     */



    /**
     * KRUSKAL'S MINIMUM SPANNING TREE:
     * Supposing edges is an Mx3 matrix representing a graph:
     * node edges[i][0] <---> edges[i][1] with weight edges[i][2]
     * The idea is to find the MSP the minimal weight set of edges connecting all nodes:
     * 1. Sort all edges by ascending weight;
     * 2. Walk through all edges in odred, if nodes are already unified continue else
     *  add edge to solution and unify nodes groups
     * 3. End when all nodes are connected or there are no more edges
     *
     * @param edges
     * @param nodes
     * @return
     */
    public int kruskalMSP(int[][] edges, int nodes){
        UnionFind ds = new UnionFind(nodes);
        Arrays.sort(edges, (a1, a2) -> a1[2] -a2[2]);

        List<int[]> mst = new ArrayList();
        int totWeight = 0;

        for(int[] edge : edges){
            if(ds.connected(edge[0], edge[1])) continue;

            mst.add(edge);
            ds.unify(edge[0], edge[1]);
            totWeight+=edge[2];

            if(ds.groups() == 1) break;
        }

        return totWeight;
    }

    /**
     * MERGE SORT: main idea is that if an array is not sorted,
     * we split it in two halves. If those are not sorted we split them again.
     * Finally we end up with a single element array which is obviiously sorted.
     * The we merge sorted arrays which can be done in linear time by scanning
     * both halves in order
     *
     * AVG complexity - O(n*log(N)) but requires copy of data: not in place
     * WC - O(n*log(N))
     */
    public T[] mergeSort(T[] arr){
        return mergeSort(arr, 0, arr.length);
    }

    // Last is not included
    public T[] mergeSort(T[] arr, int first, int last){
        int length = last - first;

        //AlreadySorted
        if(length <= 1) return arr;

        int middle = (first + last) / 2;
        T[] merge1 = mergeSort(arr, 0, middle);
        T[] merge2 = mergeSort(arr, middle +1, last);

        return merge(merge1, merge2, last);
    }

    private T[] merge(T[] arr1, T[] arr2, int size){
        int i = 0, j = 0, k = 0;
        T[] res = (T[]) new Object[size];

        for(;i < arr1.length && j < arr2.length; k++){
            if(arr1[i].compareTo(arr2[j]) <= 0){
                res[k] = arr1[i];
                i++;
            }else{
                res[k] = arr2[j];
                j++;
            }
        }

        while(i < arr1.length){
            res[k] = arr1[i]; i++; k++;
        }

        while(j < arr2.length){
            res[k] = arr2[j]; j++; k++;
        }
        return res;
    }


    /**
     * QUICK SORT:
     * We take a pivot point (el in the list), move all bigger things to the right of pivot
     * and all smaller things to the left of it. Recurse and sort the left side and the right side.
     *
     * AVG. Complexity - O(n*log(N)) in place algorithm
     * WC - O(N*N) when we choose as pivot always the larger or smaller element
     */
    public void quickSort(T[] arr){
        quickSort(arr, 0, arr.length);
    }

    private void quickSort(T[] arr, int first, int last){
        if(last - first <= 1) return;

        // We know nothing about list elements thus we can select any element as pivot
        // Using the last one we can avoid the need of swapping pivot with the last element of the list
        T pivot = arr[last-1];

        // Points to the next element to be swapped
        int nextSwap = first;
        // i goes through the array
        for (int i = first; i < last-1; i++){
            // If value is minor than pivot we swap it with next swap index
            if(arr[i].compareTo(pivot) <  0){
                T tmp = arr[nextSwap];
                arr[nextSwap] = arr[i];
                arr[i] = tmp;
                nextSwap++;
            }
        }

        // Setting pivot to its proper position
        arr[last-1] = arr[nextSwap];
        arr[nextSwap] = pivot;

        quickSort(arr, first, nextSwap-1);
        quickSort(arr, nextSwap+1, last);

    }



    /**
     * COUNTING SORT:
     * Basic assumption is that we are sorting a list of items
     * AND items are all element from a limited (and relativly small) set of keys eg. [0, 100] integers.
     * 1. We scan the array recording how many items of each type are there using an array of counters
     * 2. Scan the array of counters disposing in the result array as many elements as we found for each type
     *
     * Complexity - O(N+k) N is the number of items, K is the number of keys
     * NB if K is in the order of N square we have got a problem :)
     */

    /**
     * RADIX SORT:
     * The idea here is that in case our set K is too big we need to represent it in a more compact way.
     * 1. Imagine each of those K keys is an integer in base b - it will be represented on log_b(K)+1 digits
     * 2. We use counting sort to sort digits on each digit starting from least up to the most significant one
     *
     * Complexity - O((N+b)*log_b(K))
     * - To minimize it we must choose b = N in that case if k <= N^c - we get O(NC) linear time
     */


    /**
     * Moore's Voting Algorithm - Find Majority Element (for array of length N appears at least N/2 times)
     * The algorithm always identifies a candidate majority element even if no majority element is present
     * thus requires a verification step
     */

    public int majorityElement(int[] nums) {
        int candidate = nums[0], count = 1;

        for(int i = 1; i<nums.length; i++){
            if(candidate == nums[i]) {
                count++;
            }else if(count == 0){
                candidate = nums[i]; // Set up new candidate
                count = 1;
            }else{
                count--;
            }
        }

        // check if candidate is actually a majority element
        count = 0;
        for(int x : nums) if(x==candidate) count++;

        return count >= nums.length/2 ? candidate : null;
    }


}

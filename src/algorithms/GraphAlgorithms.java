package algorithms;

import dataStructures.trees.UnionFind;

import java.util.*;

public class GraphAlgorithms {

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

    // TODO

// ================================================================================================================


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

            // already found sp for the node
            if(dist[cur[1]] <= cur[0]) continue;
            // Found sp up to it
            dist[cur[1]] = cur[0];

            // Relaxing
            List<Integer[]> c_adj = adj.get(cur[1]);
            for(Integer[] edg : c_adj){
                if(cur[0]+edg[1] < dist[edg[0]])
                    pq.offer(new int[]{cur[0] + edg[1], edg[0]});
            }
        }

        return dist;
    }


// ================================================================================================================

    /**
     * Floyd Warshall Algorithm - Perfect for AllPairsDP in small graphs (about 200 nodes)
     * Time complexity is O(V^3)
     *
     * - We start representing the graph edges as a matrix
     *      adj[i][j] = weight of the edge among node i and j or +INF if no edge exists
     *
     * - int DP[N][N][N]
     *  DP[k][i][j] = sp from i to j routing through nodes 0..k
     *  DP[0][i][j] = adj[i][j]
     *  then - DP[k][i][j] = min(dp[k-1][i][j], dp[k-1][i][k]+dp[k-1][k][j])
     */
    public int[][] floydWarshall(int[][] adj){
        int n = adj.length;
        int[][] dp = new int[n][n];
        int[][] next = new int[n][n];
        for(int i = 0; i< n; i++) dp[i] = Arrays.copyOf(adj[i], n);

        for(int k = 0; k<n; k++)
            for(int i = 0; i<n; i++)
                for(int j  =0; j<n; j++)
                    if(dp[i][k] + dp[k][j] < dp[i][j]) {
                        dp[i][j] = dp[i][k] + dp[k][j];
                        next[i][j] = next[i][k];
                    }
        // For negative cycle detection perform ther same triple loop
        // But every time you can relax a distance, write -INF to mark that edge as affected by a neg cycle
        return dp;
    }

// ================================================================================================================

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

// ================================================================================================================

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

// ================================================================================================================

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
}

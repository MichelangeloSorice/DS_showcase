
// Array based union find
public class UnionFind {
    // number of elements
    private int size;

    // size of each group
    private int[] szs;

    // array modelling the graph
    // id[i] = z means that node z is the parent of node i
    // id[i] = i means that i is a root node
    private int[] id;

    // count of existing groups
    private int numGroups;

    public UnionFind(int size){
        if(size <= 0) throw new IllegalArgumentException("Attempting to create UnionFind with size <= 0!");

        szs = new int[size];
        id = new int[size];

        this.size = numGroups = size;

        for(int i = 0; i<size; i++) {
            szs[i] = 1;
            id[i] = i;
        }
    }

    // Finds the group which element el belongs to
    public int find(int el){

        if(el >= size) throw new IllegalArgumentException("No such element!");

        // Finding root node
        int root = el;
        while(root != id[root]) root = id[root]; // Following links up to root node

        // Called path compression make each element of the group point to root node
        // Enables amortized constant time for find method (as Dynamic Arrays insertion)
        while(el != root){
            int next = id[el];
            id[el] = root;
            el = next;
        }

        return root;
    }

    public void unify(int i, int j){
        int iRoot = find(i);
        int jRoot = find(j);

        if(iRoot != jRoot){
            if(szs[iRoot] >= szs[jRoot]){
                // make root of group j point to root of group i
                id[jRoot] = iRoot;
                szs[iRoot] += szs[jRoot];
                szs[jRoot] = 0;
            }else{
                id[iRoot] = jRoot;
                szs[jRoot] += szs[iRoot];
                szs[iRoot] = 0;
            }

            numGroups--;
        }

    }

    // States whether or not two elements bvelong to the same group
    public boolean connected(int i, int j){
        return find(i) == find(j);
    }

    public int size(){
        return size;
    }

    public int groups(){
        return numGroups;
    }

    public int groupSize(int el){
        return szs[find(el)];
    }
}

import java.util.*;

public class PQueue<T extends Comparable<T>> {
    // elements inside the heap
    private int heapSize = 0;
    // Internal heap capacity
    private int heapCapacity = 0;

    // Dynamic list to track elements within the heap
    private List<T> heap = null;

    // Map keeping track for each element value the set of indices
    // where it can be found within the heap
    // Enables O(logN) removals and O(1) contains checks
    private Map<T, TreeSet<Integer>> sup = new HashMap<>();

    public PQueue(){
        this(1);
    }
    public PQueue(int heapSize) {
        heap = new ArrayList<T>(heapSize);
    }

    // Construct a priority queue using heapify in O(n) time, a great explanation can be found at:
    // http://www.cs.umd.edu/~meesh/351/mount/lectures/lect14-heapsort-analysis-part.pdf
    public PQueue(T[] elems) {

        heapSize = heapCapacity = elems.length;
        heap = new ArrayList<T>(heapCapacity);

        // Place all element in heap
        for (int i = 0; i < heapSize; i++) heap.add(elems[i]);

        // Heapify process, O(n)
        for (int i = Math.max(0, (heapSize / 2) - 1); i >= 0; i--) boubleDownFor(i);
    }

    public boolean isEmpty(){
        return heapSize == 0;
    }

    public int size(){
        return heapSize;
    }

    public T peek(){
        if(isEmpty()) return null;
        return heap.get(0);
    }

    public T poll(){
        return removeAt(0);
    }

    public boolean contains(T elem){
        if(elem == null) return false;
        return sup.containsKey(elem);
    }

    public void add(T elem){
        if(elem == null) throw new RuntimeException("Attempting to add null element!");

        if(heapSize < heapCapacity){
            heap.set(heapSize, elem);
        }else{
            heap.add(elem);
            heapCapacity++;
        }

        int newElemIdx = heapSize;
        heapSize++;

        addToSup(elem, newElemIdx);
        boubleUpFor(newElemIdx);

    }


    public boolean remove(T elem){
        if(elem == null) throw new IllegalArgumentException();

        if(!contains(elem)) return false;

        Integer elIdx = sup.get(elem).first();
        if(elIdx != null) removeAt(elIdx);
        return elIdx != null;

    }


    private T removeAt(int idx){
        if(isEmpty()) return null;

        // Swapping last element with the one to be removed
        int lastElementIdx = heapSize -1;
        swapElements(lastElementIdx, idx);

        T removedElem = heap.get(lastElementIdx);
        heap.set(lastElementIdx, null);
        sup.get(removedElem).remove(lastElementIdx);
        heapSize--;

        boubleDownFor(idx);

        if(less(idx, getParentIdx(idx))) boubleUpFor(idx);

        return removedElem;

    }


    private void addToSup(T elem, int newElemIdx){
        if(sup.containsKey(elem)){
            sup.get(elem).add(newElemIdx);
        }else{
            TreeSet<Integer> set =new TreeSet<>();
            set.add(newElemIdx);
            sup.put(elem, set);
        }
    }

    private void boubleUpFor(int idx){

        int newElementIdx = idx;
        for(Integer parentIdx = getParentIdx(newElementIdx);
            // Heap invariant constraint parent < child -- MIN HEAP
            parentIdx != null &&  less(newElementIdx, parentIdx);
            parentIdx = getParentIdx(newElementIdx)){

            swapElements(parentIdx, newElementIdx);
            newElementIdx = parentIdx;
        }
    }

    private void boubleDownFor(int idx){

        while(true){
            int left = 2*idx +1;
            int right = 2*idx +2;
            int smallest = left;

            // Search for the smallest child node
            if(right < heapSize && less(right, left)) smallest = right;

            if(left >= heapSize || less(idx, smallest)) break;

            swapElements(idx, smallest);
            idx = smallest;
        }

    }

    private boolean less(int aIdx, int bIdx){
        T aElem = heap.get(aIdx);
        T bElem = heap.get(bIdx);

        return aElem.compareTo(bElem) <= 0;
    }


    private void swapElements(int aIdx, int bIdx){
        T aElem = heap.get(aIdx);
        T bElem = heap.get(bIdx);

        heap.set(aIdx, bElem);
        heap.set(bIdx, aElem);

        TreeSet<Integer> aSet = sup.get(aElem);
        TreeSet<Integer> bSet = sup.get(bElem);

        aSet.remove(aIdx);
        aSet.add(bIdx);

        bSet.remove(bIdx);
        bSet.add(aIdx);

        sup.put(aElem, aSet);
        sup.put(bElem, bSet);
    }

    private Integer getParentIdx(int idx){
        if(idx == 0) return null;
        if (firstChild(idx)) return (idx-1) / 2;
        else return (idx-2) / 2;
    }

    private boolean firstChild(int idx){
        return (idx % 2) == 1;
    }

}

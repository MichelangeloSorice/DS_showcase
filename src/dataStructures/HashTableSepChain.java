package dataStructures;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * Generic key value entry where K must be an hashable DataType
 * @param <K>
 * @param <V>
 */
class Entry<K, V>{
    int hash;
    K key;
    V value;

    public Entry(K key, V value){
        this.hash = key.hashCode();// built in method for hash codes
        this.value = value;
        this.key = key;
    }

    public boolean equals(Entry<K, V> other){
        if(this.hash != other.hash) return false;
        return key.equals(other.key);
    }
}


public class HashTableSepChain<K,V> implements Iterable<K>{

    private static final int DEFAULT_CAPACITY = 3;
    private static final double DEFAULT_LOAD_FACTOR= 0.75;

    // Resize after we exceed it -- is numKeys/capacity
    private double maxLoadFactor;
    // threshold is the integer representing the number of keys we can store without exceding the load factor
    private int capacity, threshold, size = 0;
    private LinkedList<Entry<K,V>>[] table;


    public HashTableSepChain(){
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSepChain(int capacity){
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSepChain(int capacity, double maxLoadFactor){
        if(capacity < 0) throw new IllegalArgumentException("Illegal capacity");
        if(maxLoadFactor <= 0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor))
            throw new IllegalArgumentException("Illegal maxLoadFactor");

        this.maxLoadFactor = maxLoadFactor;
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        this.threshold = (int) maxLoadFactor*capacity;
        table = new LinkedList[this.capacity];
    }

    public int size() { return size; }

    public boolean isEmpty()  { return size == 0; }

    // Used to convert keyHash in the domain [0, capacity)
    private int normalizeIndex(int keyHash){
        return Math.abs(keyHash) % capacity;
    }

    public void clear(){
        Arrays.fill(table, null);
        size = 0;
    }

    public boolean containsKey(K needle){
        int idx = normalizeIndex(needle.hashCode());
        return searchEntry(idx, needle) != null;
    }


    public V put(K key, V value){
        return insert(key, value);
    }

    public V get(K key){

        if(key == null) return null;
        int bucketIdx = normalizeIndex(key.hashCode());
        Entry<K, V> entry = searchEntry(bucketIdx, key);
        return entry ==  null ? null : entry.value;
    }


    public V remove(K key){
        if(key == null) throw new IllegalArgumentException("Null key");
        int bucketIndex = normalizeIndex(key.hashCode());
        return removeEntry(bucketIndex, key);
    }



    private V insert(K key, V value){
        if(key == null) throw new IllegalArgumentException("Null key");
        Entry<K,V> entry = new Entry<>(key, value);
        int bucketIdx = normalizeIndex(entry.hash);
        return insertEntry(bucketIdx, entry);
    }


    private V removeEntry(int bucketIdx, K key){
        LinkedList<Entry<K,V>> bucketEntries = table[bucketIdx];

        if (bucketEntries == null) return null;

        for(int i = 0; i<bucketEntries.size(); i++){
            Entry<K,V> entry = bucketEntries.get(i);
            if(key.equals(entry.key)){
                V value = entry.value;
                bucketEntries.remove(i);
                size--;
                return value;
            }
        }

        return null;
    }

    private V insertEntry(int bucketIdx, Entry<K,V> entry){
        LinkedList<Entry<K,V>> bucketEntries = table[bucketIdx];

        if(bucketEntries == null){
            table[bucketIdx] = bucketEntries = new LinkedList<>();
            bucketEntries.add(entry);
            size++;
            evaluateThreshold();
            return entry.value;
        }

        Entry<K,V> existingEntry = searchEntry(bucketIdx, entry.key);
        if(existingEntry == null){
            table[bucketIdx].add(entry);
            size++;
            evaluateThreshold();
        } else {
          V oldVal = existingEntry.value;
          existingEntry.value = entry.value;
          return oldVal;
        }

        return entry.value;
    }

    private Entry<K,V> searchEntry(int idx, K key){

        if(key == null) return  null;
        LinkedList<Entry<K,V>> bucketEntries = table[idx];
        if(bucketEntries == null) return null;

        for(Entry<K,V> entry: bucketEntries){
            if(key.equals(entry.key)) return  entry;
        }

        return null;
    }


    private void evaluateThreshold(){
        if(size > threshold) increaseCapacity();
    }

    private void increaseCapacity(){
        capacity *= 2;
        threshold = (int) maxLoadFactor*capacity;

        LinkedList<Entry<K,V>>[] newTable = new LinkedList[capacity];


        for(int i = 0; i<table.length; i++){
            if(table[i] != null){

                for(Entry entry: table[i]){
                    int newBucketIdx = normalizeIndex(entry.hash);
                    LinkedList<Entry<K,V>> newBucketEntries = newTable[newBucketIdx];
                    if(newBucketEntries == null) newTable[newBucketIdx] = newBucketEntries = new LinkedList<>();
                    newBucketEntries.add(entry);
                }

                table[i].clear();
                table[i] = null;
            }
        }

        table = newTable;
    }

    @Override
    public Iterator<K> iterator() {
        //TODO
        return null;
    }
}

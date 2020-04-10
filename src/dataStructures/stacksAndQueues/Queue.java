package dataStructures.stacksAndQueues;

import dataStructures.DLList;

public class Queue<T> {
    private DLList<T> data = new DLList<>();

    public int size(){
        return data.size();
    }

    public boolean isEmpty(){
        return data.isEmpty();
    }

    public void enqueue(T element){
        data.add(element);
    }

    public T dequeue(){
        return data.removeFirst();
    }
}

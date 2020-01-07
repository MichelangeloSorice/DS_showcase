import java.util.Iterator;

public class DynamicArray<T> implements Iterable<T> {

    private T[] data;
    private int capacity;
    private int len;

    public DynamicArray(){
        this(32);
    }

    public DynamicArray(int size){
        if(size < 0 ) throw new IllegalArgumentException("Size must be greater or equal than 0");
        this.data = (T[]) new Object[size];
        this.capacity = size;
        this.len = 0;
    }

    public int size(){ return len; }
    public boolean isEmpty() { return len == 0;}

    public T get(int idx){ return data[idx]; }
    public void set(int idx, T value){ data[idx] = value; }


    public void add(T elem){
        if(len +1 > capacity) doubleSize();
        data[len] = elem;
        len++;
    }

    public void  removeAt(int index){
        if(index < 0 || index >= len) throw new IndexOutOfBoundsException();
        for(int i=0, j= 0; i < len-1; i++, j++){
           if(i == index) j++;
           data[i] = data[j];
        }
    }

    public void remove(T elem){
        for(int i = 0; i < len; i++){
            if(data[i].equals(elem)) removeAt(i);
        }
    }



    private void doubleSize(){
        if(capacity == 0) capacity = 1;
        else { capacity *= 2; }

        T[] newData = (T[]) new Object[capacity];
        for(int i = 0; i < len; i++) newData[i] = data[i];

        this.data = newData;
    }


    @Override
    public Iterator<T> iterator() {
        return null;
    }
}

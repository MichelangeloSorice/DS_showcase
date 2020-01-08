public class Stack<T> {

    private DLList<T> data = new DLList<>();

    public int size(){
        return data.size();
    }

    public boolean isEmpty(){
        return data.isEmpty();
    }

    public void push(T elem){
        data.addFirst(elem);
    }

    public T pop(){
        if(isEmpty()) throw new RuntimeException("Empty stack!");
        return data.removeFirst();
    }

}

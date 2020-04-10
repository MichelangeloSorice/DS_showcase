package dataStructures;

public class DLList<T> {
    private DNode<T> head;
    private DNode<T> tail;
    private int size = 0;



    public void clear(){
        DNode<T> curr = head;
        while(curr != null){
            DNode<T> next = curr.next;
            curr.setValue(null);
            curr.setPrevious(null);
            curr.setNext(null);
            curr = next;
        }

        head = tail = null;
    }

    public int size(){ return size;}
    public boolean isEmpty() { return isEmpty();}

    public void add(T elem){
        DNode<T> nodeElem = new DNode<>(elem);
        if(isEmpty()){
            head = tail = nodeElem;
        }else{
            tail.setNext(nodeElem);
        }
        size++;
    }

    public void addFirst(T elem){
        DNode<T> nodeElem = new DNode<>(elem);
        if(isEmpty()){
            head = tail = nodeElem;
        }else{
            nodeElem.setNext(head);
            head.setPrevious(nodeElem);
            head = nodeElem;
        }
        size++;
    }
    
    public T peekFirst(){
        if(isEmpty()) return null;
        return head.value();
    }

    public T peekLast(){
        if(isEmpty()) return null;
        return tail.value();
    }

    public T removeFirst(){
        if(isEmpty()) throw new RuntimeException("Empty list!");

        DNode<T> curHead = head;
        head = curHead.getNext();
        head.previous = null;
        curHead.setNext(null);
        size--;

        if(isEmpty()) tail = null;
        return curHead.value();
    }

    public T removeLast(){
        if(isEmpty()) throw new RuntimeException("Empty list!");

        DNode<T> curTail = tail;
        tail = curTail.getPrevious();
        tail.next = null;
        curTail.setPrevious(null);
        size--;

        if(isEmpty()) head = null;
        return curTail.value();
    }

    public T removeAt(int idx){
        if(idx < 0 || idx >= size) throw new IllegalArgumentException();

        int base, step;
        DNode<T> removeNode;

        if( idx > size/2 ){
            removeNode = tail;
            base = size;
            step = -1;
        }else {
            removeNode = head;
            base = 0;
            step = 1;
        }

        while(base != idx) {
            removeNode = removeNode.next;
            base += step;
        }

        return remove(removeNode);
    }


    public boolean remove(Object elem){

        if(elem == null){
            for(DNode<T> trav = head; trav != null; trav = trav.next){
                if(trav.value() == null){
                    remove(trav);
                    return true;
                }
            }
        }else{
            for(DNode<T> trav = head; trav != null; trav = trav.next){
                if(elem.equals(trav.value())){
                    remove(trav);
                    return true;
                }
            }
        }

        return false;

    }

    public int indexOf(Object elem){
        int i = 0;

        if(elem == null){
            for(DNode<T> trav = head; trav != null; trav = trav.next, i++){
                if(trav.value() == null) return i;
            }
        }else{
            for(DNode<T> trav = head; trav != null; trav = trav.next, i++){
                if(elem.equals(trav.value())) return i;
            }
        }

        return -1;

    }

    public boolean contains(Object elem){
        return indexOf(elem) != -1 ? true : false;
    }

    private T remove(DNode<T> node){
        if(node.getNext() ==  null) return removeLast();
        if(node.getPrevious() == null) return removeFirst();

        node.getPrevious().setNext(node.getNext());
        node.getNext().setPrevious(node.getPrevious());
        size--;

        T data = node.value();
        node.setValue(null);
        node.setPrevious(null);
        node.setNext(null);

        return data;
    }




    private class DNode<T> {
        private DNode next;
        private DNode previous;
        private T value;

        public DNode(T value){ this(value, null, null); }
        public DNode(T value, DNode<T> next){ this(value, next, null); }
        public DNode(T value, DNode<T> next, DNode<T> previous){
            this.value = value;
            this.next = next;
            this.previous = previous;
        }

        public DNode getNext(){ return this.next; }
        public DNode getPrevious(){ return this.previous; }
        public T value(){ return this.value; }

        public void setNext(DNode next){ this.next = next; }
        public void setPrevious(DNode previous) { this.previous = previous; }
        public void setValue(T val){ this.value = val; }
    }

}

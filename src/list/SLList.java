package list;

public class SLList<T> {
    private SNode head;




    private class SNode {
        private SNode next;
        private T value;

        public SNode(T value){
            this.value = value;
            this.next = null;
        }

        public SNode next(){ return this.next; }
        public T value(){ return this.value; }

        public void setNext(SNode next){ this.next = next; }
        public void setValue(T val){ this.value = val; }
    }

}

public class LinkedListDeque<T> {
    private class StuffNode{
        public T item;
        public StuffNode prev;
        public StuffNode next;

        public StuffNode(T i, StuffNode p, StuffNode n) {
            item = i;
            prev = p;
            next = n;
        }

    }

    private StuffNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new StuffNode((T)null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public LinkedListDeque(T item) {
        sentinel = new StuffNode((T)null, null, null);
        sentinel.next = new StuffNode(item, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    /** The Deque API */
    public void addFirst(T item) {
        sentinel.next = new StuffNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    public void addLast(T item) {
        sentinel.prev = new StuffNode(item,sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    public boolean isEmpty() {
        if(size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        StuffNode p = sentinel;
        while (p.next != sentinel) {
            System.out.print(p.next.item + " ");
            p = p.next;
        }
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T firstitem = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return firstitem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T lastitem = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return lastitem;
    }

    public T get(int index) {
        if (index>=size) {
            return null;
        }
        StuffNode p = sentinel;
        for(int i = index; i>=0; i--) {
            p = p.next;
        }
        return p.item;
    }

    public T getRecursiveHelper(StuffNode p, int index){
        if (index == 0) {
            return p.item;
        }
        return getRecursiveHelper(p.next, index-1);
    }

    public T getRecursive(int index) {
        if (index>=size) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }


}
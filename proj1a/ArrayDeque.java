public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextlast;
    private int nextfirst;
    private int capacity;
    private int initialCapacity = 8;
    private int factor = 2;
    private int currentfirst;
    private int currentlast;
    private int oldCapacity;

    public ArrayDeque() {
        capacity = initialCapacity;
        items = (T[]) new Object[initialCapacity];
        size = 0;
        nextlast = 0;
        nextfirst = capacity - 1;
    }

    /** decrease a given index in the circle array. */
    private int minusOne(int index, int capa) {
        if (index == 0) {
            return capa - 1;
        }
        return index - 1;
    }
    /** increase a given index in the circle array. */
    private int plusOne(int index, int capa) {
        if (index == capa - 1) {
            return 0;
        }
        return index + 1;
    }

    /** expand the size of the array if its size is full. */
    private void expand() {
        if (size == capacity) {
            oldCapacity = capacity;
            capacity = capacity * factor;
            resize(capacity);
        }
    }

    /** contract the size of the array if its usage ratio is less than 25%. */
    private void contract() {
        double R = (double) size / capacity;
        if (R < 0.25 && capacity >= 16) {
            oldCapacity = capacity;
            capacity = capacity / factor;
            resize(capacity);
        }
    }

    /** change the size of the array to a given capacity;
     *  rearrange the sequence in the new array. */
    private void resize(int capa) {
        currentfirst = plusOne(nextfirst, oldCapacity);
        currentlast = minusOne(nextlast, oldCapacity);

        T[] a = (T[]) new Object[capa];
        nextfirst = capacity - 1;
        nextlast = 0;

        for (int i = currentfirst; i != currentlast; i = plusOne(i, oldCapacity)) {
            a[nextlast] = items[i];
            nextlast = plusOne(nextlast, capa);
        }
        a[nextlast] = items[currentlast];
        nextlast = plusOne(nextlast, capa);
        items = a;
    }

    /** add an  to the back end of the array,
      * must take constant time, except during resizing operations. */
    public void addLast(T x) {
        expand();

        items[nextlast] = x;
        size += 1;
        nextlast = plusOne(nextlast, capacity);
    }

    /** add an  to the front end of the array,
      * must take constant time, except during resizing operations. */
    public void addFirst(T x) {
        expand();

        items[nextfirst] = x;
        size += 1;
        nextfirst = minusOne(nextfirst, capacity);
    }

    /** remove an  from the back end of the array,
      * must take constant time, except during resizing operations. */
    public T removeLast() {
        contract();

        if (isEmpty()) {
            return null;
        }
        nextlast = minusOne(nextlast, capacity);
        T lastItem = items[nextlast];
        items[nextlast] = null;
        size -= 1;
        return lastItem;
    }

    /** remove an  to the back end of the array,
      * must take constant time, except during resizing operations. */
    public T removeFirst() {
        contract();

        if (isEmpty()) {
            return null;
        }
        nextfirst = plusOne(nextfirst, capacity);
        T firstitem = items[nextfirst];
        items[nextfirst] = null;
        size -= 1;
        return firstitem;
    }

    /** return the item of a given index in the array. */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        currentfirst = plusOne(nextfirst, capacity);
        T[] a = (T[]) new Object[size];
        for (int i = 0; i < size; i++) {
            a[i] = items[currentfirst];
            currentfirst = plusOne(currentfirst, capacity);
        }
        return a[index];
    }

    /** return the size of the array.
      * invariant: size is the number of s in the array. */
    public int size() {
        return size;
    }

    /** judge if the array is empty.
      * return true when the array is empty. */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public void printDeque() {
        currentfirst = plusOne(nextfirst, capacity);
        while (currentfirst != nextlast) {
            System.out.print(items[currentfirst] + " ");
            currentfirst = plusOne(currentfirst, capacity);
        }
        System.out.println();
    }

    /** unofficial test code */
    /**
    public static void main(String[] args) {
        ArrayDeque<Integer> testArray = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            testArray.addLast(i);
        }

        for (int i = 10; i < 18; i++) {
            testArray.addFirst(i);
        }

        testArray.printDeque();
        System.out.println(testArray.get(18));

        testArray.addFirst(30);
        testArray.addLast(40);
        testArray.removeFirst();
        for (int i = 0; i < 8; i++) {
            testArray.removeLast();
        }


        testArray.printDeque();
    } */

}

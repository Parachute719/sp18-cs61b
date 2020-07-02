public class ArrayDeque<Item> {
    private Item[] items;
    private int size;
    private int nextlast;
    private int nextfirst;
    private int capacity;
    private int initialCapacity = 8;
    private int factor = 2;

    public ArrayDeque() {
        capacity = initialCapacity;
        items = (Item[]) new Object[initialCapacity];
        size = 0;
        nextlast = 0;
        nextfirst = capacity - 1;
    }

    /** decrease a given index in the circle array. */
    public int minusOne(int index) {
        if (index == 0) {
            return capacity - 1;
        }
        return index - 1;
    }
    /** increase a given index in the circle array. */
    public int plusOne(int index) {
        if (index == capacity - 1) {
            return 0;
        }
        return index + 1;
    }

    /** expand the size of the array if its size is full. */
    public void expand() {
        if (size == capacity) {
            capacity = capacity * factor;
            resize(capacity);
        }
    }

    /** contract the size of the array if its usage ratio is less than 25%. */
    public void contract() {
        double R = size / capacity;
        if (R<0.25 && capacity>=16) {
            capacity = capacity / factor;
            resize(capacity);
        }
    }

    /** change the size of the array to a given capacity. */
    public void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    /** add an item to the back end of the array,
      * must take constant time, except during resizing operations. */
    public void addLast(Item x) {
        expand();

        items[nextlast] = x;
        size += 1;
        nextlast = plusOne(nextlast);
    }

    /** add an item to the front end of the array,
      * must take constant time, except during resizing operations. */
    public void addFirst(Item x) {
        expand();

        items[nextfirst] = x;
        size += 1;
        nextfirst = minusOne(nextfirst);
    }

    /** remove an item from the back end of the array,
      * must take constant time, except during resizing operations. */
    public Item removeLast() {
        contract();

        if(isEmpty()) {
            return null;
        }
        nextlast = minusOne(nextlast);
        Item lastItem = items[nextlast];
        items[nextlast] = null;
        size -= 1;
        return lastItem;
    }

    /** remove an item to the back end of the array,
      * must take constant time, except during resizing operations. */
    public Item removefirst() {
        contract();

        if(isEmpty()) {
            return null;
        }
        nextfirst = plusOne(nextfirst);
        Item firstItem = items[nextfirst];
        items[nextfirst] = null;
        size -= 1;
        return firstItem;
    }

    /** return the item of index i in the array. */
    public Item get(int i) {
        return items[i];
    }

    /** return the size of the array.
      * invariant: size is the number of items in the array. */
    public int size() {
        return size;
    }

    /** judge if the array is empty.
      * return true when the array is empty. */
    public boolean isEmpty() {
        if(size == 0) {
            return true;
        }
        return false;
    }

}

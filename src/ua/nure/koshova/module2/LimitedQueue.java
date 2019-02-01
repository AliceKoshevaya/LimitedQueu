package ua.nure.koshova.module2;

import java.text.Collator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Describe a generic class that implements the standard Queue interface to represent a queue of limited
 * length with two current pointers (to the beginning and to the end of the queue). Queue items are stored
 * in an array. The fixed length of the array is set in the constructor and further is considered unchanged.
 * Elements are added to the end and taken from the beginning. When adding and removing elements, the pointers
 * are shifted inside the array. After the end of the queue has shifted to the end of the array, it moves to
 * the beginning. A similar procedure is performed with a pointer to the beginning of the queue. If the pointer
 * to the end "catches up" with the pointer to the start, the queue is considered overflowing. If the start
 * pointer “catches up” with the end pointer, the queue is considered empty.
 *
 * @param <T>
 */
public class LimitedQueue<T> extends AbstractQueue<T> {

    private final int size;
    private T[] array;
    private int queueStart;
    private int queueEnd;

    public LimitedQueue() {
        this.size = 0;
    }

    public LimitedQueue(int size) {
        super();
        if (size <= 0) {
            throw new IllegalArgumentException("Invalid size");
        }
        this.queueStart = -1;
        this.queueEnd = -1;
        this.size = size;
        this.array = (T[]) new Object[size];
    }

    public void printState() {
        System.out.println(
                "Queue start: " + queueStart + ", Queue end: " + queueEnd + "\n" + Arrays.asList(array));
    }

    @Override
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (queueStart == -1 && queueEnd == -1);
    }

    public boolean isFull() {
        return (queueEnd + 1) % size == queueStart;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyQueueIterator();
    }

    public boolean add(T e) {
        if (isFull()) {
            throw new IllegalStateException("Error! Queue is Full");
        }
        return offer(e);
    }

    @Override
    public boolean offer(T e) {
        if (isFull()) {
            return false;
        } else if (isEmpty()) {
            queueStart++;
            queueEnd++;
            array[queueEnd] = e;
        } else {
            queueEnd = (queueEnd + 1) % size;
            array[queueEnd] = e;
        }
        return true;
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new IllegalStateException("Error! Queue is Empty");
        }
        return poll();
    }

    @Override
    public T poll() {
        T data;
        if (isEmpty()) {
            return null;
        } else if (queueStart == queueEnd) {
            data = array[queueStart];
            array[queueStart] = null;
            queueStart = -1;
            queueStart = -1;
        } else {
            data = array[queueStart];
            array[queueStart] = null;
            queueStart = (queueStart + 1) % size;
        }
        return data;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return array[0];
        }
    }

    @Override
    public String toString() {
        return "Queue: " + Arrays.toString(array);
    }

    private class MyQueueIterator implements Iterator<T> {

        private int index;

        public MyQueueIterator() {
            this.index = queueStart;
        }

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public T next() {
            T element = array[index];
            index++;
            return element;
        }
    }

    /**
     * String queue
     */
    class StringQueue {

        private LimitedQueue<T> queue;
        private Collator coll = Collator.getInstance(new Locale("uk"));

        public StringQueue(int size) {
            this.queue = new LimitedQueue<>(size);
        }

        public void printState() {
            queue.printState();
        }

        public boolean addWithRegex(String s) {
            Pattern pattern = Pattern.compile("(^|\\s)\\p{Lu}.*");
            Matcher m = pattern.matcher(s);
            if (m.matches()) {
                return queue.add((T) s);
            } else {
                throw new IllegalArgumentException("Invalid string! Enter a string with a capital letter");
            }
        }

        public void printInOrder() {
            coll.setStrength(Collator.PRIMARY);
            List<String> temp = Arrays.asList(Arrays.copyOf(this.queue.array, this.queue.size(), String[].class));
            Collections.sort(temp, (String o1, String o2) -> coll.compare(o1, o2));
            System.out.println(temp.toString());
        }
    }
}

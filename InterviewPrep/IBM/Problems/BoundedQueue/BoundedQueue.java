package InterviewPrep.IBM.Problems.BoundedQueue;

import java.util.NoSuchElementException;

public class BoundedQueue {

    private final int[] buffer;
    private final int capacity;
    private int head;
    private int tail;
    private int size;

    public BoundedQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.buffer = new int[capacity];
    }

    public boolean offer(int value) {
        if (size == capacity) {
            return false;
        }
        buffer[tail] = value;
        tail = (tail + 1) % capacity;
        size++;
        return true;
    }

    public int poll() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        int value = buffer[head];
        head = (head + 1) % capacity;
        size--;
        return value;
    }

    public int peek() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        return buffer[head];
    }

    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public static void main(String[] args) {
        BoundedQueue q = new BoundedQueue(3);

        check("initially empty", q.isEmpty());
        check("initially not full", !q.isFull());

        check("offer 10", q.offer(10));
        check("offer 20", q.offer(20));
        check("offer 30", q.offer(30));
        check("now full", q.isFull());
        check("offer 40 dropped (full)", !q.offer(40));
        check("size still 3", q.size() == 3);

        check("peek is 10", q.peek() == 10);
        check("poll returns 10", q.poll() == 10);
        check("not full after poll", !q.isFull());

        check("offer 40 accepted after poll", q.offer(40));
        check("poll returns 20", q.poll() == 20);
        check("poll returns 30", q.poll() == 30);
        check("poll returns 40 (wraparound)", q.poll() == 40);
        check("queue empty again", q.isEmpty());

        boolean threw = false;
        try {
            q.poll();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        check("poll on empty throws", threw);

        boolean threw2 = false;
        try {
            new BoundedQueue(0);
        } catch (IllegalArgumentException e) {
            threw2 = true;
        }
        check("capacity 0 throws", threw2);

        BoundedQueue cap1 = new BoundedQueue(1);
        check("offer 7 to cap-1 queue", cap1.offer(7));
        check("cap-1 queue full", cap1.isFull());
        check("cap-1 poll returns 7", cap1.poll() == 7);
        check("cap-1 queue empty after poll", cap1.isEmpty());
        check("cap-1 offer 8 reuses slot", cap1.offer(8));
        check("cap-1 poll returns 8", cap1.poll() == 8);
    }

    private static void check(String name, boolean condition) {
        System.out.println((condition ? "PASS " : "FAIL ") + name);
    }
}

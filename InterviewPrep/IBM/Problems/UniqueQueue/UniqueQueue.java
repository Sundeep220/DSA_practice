package InterviewPrep.IBM.Problems.UniqueQueue;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class UniqueQueue {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private final Set<Integer> seen = new HashSet<>();

    public boolean enqueue(int value) {
        if (seen.contains(value)) {
            return false;
        }
        Node node = new Node(value);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        seen.add(value);
        size++;
        return true;
    }

    public int dequeue() {
        if (head == null) {
            throw new NoSuchElementException("Queue is empty");
        }
        int value = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        seen.remove(value);
        size--;
        return value;
    }

    public int peek() {
        if (head == null) {
            throw new NoSuchElementException("Queue is empty");
        }
        return head.value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        UniqueQueue q = new UniqueQueue();

        check("enqueue 1", q.enqueue(1));
        check("enqueue 2", q.enqueue(2));
        check("enqueue 3", q.enqueue(3));
        check("duplicate 2 rejected", !q.enqueue(2));
        check("size is 3", q.size() == 3);
        check("peek is 1", q.peek() == 1);

        check("dequeue returns 1", q.dequeue() == 1);
        check("after dequeue, re-enqueue 1 accepted", q.enqueue(1));
        check("size is 3 again", q.size() == 3);

        check("dequeue returns 2", q.dequeue() == 2);
        check("dequeue returns 3", q.dequeue() == 3);
        check("dequeue returns 1 (re-enqueued)", q.dequeue() == 1);
        check("queue is empty", q.isEmpty());

        boolean threw = false;
        try {
            q.dequeue();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        check("dequeue on empty throws", threw);

        UniqueQueue all = new UniqueQueue();
        all.enqueue(5);
        all.enqueue(5);
        all.enqueue(5);
        check("all duplicates, size stays 1", all.size() == 1);
    }

    private static void check(String name, boolean condition) {
        System.out.println((condition ? "PASS " : "FAIL ") + name);
    }
}

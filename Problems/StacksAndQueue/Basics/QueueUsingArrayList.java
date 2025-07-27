package Problems.StacksAndQueue.Basics;

import java.util.ArrayList;

public class QueueUsingArrayList {
    private ArrayList<Integer> queue = new ArrayList<>(10);
    private int head = 0, tail = 0;

    public void enqueue(int data) {
        queue.add(tail++, data);
    }

    public int dequeue() {
        return queue.get(head++);
    }

    public int peek() {
        return queue.get(head);
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public boolean isFull() {
        return tail == queue.size();
    }
}

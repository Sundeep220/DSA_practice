package Problems.StacksAndQueue.Basics;

import java.util.Stack;

public class QueueUsingStacks {
    private final Stack<Integer> inStack = new Stack<>();
    private final Stack<Integer> outStack = new Stack<>();

    // Enqueue
    public void enqueue(int x) {
        inStack.push(x);
    }

    // Dequeue
    public Integer dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return null;
        }
        shiftStacks();
        return outStack.pop();
    }

    // Peek front element
    public Integer peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return null;
        }
        shiftStacks();
        return outStack.peek();
    }

    // Transfer elements if outStack is empty
    private void shiftStacks() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
    }

    // Check if queue is empty
    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    public int size() {
        return inStack.size() + outStack.size();
    }

    public static void main(String[] args) {
        QueueUsingStacks queue = new QueueUsingStacks();

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        System.out.println("Front: " + queue.peek());     // 10
        System.out.println("Dequeued: " + queue.dequeue()); // 10
        System.out.println("Front: " + queue.peek());     // 20
        System.out.println("Dequeued: " + queue.dequeue()); // 20
//        System.out.println("Dequeued: " + queue.dequeue()); // 30
//        System.out.println("Peek: " + queue.peek());       // null
//        System.out.println("Dequeued: " + queue.dequeue()); // null;
        queue.enqueue(40);
        System.out.println("Front: " + queue.peek());     // 30
        queue.enqueue(50);
        System.out.println("Dequeued: " + queue.dequeue()); // 30
        System.out.println("Front: " + queue.peek());     // 40
    }
}

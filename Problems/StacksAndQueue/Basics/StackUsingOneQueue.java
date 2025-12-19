package Problems.StacksAndQueue.Basics;

import java.util.LinkedList;
import java.util.Queue;

public class StackUsingOneQueue {
    private final Queue<Integer> queue = new LinkedList<>();

    // Push: Enqueue and rotate
    public void push(int x) {
        queue.add(x);
        int size = queue.size();
        for (int i = 0; i < size - 1; i++) {
            queue.offer(queue.poll());  // safer than remove()
        }
    }

    // Pop: Poll from front
    public Integer pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty.");
            return null;
        }
        return queue.poll();  // safer than remove()
    }

    // Top: Peek front
    public Integer top() {
        if (isEmpty()) {
            System.out.println("Stack is empty.");
            return null;
        }
        return queue.peek();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public static void main(String[] args) {
        StackUsingOneQueue stack = new StackUsingOneQueue();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Top: " + stack.top());  // 30
        System.out.println("Pop: " + stack.pop());  // 30
        System.out.println("Top: " + stack.top());  // 20
    }



}

package Problems.StacksAndQueue.Basics;

import java.util.LinkedList;
import java.util.Queue;

public class StackUsingTwoQueues {
    Queue<Integer> queue1 = new LinkedList<>(), queue2 = new LinkedList<>();

    public StackUsingTwoQueues() {}

    public void push(int x) {
        while(!queue1.isEmpty()) {
            queue2.offer(queue1.poll());
        }
        queue1.offer(x);
        while(!queue2.isEmpty()) {
            queue1.offer(queue2.poll());
        }
    }

    public Integer pop() {
        return queue1.poll();
    }

    public Integer top() {
        return queue1.peek();
    }

    public static void main(String[] args) {
        StackUsingTwoQueues stack = new StackUsingTwoQueues();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.top());  // 3
        System.out.println(stack.pop());  // 3
        stack.push(4);
        System.out.println(stack.top());  // 4
    }
}

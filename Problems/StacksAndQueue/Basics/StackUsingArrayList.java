package Problems.StacksAndQueue.Basics;

import java.util.ArrayList;

public class StackUsingArrayList {
    private ArrayList<Integer> stack = new ArrayList<>(10);
    private int top = 0;
    public void push(int data) {
        stack.add(top++, data);
    }

    public int pop() {
        return stack.get(--top);
    }

    public int peek() {
        return stack.get(top - 1);
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public boolean isFull() {
        return top == stack.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = top - 1; i >= 0; i--) {
            sb.append(stack.get(i)).append(" ");
        }
        return sb.toString();
    }

}

package Problems.StacksAndQueue.Basics;

public class StacksUsingArrays {
    private int[] stack = new int[10];
    private int top = 0;

    public void push(int data) {
        stack[top++] = data;
    }

    public int pop() {
        return stack[--top];
    }

    public int peek() {
        return stack[top - 1];
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public boolean isFull() {
        return top == stack.length;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = top - 1; i >= 0; i--) {
            sb.append(stack[i]).append(" ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        StacksUsingArrays stack = new StacksUsingArrays();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack);
        System.out.println("Popped: " + stack.pop());
        System.out.println(stack);
        System.out.println("Is Empty: " + stack.isEmpty());
        System.out.println("Is Full: " + stack.isFull());
        System.out.println("Peek: " + stack.peek());
    }
}

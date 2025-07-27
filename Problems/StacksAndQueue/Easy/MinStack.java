package Problems.StacksAndQueue.Easy;

import java.util.LinkedList;
import java.util.Stack;

public class MinStack {
    // Problem: https://leetcode.com/problems/min-stack/

    // Using Pair class

    class Pair{
        int val;
        int min;

        public Pair(int val, int min){
            this.val = val;
            this.min = min;
        }
    }

    Stack<Pair> stack = new Stack<>();

    public void push(int val) {
        if(stack.isEmpty()){
            stack.push(new Pair(val, val));
        } else {
            stack.push(new Pair(val, Math.min(val, stack.peek().min)));
        }
    }

    public void pop() {
        stack.pop();

    }

    public int top() {
        return stack.peek().val;
    }

    public int getMin() {
        return stack.peek().min;
    }


    // Without using extra space

    Stack<Integer> stack2 = new Stack<>();
    int min = Integer.MAX_VALUE;

    public void push2(int val) {
        if(stack2.isEmpty()){
            stack2.push(val);
            min = val;
        } else {
            if(val > min)
                stack2.push(val);
            else {
                stack2.push(2 * val - min);
                min = val;
            }
        }
    }

    public void pop2() {
        if(stack2.isEmpty())
            return;

        int x = stack2.peek();
        stack2.pop();

        if(x < min)
            min = 2 * min - x;

    }

    public int top2() {
        if(stack2.isEmpty())
            return -1;

        if(stack2.peek() < min)
            return min;
        else
            return stack2.peek();
    }

    public int getMin2() {
        return min;
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.getMin());
    }
}

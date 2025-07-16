package Problems.Recursion.Medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class SortaStack {
    // Problem: Sort a Stack

    // Brute Force: Using a temp stack
    // Time Complexity: O(n) Space Complexity: O(n)
    public static void sortStack(Stack<Integer> stack) {
        Stack<Integer> temp = new Stack<>();
        while (!stack.isEmpty()) {
            int curr = stack.pop();
            while (!temp.isEmpty() && temp.peek() > curr) {
                Integer top = temp.pop();
                stack.push(top);
            }
            temp.push(curr);
        }
        while (!temp.isEmpty()) {
            stack.push(temp.pop());
        }
    }

    // Solution2: Using recursion
    // Time Complexity: O(n^2) Space Complexity: O(n)
    public static void sortStackRec(Stack<Integer> stack) {
        if (!stack.isEmpty()) {
            int top = stack.pop();
            sortStackRec(stack);
            insertSorted(stack, top);
        }
    }

    // Solution3: Using List
    // Time Complexity: O(nlogn) Space Complexity: O(n)
    public static void sortStackList(Stack<Integer> stack) {
        List<Integer> list = new ArrayList<>();
        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }
        Collections.sort(list);
        for (int i = list.size() - 1; i >= 0; i--) {
            stack.push(list.get(i)); // push largest last → smallest on top
        }
    }

    private static void insertSorted(Stack<Integer> stack, int element) {
        if (stack.isEmpty() || stack.peek() <= element) {
            stack.push(element);
        } else {
            int temp = stack.pop();
            insertSorted(stack, element);
            stack.push(temp);
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(3);
        stack.push(2);
        stack.push(1);
        System.out.println(stack);
        sortStack(stack);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}

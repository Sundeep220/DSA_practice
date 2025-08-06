package Problems.StacksAndQueue.Hard;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StockSpanner {
    // PRoblem: https://leetcode.com/problems/online-stock-span/

    // Brute Force
    // Time Complexity: O(n^2)
    // Space Complexity: O(n)
    List<Integer> prices = new ArrayList<>();

    public int nextBrute(int price) {
        prices.add(price);
        int span = 1;

        // Traverse backwards to count span
        for (int i = prices.size() - 2; i >= 0; i--) {
            if (prices.get(i) <= price) {
                span++;
            } else {
                break;
            }
        }
        return span;
    }

    // Optimal Solution: Time Complexity: O(n) Space Complexity: O(n)
    // Caluclating PGE on the fly
    Stack<int[]> stack; // {price, span}

    public StockSpanner() {
        stack = new Stack<>();
    }

    public int next(int price) {
        int span = 1;

        // Merge spans of smaller/equal previous prices
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];
        }

        stack.push(new int[]{price, span});
        return span;
    }
}

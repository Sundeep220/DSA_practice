package Problems.Strings.Easy;

import java.util.Stack;

public class CountDepthOfParanthesis {

    //Brute Force:
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static int maxDepthBF(String s) {
        int max = 0;
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(c);
                max = Math.max(max, stack.size());
            } else if (c == ')') {
                stack.pop();
            }
        }
        return max;
    }


    //Optimal Solution:
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int maxDepth(String s) {
        int count = 0, max = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                count += 1;
                max = Math.max(max, count);
            } else if (c == ')') {
                count -= 1;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(maxDepthBF("(1+(2*3)+((8)/4))+1"));
        System.out.println(maxDepthBF("()(())((()()))"));
        System.out.println(maxDepth("(1+(2*3)+((8)/4))+1"));
        System.out.println(maxDepth("()(())((()()))"));

    }
}

package Problems.Strings.Easy;

public class RemoveOutermostParenthesis {

    // Brute Force Solution
    public static String removeOuterParenthesesBrute(String s) {
        StringBuilder result = new StringBuilder();
        int start = 0;
        int balance = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') balance++;
            else balance--;

            // Primitive ends when balance == 0
            if (balance == 0) {
                result.append(s.substring(start + 1, i)); // remove outermost ()
                start = i + 1;
            }
        }
        return result.toString();
    }

    // Optimal Solution
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static String removeOuterParentheses(String s) {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                if (count > 0) sb.append(c);
                count++;
            } else {
                count--;
                if (count > 0) sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "(()())(())";
        System.out.println(removeOuterParenthesesBrute(s));
        System.out.println(removeOuterParentheses(s)); // "()()()()(())"
    }
}

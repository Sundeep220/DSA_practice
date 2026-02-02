package Problems.DynamicProgramming.Partitions;
import java.util.ArrayList;
import java.util.List;

public class PalindromPartioning {
    // https://leetcode.com/problems/palindrome-partitioning/

    //| Approach     | Palindrome Check | Overall Time   | Extra Space |
    //| ------------ | ---------------- | -------------- | ----------- |
    //| Brute Force  | O(n)             | O(n³)          | O(n) + Recusion Stack O(n)       |
    //| Memoization  | O(1)             | O(n² + output) | O(n²)   + Recusion Stack O(n)      |
    //| Tabulation ⭐ | O(1)             | O(n² + output) | O(n²)       |

    // Recursion
    public static List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>();
        List<String> list = new ArrayList<>();
        helper(s, 0, list, ans);
        return ans;
    }

    public static void helper(String s, int index, List<String> list, List<List<String>> ans) {
        if (index == s.length()) {
            ans.add(new ArrayList<>(list));
            return;
        }

        for (int i = index; i < s.length(); i++) {
            if (isPalindrome(s, index, i)) {
                list.add(s.substring(index, i + 1));
                helper(s, i + 1, list, ans);
                list.removeLast();
            }
        }
    }

    public static boolean isPalindrome(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) return false;
            start++;
            end--;
        }
        return true;
    }


    // Memoization
    public static List<List<String>> partitionII(String s) {
        int n = s.length();
        Boolean[][] memo = new Boolean[n][n];

        List<List<String>> ans = new ArrayList<>();
        backtrack(s, 0, new ArrayList<>(), ans, memo);
        return ans;
    }

    static void backtrack(String s, int index, List<String> path, List<List<String>> ans, Boolean[][] memo) {

        if (index == s.length()) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int i = index; i < s.length(); i++) {
            if (isPal(s, index, i, memo)) {
                path.add(s.substring(index, i + 1));
                backtrack(s, i + 1, path, ans, memo);
                path.remove(path.size() - 1);
            }
        }
    }

    static boolean isPal(String s, int i, int j, Boolean[][] memo) {

        // base cases
        if (i >= j) return true;

        if (memo[i][j] != null)
            return memo[i][j];

        boolean result =
                (s.charAt(i) == s.charAt(j))
                        && isPal(s, i + 1, j - 1, memo);

        memo[i][j] = result;
        return result;
    }

    // Tabulation
    public static List<List<String>> partitionIII(String s) {
        boolean[][] pal = buildPalindromeTable(s);
        List<List<String>> ans = new ArrayList<>();
        backtrack(s, 0, new ArrayList<>(), ans, pal);
        return ans;
    }

    static void backtrack(String s, int index, List<String> path,
                          List<List<String>> ans, boolean[][] pal) {

        if (index == s.length()) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int i = index; i < s.length(); i++) {
            if (pal[index][i]) {
                path.add(s.substring(index, i + 1));
                backtrack(s, i + 1, path, ans, pal);
                path.remove(path.size() - 1);
            }
        }
    }


    static boolean[][] buildPalindromeTable(String s) {
        int n = s.length();
        boolean[][] pal = new boolean[n][n];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (j - i < 2 || pal[i + 1][j - 1]) {
                        pal[i][j] = true;
                    }
                }
            }
        }
        return pal;
    }


    // Another way
    static boolean[][] buildPalindromeTableII(String s) {
        int n = s.length();
        boolean[][] pal = new boolean[n][n];

        // BASE CASE 1: length = 1
        for (int i = 0; i < n; i++) {
            pal[i][i] = true;
        }

        // BASE CASE 2: length = 2
        for (int i = 0; i < n - 1; i++) {
            pal[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
        }

        // TABULATION: length >= 3
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    pal[i][j] = pal[i + 1][j - 1];
                }
            }
        }

        return pal;
    }


    public static void main(String[] args) {
        String s = "aab"; // [["a","a","b"],["aa","b"]]
        List<List<String>> ans = partition(s);
        System.out.println(ans);
    }
}

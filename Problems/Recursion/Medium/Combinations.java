package Problems.Recursion.Medium;
import java.util.*;
public class Combinations {
    // https://leetcode.com/problems/combinations/


    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(1, n, k, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int start, int n, int k, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i <= n; i++) {
            current.add(i);
            backtrack(i + 1, n, k, current, result);
            current.removeLast(); // backtrack
        }
    }

    public static void main(String[] args) {
        System.out.println(combine(4, 2));
    }
}

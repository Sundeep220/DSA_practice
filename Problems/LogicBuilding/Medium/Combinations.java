package Problems.LogicBuilding.Medium;
import java.util.*;


public class Combinations {
    // Problem Link: https://leetcode.com/problems/combinations/
    // Time Complexity: O(C(n,k)*k)
    // Space Complexity: O(C(n,k)*k)
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> currCombination = new ArrayList<>();
        backtrack(1, currCombination, res, n, k);
        return res;
    }

    void backtrack(int start, List<Integer> currCombination, List<List<Integer>> res, int n, int k) {
        if (currCombination.size() == k) {
            res.add(new ArrayList<>(currCombination)); // deep copy is important
            return;
        }
        // This checks and goes for all combinations which are not applicable like
        // for eg: [1,2,3,4] and k = 3, we can't have combination [1,2] as k = 3, but the loop will still go till we get [1,2]
        // That's necessary, so we can break the loop when start > n - k + 1
//        for (int i = start; i <= n; i++) {
        for (int i = start; i <= n - k + currCombination.size() + 1; i++) {
            currCombination.add(i);
            backtrack(i + 1, currCombination, res, n, k); // use i + 1, not start + 1
            currCombination.removeLast(); // backtrack
        }
    }

    public static void main(String[] args) {
        int n = 4, k = 2;
        System.out.println(new Combinations().combine(n, k));
    }

}

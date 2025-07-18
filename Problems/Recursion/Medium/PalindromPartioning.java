package Problems.Recursion.Medium;
import java.util.ArrayList;
import java.util.List;

public class PalindromPartioning {
    // https://leetcode.com/problems/palindrome-partitioning/

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
                list.remove(list.size() - 1);
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

    public static void main(String[] args) {
        String s = "aab"; // [["a","a","b"],["aa","b"]]
        List<List<String>> ans = partition(s);
        System.out.println(ans);
    }
}

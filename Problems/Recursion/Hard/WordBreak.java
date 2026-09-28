package Problems.Recursion.Hard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreak {
    // Problem: https://leetcode.com/problems/word-break/

    // Using Recursion: Checking for every prefix and suffix
    public boolean wordBreak(String s, List<String> wordDict) {
        return check(s, wordDict, new HashSet<>(wordDict));
    }

    public boolean check(String s, List<String> wordDict, HashSet<String> dict) {
        if(s.isEmpty())
            return true;

        // Checking for every prefix and suffix
        for(int i = 1; i <= s.length(); i++) {
            String prefix = s.substring(0, i);
            String suffix = s.substring(i);

            if(dict.contains(prefix) && check(suffix, wordDict, dict)) {
                return true;
            }
        }

        return false;
    }

    public boolean wordBreakRecII(String s, List<String> wordDict) {

        Set<String> dict = new HashSet<>(wordDict);

        return solve(s, 0, dict);
    }

    private boolean solve(String s, int start, Set<String> dict) {

        // Reached the end -> successfully segmented
        if (start == s.length()) {
            return true;
        }

        // Try every possible word starting at `start`
        for (int end = start + 1; end <= s.length(); end++) {

            String word = s.substring(start, end);

            if (dict.contains(word)) {

                // If the remaining string can be segmented
                if (solve(s, end, dict)) {
                    return true;
                }
            }
        }

        return false;
    }


    // Optmized Solution: Using Memoization
    public boolean wordBreakOptimized(String s, List<String> wordDict) {
        return checkOptimized(s, wordDict, new HashSet<>(wordDict), new HashMap<String, Boolean>());
    }

    public boolean checkOptimized(String s, List<String> wordDict, HashSet<String> dict, HashMap<String, Boolean> memo) {
        if(s.isEmpty())
            return true;

        if(memo.containsKey(s))
            return memo.get(s);

        // Checking for every prefix and suffix
        for(int i = 1; i <= s.length(); i++) {
            String prefix = s.substring(0, i);
            String suffix = s.substring(i);

            if(dict.contains(prefix) && checkOptimized(suffix, wordDict, dict, memo)) {
                memo.put(s, true);
                return true;
            }
        }

        memo.put(s, false);
        return false;
    }

    public boolean wordBreakMemoII(String s, List<String> wordDict) {

        Set<String> dict = new HashSet<>(wordDict);

        Boolean[] memo = new Boolean[s.length()];

        return solve(s, 0, dict, memo);
    }

    private boolean solve(String s, int start, Set<String> dict, Boolean[] memo) {

        // Reached the end
        if (start == s.length()) {
            return true;
        }

        // Already calculated
        if (memo[start] != null) {
            return memo[start];
        }

        // Try every possible word starting at start
        for (int end = start + 1; end <= s.length(); end++) {

            String word = s.substring(start, end);

            if (dict.contains(word)) {

                if (solve(s, end, dict, memo)) {
                    memo[start] = true;
                    return true;
                }
            }
        }

        memo[start] = false;
        return false;
    }

    public static void main(String[] args) {
        String s = "leetcode";
        List<String> wordDict = List.of("leet", "code");
        System.out.println(new WordBreak().wordBreak(s, wordDict));

        s = "catsandog";
        wordDict = List.of("cats", "dog", "sand", "and", "cat");
        System.out.println(new WordBreak().wordBreak(s, wordDict));
    }
}

package Problems.Recursion.Hard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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

    public static void main(String[] args) {
        String s = "leetcode";
        List<String> wordDict = List.of("leet", "code");
        System.out.println(new WordBreak().wordBreak(s, wordDict));

        s = "catsandog";
        wordDict = List.of("cats", "dog", "sand", "and", "cat");
        System.out.println(new WordBreak().wordBreak(s, wordDict));
    }
}

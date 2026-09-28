package Problems.Recursion.Hard;

import java.util.*;

public class WordBreakII {
    /*
     * INTUITION:
     *
     * We use DFS + backtracking to try every dictionary word
     * that can start at the current position.
     *
     * solve(start) means:
     *
     *     "Return all possible sentences that can be formed
     *      from s[start ... end]."
     *
     * For every valid dictionary word:
     *
     *     1. Take the word.
     *     2. Recursively solve the remaining string.
     *     3. Add the current word in front of every
     *        sentence returned by the recursion.
     *
     * We memoize solve(start) because the same index can be
     * reached through multiple paths.
     */
    public List<String> wordBreak(
            String s,
            List<String> wordDict) {

        Set<String> dict = new HashSet<>(wordDict);

        Map<Integer, List<String>> memo = new HashMap<>();

        return solve(s, 0, dict, memo);
    }

    private List<String> solve(
            String s,
            int start,
            Set<String> dict,
            Map<Integer, List<String>> memo) {

        // If we reached the end, there is one valid
        // way to complete the sentence: an empty suffix.
        if (start == s.length()) {
            return List.of("");
        }

        // Return previously calculated results.
        if (memo.containsKey(start)) {
            return memo.get(start);
        }

        List<String> result = new ArrayList<>();

        // Try every possible substring starting at `start`.
        for (int end = start + 1; end <= s.length(); end++) {

            String word = s.substring(start, end);

            // Only continue if this substring is a dictionary word.
            if (!dict.contains(word)) {
                continue;
            }

            // Find all valid sentences for the remaining string.
            List<String> remainingSentences =
                    solve(s, end, dict, memo);

            for (String remaining : remainingSentences) {

                // If nothing remains, don't add an extra space.
                if (remaining.isEmpty()) {
                    result.add(word);
                } else {
                    result.add(word + " " + remaining);
                }
            }
        }

        memo.put(start, result);

        return result;
    }
}

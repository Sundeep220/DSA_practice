package Problems.Trie;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CountDistinctSubstrings {

    // Problem: https://www.naukri.com/code360/problems/count-distinct-substrings_985292

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
    }

    // Brute Force: Generate all substrings
    //| Metric | Value                                |
    //| ------ | ------------------------------------ |
    //| Time   | **O(N³)** (substring copy + hashing) |
    //| Space  | **O(N²)**                            |
    public static int countDistinctSubstringsBrute(String s) {
        Set<String> set = new HashSet<>();

        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                set.add(s.substring(i, j));
            }
        }

        return set.size() + 1; // empty substring
    }

    // Using Trie:
    // Time Complexity: O(N^2)
    // Space: O(N^2)
    public static int countDistinctSubstrings(String s) {

        TrieNode root = new TrieNode();
        int count = 0; // counts distinct non-empty substrings

        // Insert all suffixes
        for (int i = 0; i < s.length(); i++) {

            TrieNode node = root;

            for (int j = i; j < s.length(); j++) {
                char ch = s.charAt(j);
                int index = ch - 'a';

                // New node = new distinct substring
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                    count++;
                }

                node = node.children[index];
            }
        }

        // +1 for empty substring
        return count + 1;
    }


    // Most Optimal: Using Suffix + LCP
    //| Metric | Value                                    |
    //| ------ | ---------------------------------------- |
    //| Time   | **O(N² log N)** (due to sorting strings) |
    //| Space  | **O(N²)**                                |
    public static int countDistinctSubstringsOptimal(String s) {
        int n = s.length();
        String[] suffixes = new String[n];

        for (int i = 0; i < n; i++) {
            suffixes[i] = s.substring(i);
        }

        Arrays.sort(suffixes);

        int lcpSum = 0;
        for (int i = 1; i < n; i++) {
            lcpSum += lcp(suffixes[i], suffixes[i - 1]);
        }

        int totalSubstrings = n * (n + 1) / 2;
        return totalSubstrings - lcpSum + 1; // +1 for empty substring
    }

    private static int lcp(String a, String b) {
        int len = Math.min(a.length(), b.length());
        int i = 0;
        while (i < len && a.charAt(i) == b.charAt(i)) {
            i++;
        }
        return i;
    }

}

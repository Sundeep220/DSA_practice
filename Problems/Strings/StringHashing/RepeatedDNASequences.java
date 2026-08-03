package Problems.Strings.StringHashing;

import java.util.*;

/**
 * LeetCode 187 - Repeated DNA Sequences: https://leetcode.com/problems/repeated-dna-sequences/description/
 *
 * This file contains two approaches:
 *
 * 1. Brute Force
 * 2. HashSet + substring() (Recommended / Optimal for interviews)
 *
 * Note:
 * A generic Rolling Hash (Rabin-Karp) implementation is intentionally omitted
 * from this file because the HashSet solution is already O(n) due to the
 * substring length being fixed at 10. A separate Rolling Hash implementation
 * is better kept as a learning template.
 */
public class RepeatedDNASequences {

    // ============================================================
    // Approach 1 : Brute Force
    // ============================================================
    //
    // Intuition:
    // ----------
    // Generate every substring of length 10.
    // For every substring, scan the remaining string to count
    // how many times it appears.
    //
    // If it appears more than once, add it to the answer.
    //
    // Very slow because every substring searches the entire string again.
    //
    // Time Complexity:
    // O(n^2)
    //
    // Space Complexity:
    // O(1) excluding answer.
    // ============================================================
    public List<String> bruteForce(String s) {

        List<String> ans = new ArrayList<>();
        Set<String> added = new HashSet<>();

        if (s.length() < 10)
            return ans;

        for (int i = 0; i <= s.length() - 10; i++) {
            String current = s.substring(i, i + 10);

            if (added.contains(current))
                continue;

            int count = 0;
            for (int j = 0; j <= s.length() - 10; j++) {
                if (current.equals(s.substring(j, j + 10))) {
                    count++;
                }

                if (count > 1) {
                    ans.add(current);
                    added.add(current);
                    break;
                }
            }
        }

        return ans;
    }

    // ============================================================
    // Approach 3 : Generic Rabin-Karp / Rolling Hash
    // ============================================================
    //
    // Intuition:
    // ----------
    // Instead of storing every DNA substring, store its polynomial hash.
    //
    // Maintain:
    //
    // hash -> starting indices having the same hash.
    //
    // Why indices instead of just hashes?
    // Because different substrings may produce the same hash
    // (Hash Collision).
    //
    // Whenever a hash repeats:
    //
    // 1. Compare the actual substrings.
    // 2. If equal, add to answer.
    //
    // This is exactly Rabin-Karp.
    //
    // Time Complexity:
    // Average : O(n)
    //
    // Worst   : O(n * 10)
    // (Collision verification compares at most 10 characters)
    //
    // Space Complexity:
    // O(n)
    // ============================================================
    public List<String> rollingHash(String s) {

        List<String> ans = new ArrayList<>();

        int WINDOW = 10;

        if (s.length() < WINDOW)
            return ans;

        long BASE = 31;
        long MOD = 1_000_000_007;

        long power = 1;

        // Compute BASE^(WINDOW-1)
        for (int i = 0; i < WINDOW - 1; i++) {
            power = (power * BASE) % MOD;
        }

        long windowHash = 0;

        // Compute hash of first window
        for (int i = 0; i < WINDOW; i++) {
            windowHash = (windowHash * BASE + value(s.charAt(i))) % MOD;
        }

        // Hash -> List of starting indices
        Map<Long, List<Integer>> seen = new HashMap<>();

        seen.computeIfAbsent(windowHash, k -> new ArrayList<>()).add(0);

        Set<String> repeated = new HashSet<>();

        // Slide the window
        for (int i = 1; i <= s.length() - WINDOW; i++) {

            int outgoing = value(s.charAt(i - 1));
            int incoming = value(s.charAt(i + WINDOW - 1));

            // Remove outgoing character
            windowHash = (windowHash - outgoing * power) % MOD;

            // Keep modulo positive
            if (windowHash < 0)
                windowHash += MOD;

            // Shift remaining characters
            windowHash = (windowHash * BASE) % MOD;

            // Add incoming character
            windowHash = (windowHash + incoming) % MOD;

            // Same hash seen before -> verify actual substring
            if (seen.containsKey(windowHash)) {

                String current = s.substring(i, i + WINDOW);

                for (int start : seen.get(windowHash)) {

                    String previous = s.substring(start, start + WINDOW);

                    // Collision verification
                    if (current.equals(previous)) {
                        repeated.add(current);
                        break;
                    }
                }
            }

            // Store current window starting index
            seen.computeIfAbsent(windowHash, k -> new ArrayList<>()).add(i);
        }

        ans.addAll(repeated);
        return ans;
    }

    // Maps DNA characters to numbers
    private int value(char c) {
        return switch (c) {
            case 'A' -> 1;
            case 'C' -> 2;
            case 'G' -> 3;
            default -> 4; // T
        };
    }

    // ============================================================
    // Approach 2 : HashSet + substring() (Optimal)
    // ============================================================
    //
    // Intuition:
    // ----------
    // Every valid DNA sequence has a FIXED length of 10.
    //
    // Slide a window of size 10.
    //
    // Maintain:
    //
    // seen      -> sequences seen for the first time
    // repeated  -> sequences seen more than once
    //
    // For every window:
    //
    // 1. Extract substring.
    // 2. If first time -> put into seen.
    // 3. Otherwise -> put into repeated.
    //
    // Since substring length is always 10 (constant),
    // substring creation is treated as O(1).
    //
    // Time Complexity:
    // O(n)
    //
    // Space Complexity:
    // O(n)
    // ============================================================
    public List<String> optimal(String s) {

        List<String> ans = new ArrayList<>();

        if (s.length() < 10)
            return ans;

        Set<String> seen = new HashSet<>();
        Set<String> repeated = new HashSet<>();

        // Slide a fixed-size window of length 10
        for (int i = 0; i <= s.length() - 10; i++) {

            // Current DNA sequence
            String dna = s.substring(i, i + 10);

            // add() returns false if already present
            if (!seen.add(dna)) {
                repeated.add(dna);
            }
        }

        ans.addAll(repeated);
        return ans;
    }

    public static void main(String[] args) {

        RepeatedDNASequences obj = new RepeatedDNASequences();

        String s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";

        System.out.println("Brute Force:");
        System.out.println(obj.bruteForce(s));

        System.out.println();

        System.out.println("Optimal:");
        System.out.println(obj.optimal(s));
    }
}

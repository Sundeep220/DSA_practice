package Problems.Trie;

import java.util.*;

/*
========================================================
421. Maximum XOR of Two Numbers in an Array
========================================================

We solve this problem using 3 approaches:
1) Brute Force
2) Better (Greedy Bitmask)
3) Optimal (Bitwise Trie)

========================================================
*/

public class MaxXORofTwoNumbers {

    // Problem: https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/description/
    /*
    ========================================================
    1️⃣ BRUTE FORCE
    ========================================================

    INTUITION:
    Try every possible pair (i, j) and compute XOR.
    Track the maximum XOR.

    WHY IT WORKS:
    XOR is computed directly for all pairs.

    TIME COMPLEXITY:
    O(N^2)

    SPACE COMPLEXITY:
    O(1)

    LIMITATION:
    Too slow for large inputs.
    */

    public static int maxXorBrute(int[] nums) {
        int max = 0;

        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                max = Math.max(max, nums[i] ^ nums[j]);
            }
        }
        return max;
    }

    /*
    ========================================================
    2️⃣ BETTER SOLUTION (Greedy + HashSet)
    ========================================================

    INTUITION:
    - Build the result bit by bit (from MSB → LSB).
    - Try to see if we can make the current bit = 1.
    - Use prefix masking and HashSet to test feasibility.

    KEY IDEA:
    If a ^ b = target
    => a = b ^ target

    GOAL: Make the leftmost (most significant) bits as 1 as possible
    Build the answer bit by bit from MSB → LSB, and at each step check if it’s possible to make that bit 1

    TIME COMPLEXITY:
    O(32 * N) ≈ O(N)

    SPACE COMPLEXITY:
    O(N)

    NOTE:
    This is GREEDY on bits, not on numbers.
    */

    public static int maxXorBetter(int[] nums) {
        int max = 0;
        int mask = 0;

        // Iterate from MSB (31) to LSB (0)
        for (int bit = 31; bit >= 0; bit--) {

            mask |= (1 << bit);
            Set<Integer> prefixes = new HashSet<>();

            // Store masked prefixes
            for (int num : nums) {
                prefixes.add(num & mask);
            }

            // Try to set current bit
            int candidate = max | (1 << bit);

            for (int prefix : prefixes) {
                if (prefixes.contains(prefix ^ candidate)) {
                    max = candidate;
                    break;
                }
            }
        }
        return max;
    }

    /*
    ========================================================
    3️⃣ OPTIMAL SOLUTION (Bitwise Trie)
    ========================================================

    INTUITION:
    - Insert numbers in binary form into a Trie.
    - For each number, try to go opposite bit (to maximize XOR).
    - Greedy choice at every bit level.

    WHY IT WORKS:
    XOR is maximized when bits differ.
    Trie lets us choose best opposite bit in O(1).
    Make the leftmost (most significant) bits as 1 as possible

    TIME COMPLEXITY:
    O(N * 32) ≈ O(N)

    SPACE COMPLEXITY:
    O(N * 32)

    THIS IS THE MOST COMMON INTERVIEW SOLUTION.
    */

    static class TrieNode {

        // children[0] -> bit 0
        // children[1] -> bit 1
        TrieNode[] children = new TrieNode[2];

        boolean containsKey(int bit) {
            return children[bit] != null;
        }

        TrieNode get(int bit) {
            return children[bit];
        }

        void put(int bit, TrieNode node) {
            children[bit] = node;
        }
    }


    static class Trie {

        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        /*
         * Insert a number into the Trie (binary form)
         *
         * Time Complexity: O(32)
         * Space Complexity: O(32) per number
         */
        public void insert(int num) {
            TrieNode node = root;

            for (int bit = 31; bit >= 0; bit--) {
                int currBit = (num >> bit) & 1;

                if (!node.containsKey(currBit)) {
                    node.put(currBit, new TrieNode());
                }
                node = node.get(currBit);
            }
        }

        /*
         * Find maximum XOR of num with any number present in Trie
         *
         * Time Complexity: O(32)
         * Space Complexity: O(1)
         */
        public int getMaxXor(int num) {
            TrieNode node = root;
            int maxXor = 0;

            for (int bit = 31; bit >= 0; bit--) {
                int currBit = (num >> bit) & 1;
                int oppositeBit = 1 - currBit;

                // Prefer opposite bit to maximize XOR
                if (node.containsKey(oppositeBit)) {
                    maxXor |= (1 << bit);
                    node = node.get(oppositeBit);
                } else {
                    node = node.get(currBit);
                }
            }

            return maxXor;
        }
    }


    public static int maxXorOptimal(int[] nums) {
        Trie trie = new Trie();

        for (int num : nums) {
            trie.insert(num);
        }

        int max = 0;
        for (int num : nums) {
            max = Math.max(max, trie.getMaxXor(num));
        }
        return max;
    }
}

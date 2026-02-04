package Problems.Trie;

import java.util.*;

public class MaxXORofElementFromQueries {

    // Problem: https://leetcode.com/problems/maximum-xor-with-an-element-from-array/
    /* =====================================================
       TrieNode definition (modular, consistent style)
       ===================================================== */
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

    /* =====================================================
       Trie class (insert + max XOR query)
       ===================================================== */
    static class Trie {

        private TrieNode root = new TrieNode();

        // Insert a number into the bitwise Trie
        // Time Complexity: O(32)
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

        // Get maximum XOR with num
        // Time Complexity: O(32)
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

    /*
    ========================================================
    OPTIMAL SOLUTION — OFFLINE QUERIES + BITWISE TRIE
    💡 Idea: OFFLINE PROCESSING
        - Sort nums in ascending order
        - Sort queries by mi in ascending order
        - Maintain a Trie
        - As mi increases:
        - Insert into Trie all nums <= mi
        - For current query:
            - Trie contains exactly valid numbers
            - Query max XOR with xi
    ========================================================

    TIME COMPLEXITY:
    Sorting nums: O(N log N)
    Sorting queries: O(Q log Q)
    Trie operations: O((N + Q) * 32)

    SPACE COMPLEXITY:
    Trie: O(N * 32)
    Extra arrays: O(Q)
    */

    public static int[] maximizeXor(int[] nums, int[][] queries) {

        int n = nums.length;
        int q = queries.length;

        /* =====================================================
           OFFLINE STEP 1:
           Sort nums so we can incrementally insert
           only numbers <= current query's mi
           ===================================================== */
        Arrays.sort(nums);

        /* =====================================================
           OFFLINE STEP 2:
           Convert queries into a new array:
           [mi, xi, originalIndex]

           Why?
           - We want to sort queries by mi
           - We must still return answers in original order
           ===================================================== */
        int[][] qArr = new int[q][3];
        for (int i = 0; i < q; i++) {
            qArr[i][0] = queries[i][1]; // mi (upper bound)
            qArr[i][1] = queries[i][0]; // xi (value to XOR)
            qArr[i][2] = i;             // original index
        }

        /* =====================================================
           OFFLINE STEP 3:
           Sort queries by mi (ascending)
           ===================================================== */
        Arrays.sort(qArr, Comparator.comparingInt(a -> a[0]));

        Trie trie = new Trie();
        int[] ans = new int[q];

        /* =====================================================
           OFFLINE STEP 4:
           Use a pointer over nums[]
           This pointer only moves forward (never backward)
           ===================================================== */
        int idx = 0;

        /* =====================================================
           OFFLINE STEP 5:
           Process queries in increasing order of mi
           ===================================================== */
        for (int i = 0; i < q; i++) {

            int mi = qArr[i][0];
            int xi = qArr[i][1];
            int originalIndex = qArr[i][2];

            /* =================================================
               OFFLINE STEP 6:
               Insert all nums[j] such that:
               nums[j] <= mi

               Once inserted, they remain for future queries
               ================================================= */
            while (idx < n && nums[idx] <= mi) {
                trie.insert(nums[idx]);
                idx++;
            }

            /* =================================================
               OFFLINE STEP 7:
               If no numbers were inserted yet,
               then no valid nums[j] <= mi exists
               ================================================= */
            if (idx == 0) {
                ans[originalIndex] = -1;
            } else {
                /* =============================================
                   OFFLINE STEP 8:
                   Trie now contains EXACTLY the valid numbers
                   for this query → compute max XOR
                   ============================================= */
                ans[originalIndex] = trie.getMaxXor(xi);
            }
        }

        /* =====================================================
           OFFLINE STEP 9:
           Return answers in original query order
           ===================================================== */
        return ans;
    }
}

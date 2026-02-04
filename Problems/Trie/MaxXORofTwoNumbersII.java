package Problems.Trie;

public class MaxXORofTwoNumbersII {

    // Problem: https://www.naukri.com/code360/problems/maximum-xor_973113
    /*
    ========================================================
    OPTIMAL SOLUTION — BITWISE TRIE (MODULAR VERSION)
    ========================================================

    INTUITION:
    - Insert all numbers from arr2 into a binary Trie
    - For each number in arr1, greedily find the number
      in arr2 that maximizes XOR by choosing opposite bits

    TIME COMPLEXITY:
    O((N + M) * 32) ≈ O(N + M)

    SPACE COMPLEXITY:
    O(M * 32)
    */
    static class TrieNode {

        // children[0] -> bit 0
        // children[1] -> bit 1
        MaxXORofTwoNumbers.TrieNode[] children = new MaxXORofTwoNumbers.TrieNode[2];

        boolean containsKey(int bit) {
            return children[bit] != null;
        }

        MaxXORofTwoNumbers.TrieNode get(int bit) {
            return children[bit];
        }

        void put(int bit, MaxXORofTwoNumbers.TrieNode node) {
            children[bit] = node;
        }
    }


    static class Trie {

        private MaxXORofTwoNumbers.TrieNode root;

        public Trie() {
            root = new MaxXORofTwoNumbers.TrieNode();
        }

        /*
         * Insert a number into the Trie (binary form)
         *
         * Time Complexity: O(32)
         * Space Complexity: O(32) per number
         */
        public void insert(int num) {
            MaxXORofTwoNumbers.TrieNode node = root;

            for (int bit = 31; bit >= 0; bit--) {
                int currBit = (num >> bit) & 1;

                if (!node.containsKey(currBit)) {
                    node.put(currBit, new MaxXORofTwoNumbers.TrieNode());
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
            MaxXORofTwoNumbers.TrieNode node = root;
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
    
    public static int maxXor(int[] arr1, int[] arr2) {

        Trie trie = new Trie();

        // Step 1: Insert all elements of arr2
        for (int num : arr2) {
            trie.insert(num);
        }

        // Step 2: Query max XOR for each element of arr1
        int max = 0;
        for (int num : arr1) {
            max = Math.max(max, trie.getMaxXor(num));
        }

        return max;
    }
}

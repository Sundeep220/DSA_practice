package Problems.Trie;

class TrieNode {

    // Each index represents a lowercase English character
    private final TrieNode[] children;
    public boolean isEnd;

    public TrieNode() {
        children = new TrieNode[26];
        isEnd = false;
    }

    // Check if a character link exists
    boolean containsKey(char ch) {
        return children[ch - 'a'] != null;
    }

    // Get the next node for a character
    TrieNode get(char ch) {
        return children[ch - 'a'];
    }

    // Create and assign a node for a character
    void put(char ch, TrieNode node) {
        children[ch - 'a'] = node;
    }

    // Mark end of word
    void setEnd() {
        isEnd = true;
    }

    // Check if node is end of a word
    boolean isEnd() {
        return isEnd;
    }
}


public class Trie {

    // Problem: https://leetcode.com/problems/implement-trie-prefix-tree/

    private TrieNode root;

    // Time Complexity: O(1)
    public Trie() {
        root = new TrieNode();
    }


    /*
     * Inserts a word into the Trie
     *
     * Time Complexity: O(L)
     *   where L = length of the word
     *
     * Space Complexity: O(L) (only if new nodes are created)
     */
    public void insert(String word) {
        TrieNode node = root;

        for (char ch : word.toCharArray()) {

            // If character does not exist, create it
            if (!node.containsKey(ch)) {
                node.put(ch, new TrieNode());
            }

            // Move to next node
            node = node.get(ch);
        }

        // Mark end of word
        node.setEnd();
    }


    /*
     * Searches for a complete word in the Trie
     *
     * Time Complexity: O(L)
     * Space Complexity: O(1)
     */
    public boolean search(String word) {
        TrieNode node = root;

        for (char ch : word.toCharArray()) {

            // Character path missing
            if (!node.containsKey(ch)) {
                return false;
            }

            node = node.get(ch);
        }

        // Word exists only if end flag is true
        return node.isEnd();
    }

    /* ================= Prefix Search ================= */

    /*
     * Checks if any word in the Trie starts with the given prefix
     *
     * Time Complexity: O(L)
     * Space Complexity: O(1)
     */
    public boolean startsWith(String prefix) {
        TrieNode node = root;

        for (char ch : prefix.toCharArray()) {

            if (!node.containsKey(ch)) {
                return false;
            }

            node = node.get(ch);
        }

        // Prefix path exists
        return true;
    }
}


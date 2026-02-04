package Problems.Trie;


class TrieIII {
    private TrieNode root;

    public TrieIII() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            if (!node.containsKey(ch)) {
                node.put(ch, new TrieNode());
            }
            node = node.get(ch);
        }
        node.isEnd = true;
    }

    public boolean isCompleteString(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            if (!node.containsKey(ch)) {
                return false;
            }
            node = node.get(ch);
            if (!node.isEnd) {
                return false;
            }
        }
        return true;
    }
}

public class Solution {
    // Problem: https://www.naukri.com/code360/problems/complete-string_2687860
    // | Operation        | Complexity   |
    //| ---------------- | ------------ |
    //| Insert all words | O(N × L)     |
    //| Check all words  | O(N × L)     |
    //| Total            | **O(N × L)** |
    //| Space            | **O(N × L)** |
    public static String completeString(int n, String[] a) {
        TrieIII trie = new TrieIII();

        // Step 1: Insert all words
        for (String word : a) {
            trie.insert(word);
        }

        String answer = "";

        // Step 2: Check each word
        for (String word : a) {
            if (trie.isCompleteString(word)) {

                if (word.length() > answer.length()) {
                    answer = word;
                }
                // since we want answer in lexicographical order
                else if (word.length() == answer.length() && word.compareTo(answer) < 0) {
                    answer = word;
                }
            }
        }

        return answer.isEmpty() ? "None" : answer;
    }
}

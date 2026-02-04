package Problems.Trie;

class TrieNodeII {

    TrieNodeII[] children = new TrieNodeII[26];
    int countPrefix = 0;
    int countEnd = 0;

    boolean containsKey(char ch) {
        return children[ch - 'a'] != null;
    }

    TrieNodeII get(char ch) {
        return children[ch - 'a'];
    }

    void put(char ch, TrieNodeII node) {
        children[ch - 'a'] = node;
    }

    void increasePrefix() {
        countPrefix++;
    }

    void decreasePrefix() {
        countPrefix--;
    }

    void increaseEnd() {
        countEnd++;
    }

    void decreaseEnd() {
        countEnd--;
    }
}

public class TrieII {

    // Problem: https://www.naukri.com/code360/problems/implement-trie_1387095?leftPanelTabValue=SUBMISSION

    private TrieNodeII root;

    public TrieII() {
        root = new TrieNodeII();
    }

    public void insert(String word) {
        TrieNodeII node = root;

        for (char ch : word.toCharArray()) {
            if (!node.containsKey(ch)) {
                node.put(ch, new TrieNodeII());
            }
            node = node.get(ch);
            node.increasePrefix();
        }
        node.increaseEnd();
    }

    public int countWordsEqualTo(String word) {
        TrieNodeII node = root;

        for (char ch : word.toCharArray()) {
            if (!node.containsKey(ch)) {
                return 0;
            }
            node = node.get(ch);
        }
        return node.countEnd;
    }

    public int countWordsStartingWith(String prefix) {
        TrieNodeII node = root;

        for (char ch : prefix.toCharArray()) {
            if (!node.containsKey(ch)) {
                return 0;
            }
            node = node.get(ch);
        }
        return node.countPrefix;
    }

    public void erase(String word) {
        TrieNodeII node = root;

        for (char ch : word.toCharArray()) {
            TrieNodeII nextNode = node.get(ch);
            nextNode.decreasePrefix();
            node = nextNode;
        }
        node.decreaseEnd();
    }
}


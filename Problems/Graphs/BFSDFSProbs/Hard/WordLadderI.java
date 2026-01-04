package Problems.Graphs.BFSDFSProbs.Hard;

import java.util.*;

public class WordLadderI {
    //Problem:https://leetcode.com/problems/word-ladder/description/

    // BFS Code (Using visited, no input tampering) Using Two Sets
    // Time Complexity: O(N × L × 26) Space: O(N × L)
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return 0;

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(beginWord);
        visited.add(beginWord);

        int level = 1; // beginWord counts as level 1

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                String word = queue.poll();

                if (word.equals(endWord)) return level;

                char[] chars = word.toCharArray();

                for (int pos = 0; pos < chars.length; pos++) {
                    char original = chars[pos];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;

                        chars[pos] = c;
                        String nextWord = new String(chars);

                        if (dict.contains(nextWord) && !visited.contains(nextWord)) {
                            visited.add(nextWord);
                            queue.offer(nextWord);
                        }
                    }
                    chars[pos] = original; // restore
                }
            }
            level++;
        }
        return 0;
    }


    // BFS Traversal with One Set only
    class Pair{
        String word;
        int level;

        Pair(String w, int l){
            this.word = w;
            this.level = l;
        }
    }

    public int ladderLengthOptimal(String beginWord, String endWord, List<String> wordList) {

        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return 0;

        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(beginWord, 1));

        // Mark beginWord as visited by removing it
        dict.remove(beginWord);

        while (!queue.isEmpty()) {
            Pair curr = queue.poll();
            String word = curr.word;
            int level = curr.level;

            if (word.equals(endWord)) return level;

            char[] chars = word.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                char original = chars[i];

                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == original) continue;

                    chars[i] = c;
                    String nextWord = new String(chars);

                    if (dict.contains(nextWord)) {
                        queue.offer(new Pair(nextWord, level + 1));
                        dict.remove(nextWord); // ✅ mark visited
                    }
                }
                chars[i] = original; // restore
            }
        }
        return 0;
    }

}

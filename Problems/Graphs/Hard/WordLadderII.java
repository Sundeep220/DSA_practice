package Problems.Graphs.Hard;

import java.util.*;

public class WordLadderII {

    // Better Solution: Simple BFS Approach but can giev TLE
    // Time: O(P × L × 26 × path_length) Space: O(P × path_length)
    public List<List<String>> findLadders(
            String beginWord, String endWord, List<String> wordList) {

        List<List<String>> ans = new ArrayList<>();
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return ans;

        Queue<List<String>> queue = new LinkedList<>();
        queue.offer(new ArrayList<>(List.of(beginWord)));

        boolean found = false;

        while (!queue.isEmpty() && !found) {
            int size = queue.size();
            Set<String> usedThisLevel = new HashSet<>();

            for (int i = 0; i < size; i++) {
                List<String> path = queue.poll();
                String last = path.get(path.size() - 1);

                if (last.equals(endWord)) {
                    ans.add(path);
                    found = true;
                }

                char[] chars = last.toCharArray();
                for (int pos = 0; pos < chars.length; pos++) {
                    char original = chars[pos];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;

                        chars[pos] = c;
                        String next = new String(chars);

                        if (dict.contains(next)) {
                            List<String> newPath = new ArrayList<>(path);
                            newPath.add(next);
                            queue.offer(newPath);
                            usedThisLevel.add(next);
                        }
                    }
                    chars[pos] = original;
                }
            }

            // remove words only after level finishes
            dict.removeAll(usedThisLevel);
        }

        return ans;
    }

    // Optimal Solution: BFS With DFS Backtracking: Ideas is:
    // Use BFS to compute the shortest distance (level) of every reachable word from beginWord.
    //Then use DFS backtracking, moving only to words whose level is exactly +1, to generate all shortest paths.
    //Phase 1 — BFS to Compute Levels
    //What we store
        //Map<String, Integer> levelMap
        //level of each word from beginWord
    // Phase 2 - DFS to backtrack and build path
        //After (optimized reverse DFS)
        //DFS starts from endWord
        //Moves backward using

    // Time:
        //BFS: O(N × L × 26)
        //DFS: O(output size)
    //Space: O(N × L)

    public List<List<String>> findLaddersOptimal(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return result;

        Map<String, Integer> level = bfsLevels(beginWord, endWord, dict);
        if (!level.containsKey(endWord)) return result;

        // 🔥 Start DFS from endWord
        List<String> path = new ArrayList<>();
        path.add(endWord);

        dfsReverse(endWord, beginWord, level, dict, path, result);

        return result;
    }

    private Map<String, Integer> bfsLevels(String beginWord, String endWord, Set<String> dict) {

        Map<String, Integer> level = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        level.put(beginWord, 0);

        while (!queue.isEmpty()) {
            String word = queue.poll();
            int currLevel = level.get(word);

            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char original = chars[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == original) continue;
                    chars[i] = c;
                    String next = new String(chars);

                    if (dict.contains(next) && !level.containsKey(next)) {
                        level.put(next, currLevel + 1);
                        queue.offer(next);
                    }
                }
                chars[i] = original;
            }
        }
        return level;
    }


    private void dfsReverse(String word, String beginWord, Map<String, Integer> level, Set<String> dict, List<String> path, List<List<String>> result) {

        // 🔥 Reached beginWord → valid path
        if (word.equals(beginWord)) {
            List<String> valid = new ArrayList<>(path);
            Collections.reverse(valid);
            result.add(valid);
            return;
        }

        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char original = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == original) continue;
                chars[i] = c;
                String prev = new String(chars);

                // 🔥 Move ONLY to previous level
                if (level.containsKey(prev) &&
                        level.get(prev) == level.get(word) - 1) {

                    path.add(prev);
                    dfsReverse(prev, beginWord, level, dict, path, result);
                    path.remove(path.size() - 1);
                }
            }
            chars[i] = original;
        }
    }
}



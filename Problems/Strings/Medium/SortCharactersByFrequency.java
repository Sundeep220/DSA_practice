package Problems.Strings.Medium;

import java.util.*;

public class SortCharactersByFrequency {
    // Problem Link: https://leetcode.com/problems/sort-characters-by-frequency/

    //Brute Force: Using HashMap and sorting based on count
    public static String frequencySortBrute(String s) {
        Map<Character, Integer> map = new HashMap<>(); // char, count
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        StringBuilder sb = new StringBuilder();
        int maxF = s.length();
        while(maxF > 0) {
            for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                if (entry.getValue() == maxF) {
                    for (int i = 0; i < entry.getValue(); i++) {
                        sb.append(entry.getKey());
                    }
                }
            }
            maxF--;
        }
        return sb.toString();
    }

    // Better Solution1: Using HashMap and Priority Queue
    public static String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        Comparator<Map.Entry<Character, Integer>> comparator = (a, b) -> Integer.compare(b.getValue(), a.getValue());
        PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>(comparator);
        pq.addAll(map.entrySet());

        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            Map.Entry<Character, Integer> entry = pq.poll();
//            for (int i = 0; i < entry.getValue(); i++) {
//                sb.append(entry.getKey());
//            }
            sb.append(String.valueOf(entry.getKey()).repeat(Math.max(0, entry.getValue())));
        }
        return sb.toString();
    }

    // Better Solution2: Using HashMap and List
    public static String frequencySortBetter(String s) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        // Sort the map based on values in descending order, using List
        List<Map.Entry<Character, Integer>> entries = new ArrayList<>(freq.entrySet());
        entries.sort((a, b) -> b.getValue() - a.getValue());

        StringBuilder result = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : entries) {
            char c = entry.getKey();
            int count = entry.getValue();
            for (int i = 0; i < count; i++) {
                result.append(c);
            }
        }

        return result.toString();
    }

    // Optimal Solution
    public static String frequencySortOptimal(String s) {
        int[] f = new int[128];
        for (char c : s.toCharArray()) {
            f[c] += 1;
        }
        char[] res = new char[s.length()];
        int i = 0;
        while (i < s.length()) {
            int maxi = findMaxI(f);
            int freq = f[maxi];
            while (freq > 0) {
                res[i++] = (char) (maxi);
                freq--;
            }
            f[maxi] = 0;
        }
        return new String(res);
    }

    public static int findMaxI(int[] f) {
        int maxi = 0;
        int max = 0;
        for (int i = 0; i < f.length; i++) {
            if (f[i] > max) {
                maxi = i;
                max = f[i];
            }
        }
        return maxi;
    }


    public static void main(String[] args) {
        String s = "Aabb";
        System.out.println(frequencySortBrute(s));
        System.out.println(frequencySort(s));
        System.out.println(frequencySortBetter(s));
    }
}

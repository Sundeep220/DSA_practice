package Problems.Strings.Medium;

public class BeutySumProblem {
    // Problem: https://leetcode.com/problems/sum-of-beauty-of-all-substrings

    // Brute Force:
    // Generate all possible substrings and calculate the beauty for each substring
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static int beautySum(String s) {
        int n = s.length();
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                res += beauty(s.substring(i, j + 1));
            }
        }
        return res;
    }

    public static int beauty(String s) {
        int[] freq = new int[26]; // Using an array for faster access, or we can use HashMap
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        int min = Integer.MAX_VALUE, max = 0;
        for (int f : freq) {
            if (f > 0) {
                min = Math.min(min, f);
                max = Math.max(max, f);
            }
        }
        return max - min;
    }

    // // Better Solution: Instead of calulating freq on each substring, we can just count in same function
    public static int beautySumBetter(String s) {
        int n = s.length();
        int res = 0;
        for(int i = 0; i < n; i++) {
            int[] freq = new int[26];
            for(int j = i; j < n; j++) {
                freq[s.charAt(j) - 'a']++;
                int min = Integer.MAX_VALUE, max = 0;
                for(int f : freq) {
                    if(f > 0) {
                        min = Math.min(min, f);
                        max = Math.max(max, f);
                    }
                }
                res += max - min;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "aabcb";
        System.out.println(beautySum(s));
        System.out.println(beautySumBetter(s));
    }
}

package Problems.Greedy.Easy;

import java.util.Arrays;

public class AssignCookies {
    // Problem: https://leetcode.com/problems/assign-cookies/

    // Brute Force: O(n^2) time | O(1) space
    // Look for every child and check if it can be assigned to any of the cookies
    public static int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g); // sort greed factors
        Arrays.sort(s); // sort cookies

        boolean[] used = new boolean[s.length];
        int count = 0;

        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < s.length; j++) {
                if (!used[j] && s[j] >= g[i]) {
                    used[j] = true; // assign cookie
                    count++;
                    break; // move to next child
                }
            }
        }
        return count;
    }

    // Optimal Solution: O(nlogn) time | O(1) space
    // Sort both arrays and use two pointers -> Greedy
    public static int findContentChildrenOptimal(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int i = 0, j = 0, count = 0;
        while (i < g.length && j < s.length) {
            if (g[i] <= s[j]) {
                count++;
                i++;
            }
            j++;
        }
        return count;
    }
}

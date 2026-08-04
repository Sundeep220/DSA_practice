package Problems.Strings.StringHashing;

import java.util.ArrayList;

public class SearchPattern {
    // Problem: https://www.geeksforgeeks.org/problems/search-pattern0205/1

    // Brute Force:
    // Time Complexity: O(n*m)
    // Space Complexity: O(1)
    ArrayList<Integer> searchBrute(String pat, String txt) {

        ArrayList<Integer> ans = new ArrayList<>();

        int n = txt.length();
        int m = pat.length();

        for (int i = 0; i <= n - m; i++) {

            int j = 0;

            while (j < m && txt.charAt(i + j) == pat.charAt(j)) {
                j++;
            }

            if (j == m)
                ans.add(i);
        }

        return ans;
    }

    // Using Built in Java indexOf
    // Time Complexity: O(n) or O(n*m) based on JDK
    // Space Complexity: O(1)
    ArrayList<Integer> searchBuiltIn(String pat, String txt) {

        ArrayList<Integer> ans = new ArrayList<>();

        int index = txt.indexOf(pat);

        while (index != -1) {

            ans.add(index);

            index = txt.indexOf(pat, index + 1);
        }

        return ans;
    }

    // Using Z Function
    // Time Complexity: O(n + m)
    // Space Complexity: O(n + m)
    private int[] zFunction(String s) {
        int n = s.length();
        int[] z = new int[n];
        int l = 0;
        int r = 0;
        for (int i = 1; i < n; i++) {
            if (i <= r) {
                z[i] = Math.min(r - i + 1, z[i - l]);
            }

            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }

            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }

        return z;
    }

    ArrayList<Integer> searchZF(String pat, String txt) {
        ArrayList<Integer> ans = new ArrayList<>();
        String combined = pat + "$" + txt;
        int[] z = zFunction(combined);
        int m = pat.length();

        for (int i = m + 1; i < z.length; i++) {
            if (z[i] == m)
                ans.add(i - m - 1);
        }

        return ans;
    }
}

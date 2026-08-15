package Problems.Strings.Medium;

public class CompareVersions {
    // Problem: https://leetcode.com/problems/compare-version-numbers/description/

    // Brute Force: Split + Parse Everything
    //Split both strings by .
    //Convert every revision to an integer.
    //Compare corresponding elements.
    //If one array is shorter, use 0.
    // Time: O(N)
    // Space: O(N)
    public int compareVersionBruteForce(String version1, String version2) {

        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");

        int n = Math.max(v1.length, v2.length);

        for (int i = 0; i < n; i++) {

            int num1 = i < v1.length ? Integer.parseInt(v1[i]) : 0;
            int num2 = i < v2.length ? Integer.parseInt(v2[i]) : 0;

            if (num1 < num2) {
                return -1;
            }

            if (num1 > num2) {
                return 1;
            }
        }

        return 0;
    }

    // Optimal: Using Two Pointers
//    For each revision:
//
//    Build revision number from version1
//    Build revision number from version2
//            Compare
//    Move past .
//    Repeat
    // Time: O(N)
    // Space: O(1)
    public int compareVersionOptimal(String version1, String version2) {

        int i = 0;
        int j = 0;

        while (i < version1.length() || j < version2.length()) {

            int num1 = 0;
            int num2 = 0;

            // Build revision from version1
            while (i < version1.length() && version1.charAt(i) != '.') {
                num1 = num1 * 10 + (version1.charAt(i) - '0');
                i++;
            }

            // Build revision from version2
            while (j < version2.length() && version2.charAt(j) != '.') {
                num2 = num2 * 10 + (version2.charAt(j) - '0');
                j++;
            }

            // Compare revisions
            if (num1 < num2) {
                return -1;
            }

            if (num1 > num2) {
                return 1;
            }

            // Skip '.'
            if (i < version1.length()) {
                i++;
            }

            if (j < version2.length()) {
                j++;
            }
        }

        return 0;
    }
}

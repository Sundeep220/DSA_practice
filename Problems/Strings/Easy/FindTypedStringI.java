package Problems.Strings.Easy;

public class FindTypedStringI {
    // Problem: https://leetcode.com/problems/find-the-original-typed-string-i/description/?envType=daily-question&envId=2025-07-01

    public int possibleStringCount(String word) {
        int count = 1; // Original string

        int i = 0;
        while (i < word.length()) {
            int j = i;
            // Find next group and increasing the count
            while (j < word.length() && word.charAt(j) == word.charAt(i)) {
                j++;
            }

            // Once we find the length of group we can atmost have (groupLen - 1) groups after it
            int groupLen = j - i;
            if (groupLen > 1) {
                // Add (groupLen - 1) groups to count
                count += (groupLen - 1);
            }
            i = j; // Move to next group
        }

        return count;
    }
}

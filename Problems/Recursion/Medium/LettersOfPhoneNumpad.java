package Problems.Recursion.Medium;

import java.util.ArrayList;
import java.util.List;

public class LettersOfPhoneNumpad {
    // https://leetcode.com/problems/letter-combinations-of-a-phone-number/

    public static List<String> letterCombinations(String digits) {
        String[] mapping = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        List<String> ans = new ArrayList<>();
        if (digits.isEmpty()) return ans;

        // E
        if (digits.length() == 1) {
            for (int i = 0; i < mapping[digits.charAt(0) - '0'].length(); i++) {
                ans.add(String.valueOf(mapping[digits.charAt(0) - '0'].charAt(i)));
            }
            return ans;
        }

        backtrack(ans, digits, 0, new StringBuilder(), mapping);
        return ans;
    }

    public static void backtrack(List<String> ans, String digits, int index, StringBuilder current, String[] mapping) {
        if (index == digits.length()) {
            ans.add(current.toString());
            return;
        }

        // For each digit, get the corresponding letters
        String letters = mapping[digits.charAt(index) - '0'];

        for (int i = 0; i < letters.length(); i++) {
            current.append(letters.charAt(i));
            backtrack(ans, digits, index + 1, current, mapping);
            current.deleteCharAt(current.length() - 1); // Backtrack
        }

    }
}

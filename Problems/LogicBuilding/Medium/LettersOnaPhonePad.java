package Problems.LogicBuilding.Medium;
import java.util.*;

public class LettersOnaPhonePad {
    // Problem Link: https://leetcode.com/problems/letter-combinations-of-a-phone-number/
    public class Solution {

        public List<String> letterCombinations(String digits) {
            List<String> result = new ArrayList<>();

            // Edge case: If input is empty, return an empty list.
            if (digits == null || digits.length() == 0) return result;

            // ✅ Step 1: Build the digit-to-letter mapping like a phone keypad
            Map<Character, String> phoneMap = new HashMap<>();
            phoneMap.put('2', "abc");
            phoneMap.put('3', "def");
            phoneMap.put('4', "ghi");
            phoneMap.put('5', "jkl");
            phoneMap.put('6', "mno");
            phoneMap.put('7', "pqrs");
            phoneMap.put('8', "tuv");
            phoneMap.put('9', "wxyz");

            // ✅ Step 2: Start backtracking from index 0 and an empty combination
            backtrack(0, digits, new StringBuilder(), phoneMap, result);

            return result;
        }

        /**
         * ✅ Backtracking Approach Explanation:
         * - Each digit maps to a set of letters (like on a phone keypad).
         * - We treat this as a tree where:
         *     - Each level of recursion = one digit
         *     - Each branch = one letter choice for that digit
         * - We recursively build all paths (combinations) by appending letters.
         * - Once a full combination is built (length == digits.length), we add it to the result.
         *
         * @param index Current digit position we're working on
         * @param digits The original input digit string (e.g., "23")
         * @param combination The current combination being built
         * @param phoneMap The map of digits to possible letters
         * @param result The final list storing all valid combinations
         */
        private void backtrack(int index, String digits, StringBuilder combination,
                               Map<Character, String> phoneMap, List<String> result) {

            // ✅ Base Case: If we've formed a full combination, add to result
            if (index == digits.length()) {
                result.add(combination.toString());
                return;
            }

            // ✅ Step 3: Get the letters corresponding to the current digit
            char currentDigit = digits.charAt(index);
            String letters = phoneMap.get(currentDigit);

            // ✅ Step 4: Try each letter one by one, and go deeper recursively
            for (char letter : letters.toCharArray()) {
                combination.append(letter); // Choose
                backtrack(index + 1, digits, combination, phoneMap, result); // Explore
                combination.deleteCharAt(combination.length() - 1); // Un-choose (Backtrack)
            }
        }
    }

}

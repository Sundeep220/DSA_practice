package Problems.Strings.Easy;

import java.util.Stack;

public class ReverseWordsInString {
    // Problem: https://leetcode.com/problems/reverse-words-in-a-string/description/

    //Brute Force: Using Stack
    // Time Complexity: O(n) Space Complexity: O(n)
    public static String reverseWords(String s) {
        Stack<String> stack = new Stack<>();
        String[] words = s.split(" ");
        for (String word : words) {
            stack.push(word);
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
            if (!stack.isEmpty()) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    // Brute Solution: Using StringBuilder
    // Time Complexity: O(n) Space Complexity: O(n)
    public static String reverseWordsBrute(String s) {
        String[] words = s.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]);
            if (i != 0) sb.append(" ");
        }

        return sb.toString();
    }

    //Optimal Solution: Using reversal Inplace
    // First we remove all the leading and trailing spaces
    // Then we remove any extra tab spaces between the words,
    // Then we get an character array of the string
    // Then we reverse the whole array
    // Then we reverse each word in the array
    // Then we convert the character array to a string
    public static String reverseWordsOptimal(String s) {
        char[] words = s.trim().replaceAll("\\s+", " ").toCharArray();
        reverse(words, 0, words.length -1);
        int start = 0;
        for (int end = 0; end <= words.length; end++) {
            if (end == words.length || words[end] == ' ') {
                reverse(words, start, end - 1);
                start = end + 1;
            }
        }
        return new String(words);
    }

    public static void reverse(char[] arr, int start, int end) {
        while(start < end) {
            char temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        String s = "the sky is blue";
        System.out.println(reverseWordsBrute(s));
        System.out.println(reverseWords(s));
        System.out.println(reverseWordsOptimal(s));

    }
}

package Problems.Strings.Easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidAnagrams {
    // Problem: https://leetcode.com/problems/valid-anagram/

    // Brute Force: generate all permutations and check if t is a permutation of s
    // Time Complexity: O(n! * m), Space Complexity: O(n! * m)
    public static boolean isAnagram(String s, String t) {
        if(s.length() != t.length()) return false;
        List<String> permutations = new ArrayList<>();
        generatePermutations(s.toCharArray(), 0 , permutations);
        return permutations.contains(t);
    }

    public static void generatePermutations(char[] arr, int start, List<String> perms) {
        if(start == arr.length) {
            perms.add(new String(arr));
            return;
        }
        for(int i = start; i < arr.length; i++) {
            swap(arr, start, i);
            generatePermutations(arr, start + 1, perms);
            swap(arr, start, i);
        }
    }

    public static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Better Approach: Sort the strings and compare them
    // Time Complexity: O(nlogn + mlogm), Space Complexity: O(1)
    public static boolean isAnagram1(String s, String t) {
        if(s.length() != t.length()) return false;
        char[] arr1 = s.toCharArray();
        char[] arr2 = t.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        return new String(arr1).equals(new String(arr2));
    }

    // Optimal Approach: Count the frequency of each character in both strings and compare them
    // Time Complexity: O(n + m), Space Complexity: O(1)
    public static boolean isAnagramOptimal(String s, String t) {
        if(s.length() != t.length()) return false;
        int[] alphabets = new int[26];
        for(int i=0; i<s.length(); i++){
            alphabets[s.charAt(i) - 'a']++;
            alphabets[t.charAt(i) - 'a']--;
        }

        for(int i=0; i<26; i++){
            if(alphabets[i] != 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "anagram", t = "nagaram";
        System.out.println(isAnagram(s, t));
        System.out.println(isAnagram1(s, t));
        System.out.println(isAnagramOptimal(s, t));
    }
}

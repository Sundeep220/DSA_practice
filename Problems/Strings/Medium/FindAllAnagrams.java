package Problems.Strings.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAllAnagrams {
    // Problem: https://leetcode.com/problems/find-all-anagrams-in-a-string/

    // Brute Force: Check every substring of length p.length().
    // Time: O((m - n + 1) × n) => O(m * n)
    // Space: O(n)
    public List<Integer> findAnagramsBrute(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if(s == null || p == null || s.length() < p.length()) return res;
        int m = s.length();
        int n = p.length();
        for(int i = 0; i < m; i++){
            if(i + n <= m){
                String window = s.substring(i, i + n);
                if(isAnagram(window, p))
                    res.add(i);
            }
        }
        return res;
    }

    public boolean isAnagram(String s, String t) {
        int[] map = new int[26];
        if(s.length() != t.length()) return false;

        for(int i = 0; i < s.length(); i++){
            map[s.charAt(i) - 'a']++;
            map[t.charAt(i) - 'a']--;
        }


        for(int count: map){
            if(count != 0) return false;
        }

        return true;
    }

    // Better
    // Intuition:
    // Reuse previous window.
    // Remove left character and add right character.
    // Time: O(n)
    // Space: O(26)
    public List<Integer> better(String s,String p){
        List<Integer> ans=new ArrayList<>();
        if(s.length()<p.length()) return ans;

        int[] target=new int[26];
        int[] window=new int[26];

        for(char c:p.toCharArray()) target[c-'a']++;

        int k=p.length();

        for(int i=0;i<k;i++) window[s.charAt(i)-'a']++;

        if(Arrays.equals(window,target)) ans.add(0);

        for(int r=k;r<s.length();r++){
            window[s.charAt(r)-'a']++;
            window[s.charAt(r-k)-'a']--;
            if(Arrays.equals(window,target))
                ans.add(r-k+1);
        }
        return ans;
    }

    // Optimal
    // Intuition:
    // Maintain a count of how many of the 26 frequencies match.
    // Avoid Arrays.equals() every slide.
    // Time: O(n)
    // Space: O(26)
    public List<Integer> optimal(String s,String p){
        List<Integer> ans=new ArrayList<>();
        if(s.length()<p.length()) return ans;

        int[] target=new int[26];
        int[] window=new int[26];

        for(char c:p.toCharArray()) target[c-'a']++;

        int k=p.length();

        for(int i=0;i<k;i++) window[s.charAt(i)-'a']++;

        int matches=0;
        for(int i=0;i<26;i++)
            if(window[i]==target[i]) matches++;

        if(matches==26) ans.add(0);

        for(int r=k;r<s.length();r++){
            int in=s.charAt(r)-'a';
            int out=s.charAt(r-k)-'a';

            window[in]++;
            if(window[in]==target[in]) matches++;
            else if(window[in]-1==target[in]) matches--;

            window[out]--;
            if(window[out]==target[out]) matches++;
            else if(window[out]+1==target[out]) matches--;

            if(matches==26)
                ans.add(r-k+1);
        }

        return ans;
    }
}

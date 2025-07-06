package Problems.Strings.Easy;

import java.util.HashMap;
import java.util.Map;

public class IsoMorphicStrings {
    // Problem: https://leetcode.com/problems/isomorphic-strings/

    // Brute Force: O(2n) time | O(2n) space
    // Using Two HashMap to store the mapping between s and t
    public boolean isIsomorphicBrute(String s, String t) {
        if (s.length() != t.length()) return false;

        Map<Character, Character> mapST = new HashMap<>();
        Map<Character, Character> mapTS = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char cs = s.charAt(i);
            char ct = t.charAt(i);

            if (mapST.containsKey(cs) && mapST.get(cs) != ct)
                return false;

            if (mapTS.containsKey(ct) && mapTS.get(ct) != cs)
                return false;

            mapST.put(cs, ct);
            mapTS.put(ct, cs);
        }

        return true;
    }

    // Better Solution: Using only one HashMap
    // O(n) time | O(n) space
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) return false;
        HashMap<Character,Character> mp=new HashMap<>();
        for(int i=0;i<s.length();i++){
            Character x=s.charAt(i);
            Character y=t.charAt(i);
            if(mp.containsKey(x)){
                if(mp.get(x)!=y){
                    return false;
                }
            }
            else if(mp.containsValue(y)){
                return false;
            }

            else{
                mp.put(x,y);
            }
        }

        return true;
    }

    // Optimal Solution: O(n) time
    // Using two arrays instead of HashMap
    public boolean isIsomorphicOptimal(String s, String t) {
        if (s.length() != t.length()) return false;
        // 256 size arrays kyunki total ASCII characters ke liye
        int[] m1 = new int[256];
        int[] m2 = new int[256];
        for (int i = 0; i < s.length(); i++) {
            // Agar dono characters ka last index same nahi hai
            if (m1[s.charAt(i)] != m2[t.charAt(i)]) return false;

            // i+1 isliye store kar rahe kyunki default value 0 hoti hai
            m1[s.charAt(i)] = i + 1;
            m2[t.charAt(i)] = i + 1;
        }
        return true;
    }


}

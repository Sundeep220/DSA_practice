package Problems.Strings.Easy;

import java.util.HashMap;

public class RomanToInt {
    // Problem: https://leetcode.com/problems/roman-to-integer/

    // Solution 1: Using HashMap
    public int romanToIntHash(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int result = 0;
        for(int i = s.length() - 1; i >= 0; i--) {
            int curr = map.get(s.charAt(i));
            if(i < s.length() - 1 && map.get(s.charAt(i + 1)) > curr) {
                result -= curr;
            } else {
                result += curr;
            }
        }
        return result;
    }

    // Solution 2:
    // Using array instead of HashMap
    public int romanToIntOptimal(String s) {
        int[] val = new int[128];  // ASCII size
        val['I'] = 1;
        val['V'] = 5;
        val['X'] = 10;
        val['L'] = 50;
        val['C'] = 100;
        val['D'] = 500;
        val['M'] = 1000;

        int total = 0;

        for (int i = 0; i < s.length() - 1; i++) {
            if (val[s.charAt(i)] < val[s.charAt(i + 1)]) {
                total -= val[s.charAt(i)];
            } else {
                total += val[s.charAt(i)];
            }
        }

        // Add the value of the last Roman numeral
        total += val[s.charAt(s.length() - 1)];

        return total;
    }


    // Most Optimal Solution: O(n) time | O(1) space
    public int romanToInt(String s) {
        int result = 0;
        int prev = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int curr = getValue(s.charAt(i));
            if (curr < prev) {
                result -= curr;
            } else {
                result += curr;
            }
            prev = curr;
        }
        return result;
    }

    private int getValue(char c) {
        return switch (c) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> 0;
        };
    }


    public static void main(String[] args) {
        RomanToInt obj = new RomanToInt();
        System.out.println(obj.romanToInt("MCMXCIV"));
        System.out.println(obj.romanToIntHash("MCMXCIV"));
        System.out.println(obj.romanToIntOptimal("MCMXCIV"));
    }
}

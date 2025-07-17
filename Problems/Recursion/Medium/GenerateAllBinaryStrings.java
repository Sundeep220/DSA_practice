package Problems.Recursion.Medium;

import java.util.ArrayList;
import java.util.List;

public class GenerateAllBinaryStrings {
    // Problem Link: https://www.geeksforgeeks.org/problems/generate-all-binary-strings/1

    public static List<String> generateBinaryStrings(int N) {
        // Code here
        List<String> ans = new ArrayList<>();
        generate(N, 0, new StringBuilder(), ans, '0');
        return ans;
    }

    public static void generate(int N, int index, StringBuilder sb, List<String> ans, char prev) {
        if(index == N) {
            ans.add(sb.toString());
            return;
        }

        // Always safe to add '0'
        sb.append('0');
        generate(N, index + 1, sb, ans, '0');
        sb.deleteCharAt(sb.length() - 1);  // backtrack

        // Add '1' only if previous wasn't '1'
        if (prev != '1') {
            sb.append('1');
            generate(N, index + 1, sb, ans, '1');
            sb.deleteCharAt(sb.length() - 1);  // backtrack
        }


    }

    public static void main(String[] args) {
        int N = 3;
        System.out.println(generateBinaryStrings(N));
    }
}

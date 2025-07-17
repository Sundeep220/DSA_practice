package Problems.Recursion.Medium;

import java.util.ArrayList;
import java.util.List;

public class GenerateBinaryStringFromPattern {
    // Problem: https://www.naukri.com/code360/problems/generate-all-binary-strings-from-pattern_1089566?leftPanelTabValue=PROBLEM

    public static List<String> generateBinaryStrings(String pattern) {
        List<String> ans = new ArrayList<>();
        generate(pattern, 0, new StringBuilder(), ans);
        return ans;
    }

    public static void generate(String pattern, int index, StringBuilder sb, List<String> ans) {
        if(index == pattern.length()) {
            ans.add(sb.toString());
            return;
        }

        if(pattern.charAt(index) == '?'){
            // Add 0 and 1
            sb.append('0');
            generate(pattern, index + 1, sb, ans);
            sb.deleteCharAt(sb.length() - 1); // Backtrack

            sb.append('1');
            generate(pattern, index + 1, sb, ans);
            sb.deleteCharAt(sb.length() - 1); // Backtrack
        }else {
            // Just go to next index
            sb.append(pattern.charAt(index));
            generate(pattern, index + 1, sb, ans);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}

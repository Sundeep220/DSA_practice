package Problems.Recursion.Medium;
import java.util.ArrayList;
import java.util.List;


public class GenerateParenthesis {
    // https://leetcode.com/problems/generate-parentheses/
    public static List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        generate(n, 0, 0, new StringBuilder(), ans);
        return ans;
    }

    public static void generate(int n, int openIndex, int closeIndex, StringBuilder current, List<String> ans){
        if(openIndex == n && closeIndex == n){
            ans.add(current.toString());
            return;
        }

        // add '('
        if(openIndex < n){
            current.append('(');
            generate(n, openIndex + 1, closeIndex, current, ans);
            current.deleteCharAt(current.length() - 1); //backtrack
        }

        // add ')'
        if(openIndex > closeIndex){
            current.append(')');
            generate(n, openIndex, closeIndex + 1, current, ans);
            current.deleteCharAt(current.length() - 1); //backtrack
        }
    }

    public static void main(String[] args) {
        int n = 3;
        System.out.println(generateParenthesis(n));
    }
}

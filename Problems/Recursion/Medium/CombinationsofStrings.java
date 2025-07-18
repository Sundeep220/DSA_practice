package Problems.Recursion.Medium;
import java.util.ArrayList;
import java.util.List;
public class CombinationsofStrings {

    public static List<String> getCombinations(String s1, String s2) {
        List<String> result = new ArrayList<>();
        // Edge cases
        if (s1.isEmpty() && s2.isEmpty()) return result;

        if (s1.isEmpty()) {
            for (char c : s2.toCharArray()) {
                result.add(String.valueOf(c));
            }
            return result;
        }

        if (s2.isEmpty()) {
            for (char c : s1.toCharArray()) {
                result.add(String.valueOf(c));
            }
            return result;
        }
        generateRecursive(s1, s2, 0, 0, "", result);
        return result;
    }

    public static void generateRecursive(String s1, String s2, int i, int j, String current, List<String> result) {
        if (current.length() == 2) {
            result.add(current);
            return;
        }

        if (current.isEmpty()) {
            // First character from s1
            for (int x = 0; x < s1.length(); x++) {
                generateRecursive(s1, s2, x, 0, current + s1.charAt(x), result);
            }
        } else {
            // Second character from s2
            for (int y = 0; y < s2.length(); y++) {
                generateRecursive(s1, s2, i, y, current + s2.charAt(y), result);
            }
        }
    }

    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "";
        List<String> combinations = getCombinations(s1, s2);
        System.out.println(combinations);
    }

}

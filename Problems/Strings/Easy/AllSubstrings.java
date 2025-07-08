package Problems.Strings.Easy;

import java.util.ArrayList;
import java.util.List;

public class AllSubstrings {
    public static List<String> printAllSubstringsWithoutSubfunction(String s) {
        int n = s.length();
        List<String> result = new ArrayList<>();
        for (int start = 0; start < n; start++) {
            StringBuilder temp = new StringBuilder();

            for (int end = start; end < n; end++) {
                temp.append(s.charAt(end));
                result.add(temp.toString());
            }
        }
        return result;
    }

    public static List<String> generateSubstrings(String s) {
        List<String> result = new ArrayList<>();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                result.add(s.substring(i, j));
            }
        }
        return result;
    }


    public static void main(String[] args) {
        String s = "babad";
        System.out.println(generateSubstrings(s));
        System.out.println(printAllSubstringsWithoutSubfunction(s));

    }


}

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

    public static List<String> printAllSubstrings(String s) {
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

    public static void main(String[] args) {
        String s = "abcde";
        System.out.println(printAllSubstrings(s));
        System.out.println(printAllSubstringsWithoutSubfunction(s));

    }


}

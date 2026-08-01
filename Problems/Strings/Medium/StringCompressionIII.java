package Problems.Strings.Medium;

public class StringCompressionIII {
    // Problem: https://leetcode.com/problems/string-compression-iii/description/

    public String compressedString(String word) {

        StringBuilder ans = new StringBuilder();

        int i = 0;

        while (i < word.length()) {

            char ch = word.charAt(i);
            int count = 0;

            while (i < word.length()
                    && word.charAt(i) == ch
                    && count < 9) {

                count++;
                i++;
            }

            ans.append(count);
            ans.append(ch);
        }

        return ans.toString();
    }
}

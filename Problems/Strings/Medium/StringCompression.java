package Problems.Strings.Medium;

public class StringCompression {
    // Problem: https://leetcode.com/problems/string-compression/description/
    public int compress(char[] chars) {

        int write = 0;
        int i = 0;

        while (i < chars.length) {

            char current = chars[i];
            int count = 0;

            // Count consecutive characters
            while (i < chars.length && chars[i] == current) {
                count++;
                i++;
            }

            // Write character
            chars[write++] = current;

            // Write count if greater than 1
            if (count > 1) {
                String cnt = Integer.toString(count);

                for (char digit : cnt.toCharArray()) {
                    chars[write++] = digit;
                }
            }
        }

        return write;
    }
}

package Problems.Strings.Hard;

public class DesignCompressedStringIterator {
    // Problem: https://leetcode.com/problems/design-compressed-string-iterator/description/
    // Instead of compressing a string, you're given an already compressed string and need to iterate over the original characters without fully decompressing it
    // Input: L1e2t1C1o1d1e1
    // Output: LeetCode

    static class StringIterator {

        private final String compressed;
        private int index;
        private char currentChar;
        private int remaining;

        public StringIterator(String compressedString) {
            compressed = compressedString;
            index = 0;
            remaining = 0;
        }

        public char next() {

            if (!hasNext())
                return ' ';

            if (remaining == 0) {
                currentChar = compressed.charAt(index++);
                int count = 0;
                while (index < compressed.length() && Character.isDigit(compressed.charAt(index))) {
                    count = count * 10 + (compressed.charAt(index) - '0');
                    index++;
                }
                remaining = count;
            }

            remaining--;

            return currentChar;
        }

        public boolean hasNext() {
            return remaining > 0 || index < compressed.length();
        }
    }

    public static void main(String[] args) {
        String input = "L1e2t1C1o1d1e1";
        StringIterator iterator = new StringIterator(input);
        System.out.println("Current string: " + input);
        System.out.println("Printing through iterator: ");
        while(iterator.hasNext()){
            System.out.print(iterator.next());
        }
        System.out.println();
    }
}

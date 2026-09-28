package Problems.Arrays.Hard;

import java.util.ArrayList;
import java.util.List;

public class TextJustification {
        // Problem: https://leetcode.com/problems/text-justification/description/?envType=problem-list-v2&envId=array
        /*
         * INTUITION:
         *
         * 1. Greedily pack as many words as possible into each line.
         *
         * 2. Once we know the words in a line:
         *    - Last line / single word -> left justify.
         *    - Normal line -> distribute spaces between words.
         *
         * 3. For a normal line:
         *    totalSpaces = maxWidth - totalWordLength
         *    gaps        = wordCount - 1
         *
         *    spacesPerGap = totalSpaces / gaps
         *    extraSpaces  = totalSpaces % gaps
         *
         *    Give one extra space to the leftmost `extraSpaces` gaps.
         */
        public List<String> fullJustify(String[] words, int maxWidth) {

            List<String> result = new ArrayList<>();
            int i = 0;

            while (i < words.length) {

                // --------------------------------------------------
                // 1. Greedily find how many words fit in this line
                // --------------------------------------------------

                int start = i;
                int totalWordLength = 0;

                while (i < words.length) {

                    // Number of spaces needed if we add this word.
                    // There is one mandatory space before each new word.
                    int wordsInLine = i - start;

                    int requiredLength =
                            totalWordLength
                                    + words[i].length()
                                    + wordsInLine;

                    // Adding this word would exceed maxWidth
                    if (requiredLength > maxWidth) {
                        break;
                    }

                    totalWordLength += words[i].length();
                    i++;
                }

                int wordCount = i - start;


                // --------------------------------------------------
                // 2. Last line OR line containing only one word
                // --------------------------------------------------
                //
                // These lines are left-justified:
                // one space between words and remaining spaces
                // are added at the end.
                // --------------------------------------------------

                if (i == words.length || wordCount == 1) {

                    StringBuilder line = new StringBuilder();

                    for (int j = start; j < i; j++) {

                        // Put one normal space between words
                        if (j > start) {
                            line.append(" ");
                        }

                        line.append(words[j]);
                    }

                    // Pad remaining spaces at the end
                    while (line.length() < maxWidth) {
                        line.append(" ");
                    }

                    result.add(line.toString());
                }


                // --------------------------------------------------
                // 3. Normal line -> fully justify
                // --------------------------------------------------

                else {

                    // Total spaces that need to be distributed
                    // between the words.
                    int totalSpaces = maxWidth - totalWordLength;

                    // Number of gaps between words.
                    // Example: ["This", "is", "fun"] -> 2 gaps
                    int gaps = wordCount - 1;

                    // Minimum number of spaces every gap receives
                    int spacesPerGap = totalSpaces / gaps;

                    // Remaining spaces after equal distribution.
                    // These go to the leftmost gaps.
                    int extraSpaces = totalSpaces % gaps;

                    StringBuilder line = new StringBuilder();

                    for (int j = start; j < i; j++) {

                        line.append(words[j]);

                        // Don't add spaces after the last word
                        if (j < i - 1) {

                            /*
                             * Give this gap:
                             *
                             * spacesPerGap
                             * +
                             * 1 extra space if this is one of
                             * the leftmost extraSpaces gaps.
                             */
                            int spaces =
                                    spacesPerGap
                                            + (j - start < extraSpaces ? 1 : 0);

                            line.append(" ".repeat(spaces));
                        }
                    }

                    result.add(line.toString());
                }
            }

            return result;
        }

    public static void main(String[] args) {
        System.out.println();
    }

}

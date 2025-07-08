package Problems.Strings.Medium;

public class CountHomogenous {
    // Problem: https://leetcode.com/problems/count-number-of-homogenous-substrings/
    // Solution1:
    public int countHomogenous1(String s) {
        int count = 1;  // start from 1 because we always have at least one char
        int res = 0;
        int mod = 1_000_000_007;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;  // continue the block
            } else {
                // end of a block, process the count
                res = (res + (int)((count * (long)(count + 1) / 2) % mod)) % mod;
                count = 1;  // reset for the next block
            }
        }

        // ✅ Add the last block after loop ends
        res = (res + (int)((count * (long)(count + 1) / 2) % mod)) % mod;

        return res;
    }


    // Solution2:
    public static int countHomogenous2(String s) {
        final int MOD = 1_000_000_007;  // To keep the result within 32-bit integer range as per constraints
        long ans = 0;                   // Stores the final answer (total homogenous substrings)
        long streak = 0;                // Tracks length of current homogenous character streak
        char prev = 0;                  // Previous character in the loop (initialized to 0 or any dummy char)

        for (char c : s.toCharArray()) {
            if (c == prev) {
                // Continue the streak: same character as before
                streak++;
            } else {
                // New character encountered → reset streak
                streak = 1;
            }

            // Update previous character tracker
            prev = c;

            // Add all substrings ending at this character to the answer
            // (e.g., streak = 3 → "a", "aa", "aaa" → 3 new substrings)
            ans = (ans + streak) % MOD;
        }

        return (int) ans;
    }

    public static void main(String[] args) {
        String s = "abbcccaa"; // Example input string
        System.out.println(countHomogenous2(s));
        int count = countHomogenous2(s); // Call the function to count homogenous substrings
        System.out.println("Number of homogenous substrings: " + count);
    }

}

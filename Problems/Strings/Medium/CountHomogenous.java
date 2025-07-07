package Problems.Strings.Medium;

public class CountHomogenous {

    public static int countHomogenous(String s) {
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
        int count = countHomogenous(s); // Call the function to count homogenous substrings
        System.out.println("Number of homogenous substrings: " + count);
    }

}

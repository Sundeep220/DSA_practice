package Problems.Greedy.Hard;

import java.util.Arrays;

public class Candy {
    // Problem: https://leetcode.com/problems/candy/

    // Greedy Algorithm: Using left and right pass
    // Time Complexity: O(n), Space Complexity: O(n)
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1); // Rule: everyone gets at least 1

        // Pass 1: Left → Right
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        // Pass 2: Right → Left
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
                // in cases where if in left pass we got a bigger initial value of candy and in
                // right pass if that value is less, then we would lose the correct candy values
            }
        }

        // Sum up total candies
        int total = 0;
        for (int c : candies) {
            total += c;
        }
        return total;
    }

    // Optimal Solution: Using Slope Concept:
    //Traverse ratings once.
    //Think of the sequence as slopes:
        //up = length of increasing slope
        //down = length of decreasing slope
        //peak = height of last increasing slope
    //For each increasing slope, candies increase
    //For each decreasing slope, candies decrease.
    //Handle the overlap at the peak carefully (to avoid double counting).
    // Time Complexity: O(n), Space Complexity: O(1)
    public int candyOptimal(int[] ratings) {
        int n = ratings.length;
        if (n <= 1) return n;

        int candies = 1;   // first child always gets 1
        int up = 0, down = 0, peak = 0;

        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                // ascending slope
                up++;
                peak = up;
                down = 0;
                candies += 1 + up;
            } else if (ratings[i] == ratings[i - 1]) {
                // flat slope, reset
                up = down = peak = 0;
                candies += 1;
            } else {
                // descending slope
                up = 0;
                down++;
                candies += 1 + down - (peak >= down ? 1 : 0);
            }
        }

        return candies;
    }
}

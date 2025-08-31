package Problems.Greedy.Medium;

import java.util.Arrays;

public class CoinChangeOlderVersion {
    // Given a list of coins and a target amount, find the minimum number of coins needed to make the change.
    // Assume that you have infinite number of each kind of coin.
    // You have coin denominations of 1, 2, 5, 10, 20, 50, 100, 500 and 1000.
    // Note: This Greedy Solution works only so fixed denominations of coins.
    public static int coinChange(int[] coins, int amount) {
        Arrays.sort(coins); // sort coins in ascending order

        int count = 0;
        for (int i = coins.length - 1; i >= 0; i--) {
            while (amount >= coins[i]) {
                amount -= coins[i];
                count++;
            }
        }
        return count;
    }
}

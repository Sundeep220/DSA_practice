package Problems.Greedy.Medium;


import java.util.Arrays;

public class FractionalKnapsack {
    // https://www.naukri.com/code360/problems/fractional-knapsack_975286?leftPanelTabValue=PROBLEM

    // Brute Force: O(N ^ 3) time
    static class Pair {
        int weight, value;
        Pair(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }

    public static double maximumValue(Pair[] items, int n, int w) {
        return helper(items, n, w, 0);
    }

    private static double helper(Pair[] items, int n, int w, int index) {
        // base case
        if (index == n || w == 0) return 0;

        // case 1: skip item
        double skip = helper(items, n, w, index + 1);

        // case 2: take fully (if it fits)
        double takeFull = 0;
        if (items[index].weight <= w) {
            takeFull = items[index].value + helper(items, n, w - items[index].weight, index + 1);
        }

        // case 3: take fraction
        double takeFraction = 0;
        if (items[index].weight > w) {
            takeFraction = ((double) items[index].value / items[index].weight) * w;
        }

        return Math.max(skip, Math.max(takeFull, takeFraction));
    }


    // Optimal Solution: Greedy Approach
    // Time Complexity: O(N * log(N)), Space Complexity: O(1)
    public static double maximumValueOptimal(Pair[] items, int n, int w) {
        // Step 1: Sort items by value/weight ratio in descending order
        Arrays.sort(items, (a, b) -> {
            double r1 = (double) a.value / a.weight;
            double r2 = (double) b.value / b.weight;
            return Double.compare(r2, r1); // descending
        });

        double maxValue = 0.0;
        int remaining = w;

        // Step 2: Pick items greedily
        for (Pair item : items) {
            if (remaining == 0) break;

            if (item.weight <= remaining) {
                // take full item
                maxValue += item.value;
                remaining -= item.weight;
            } else {
                // take fraction
                maxValue += ((double) item.value / item.weight) * remaining;
                remaining = 0; // knapsack full
            }
        }

        return maxValue;
    }


}

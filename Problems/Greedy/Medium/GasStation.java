package Problems.Greedy.Medium;

public class GasStation {
    // Problem: https://leetcode.com/problems/gas-station/description/?envType=problem-list-v2&envId=array
    /*
     * INTUITION:
     *
     * For every station:
     *
     *     net = gas[i] - cost[i]
     *
     * `tank` represents the gas we have accumulated
     * from our current candidate starting station.
     *
     * If tank becomes negative at station i:
     *
     *     The current start cannot reach i + 1.
     *
     *     Moreover, none of the stations between
     *     start and i can be a valid starting point.
     *
     *     Therefore, we can safely set:
     *
     *         start = i + 1
     *
     * and reset tank to 0.
     *
     * Finally:
     *
     *     If total gas < total cost -> impossible -> -1
     *     Otherwise -> the greedy start is the answer.
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {

        int totalGas = 0;
        int totalCost = 0;

        int tank = 0;
        int start = 0;

        for (int i = 0; i < gas.length; i++) {

            // Calculate total gas and total cost
            totalGas += gas[i];
            totalCost += cost[i];

            // Gas gained/lost at this station
            tank += gas[i] - cost[i];

            // Current starting point cannot reach next station
            if (tank < 0) {

                // Try the next station as the new start
                start = i + 1;

                // Start with an empty tank again
                tank = 0;
            }
        }

        // Not enough gas to complete the entire circuit
        if (totalGas < totalCost) {
            return -1;
        }

        return start;
    }
}

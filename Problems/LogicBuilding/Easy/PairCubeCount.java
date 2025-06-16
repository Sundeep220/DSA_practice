package Problems.LogicBuilding.Easy;

public class PairCubeCount {
    // https://www.geeksforgeeks.org/problems/pair-cube-count4132/1
    // given a number n, find the number of pairs of cubes whose sum is equal to n
    // i.e. a^3 + b^3 = n, find all a and b such that a^3 + b^3 = n
    static int pairCubeCount(int n) {
        // code here
        int solutions = 0;
        final double EPSILON = 1e-10; // used to check if b is a whole number

        for (int a = 1; Math.pow(a, 3) <= n; a++) {
            double remaining = n - Math.pow(a, 3);
            if (remaining < 0) break;

            double b = Math.cbrt(remaining);
            // check if b is a whole number and b >= 0
            if (b >= 0 && Math.abs(b - Math.round(b)) < EPSILON) { // b is a whole number and b >= 0
                solutions++;
            }
        }
        return solutions;
    }
}

package Problems.LogicBuilding.Easy;

import java.util.ArrayList;

public class AllDivisors {

    static ArrayList<Integer> printDivisors(int n) {
        ArrayList<Integer> divisors = new ArrayList<>();

        // Note that this loop runs till square root
        for (int i = 1; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {

                // If divisors are equal, add only once
                if (n / i == i) {
                    divisors.add(i);
                }
                // Otherwise add both
                else {
                    divisors.add(i);
                    divisors.add(n / i);
                }
            }
        }
        return divisors;
    }

}

package Problems.LogicBuilding.Easy;

import java.util.ArrayList;
import java.util.Arrays;

public class ThreeDivisorsProblem {
    // ProblemLink: https://www.geeksforgeeks.org/problems/3-divisors3942/1
    static ArrayList<Integer> threeDivisors(ArrayList<Long> query, int q) {
    // code here
    // Using Sieve of Eratothenes
    long maxN = 0;
    for (Long val : query) {
        maxN = Math.max(maxN, val);
    }
    int sqrtMax = (int)Math.sqrt(maxN);
    boolean[] primes = new boolean[ sqrtMax + 1];
    Arrays.fill(primes, true);
    primes[0] = primes[1] = false;

    for(int i = 2; i*i <= sqrtMax; i++){
        if(primes[i]){
            for(int j = i*i; j<= sqrtMax;j+=i){
                primes[j] = false;
            }
        }
    }

    ArrayList<Long> primeSquares = new ArrayList<>();
    for (int i = 2; i <= sqrtMax; i++) {
        if (primes[i]) {
            primeSquares.add(1L * i * i);
        }
    }

    ArrayList<Integer> ans = new ArrayList<>();
    for (Long n : query) {
        int count = 0;
        for (Long val : primeSquares) {
            if (val <= n) count++;
            else break;
        }
        ans.add(count);
    }

    return ans;

    }
    public static void main(String[] args) {
        ArrayList<Long> query = new ArrayList<>();
        query.add(3L);
        query.add(4L);
        query.add(5L);
        int q = 3;
        System.out.println(threeDivisors(query, q));
    }
}

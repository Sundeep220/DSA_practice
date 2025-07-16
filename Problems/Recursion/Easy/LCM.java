package Problems.Recursion.Easy;

import static Problems.Recursion.Easy.GCD.gcdIT;
import static Problems.Recursion.Easy.GCD.gcdRec;

public class LCM {
    //Brute Force
    public static int LCMBF(int n1, int n2) {
        if (n1 == 0 || n2 == 0) {
            return 0;
        }
        int n = Math.max(n1, n2);
        for (int i = n; i <= n1 * n2; i++) {
            if (i % n1 == 0 && i % n2 == 0) {
                return i;
            }
        }
        return 0;
    }

    // Opttimal Solution
    public static int LCMOP(int n1, int n2) {
        if (n1 == 0 || n2 == 0) {
            return 0;
        }
        return (n1 * n2) / gcdIT(n1, n2);
    }

    // Using Recursion
    public static int LCMRec(int n1, int n2) {
        if (n1 == 0 || n2 == 0) {
            return 0;
        }
        return (n1 * n2) / gcdRec(n1, n2);
    }
}

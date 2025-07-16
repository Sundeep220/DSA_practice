package Problems.Recursion.Easy;

public class GCD {
    //Brute Force
    public static int gcdBF(int a, int b) {
        int min = Math.min(a, b);
        for (int i = min; i > 0; i--) {
            if (a % i == 0 && b % i == 0) return i;
        }
        return 1;
    }


    //Iterative
    public static int gcdIT(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    //Recursion
    public static int gcdRec(int a, int b) {
        if (b == 0) return a;
        return gcdRec(b, a % b);
    }
}

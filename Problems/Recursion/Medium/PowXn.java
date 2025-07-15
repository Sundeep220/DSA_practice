package Problems.Recursion.Medium;

public class PowXn {
    // https://leetcode.com/problems/powx-n/

    // Using Binary Exponentiation
    public static double myPow(double x, int n) {
        if (n == 0) return 1.0;
        if (n == 1) return x;
        double res = 1.0;
        while(n != 0){
            if((n & 1) == 1){
                res =  res * x;
            }
            x = x * x;
            n = n >> 1;
        }
        return res;
    }

    // Using Recursion
    public static double myPowRecursive(double x, int n) {
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }
        return helper(x, 1D , n);
    }

    public static double helper(double x, double res, int n){
        if(n == 0) return res;

        if((n & 1) == 1)
            return helper(x, res * x, n - 1);

        return helper(x * x, res, n >> 1);
    }


    public static void main(String[] args) {
        double x = 2.0;
        int n = 10;
        System.out.println(myPow(x, n));
        System.out.println(myPowRecursive(x, n));
    }
}

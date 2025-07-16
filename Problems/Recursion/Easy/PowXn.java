package Problems.Recursion.Easy;

public class PowXn {

    public static double myPow(double x, int n) {
        if(n == 0) return 1D;
        if(n == 1) return x;
        double half = myPow(x, n >> 1);
        double result = half * half;
        if((n & 1) == 1) result *= x;
        return result;
    }

    public static void main(String[] args) {
        System.out.println(myPow(2, 10));
    }
}

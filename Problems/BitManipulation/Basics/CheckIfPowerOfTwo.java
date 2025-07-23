package Problems.BitManipulation.Basics;

public class CheckIfPowerOfTwo {
    public static boolean isPowerOfTwo(int n) {
        return (n & (n - 1)) == 0;
    }

    public static void main(String[] args) {
        int n = 5; // 101
        System.out.println(isPowerOfTwo(n)); // false
    }
}

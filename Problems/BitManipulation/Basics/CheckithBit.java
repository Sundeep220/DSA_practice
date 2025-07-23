package Problems.BitManipulation.Basics;

public class CheckithBit {
    // Check if ith bit is set or not
    public static void main(String[] args) {
        int n = 5; // 101
        int i = 2; // 100
        System.out.println((n & (1 << i)) != 0 ? "true" : "false");
        // Another way
        System.out.println(((n >> i) & 1) == 1 ? "true" : "false");
    }
}

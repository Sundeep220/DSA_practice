package Problems.BitManipulation.Basics;

public class CountSetBits {
    public static int countSetBits(int n) {
        int count = 0;
        while (n > 0) {
            if ((n & 1) == 1) {
                count++;
            }
            n = n >> 1;
        }
        return count;
    }

    // Optimal Solution: O(1) time | O(1) space
    public static int countSetBitsOptimal(int n) {
        int count = 0;
        while (n > 0) {
            n = n & (n - 1);  // Clear the rightmost set bit
            count++;  // Increment the count
        }
        return count;
    }

    public static void main(String[] args) {
        int n = 5; // 101
        System.out.println(countSetBits(n)); // 2
        System.out.println(countSetBitsOptimal(n)); // 2
    }
}

package Problems.BitManipulation.Basics;

public class RemoveLastSetBit {
    public static void main(String[] args) {
        int n = 5; // 101
        System.out.println(n & (n - 1)); // 101
    }
}

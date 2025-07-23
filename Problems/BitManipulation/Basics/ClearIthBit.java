package Problems.BitManipulation.Basics;

public class ClearIthBit {
    public static void main(String[] args) {
        int n = 5; // 101
        int i = 2; // 10
        System.out.println(n & ~(1 << i)); // 101
    }
}

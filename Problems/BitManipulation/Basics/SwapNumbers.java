package Problems.BitManipulation.Basics;

public class SwapNumbers {
    public static void swap(int a, int b) {
        // lets say a = 10, b = 20
        a = a ^ b;  // a = 1010, b = 10100 => a ^ b = 11110
        b = a ^ b;  // a = 11110, b = 10100 => a ^ b = 1010
        a = a ^ b;  // a = 11110, b = 1010 => a ^ b = 10100
        System.out.println("a: " + a);
        System.out.println("b: " + b);
    }

    // Without using XOR
    public static void swap2(int a, int b) {
        a = a + b;  // 10 + 20 = 30
        b = a - b;  // 30 - 20 = 10
        a = a - b;  // 30 - 10 = 20
        System.out.println("a: " + a);
        System.out.println("b: " + b);
    }

    public static void main(String[] args) {
        swap(10, 20);
        int x = ~5;
        System.out.println(x);
    }
}

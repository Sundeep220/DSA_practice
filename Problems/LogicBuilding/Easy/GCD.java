package Problems.LogicBuilding.Easy;

public class GCD {
    public static int gcd(int a, int b) {
        // code here
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static int gcd2(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        int a = 4;
        int b = 6;
        System.out.println(gcd(a, b));
    }
}

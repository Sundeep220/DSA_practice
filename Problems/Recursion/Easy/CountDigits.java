package Problems.Recursion.Easy;

public class CountDigits {

    public static int countDigits(int n) {
        if (n == 0) return 0;
        return 1 + countDigits(n / 10);
    }

    public static void main(String[] args) {
        int n = 1234;
        System.out.println(countDigits(n));
    }
}

package Problems.LogicBuilding.Easy;

public class ArmstrongNumber {
    static boolean armstrongNumber(int n) {
        // code here
        int sum = 0;
        int num = n;
        int digits = totdig(n);
        while(num != 0){
            int digit = num % 10;
            sum += power(digit, digits);  // optimized power
            num /= 10;
        }
        return sum == n;
    }

    static int totdig(int n){
        int tot = 0;
        while(n != 0){
            tot++;
            n /= 10;
        }
        return tot;
    }

    static int power(int base, int exp) {
        // Using Binary Exponentiation
        int result = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result *= base;
            }
            base *= base;
            exp >>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(armstrongNumber(153));
    }
}

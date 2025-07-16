package Problems.Recursion.Easy;

public class printTillN {

    // Print from N to 1
    public static void printTillN(int n) {
        if (n == 0) {
            System.out.println();
            return;
        }
        System.out.print(n+ " ");
        printTillN(n - 1);
    }

    // Print from 1 to N
    public static void printTillN2(int i, int n) {
        if(i == n) {
            System.out.print(i);
            return;
        }
        System.out.print(i + " ");
        printTillN2(i + 1, n);
    }

    public static void main(String[] args) {
        int n = 5;
        int i = 1;
        printTillN(n);

        System.out.println("------------");
        printTillN2(i, n);
    }
}

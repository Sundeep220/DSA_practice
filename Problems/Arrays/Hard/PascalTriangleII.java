package Problems.Arrays.Hard;

public class PascalTriangleII {
    // Problem: Given row number r and column number c. Print the element at position (r, c) in Pascal’s triangle.

    public static int getCoefficiant(int n, int r) {
        if (r > n) return 0;
        if (r == 0 || r == n) return 1;

        r = Math.min(r, n - r); // use symmetry

        long res = 1;
        for (int i = 0; i < r; i++) {
            res = res * (n - i)/ (i+1);
        }

        return (int) res;
    }

    public static int pascalTriangle(int r, int c) {
        return (int) getCoefficiant(r - 1, c - 1);
    }



    public static void main(String[] args) {
        int r = 5, c = 3;
        System.out.println(pascalTriangle(r, c));
    }
}

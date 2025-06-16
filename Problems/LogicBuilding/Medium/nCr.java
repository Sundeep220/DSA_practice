package Problems.LogicBuilding.Medium;

public class nCr {
    public int nCr(int n, int r) {
        if (r > n) return 0;
        if (r == 0 || r == n) return 1;

        r = Math.min(r, n - r); // use symmetry

        long res = 1;
        for (int i = 0; i < r; i++) {
            res = res * (n - i)/ (i+1);
        }

        return (int) res;
    }

    public static void main(String[] args) {
        int n = 5, r = 2;
        System.out.println(new nCr().nCr(n, r));
    }
}

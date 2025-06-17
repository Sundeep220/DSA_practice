package Problems.LogicBuilding.Medium;

import java.util.ArrayList;
import java.util.List;

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

        List<int[]> res = new ArrayList<>();
         res.add(new int[]{1,3,3,1});
         res.add(new int[]{1,3, 2, 2, 1});
         res.add(new int[]{1, 2, 3, 3, 2, 1});
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < res.get(i).length; j++)
                System.out.print(res.get(i)[j] + " ");
            System.out.println();
        }
    }
}

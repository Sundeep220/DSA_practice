package Problems.Arrays.Hard;

import java.util.ArrayList;
import java.util.List;

public class PascalTriangleI {
    // Variation 1: Given n, print nth row of pascal triangle

    // Brute Force: Generate the tree and print the nth row
    public static List<Integer> getRow(int rowIndex) {
        if(rowIndex == 0) return new ArrayList<>(List.of(1));
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0; i<rowIndex; i++){
            List<Integer> list = new ArrayList<>();
            for(int j=0; j<=i; j++){
                if(j==0 || j==i) list.add(1);
                else list.add(res.get(i-1).get(j-1) + res.get(i-1).get(j));
            }
            res.add(list);
        }
        return res.get(rowIndex-1);
    }

    // Better: Generate row only for each interval
    public static List<Integer> getRow2(int rowIndex) {
        List<Integer> prev = new ArrayList<>();
        prev.add(1);
        for(int i=1; i<rowIndex; i++){
            List<Integer> curr = new ArrayList<>();
            curr.add(1);
            for(int j=1; j<i; j++){
                curr.add(prev.get(j-1) + prev.get(j));
            }
            curr.add(1);
            prev = curr;
        }
        return prev;
    }

    // Better: One more solution
    public static long nCr(int n, int r) {
        long res = 1;

        r = Math.min(r, n - r); // as nCr(n, r) = nCr(n, n-r)

        // calculating nCr:
        for (int i = 0; i < r; i++) {
            res = res * (n - i);
            res = res / (i + 1);
        }
        return res;
    }

    public static List<Integer> pascalTriangle(int n) {
        List<Integer> res = new ArrayList<>();
        // printing the entire row n:
        for (int c = 1; c <= n; c++) {
            res.add((int) nCr(n - 1, c - 1));
        }
        return res;
    }


    // Optimal: Using Binomial Coefficient
    public static List<Integer> getRow3(int rowIndex) {
        List<Integer> res = new ArrayList<>();
        int k = 1;
        res.add(1);
        for(int i=1; i<rowIndex; i++){
            k = k * (rowIndex - i) / i;
            res.add(k);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(getRow(4));
        System.out.println(getRow2(4));
        System.out.println(getRow3(4));
        System.out.println(pascalTriangle(4));
    }
}

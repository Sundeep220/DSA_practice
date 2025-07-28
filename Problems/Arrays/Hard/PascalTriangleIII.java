package Problems.Arrays.Hard;

import java.util.ArrayList;
import java.util.List;

public class PascalTriangleIII {
    //Problem: Given N, print pascal triangle for N rows.
    //Time Complexity: O(N^2) Space Complexity: O(1)
    //Ex: Input: 5 Output: 1 5 10 10 5 1

    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>(); // to store all the rows
        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            for(int j=0; j<=i; j++){
                if(i == j || j == 0)
                    row.add(1);
                else row.add(res.get(i-1).get(j-1) + res.get(i-1).get(j) );// add the previous row j));
            }
            res.add(row);
        }
        return res;
    }

    public List<List<Integer>> generateII(int numRows) {

        List<List<Integer>> ans = new ArrayList<>();

        for (int i=1; i<=numRows; i++) {
            ans.add(generateRow(i));
        }

        return ans;
    }

    public List<Integer> generateRow(int rowNum) {

        List<Integer> row = new ArrayList<>();
        int num = 1;
        row.add(num);

        for (int i=1; i<rowNum; i++) {
            num *= (rowNum - i);
            num /= i;
            row.add(num);
        }

        return row;
    }

    public static void main(String[] args) {
        int n = 5;
        List<List<Integer>> res = generate(n);
        for (List<Integer> re : res) {
            for (Integer integer : re)
                System.out.print(integer + " ");
            System.out.println();
        }
    }
}

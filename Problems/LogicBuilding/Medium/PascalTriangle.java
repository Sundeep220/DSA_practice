package Problems.LogicBuilding.Medium;

import java.util.ArrayList;

//        | Approach      | Time     | Space    | Comments                      |
//        | ------------- | -------- | -------- | ----------------------------- |
//        | Full Triangle | O(n²)    | O(n²)    | Easy, but not efficient       |
//        | Prev Row Only | O(n²)    | O(n)     | Better memory usage           |
//        | Binomial Math | **O(n)** | **O(n)** | Best, fast, no triangle build |

public class PascalTriangle {
    // Naive Approach
    ArrayList<Integer> nthRowOfPascalTriangleNaive(int n) {
        ArrayList<ArrayList<Integer>> triangle = new ArrayList<>();
        // code here
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i)
                    row.add(1); // if corner element, then it is 1
                else // otherwise calculating based on previous row.
                    row.add(triangle.get(i - 1).get(j - 1) + triangle.get(i - 1).get(j));
            }
            triangle.add(row);
        }

        return triangle.get(n-1);
    }

    // Space Optimized:
    //Time: O(n²)
    //Space: O(n)
    ArrayList<Integer> nthRowOfPascalTriangleSpaceOptimized(int n) {
        ArrayList<Integer> row = new ArrayList<>();
        row.add(1); // First row is always [1]

        for (int i = 1; i < n; i++) {
            ArrayList<Integer> newRow = new ArrayList<>();
            newRow.add(1); // First element is always 1

            for (int j = 1; j < i; j++) {
                newRow.add(row.get(j - 1) + row.get(j)); // middle elements
            }

            newRow.add(1); // Last element is always 1
            row = newRow; // update row for next iteration
        }

        return row;
    }

    // Most Optimal
    //Time: O(n)
    //Space: O(1)
    ArrayList<Integer> nthRowOfPascalTriangle(int n) {
        ArrayList<Integer> row = new ArrayList<>();
        long val = 1;
        row.add(1); // C(n, 0) is always 1

        for (int k = 1; k < n; k++) {
            val = val * (n - k ) / k;  // Using C(n,k) = C(n,k-1) * (n-k+1)/k
            row.add((int) val);
        }

        return row;
    }

    public static void main(String[] args) {
        PascalTriangle pascalTriangle = new PascalTriangle();
        System.out.println(pascalTriangle.nthRowOfPascalTriangle(5));
        System.out.println(pascalTriangle.nthRowOfPascalTriangleSpaceOptimized(5));
        System.out.println(pascalTriangle.nthRowOfPascalTriangleNaive(5));
    }
}

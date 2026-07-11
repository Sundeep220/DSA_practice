package Problems.Arrays.Medium;

public class ProductOfArrayExceptItself {
    // Problem: https://leetcode.com/problems/product-of-array-except-self/?envType=problem-list-v2&envId=array

    /*
     * Brute Force
     *
     * For every index, multiply all other elements.
     *
     * Time Complexity: O(n^2)
     * Space Complexity: O(1) (excluding output array)
     */
    public int[] productExceptSelf(int[] nums) {

        int n = nums.length;
        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {

            int product = 1;

            for (int j = 0; j < n; j++) {

                if (i != j) {
                    product *= nums[j];
                }
            }

            ans[i] = product;
        }

        return ans;
    }

    /*
     * Better Solution - Prefix and Suffix Arrays
     *
     * Compute prefix and suffix products separately.
     * Product except self is prefix[i] * suffix[i].
     *
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int[] productExceptSelfBetter(int[] nums) {

        int n = nums.length;

        int[] prefix = new int[n];
        int[] suffix = new int[n];
        int[] ans = new int[n];

        prefix[0] = 1;
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] * nums[i - 1];
        }

        suffix[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            suffix[i] = suffix[i + 1] * nums[i + 1];
        }

        for (int i = 0; i < n; i++) {
            ans[i] = prefix[i] * suffix[i];
        }

        return ans;
    }

    /*
     * Optimal Solution - Prefix Product + Running Suffix Product
     *
     * Store prefix products in the answer array.
     * Traverse from right to left while maintaining a running
     * suffix product and multiply it with the stored prefix.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1) (excluding output array)
     */
    public int[] productExceptSelfOptimal(int[] nums) {

        int n = nums.length;
        int[] ans = new int[n];

        ans[0] = 1;

        // Store prefix products
        for (int i = 1; i < n; i++) {
            ans[i] = ans[i - 1] * nums[i - 1];
        }

        int suffixProduct = 1;

        // Multiply with suffix products
        for (int i = n - 1; i >= 0; i--) {
            ans[i] *= suffixProduct;
            suffixProduct *= nums[i];
        }

        return ans;
    }
}

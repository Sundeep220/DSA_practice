package Problems.Arrays.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NextPermutation {

    // Brute Force: Generate all possible permutations and find the next permutation
    // Time Complexity: O(n!) Space Complexity: O(n!)
    public static int[] nextPermutation(int[] nums) {
        List<int[]> perms = new ArrayList<>();
        permutations(perms, 0, nums);
        for (int i = 0; i < perms.size(); i++) {
            if (Arrays.equals(perms.get(i), nums)) {
                return perms.get(i + 1);
            }
        }
        // it means that the given array is the last permutation, so we return the first permutation
        return perms.getFirst();
    }

    public static void permutations(List<int[]> perms, int start, int[] currentPerm) {
        if(start == currentPerm.length) {
            perms.add(currentPerm.clone()); // must clone to avoid overwriting
            return;
        }

        for (int i = start; i < currentPerm.length; i++) {
            swap(i, start, currentPerm); // choose
            permutations(perms, start + 1, currentPerm); // explore
            swap(i, start, currentPerm); // un-choose (backtrack)
        }
    }

    public static void swap(int i, int j, int[] arr) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    // Optimal Solution: Greedy Approach
    // Time Complexity: O(n), Space Complexity: O(1)
    public static int[] nextPermutationOptimal(int[] nums) {
        int n = nums.length;
        int i = n - 2;

        // find the first element from right that is smaller than the element next to it
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        // if no such element is found, reverse the whole array
        if (i < 0) {
            reverse(0, n - 1, nums);
            return nums;
        }

        // if such an element is found, find the first element from right that is greater than this pivot
        int j = n - 1;
        while (j >= 0 && nums[i] >= nums[j]) {
            j--;
        }

        // swap these two elements
        swap(i, j, nums);

        // reverse the suffix
        reverse(i + 1, n - 1, nums);
        return nums;
    }

    public static void reverse(int start, int end, int[] arr){
        while(start < end){
            int t = arr[start];
            arr[start] = arr[end];
            arr[end] = t;
            start++; end--;
        }
    }

    public static void main(String[] args) {
        int[] a = {1,2,3};
        System.out.println(Arrays.toString(nextPermutation(a)));
        System.out.println(Arrays.toString(nextPermutationOptimal(a)));
    }
}

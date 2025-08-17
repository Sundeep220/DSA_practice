package Problems.Heaps.Easy;

public class VerifyMinHeap {
    // Problem: Given a array of integers, verify whether it forms a valid binary min heap or not.
    // A valid binary min heap is a binary tree where each node is greater than or equal to its left and right child.
    // Time Complexity: O(n) Space Complexity: O(1)
    public static boolean isValidMinHeap(int[] nums) {
        int n = nums.length;
        for(int i = 0; i <= (n-2)/2; i++) { // n-2/2 is the last parent index of the tree. (n-2)/2 is the last parent index of the half of the tree (left half)
            int left = 2 * i + 1; // left child index of the current node
            int right = 2 * i + 2; // right child index of the current node

            if(left < n && nums[i] > nums[left]) // if the current node is greater than its left child, return false
                return false;
            if(right < n && nums[i] > nums[right]) // if the current node is greater than its right child, return false
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 3, 5, 7, 9, 8};   // valid min-heap
        int[] arr2 = {10, 15, 14, 25, 30}; // valid min-heap
        int[] arr3 = {10, 5, 14, 25, 30};  // not a min-heap

        System.out.println(isValidMinHeap(arr1)); // true
        System.out.println(isValidMinHeap(arr2)); // true
        System.out.println(isValidMinHeap(arr3)); // false
    }
}

package Problems.Arrays.Medium;

import java.util.ArrayList;
import java.util.Arrays;

public class DutchNationalFlagProb {
    // Problem: https://leetcode.com/problems/sort-colors/

    // Brute Force: Sort the array and return
    // Time Complexity: O(n log n)
    // Space Complexity: O(1)

    public static int[] sortColors(int[] nums) {
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }

    private static void merge(int[] arr, int low, int mid, int high) {
        ArrayList<Integer> temp = new ArrayList<>(); // temporary array
        int left = low;      // starting index of left half of arr
        int right = mid + 1;   // starting index of right half of arr

        //storing elements in the temporary array in a sorted manner//

        while (left <= mid && right <= high) {
            if (arr[left] <= arr[right]) {
                temp.add(arr[left]);
                left++;
            } else {
                temp.add(arr[right]);
                right++;
            }
        }

        // if elements on the left half are still left //
        while (left <= mid) {
            temp.add(arr[left]);
            left++;
        }

        //  if elements on the right half are still left //
        while (right <= high) {
            temp.add(arr[right]);
            right++;
        }

        // transferring all elements from temporary to arr //
        for (int i = low; i <= high; i++) {
            arr[i] = temp.get(i - low); // i - low because the index of temp array is 0 based
        }
    }

    public static void mergeSort(int[] arr, int low, int high) {
        if (low >= high) return;
        int mid = low + (high - low) / 2;
        mergeSort(arr, low, mid);  // left half
        mergeSort(arr, mid + 1, high); // right half
        merge(arr, low, mid, high);  // merging sorted halves
    }


    // Optimal: Dutch National Flag Algorithm
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    // Algorithim: We can use Dutch National Flag Algorithm to sort an array of 0s, 1s and 2s.
    // Link: https://takeuforward.org/data-structure/sort-an-array-of-0s-1s-and-2s/, https://www.geeksforgeeks.org/dsa/sort-an-array-of-0s-1s-and-2s/#expected-approach-dutch-national-flag-algorithm-one-pass-on-time-and-o1-space
    // This algorithm contains 3 pointers i.e. low, mid, and high, and 3 main rules.  The rules are the following:
    //  1. arr[0….low-1] contains 0. [Extreme left part]
    //  2. arr[low….mid-1] contains 1.
    //  3. arr[high+1….n-1] contains 2. [Extreme right part], n = size of the array
    //  4. arr[mid+1...high] can be anything. Itis the unsorted segment.

    // Steps:
//    First, we will run a loop that will continue until mid <= high.
//    There can be three different values of mid pointer i.e. arr[mid]
//    If arr[mid] == 0, we will swap arr[low] and arr[mid] and will increment both low and mid. Now the subarray from index 0 to (low-1) only contains 0.
//    If arr[mid] == 1, we will just increment the mid pointer and then the index (mid-1) will point to 1 as it should according to the rules.
//    If arr[mid] == 2, we will swap arr[mid] and arr[high] and will decrement high. Now the subarray from index high+1 to (n-1) only contains 2.
//    In this step, we will do nothing to the mid-pointer as even after swapping, the subarray from mid to high(after decrementing high) might be unsorted. So, we will check the value of mid again in the next iteration.
//    Finally, our array should be sorted.
    public static void sortColorsDutch(int[] nums) {
        int n = nums.length;
        int low = 0, mid = 0, high = n - 1;
        while (mid <= high) {
            if (nums[mid] == 0) {
                int t = nums[low];
                nums[low] = nums[mid];
                nums[mid] = t;
                low++;
                mid++;
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                int t = nums[mid];
                nums[mid] = nums[high];
                nums[high] = t;
                high--;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = { 2, 0, 2, 1, 1, 0 };
        sortColors(arr);
        System.out.println(Arrays.toString(arr));
        sortColorsDutch(arr);
        System.out.println(Arrays.toString(arr));
    }
}

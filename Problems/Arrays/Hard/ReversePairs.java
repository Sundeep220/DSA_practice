package Problems.Arrays.Hard;

public class ReversePairs {
    // Problem: https://leetcode.com/problems/reverse-pairs/
    // Intution: https://takeuforward.org/data-structure/count-reverse-pairs/

    // Brute Force: O(n^2) time | O(1) space
    public static int reversePairsBrute(int[] nums) {
        int count = 0;
        for(int i = nums.length - 1; i > 0; i--) {
            for(int j = i - 1; j >= 0; j--) {
                if(nums[j] > 2 * nums[i]) {
                    count++;
                }
            }
        }
        return count;
    }

    // Optimal Solution: Using Merge Sort and Counting Inversions
    // Time Complexity: O(nlogn)
    // Space Complexity: O(n)

    public static int reversePairsOptimal(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    public static int mergeSort(int[] nums, int start, int end) {
        int count = 0;
        if(start >= end) return count;
        int mid = start + (end - start) / 2;
        count += mergeSort(nums, start, mid);
        count += mergeSort(nums, mid + 1, end);
        // before merging, count the pairs
        count += countPairs(nums, start, mid, end);

        // then merge
        merge(nums, start, mid, end);
        return count;
    }

    public static int countPairs(int[] nums, int start, int mid, int end) {
        int count = 0, j = mid + 1;
        for(int i = start; i <= mid; i++) {
            while(j <= end && nums[i] > 2 * nums[j]) {
                // move until nums[j] is less than 2 * nums[i], this gives count of numbers for future refercnce,
                // for eg: [6, 13, 24]  and [1,2,3,5,7] if 6 -> {1, 2} then 13 and 24 will definitetly have {1,2} as smaller number 6
                // is greater than 2 * 1 & 2 *2 .
                j++;
            }
            count += j - mid - 1;  // j - (mid + 1)
        }
        return count;
    }

    public static void merge(int[] nums, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];
        int i = start, j = mid + 1, k = 0;
        while(i <= mid && j <= end) {
            if(nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }
        while(i <= mid) {
            temp[k++] = nums[i++];
        }
        while(j <= end) {
            temp[k++] = nums[j++];
        }
        for(int l = start; l <= end; l++) {
            nums[l] = temp[l - start];
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 2, 3, 1};
        System.out.println(reversePairsBrute(nums));
        System.out.println(reversePairsOptimal(nums));
    }
}

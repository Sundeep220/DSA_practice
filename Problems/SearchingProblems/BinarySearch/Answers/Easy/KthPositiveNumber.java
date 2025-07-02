package Problems.SearchingProblems.BinarySearch.Answers.Easy;
import java.util.*;
public class KthPositiveNumber {
    // https://leetcode.com/problems/kth-missing-positive-number/

    //Brute Force:
    public int findKthPositive(int[] arr, int k) {
        int missingCount = 0;
        int num = 1;
        int i = 0;
        while (true) {
            if (i < arr.length && arr[i] == num) {
                i++;
            } else {
                missingCount++;
                if (missingCount == k) {
                    return num;
                }
            }
            num++;
        }
    }

    // Brute Force: Using Set
    public int findKthPositiveSet(int[] arr, int k) {
        Set<Integer> set = new HashSet<>();
        for (int num : arr) set.add(num);

        int missing = 0;
        int i = 1;
        while (true) {
            if (!set.contains(i)) {
                missing++;
                if (missing == k) return i;
            }
            i++;
        }
    }

    // Brute Force: Using Observation
    // for each element in arary:
    // if a[i] <= k, k++
    // else return k as this is the kth missing number
    public int findKthPositiveObservation(int[] arr, int k) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] <= k) k++;
            else return k;
        }
        return k;
    }

    // Using Binary Search
    // Intution: for each index i in the array:
    // for eg: a = [2,3,4,7,11]
    // the total missing numbers = a[i] - (i+1), eg: for 2, total missing numbers till 2 => 2 - (0+1) = 1, and  we know that one number is 1
    // so for each element we calculate total missing numbers till that element
    // and apply binary search such that:
    // for mid:
    // 1. if mid - (mid+1) < k, then left = mid+1
    // 2. else right = mid-1
    // finally after ending the loop, we will have high pointing to number to which we need to add to get kth missing number
    // and low will be at high + 1 position
    // so to get kth missing number, we calculate it as this:
    // kth missing number = a[high] + (k - (a[high] - (high+1))) => high + 1 + k => low + k (As low = high + 1)
    public int findKthPositiveBinarySearch(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] - (mid + 1) < k) {  // calculating total missing numbers till mid
                left = mid + 1;    // for eg: for 2 in arr, and k = 5, total missing numbers till 2 => 2 - (0+1) = 1 and 1 < 5, we need to increase left as 5th element will be after 2
            } else {
                right = mid - 1;
            }
        }
        return left + k;
    }

    public static void main(String[] args) {
        int[] arr = {2, 3, 4, 7, 11};
        int k = 5;
        System.out.println(new KthPositiveNumber().findKthPositive(arr, k));
        System.out.println(new KthPositiveNumber().findKthPositiveSet(arr, k));
        System.out.println(new KthPositiveNumber().findKthPositiveObservation(arr, k));
        System.out.println(new KthPositiveNumber().findKthPositiveBinarySearch(arr, k));
    }

}

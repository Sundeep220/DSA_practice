package Problems.Arrays.Hard;

import java.util.ArrayList;

public class CountingInversions {
    // Problem: https://www.geeksforgeeks.org/dsa/inversion-count-in-array-using-merge-sort/
    // https://www.geeksforgeeks.org/problems/inversion-of-array-1587115620/1

    // Brute Force: O(n^2) time | O(1) space
    public static int countInversionsBrute(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    count++;
                }
            }
        }
        return count;
    }

    // Optimal Solution: O(nlogn) time | O(n) space
    // Using Merge Sort but adding small change to it

    public static int countInversionsOptimal(int[] arr) {
        return mergeSort(arr, 0, arr.length - 1);
    }

    public static int mergeSort(int[] arr, int low, int high) {
        int count = 0;
        if(low >= high) return count;
        int mid = low + (high - low) / 2;
        count += mergeSort(arr, low, mid);
        count += mergeSort(arr, mid + 1, high);
        count += merge(arr, low, mid, high);
        return count;
    }

    public static int merge(int[] arr, int low, int mid, int high) {
        int count = 0;
        ArrayList<Integer> temp = new ArrayList<>();
        int left = low;
        int right = mid + 1;
        while (left <= mid && right <= high) {
            if (arr[left] <= arr[right]) {
                temp.add(arr[left]);
                left++;
            } else {
                temp.add(arr[right]);
                right++;
                count += mid - left + 1;
            }
        }
        
        for (int i = low; i <= high; i++) {
            arr[i] = temp.get(i - low);
        }
        return count;
    }

    public static void main(String[] args) {
        int[] arr = {5,4,3,2,1};
        System.out.println(countInversionsBrute(arr));
        System.out.println(countInversionsOptimal(arr));
    }
}

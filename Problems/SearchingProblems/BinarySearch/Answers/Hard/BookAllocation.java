package Problems.SearchingProblems.BinarySearch.Answers.Hard;

import java.util.Arrays;
import java.util.Collections;

public class BookAllocation {
    // Problem: https://www.naukri.com/code360/problems/allocate-books_1090540

    // Brute Force:
    public static int findPages(int[] arr, int n, int m) {
        int books = arr.length;
        if(books < m) {
            return -1;
        }
        int minMaxPages = Arrays.stream(arr).max().getAsInt();
        int maxPagesAllocated = Arrays.stream(arr).sum();

        for(int i = minMaxPages; i <= maxPagesAllocated; i++) {
            if(countPages(arr, i) <= m) {
                return i;
            }
        }

        return -1;

    }

    public static int countPages(int[] arr, int pages) {
        int countStudents = 1;
        int pagesAllocated = 0;
        for (int j : arr) {
            if (pagesAllocated + j > pages) {
                countStudents++;   // give this book to next student
                pagesAllocated = j;
            } else {
                pagesAllocated += j; // add this book to current student
            }
        }
        return countStudents;
    }

    // Optimal Solution: Using Binary Search
    // Time Complexity: O(nlogm) time | O(1) space
    public static int findPagesOptimal(int[] arr, int n, int m) {
        if(n < m) return -1;
        int minMaxPages = Arrays.stream(arr).max().getAsInt();
        int maxPagesAllocated = Arrays.stream(arr).sum();
        int ans = 0;
        while(minMaxPages <= maxPagesAllocated) {
            int mid = (minMaxPages + maxPagesAllocated) / 2;
            if(countPages(arr, mid) <= m) {
                ans = mid;
                maxPagesAllocated = mid - 1;
            } else {
                minMaxPages = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {12, 34, 67, 90};
        int m = 2;
        System.out.println(findPages(arr, arr.length, m));
        System.out.println(findPagesOptimal(arr, arr.length, m));
    }
}

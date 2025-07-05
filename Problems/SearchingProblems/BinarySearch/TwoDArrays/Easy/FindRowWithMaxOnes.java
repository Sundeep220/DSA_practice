package Problems.SearchingProblems.BinarySearch.TwoDArrays.Easy;

public class FindRowWithMaxOnes {
    // Problem: https://leetcode.com/problems/row-with-maximum-ones/description/

    //Brute Force: O(m*n) time | O(1) space
    public static int findRowWithMaxOnesBrute(int[][] grid){
        int row = -1, maxOnes = 0;
        for(int i = 0; i < grid.length; i++){
            int countOnes = 0;
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j] == 1) countOnes++;
            }
            if(countOnes > maxOnes) {
                maxOnes = countOnes;
                row = i;
            }
        }
        return row;
    }

    // Optimal Solution: Binary Search
    // Time: O(m*logn) time | O(1) space
    // We can think of this problem as counting occurrences of 1s in each row
    public static int findRowWithMaxOnesOptimal(int[][] grid){
        int row = -1, maxOnes = 0;
        for(int i = 0; i < grid.length; i++){
            int countOnes = countOccurrences(grid[i], 1);
            if(countOnes > maxOnes) {
                maxOnes = countOnes;
                row = i;
            }
        }
        return row;
    }

    public static int countOccurrences(int[] arr, int target){
        int firstIndex = findFirstOccurrence(arr, target);
        if(firstIndex == -1) return 0;
        int lastIndex = findLastOccurrence(arr, target);
        return lastIndex - firstIndex + 1;
    }

    public static int findFirstOccurrence(int[] arr, int target){
        int low = 0, high = arr.length - 1, ans = -1;
        while(low <= high){
            int mid = low + (high - low) / 2;
            if(arr[mid] < target) low = mid + 1;
            else if(arr[mid] > target) high = mid - 1;
            else {
                ans = mid;
                high = mid - 1;
            }
        }
        return ans;
    }

    public static int findLastOccurrence(int[] arr, int target){
        int low = 0, high = arr.length - 1, ans = -1;
        while(low <= high){
            int mid = low + (high - low) / 2;
            if(arr[mid] < target) low = mid + 1;
            else if(arr[mid] > target) high = mid - 1;
            else {
                ans = mid;
                low = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] grid = {{0,0,0,1},{0,0,1,1},{0,1,1,1}}; // Output: 2
        System.out.println(findRowWithMaxOnesBrute(grid));
        System.out.println(findRowWithMaxOnesOptimal(grid));
    }
}

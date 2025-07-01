package Problems.SearchingProblems.BinarySearch.Arrays.Medium;

public class FloorAndCeil {
    //Problem: https://www.naukri.com/code360/problems/ceiling-in-a-sorted-array_1825401

    public static int[] findFloorAndCeil(int[] arr, int target) {
        int n = arr.length;
        int floor = -1, ceil = -1;
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                floor = ceil = arr[mid];
                break;
            } else if (arr[mid] < target) {
                low = mid + 1;
                floor = arr[mid];
            } else {
                high = mid - 1;
                ceil = arr[mid];
            }
        }
        return new int[] { floor, ceil };
    }

    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 4, 5, 7, 8, 9, 10 };
        int target = 6;
        int[] result = findFloorAndCeil(arr, target);
        System.out.println("Floor: " + result[0]);
        System.out.println("Ceil: " + result[1]);
    }
}

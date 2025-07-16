package Problems.Recursion.Easy;

public class BinarySearch {

    public static int binarySearch(int[] arr, int target, int start, int end) {
        if (start > end) return -1;
        int mid = start + (end - start) / 2;
        if (arr[mid] == target) return mid;
        if (arr[mid] < target) return binarySearch(arr, target, mid + 1, end);
        return binarySearch(arr, target, start, mid - 1);
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 7;
        System.out.println(binarySearch(arr, target, 0, arr.length - 1));
    }
}

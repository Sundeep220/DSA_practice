package Problems.Recursion.Easy;

public class CheckIfArraySorted {
    public static boolean checkSorted(int[] arr, int idx) {
        if(idx == arr.length - 1) return true;
        return arr[idx] < arr[idx + 1] && checkSorted(arr, idx + 1);
    }

    public static boolean checkSortedDesc(int[] arr, int idx) {
        if(idx == arr.length - 1) return true;
        return arr[idx] > arr[idx + 1] && checkSortedDesc(arr, idx + 1);
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        int[] arr2 = {1, 3, 2, 4, 5};
        int[] arr3 = {5, 4, 3, 2, 1};
        System.out.println(checkSorted(arr, 0));
        System.out.println(checkSorted(arr2, 0));
        System.out.println(checkSorted(arr3, 0));
        System.out.println(checkSortedDesc(arr, 0));
        System.out.println(checkSortedDesc(arr2, 0));
        System.out.println(checkSortedDesc(arr3, 0));
    }
}

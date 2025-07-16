package Problems.Recursion.Easy;

public class FindMax {
    public static int findMax(int[] arr, int idx) {
        if(idx == arr.length - 1) return arr[idx];
        return Math.max(arr[idx], findMax(arr, idx + 1));
    }
}

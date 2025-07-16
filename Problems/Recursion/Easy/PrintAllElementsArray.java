package Problems.Recursion.Easy;

public class PrintAllElementsArray {
    public static void print(int[] arr, int idx) {
        if (idx == arr.length) return;
        System.out.print(arr[idx] + " ");
        print(arr, idx + 1);
    }
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        print(arr, 0);
    }
}

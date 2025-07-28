package Problems.Strings.Medium;

public class NextGreaterElementIII {
    // https://leetcode.com/problems/next-greater-element-iii/
    public int nextGreaterElement(int d) {
        String num = Integer.toString(d);
        int[] arr = new int[num.length()];
        for (int i = 0; i < num.length(); i++)
            arr[i] = Character.getNumericValue(num.charAt(i));

        int n = arr.length;
        int i = n - 2;
        // find first element from right that is smaller than its next
        while (i >= 0 && arr[i] >= arr[i + 1]) {
            i--;
        }

        // find the smallest element from right that is greater than this arr[i]
        if (i >= 0) {
            int j = n - 1;
            while (arr[j] <= arr[i])
                j--;

            swap(arr, i, j);
        }

        if(i < 0)
            return -1;

        reverse(arr, i + 1, n - 1);

        long answer = 0;
        for (int digit : arr) {
            answer = answer * 10 + digit;
            if (answer > Integer.MAX_VALUE) return -1;  // overflow check
        }
        return (int)answer;
    }

    public void reverse(int[] arr, int start, int end) {
        while (start < end)
            swap(arr, start++, end--);
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

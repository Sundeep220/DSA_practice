package Problems.StacksAndQueue.Easy;

import java.util.Arrays;
import java.util.Stack;

public class NextGreaterElemetnII {
    // Note: Here given is circular array
    public static int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            res[i] = -1;  // default
            for (int j = i+1; j < i +n; j++) {
                int nextIndex = (j) % n;
                if (nums[nextIndex] > nums[i]) {
                    res[i] = nums[nextIndex];
                    break;
                }
            }
        }
        return res;
    }

    // Using Monotonic Stack
    public static int[] nextGreaterElementsOptimal(int[] nums) {
        int n = nums.length;
        int[] nge = new int[n];
        Stack< Integer > st = new Stack < > ();
        for (int i = 2 * n - 1; i >= 0; i--) {
            while (!st.isEmpty() && st.peek() <= nums[i % n]) {
                st.pop();
            }

            if (i < n) {
               nge[i] = st.isEmpty() ? -1 : st.peek();
            }

            st.push(nums[i % n]);
        }
        return nge;
    }



    public static void main(String[] args) {
        int[] nums = { 1, 2, 1 };
        System.out.println(Arrays.toString(nextGreaterElements(nums)));
        System.out.println(Arrays.toString(nextGreaterElementsOptimal(nums)));
    }
}

package Problems.LogicBuilding.Medium;

public class NextgreaterElement {
    //Problem: https://leetcode.com/problems/next-greater-element-iii/description/
    // Time Complexity: O(n)
    // Space Complexity: O(n)

        public int nextGreaterElement(int d) {
            String num = Integer.toString(d);
            int[] arr = new int[num.length()];
            for (int i = 0; i < num.length(); i++)
                arr[i] = Character.getNumericValue(num.charAt(i));

            int n = arr.length;
            int i = n - 2;

            while (i >= 0 && arr[i] >= arr[i + 1]) {
                i--;
            }

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

    public static void main(String[] args) {
        int d = 21;
        System.out.println(new NextgreaterElement().nextGreaterElement(d)); // Outputs: -1 (No next greater element)
    }
}

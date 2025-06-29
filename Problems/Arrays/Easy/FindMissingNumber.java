package Problems.Arrays.Easy;

public class FindMissingNumber {

    // Optimal Solution1: using Sum of natural numbers formula
    public static int findMissingNumber(int[] nums) {
        int n = nums.length;
        int sum = (n * (n + 1)) / 2;
        int arrSum = 0;
        for (int num : nums) {
            arrSum += num;
        }
        return sum - arrSum;
    }
    // Optimal Solution2: using XOR
    public static int findMissingNumber2(int[] nums) {
        int n = nums.length;
        int xor1= 0, xor2 = 0;
        for (int i = 0; i < n; i++) {
            xor1 ^= nums[i];   // xor1 = xor1 ^ nums[i]; XOR of array elements
            xor2 ^= (i + 1);  // xor2 = xor2 ^ (i + 1); XOR of natural numbers [1...n]
        }

        return xor1 ^ xor2;
    }


    public static void main(String[] args) {
        int[] nums = { 9, 6, 4, 2, 3, 5, 7, 0, 1, 3 };
        System.out.println("Using Sum of natural numbers formula: " + findMissingNumber(nums));
        System.out.println("Using XOR: " + findMissingNumber2(nums));
    }
}

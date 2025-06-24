package Problems.Arrays.Easy;

public class CountConsecutiveZeroesOrOnes {
    public static int countConsecutiveZeroes(int[] nums) {
        int count = 0, max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                count++;
            } else {
                count = 0;
            }
            if (count > max) {
                max = count;
            }
        }
        return max;
    }

    public static int countConsecutiveOnes(int[] nums) {
        int count = 0, max = 0;
        for (int num : nums) {
            if (num == 1) {
                count++;
            } else {
                count = 0;
            }
            if (count > max) {
                max = count;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1 };
        System.out.println("Consecutive Zeroes: " + countConsecutiveZeroes(nums));
        System.out.println("Consecutive Ones: " + countConsecutiveOnes(nums));
    }

}

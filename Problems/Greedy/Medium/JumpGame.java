package Problems.Greedy.Medium;

public class JumpGame {
    // Problem: https://leetcode.com/problems/jump-game/

    // Brute Force: Try out all possible jump lengths for each element
    public boolean canJumpBrute(int[] nums) {
        return canReach(nums, 0);
    }

    private boolean canReach(int[] nums, int pos) {
        // Base case: reached last index
        if (pos >= nums.length - 1) return true;

        int furthestJump = Math.min(pos + nums[pos], nums.length - 1);
        for (int next = pos + 1; next <= furthestJump; next++) {
            if (canReach(nums, next)) return true;
        }
        return false;
    }

    // Optimal Solution: Greedy
    // Time Complexity: O(n) time | O(1) space
    public boolean canJumpOptimal(int[] nums) {
        int lastPos = nums.length - 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (i + nums[i] >= lastPos) {
                lastPos = i;
            }
        }
        return lastPos == 0;
    }

    // Another way of writing Greedy solution
    public boolean canJumpOptimal2(int[] nums) {
        int maxReachIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > maxReachIndex) return false;
            maxReachIndex = Math.max(maxReachIndex, i + nums[i]);
        }
        return true;
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 1, 4};
        System.out.println(new JumpGame().canJumpBrute(nums));
        System.out.println(new JumpGame().canJumpOptimal(nums));
        System.out.println(new JumpGame().canJumpOptimal2(nums));
    }
}

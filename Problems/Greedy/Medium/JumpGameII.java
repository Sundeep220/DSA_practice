package Problems.Greedy.Medium;

import java.util.Arrays;

public class JumpGameII {
    // Problem: https://leetcode.com/problems/jump-game-ii/

    // Brute Force: Try out all possible jump lengths for each element
    // Time Complexity: O(2^n) time | O(n) space
    public int jumpBrute(int[] nums) {
        return jump(nums, 0, 0);
    }

    public int jump(int[] nums, int pos, int jumps) {
        if(pos >= nums.length-1) return jumps;

        int furthestJump = Math.min(pos + nums[pos], nums.length-1);
        int minJumps = Integer.MAX_VALUE;
        for(int next = pos + 1; next <= furthestJump; next++) {
            minJumps = Math.min(minJumps, jump(nums, next, jumps + 1));
        }
        return minJumps;
    }

    // Better Solution: DP Memoization
    // Time Complexity: O(n^2) time | O(n) space
    public int jumpDP(int[] nums) {
        int[][] dp = new int[nums.length][nums.length + 1];
        for(int[] row : dp)
            Arrays.fill(row, -1);
        return jumpsDP(nums, 0, 0, dp);
    }

    public int jumpsDP(int[] nums, int pos, int jumps, int[][] dp) {
        if(pos >= nums.length-1) return jumps;

        if(dp[pos][jumps] != -1) return dp[pos][jumps];

        int furthestJump = Math.min(pos + nums[pos], nums.length-1);
        int minJumps = Integer.MAX_VALUE;
        for(int next = pos + 1; next <= furthestJump; next++) {
            minJumps = Math.min(minJumps, jumpsDP(nums, next, jumps + 1, dp));
        }
        dp[pos][jumps] = minJumps;
        return minJumps;
    }

    // Optimal Solution: Greedy
    // Time Complexity: O(n) time | O(1) space
    public int jump(int[] nums) {
        int jumps = 0;
        int end = 0;
        int farthest = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (i == end) {  // need to make a jump
                jumps++;
                end = farthest;
            }
        }
        return jumps;
    }

    // ANother way of writing Greedy solution
    public int jump2(int[] nums) {
        int jumps = 0;
        int l = 0, r = 0;  // l = left, r = right, we are defining range [l, r] to which we can jump from current position
        while(r < nums.length - 1) {
            int farthest = 0;
            for(int i = l; i <= r; i++) {
                farthest = Math.max(farthest, i + nums[i]);
            }
            l = r + 1;
            r = farthest;
            jumps++;
        }
        return jumps;
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 1, 4};
        System.out.println(new JumpGameII().jumpBrute(nums));
        System.out.println(new JumpGameII().jumpDP(nums));
        System.out.println(new JumpGameII().jump(nums));
    }
}

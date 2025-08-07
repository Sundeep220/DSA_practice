package Problems.SlidingWindowAndTwoPointers.Medium;

public class MaximumPointsYouCanTakeFromCards {
    // Problem: https://leetcode.com/problems/maximum-points-you-can-take-from-cards/

    // Brute Force: O(n^2) time | O(1) space

    public static int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int leftSum = 0;
        int rightSum = 0;
        int maxSum = 0;

        for (int i = 0; i < k; i++) {
            leftSum += cardPoints[i];
        }
        maxSum = leftSum;

        int right = n - 1;
        for (int left = k - 1; left >= 0; left--) {
            leftSum -= cardPoints[left];
            rightSum += cardPoints[right--];
            maxSum = Math.max(maxSum, leftSum + rightSum);
        }

        return maxSum;
    }
}

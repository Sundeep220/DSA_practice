package Problems.Greedy.Medium;

public class ContainerWithMostWater {
    // Problem: https://leetcode.com/problems/container-with-most-water/description/?envType=problem-list-v2&envId=array

    /*
     * Brute Force
     *
     * Check every possible pair of lines and calculate
     * the area formed by them.
     *
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    public int maxArea(int[] height) {

        int n = height.length;
        int maxArea = 0;

        for (int i = 0; i < n; i++) {

            for (int j = i + 1; j < n; j++) {

                int width = j - i;
                int currArea = Math.min(height[i], height[j]) * width;

                maxArea = Math.max(maxArea, currArea);
            }
        }

        return maxArea;
    }

    /*
     * Optimal Solution - Two Pointers
     *
     * Start with the widest container.
     * Calculate the current area and move the pointer
     * pointing to the smaller height, since moving the taller
     * one cannot increase the area.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int maxAreaOptimal(int[] height) {

        int left = 0;
        int right = height.length - 1;

        int maxArea = 0;

        while (left < right) {

            int width = right - left;
            int currArea = Math.min(height[left], height[right]) * width;

            maxArea = Math.max(maxArea, currArea);

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}

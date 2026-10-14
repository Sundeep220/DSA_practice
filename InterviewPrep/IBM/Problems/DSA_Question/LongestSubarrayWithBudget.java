public class LongestSubarrayWithBudget {

    public int longestSubarray(int[] costs, int budget) {
        int left = 0;
        long windowSum = 0;
        int maxLength = 0;

        for (int right = 0; right < costs.length; right++) {
            windowSum += costs[right];

            while (windowSum > budget) {
                windowSum -= costs[left];
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(new LongestSubarrayWithBudget().longestSubarray(new int[]{3, 1, 2, 4, 1}, 6));
    }
}

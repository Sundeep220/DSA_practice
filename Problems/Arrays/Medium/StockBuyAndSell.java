package Problems.Arrays.Medium;

public class StockBuyAndSell {
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

    // Brute Force: Calculation profit for all possible combinations and find the maximum profit
    // Time Complexity: O(n^2) Space Complexity: O(1)
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        for(int i=0; i<prices.length; i++) {
            for(int j=i+1; j<prices.length; j++) {
                if(prices[j] > prices[i]) {
                    maxProfit = Math.max(maxProfit, prices[j] - prices[i]);
                }
            }
        }
        return maxProfit;
    }

    // Optimal Solution: We maintain a minimum element from start index,
    // and compare it with next elements, if next element is greater than minimum element,
    // then calculate profit and update the maximum profit. Otherwise, update the minimum element.
    public int maxProfitOptimal(int[] prices) {
        int minPrice = Integer.MAX_VALUE, maxProfit = 0;
        for (int price : prices) {
            minPrice = Math.min(minPrice, price);
            maxProfit = Math.max(maxProfit, price - minPrice);
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        int[] nums = {7,1,5,3,6,4};
        StockBuyAndSell obj = new StockBuyAndSell();
        System.out.println("Brute Force Solution: " + obj.maxProfit(nums));
        System.out.println("Optimal Solution: " + obj.maxProfitOptimal(nums));
    }
}

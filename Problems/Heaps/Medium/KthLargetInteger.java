package Problems.Heaps.Medium;

import java.util.Arrays;
import java.util.PriorityQueue;

public class KthLargetInteger {
    // Problem: https://leetcode.com/problems/find-the-kth-largest-integer-in-the-array/description/

    // using min heap
    public static String kthLargestNumber(String[] nums, int k) {
        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> {
            if (a.length() != b.length()) {
                return Integer.compare(a.length(), b.length()); // shorter → smaller
            }
            return a.compareTo(b); // lexicographic compare if same length
        });

        for (String num : nums) {
            pq.offer(num);
            if (pq.size() > k) {
                pq.poll(); // remove smallest
            }
        }

        return pq.peek(); // kth largest
    }

    // Faster: Sort the arrays and return the kth largest element
    public static String kthLargestNumberFaster(String[] nums, int k) {
        Arrays.sort(nums, (a, b) ->{
            if(a.length() != b.length())
                return Integer.compare(a.length(), b.length()); // shorter → smaller
            return a.compareTo(b);
        });
        return nums[nums.length - k];
    }

    public static void main(String[] args) {
        String[] nums = {"3", "6", "7", "10"};
        int k = 4;
        System.out.println(kthLargestNumber(nums, k));
    }
}

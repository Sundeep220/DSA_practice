package Problems.Heaps.Hard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class MedianFinder {
    // Problem: https://leetcode.com/problems/find-median-from-data-stream/

    // Brute Force: Simply use a list and at every insertion sort it.
    // Time Complexity: O(n log n)
    // Space Complexity: O(n)
    static class MedianFinderBrute {
        List<Integer> nums;
        public MedianFinderBrute() {
            nums = new ArrayList<>();
        }

        public void addNum(int num) {
            nums.add(num);
            Collections.sort(nums); // O(n log n) each time
        }

        public double findMedian() {
            int n = nums.size();
            if (n % 2 == 1) {
                return nums.get(n / 2);
            } else {
                return (nums.get(n / 2 - 1) + nums.get(n / 2)) / 2.0;
            }
        }
    }

    // Better Solution: Insert in sorted order, and evrytime at insertion use binary search
    // to find the best spot for that nums
    // Time Complexity: O(n + log n)
    // Space Complexity: O(n)
    static class MedianFinderBetter {
        private List<Integer> list;

        public MedianFinderBetter() {
            list = new ArrayList<>();
        }

        // O(n) insertion
        public void addNum(int num) {
            int idx = Collections.binarySearch(list, num);  // if not found, returns foundIdx = -(insertion_position) - 1
            if (idx < 0) {
                idx = -(idx + 1); // find insertion point
            }
            list.add(idx, num); // O(n) shift
        }

        // O(1) median
        public double findMedian() {
            int n = list.size();
            if (n % 2 == 1) {
                return list.get(n / 2);
            } else {
                return (list.get(n / 2 - 1) + list.get(n / 2)) / 2.0;
            }
        }
    }

    // Optimal Solution: Using two heaps
    // Intuition
    //Median splits numbers into two halves:
        //1. Left half ≤ median
        //2. Right half ≥ median
    //So we maintain:
        //- A max-heap (left) for the smaller half.
        //- A min-heap (right) for the larger half.
    //Steps
        //- Always balance the two heaps such that:
        //- Either both heaps have equal size, OR
        //- Left heap has 1 more element than right.
    //Median:
        //- If sizes equal → (max(left) + min(right)) / 2
        //- If left bigger → max(left)
    static class MedianFinderOptimal{
        private final PriorityQueue<Integer> left;   // max-heap
        private final PriorityQueue<Integer> right;  // min-heap

        public MedianFinderOptimal() {
            left = new PriorityQueue<>(Collections.reverseOrder());
            right = new PriorityQueue<>();
        }

        public void addNum(int num) {
            // Step 1: Add to left (max-heap)
            left.offer(num);

            // Step 2: Balance - largest of left should go to right
            right.offer(left.poll());

            // Step 3: Ensure left has equal or +1 more element than right
            if (left.size() < right.size()) {
                left.offer(right.poll());
            }
        }

        public double findMedian() {
            if (left.size() > right.size()) {
                return left.peek(); // odd case
            } else {
                return (left.peek() + right.peek()) / 2.0; // even case
            }
        }
    }
}

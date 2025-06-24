package Problems.Arrays.Easy;
import java.util.*;
public class Intersection {
    // Using HashSet
    // Time Complexity: O(n + m), Space Complexity: O(n + m)
    public static int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> set1 = new HashSet<>();  // to store nums1
        HashSet<Integer> set2 = new HashSet<>(); // to store intersection
        for (int num : nums1) {
            set1.add(num);  // add nums1 to set
        }
        for (int num : nums2) {
            if (set1.contains(num)) {  // adding elements only those elements which are present in nums1
                set2.add(num);
            }
        }
        int[] res = new int[set2.size()];
        int index = 0;
        for (int num : set2) {
            res[index++] = num;
        }
        return res;
    }
    // Using Two Pointers
    // Time Complexity: O(n + m), Space Complexity: O(1)
    public static int[] intersectionOptimal(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0, j = 0;
        int[] res = new int[Math.min(nums1.length, nums2.length)];
        int index = 0;
        while (i < nums1.length && j < nums2.length) {
            // Skip duplicates in nums1
            if (i > 0 && nums1[i] == nums1[i - 1]) {
                i++;
                continue;
            }

            if (nums1[i] == nums2[j]) {
                res[index++] = nums1[i++];
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else {
                j++;
            }
        }
        return Arrays.copyOfRange(res, 0, index);
    }

    public static void main(String[] args) {
        int[] nums1 = { 1, 2, 2, 1 };
        int[] nums2 = { 2, 1, 2};
        int[] res = intersection(nums1, nums2);
        System.out.println("Brute Force Solution: " + Arrays.toString(res));
        System.out.println("Optimal Solution: " + Arrays.toString(intersectionOptimal(nums1, nums2)));
    }
}

package Problems.Arrays.Hard;
import java.util.*;
public class ThreeSum {
    // Problem: https://leetcode.com/problems/3sum/

    // Brute Force:
    // Time Complexity: O(n^3)
    // Space Complexity: O(1)
    public static List<List<Integer>> threeSumNaive(int[] nums) {
        Set<List<Integer>> res = new HashSet<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> triplet = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(triplet);  // To handle duplicate triplets
                        res.add(triplet);           // Set ensures uniqueness
                    }
                }
            }
        }

        return new ArrayList<>(res);
    }

    // Better Solution:
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static List<List<Integer>> threeSumSet(int n, int[] arr) {
        Set<List<Integer>> st = new HashSet<>();

        for (int i = 0; i < n; i++) {
            Set<Integer> hashset = new HashSet<>();
            for (int j = i + 1; j < n; j++) {
                //Calculate the 3rd element:
                int third = -(arr[i] + arr[j]);

                //Find the element in the set:
                if (hashset.contains(third)) {
                    List<Integer> temp = Arrays.asList(arr[i], arr[j], third);
                    temp.sort(Integer::compareTo);
                    st.add(temp);
                }
                hashset.add(arr[j]);
            }
        }

        // store the set elements in the answer:
        return new ArrayList<>(st);
    }


    // Optimal Solution: We were using HashSet to store the unique triplets
    // in sorted order, so we can use two pointers to find the unique triplets
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static List<List<Integer>> threeSumOptimal(int n, int[] arr) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(arr);

        for (int i = 0; i < n; i++) {
            //remove duplicates:
            if (i != 0 && arr[i] == arr[i - 1]) continue;

            //moving 2 pointers:
            int j = i + 1;
            int k = n - 1;
            while (j < k) {
                int sum = arr[i] + arr[j] + arr[k];
                if (sum < 0) {  // we need greater elements , why are we moving j and not k? as we have sorted the array, so increase sum, we need to increase j as k is already pointing to current largest element
                    j++;
                } else if (sum > 0) {  // we need smaller elements
                    k--;
                } else {
                    // we found our triplet
                    List<Integer> temp = Arrays.asList(arr[i], arr[j], arr[k]);
                    ans.add(temp);
                    j++;
                    k--;
                    //skip the duplicates:
                    while (j < k && arr[j] == arr[j - 1]) j++;
                    while (j < k && arr[k] == arr[k + 1]) k--;
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {-1, 0, 1, 2, -1, -4};
        System.out.println(threeSumNaive(arr));
        System.out.println(threeSumSet(arr.length, arr));
        System.out.println(threeSumOptimal(arr.length, arr));

    }

}

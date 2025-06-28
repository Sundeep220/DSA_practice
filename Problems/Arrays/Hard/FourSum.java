package Problems.Arrays.Hard;
import java.util.*;
public class FourSum {
    // Problem: https://leetcode.com/problems/4sum/
    // Brute Force: O(n^4) time | O(1) space
    public static List<List<Integer>> fourSumBrute(int[] nums, int target) {
        int n = nums.length; // size of the array
        Set<List<Integer>> set = new HashSet<>();

        // checking all possible quadruplets:
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        // taking bigger data type
                        // to avoid integer overflow:
                        long sum = (long)nums[i] + nums[j];
                        sum += nums[k];
                        sum += nums[l];

                        if (sum == target) {
                            List<Integer> temp = Arrays.asList(nums[i], nums[j], nums[k], nums[l]);
                            Collections.sort(temp);
                            set.add(temp);
                        }
                    }
                }
            }
        }
        List<List<Integer>> ans = new ArrayList<>(set);
        return ans;
    }


    // Better Solution: O(n^3) time | O(n) space using Set
    public static List<List<Integer>> fourSumBetter(int[] nums, int target) {
        int n = nums.length; // size of the array
        Set<List<Integer>> st = new HashSet<>();

        // checking all possible quadruplets:
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Set<Long> hashset = new HashSet<>();
                for (int k = j + 1; k < n; k++) {
                    // taking bigger data type
                    // to avoid integer overflow:
                    long sum = nums[i] + nums[j];
                    sum += nums[k];
                    long fourth = target - sum;
                    if (hashset.contains(fourth)) {
                        List<Integer> temp = new ArrayList<>();
                        temp.add(nums[i]);
                        temp.add(nums[j]);
                        temp.add(nums[k]);
                        temp.add((int) fourth);
                        temp.sort(Integer::compareTo);
                        st.add(temp);
                    }
                    // put the kth element into the hashset:
                    hashset.add((long) nums[k]);
                }
            }
        }
        List<List<Integer>> ans = new ArrayList<>(st);
        return ans;
    }

    // Solution: O(n^3) time | O(1) space
    public List<List<Integer>> fourSum(int[] arr, int target) {
        int n = arr.length;
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(arr);

        for (int i = 0; i < n - 3; i++) {

            long target_3 = target - arr[i]; // 3 sum

            for (int j = i + 1; j < n - 2; j++) {

                long target_2 = target_3 - arr[j]; // 2 sum

                int left = j + 1;
                int right = n - 1;

                while (left < right) {
                    long sum = arr[left] + arr[right];

                    if (sum < target_2) {
                        left++;
                    } else if (sum > target_2) {
                        right--;
                    } else {
                        List<Integer> subList = new ArrayList<>();
                        subList.add(arr[i]);
                        subList.add(arr[j]);
                        subList.add(arr[left]);
                        subList.add(arr[right]);
                        list.add(subList);//Adding sublist into list of lists

                        // Processing the duplicates of 2 sum
                        while (left < right && arr[left] == arr[left + 1])
                            left++;
                        left++;

                        // Processing the duplicates of 2 sum
                        while (left < right && arr[right] == arr[right - 1])
                            right--;
                        right--;
                    }
                }

                    // Processing the duplicates of 3 sum
                    while (j < n - 2 && arr[j] == arr[j + 1])
                        j++;
                }

                // Processing the duplicates of 4 sum
                while (i < n - 3 && arr[i] == arr[i + 1])
                    i++;

            }


            return list;
        }

    public static void main(String[] args) {
        int[] nums = {1, 0, -1, 0, -2, 2};
        int target = 0;
        FourSum fourSum = new FourSum();
        List<List<Integer>> res = fourSum.fourSum(nums, target);
        System.out.println(res);
    }
}

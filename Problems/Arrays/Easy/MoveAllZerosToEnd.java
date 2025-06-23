package Problems.Arrays.Easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveAllZerosToEnd {

    // Brute Force Solution: Time Complexity: O(n) Space Complexity: O(x) where x is the number of non-zero elements in the array
    public static int[] moveZeroes(int[] nums) {
        List<Integer> temp = new ArrayList<>(); // to store all the non-zero elements in the array
        for(int i = 0; i < nums.length; i++){
            if(nums[i] != 0) temp.add(nums[i]); // add all the non-zero elements in the array to the temp array
        }
        for(int i = 0; i < temp.size(); i++){
            nums[i] = temp.get(i); // add all the non-zero elements in the array to the temp array
        }
        for(int i = temp.size(); i < nums.length; i++){
            nums[i] = 0; // add all the non-zero elements in the array to the temp array
        }
        return nums;
    }

    // Optimal Solution: Time Complexity: O(n) Space Complexity: O(1)
    // Using two pointers
    public static int[] moveZeroesOptimal(int[] nums) {
      int j = -1;

      // find the index of the first zero element
      for(int i=0; i<nums.length; i++) {
          if(nums[i] == 0) {
              j = i;
              break;
          }
      }

      if(j == -1) return nums;
      // then starting from index next to the first zero elements, swap all the non-zero elements with it, and increment j by 1 for each swap
      for(int i=j+1; i<nums.length; i++) {
          if(nums[i] != 0) {
              int temp = nums[i];
              nums[i] = nums[j];
              nums[j] = temp;
              j++;
          }
      }
      return nums;
      }

    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 3, 12};
        System.out.println("Optimal Solution: " + Arrays.toString(moveZeroesOptimal(nums)));
        System.out.println("Brute Force Solution: " + Arrays.toString(moveZeroes(nums)));
    }
}

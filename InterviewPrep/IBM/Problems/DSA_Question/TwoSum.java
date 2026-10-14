import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> seenNumbers = new HashMap<>();

        for (int currentIndex = 0; currentIndex < nums.length; currentIndex++) {
            int neededPartner = target - nums[currentIndex];

            if (seenNumbers.containsKey(neededPartner)) {
                return new int[]{seenNumbers.get(neededPartner), currentIndex};
            }

            seenNumbers.put(nums[currentIndex], currentIndex);
        }

        throw new IllegalArgumentException("No two-sum solution found");
    }

    public static void main(String[] args) {
        TwoSum solver = new TwoSum();
        System.out.println(Arrays.toString(solver.twoSum(new int[]{2, 7, 11, 15}, 9)));
    }
}

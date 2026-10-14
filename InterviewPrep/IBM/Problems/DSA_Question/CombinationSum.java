import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> allCombinations = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack(candidates, target, 0, new ArrayList<>(), allCombinations);
        return allCombinations;
    }

    private void backtrack(int[] candidates,
                           int remainingTarget,
                           int startIndex,
                           List<Integer> currentPath,
                           List<List<Integer>> allCombinations) {
        if (remainingTarget == 0) {
            allCombinations.add(new ArrayList<>(currentPath));
            return;
        }

        for (int i = startIndex; i < candidates.length; i++) {
            int candidate = candidates[i];

            if (candidate > remainingTarget) {
                break;
            }

            currentPath.add(candidate);
            backtrack(candidates, remainingTarget - candidate, i, currentPath, allCombinations);
            currentPath.remove(currentPath.size() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(new CombinationSum().combinationSum(new int[]{2, 3, 6, 7}, 7));
    }
}

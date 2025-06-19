package Problems.LogicBuilding.Medium;
import java.util.*;


public class PermutationsI {
    // Problem Link: https://leetcode.com/problems/permutations/
    // Time Complexity: O(n!)
    // Space Complexity: O(n)
    // Here instead of maintaining a index we directly add the element to the list and remove it
    // This will avoid duplicates: Can be used in https://leetcode.com/problems/permutations-ii/
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>(); // to store all the permutations
        List<Integer> permutation = new ArrayList<>(); // to store the permutation
        boolean[] freq = new boolean[nums.length];  // to keep track of elements already picked
        generatePermutations(nums,permutation,res,freq);
        return res;
    }

    public void generatePermutations(int[] a, List<Integer> permutation, List<List<Integer>> ans, boolean[] freq){
        //if the permutation list length is equal to orignal array length then we have found the permutation
        if(permutation.size() == a.length){
            ans.add(new ArrayList<>(permutation));
            return;
        }

        //check for every index element after picking it
        for(int i=0;i<a.length;i++){
            if(!freq[i]){
                freq[i] = true;
                permutation.add(a[i]);
                generatePermutations(a,permutation,ans,freq);
                //backtracking after recursive call
                permutation.remove(permutation.size() - 1);
                freq[i] = false;
            }
        }
    }
    // Here we are using index, but this program doesnt work if we get duplicates
    public  List<List<Integer>> permuteWithIndex(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        generatePermutations(0,nums,res);
        return res;
    }

    public void generatePermutations(int ind, int[] a, List<List<Integer>> ans){
        if(ind == a.length){
            List<Integer> ds = new ArrayList<>();
            for(int i=0;i<a.length;i++){
                ds.add(a[i]);
            }
            ans.add(new ArrayList<>(ds));
            return;
        }


        for(int i = ind; i<a.length;i++){
            swap(ind,i,a);
            generatePermutations(ind+1,a,ans);
            //backtrack be reswapping
            swap(ind,i,a);
        }
    }

    public static void swap(int i, int j, int[] a){
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}

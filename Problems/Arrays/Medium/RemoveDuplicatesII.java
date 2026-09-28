package Problems.Arrays.Medium;

public class RemoveDuplicatesII {
    // Problem: https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/description/?envType=problem-list-v2&envId=array
    /*
     * INTUITION:
     *
     * The array is sorted, so duplicates are adjacent.
     *
     * We use two pointers:
     *
     * read  -> scans every element
     * write -> position where the next valid element should go
     *
     * We allow each number to appear at most twice.
     *
     * For the current element nums[read]:
     *
     *     If write < 2:
     *         Always keep it.
     *
     *     Otherwise:
     *         Compare nums[read] with nums[write - 2].
     *
     *         If they are equal, we already have two copies
     *         of this number in the result -> skip it.
     *
     *         If they are different, keep it.
     *
     * The result is stored in nums[0 ... write-1].
     */
    public int removeDuplicates(int[] nums) {

        int write = 0;

        for (int read = 0; read < nums.length; read++) {

            // First two elements can always be kept.
            if (write < 2) {
                nums[write] = nums[read];
                write++;
            }

            // For the third element onwards,
            // only keep it if it is different from
            // the element two positions behind.
            else if (nums[read] != nums[write - 2]) {
                nums[write] = nums[read];
                write++;
            }
        }

        return write;
    }
}

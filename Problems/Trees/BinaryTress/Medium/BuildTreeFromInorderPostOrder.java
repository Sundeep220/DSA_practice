package Problems.Trees.BinaryTress.Medium;
import java.util.*;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
public class BuildTreeFromInorderPostOrder {
    // Problem: https://www.codingninjas.com/codestudio/problems/construct-tree-from-inorder-and-postorder-traversal_823071?leftPanelTab=0

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        Map<Integer, Integer> mapInorder = new HashMap<>();
        for(int i = 0; i < inorder.length; i++){
            mapInorder.put(inorder[i], i);
        }

        // Step 2: Call recursive helper with full ranges
        return build(postorder, 0, postorder.length - 1,
                inorder, 0, inorder.length - 1, mapInorder);
    }

    public static TreeNode build(int[] pos, int posStart, int posEnd,
                                 int[] in, int inStart, int inEnd,
                                 Map<Integer, Integer> map) {

        // Base case: if there are no elements to process, return null
        if(posStart > posEnd || inStart > inEnd) return null;

        // Step 1: The last element in postorder is the root of current subtree
        TreeNode root = new TreeNode(pos[posEnd]);

        // Step 2: Find the index of the root in inorder array
        int rootIndex = map.get(root.data);

        // Step 3: Number of nodes in left subtree
        int numsLeft = rootIndex - inStart;

        // Step 4: Recursively build the left subtree
        // Left subtree in postorder: preStart to preStart + numsLeft + 1
        // Left subtree in inorder: inStart to rootIndex-1
        root.left = build(pos, posStart, posStart + numsLeft - 1,
                in, inStart, rootIndex - 1, map);

        // Step 5: Recursively build the right subtree
        // Right subtree in postorder: preStart + numsLeft to preEnd - 1
        // Right subtree in inorder: rootIndex + 1 to inEnd
        root.right = build(pos, posStart + numsLeft, posEnd - 1,
                in, rootIndex + 1, inEnd, map);

        // Step 6: Return the root of this subtree
        return root;
    }


    // Another was of bulding: Buuild right subtree fiirst and then left subtree
    public TreeNode buildTreeII(int[] inorder, int[] postorder) {
        // Step 1: Store inorder element → index mapping for O(1) lookups
        Map<Integer, Integer> mapInorder = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            mapInorder.put(inorder[i], i);
        }

        // Step 2: Recursively build the tree
        return buildII(postorder, 0, postorder.length - 1,
                inorder, 0, inorder.length - 1,
                mapInorder);
    }

    private TreeNode buildII(int[] post, int postStart, int postEnd,
                           int[] in, int inStart, int inEnd,
                           Map<Integer, Integer> map) {

        // Base case: no elements to build
        if (postStart > postEnd || inStart > inEnd) {
            return null;
        }

        // Step 1: Last element in postorder is root
        TreeNode root = new TreeNode(post[postEnd]);

        // Step 2: Find root in inorder to split left and right
        int rootIndex = map.get(root.data);

        // Step 3: Number of nodes in right subtree
        int rightCount = inEnd - rootIndex;

        // Step 4: Build right subtree first (since postorder = left, right, root)
        root.right = buildII(post, postEnd - rightCount, postEnd - 1,
                in, rootIndex + 1, inEnd, map);

        // Step 5: Build left subtree
        root.left = buildII(post, postStart, postEnd - rightCount - 1,
                in, inStart, rootIndex - 1, map);

        // Step 6: Return root node
        return root;
    }
}

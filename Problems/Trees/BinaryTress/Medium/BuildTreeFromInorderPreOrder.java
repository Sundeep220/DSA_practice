package Problems.Trees.BinaryTress.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;
import java.util.HashMap;
import java.util.Map;
public class BuildTreeFromInorderPreOrder {
    // Problem: https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/

    // Main function to build tree from preorder and inorder arrays
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // Step 1: Create a map to store value -> index mappings for inorder
        // This allows O(1) lookup of root position in inorder array
        Map<Integer, Integer> mapInorder = new HashMap<>();
        for(int i = 0; i < inorder.length; i++){
            mapInorder.put(inorder[i], i);
        }

        // Step 2: Call recursive helper with full ranges
        return build(preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length - 1, mapInorder);
    }

    /**
     * Recursive helper to construct binary tree
     *
     * @param pre        Preorder array
     * @param preStart   Start index in preorder array
     * @param preEnd     End index in preorder array
     * @param in         Inorder array
     * @param inStart    Start index in inorder array
     * @param inEnd      End index in inorder array
     * @param map        Map from inorder values to indices
     * @return           Root node of the subtree
     */
    public static TreeNode build(int[] pre, int preStart, int preEnd,
                                 int[] in, int inStart, int inEnd,
                                 Map<Integer, Integer> map) {

        // Base case: if there are no elements to process, return null
        if(preStart > preEnd || inStart > inEnd) return null;

        // Step 1: The first element in preorder is the root of current subtree
        TreeNode root = new TreeNode(pre[preStart]);

        // Step 2: Find the index of the root in inorder array
        int rootIndex = map.get(root.data);

        // Step 3: Number of nodes in left subtree
        int numsLeft = rootIndex - inStart;

        // Step 4: Recursively build the left subtree
        // Left subtree in preorder: preStart+1 to preStart + numsLeft
        // Left subtree in inorder: inStart to rootIndex-1
        root.left = build(pre, preStart + 1, preStart + numsLeft,
                in, inStart, rootIndex - 1, map);

        // Step 5: Recursively build the right subtree
        // Right subtree in preorder: preStart + numsLeft + 1 to preEnd
        // Right subtree in inorder: rootIndex + 1 to inEnd
        root.right = build(pre, preStart + numsLeft + 1, preEnd,
                in, rootIndex + 1, inEnd, map);

        // Step 6: Return the root of this subtree
        return root;
    }
}

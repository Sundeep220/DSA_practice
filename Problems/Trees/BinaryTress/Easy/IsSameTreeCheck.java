package Problems.Trees.BinaryTress.Easy;

import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

import java.util.Objects;

public class IsSameTreeCheck {
    // Problem: https://leetcode.com/problems/same-tree/

    public boolean isSameTree(TreeNode<Integer> p, TreeNode<Integer> q) {
        if(p == null && q == null) return true;
        if(p == null || q == null) return false;

        if(!Objects.equals(p.data, q.data)) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}

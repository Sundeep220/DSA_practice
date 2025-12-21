package Problems.Trees.BinarySearchTrees.Medium;
import Problems.Trees.BinaryTress.Basics.binaryTreeImplementation.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class KthLargestNode {
    // Similar to KthSmallestNode Problem, just reverse the Inorder traversal

    // Naive Solutions:
    // Time: O(n)
    //Space: O(n)
    public int kthLargestNaive(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        inorder(root, list);
        return list.get(list.size() - k);
    }

    private void inorder(TreeNode root, List<Integer> list) {
        if (root == null) return;
        inorder(root.left, list);
        list.add((Integer) root.data);
        inorder(root.right, list);
    }

    // Time: O(n)
    //Space: O(n)
    public int kthLargestNaiveReverseInorder(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        reverseInorder(root, list);
        return list.get(k - 1);
    }

    private void reverseInorder(TreeNode root, List<Integer> list) {
        if (root == null) return;
        reverseInorder(root.right, list);
        list.add((Integer) root.data);
        reverseInorder(root.left, list);
    }

    //  Time: O(k)
    //Space: O(h)
    class State {
        int k;
        int ans = -1;
        State(int k) { this.k = k; }
    }

    public int kthLargestBetterReverseInorder(TreeNode root, int k) {
        State state = new State(k);
        dfs(root, state);
        return state.ans;
    }

    private void dfs(TreeNode node, State state) {
        if (node == null || state.k == 0) return;

        dfs(node.right, state);

        if (--state.k == 0) {
            state.ans = (int) node.data;
            return;
        }

        dfs(node.left, state);
    }

    // Time: O(k)
    //Space: O(h)
    public int kthLargestIterativeReverseInorder(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();

        while (true) {
            while (root != null) {
                stack.push(root);
                root = root.right;
            }

            root = stack.pop();
            if (--k == 0) return (int) root.data;

            root = root.left;
        }
    }

    // Time: O(n log k)
    //Space: O(k)
    public int kthLargestBetterMinHeap(TreeNode root, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        dfs(root, minHeap, k);
        return minHeap.peek();
    }

    private void dfs(TreeNode root, PriorityQueue<Integer> heap, int k) {
        if (root == null) return;

        heap.offer((Integer) root.data);
        if (heap.size() > k) heap.poll();

        dfs(root.left, heap, k);
        dfs(root.right, heap, k);
    }

    // Time: O(n)
    //Space: O(1)
    public int kthLargestOptimal(TreeNode root, int k) {
        int count = 0;

        while (root != null) {

            // Case 1: No right child
            if (root.right == null) {
                count++;
                if (count == k) return (int) root.data;
                root = root.left;
            }
            else {
                // Find inorder successor
                TreeNode succ = root.right;
                while (succ.left != null && succ.left != root) {
                    succ = succ.left;
                }

                // Create thread
                if (succ.left == null) {
                    succ.left = root;
                    root = root.right;
                }
                // Remove thread
                else {
                    succ.left = null;
                    count++;
                    if (count == k) return (int) root.data;
                    root = root.left;
                }
            }
        }
        return -1;
    }

}

package Problems.Trees.BinarySearchTrees.Medium;

import java.util.LinkedList;
import java.util.Queue;



public class PopulatingNextRightPointer {
    // Problem: https://leetcode.com/problems/populating-next-right-pointers-in-each-node/

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };

    // Using Level Order Traversal and a queue
    // Time: O(n)
    // Space: O(n)
    public Node connect(Node root) {
        if (root == null) return null;

        Queue<Node> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();   // nodes in current level

            for (int i = 0; i < size; i++) {
                Node curr = q.poll();

                // connect only within the same level
                curr.next = (i == size - 1) ? null : q.peek();

                if (curr.left != null) q.offer(curr.left);
                if (curr.right != null) q.offer(curr.right);
            }
        }
        return root;
    }

    // Without Using Extra Space
    // Time: O(n)
    // Space: O(1)
    public Node connect2(Node root) {
        if (root == null) return null;

        Node level = root;
        while (level.left != null) {
            Node curr = level;

            while (curr != null) {
                curr.left.next = curr.right;

                if (curr.next != null)
                    curr.right.next = curr.next.left;

                curr = curr.next;
            }
            level = level.left;
        }
        return root;
    }
}

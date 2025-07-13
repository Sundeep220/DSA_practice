package Problems.LinkedList.Medium;


import Problems.LinkedList.Basics.SLL.Node;

public class ReverseSLLII {
    // Problem: https://leetcode.com/problems/reverse-linked-list-ii/

    public static Node reverseII(Node head, Node tail) {
        Node prev = null;
        Node curr = head;
        while (curr != tail) {
            Node nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        return prev; // new head of the reversed segment
    }

    public static Node reverse(Node head, int left, int right) {
        if (head == null || left == right) return head;

        Node dummy = new Node(0); // a dummy node before head
        dummy.next = head;
        Node prev = dummy;

        // Step 1: move prev to the node before the `left`th node
        for (int i = 1; i < left; i++) {
            prev = prev.next;
        }

        // Step 2: reverse sublist from left to right
        Node start = prev.next;          // `left`th node
        Node end = start;
        for (int i = left; i < right; i++) {
            end = end.next;
        }

        Node after = end.next;           // node after `right`
        Node newHead = reverseII(start, after); // reverse [start, end] end exclusive, i.e., does not include end for eg: [1,2,3,4,5] -> [4,3,2,1,5]

        // Step 3: connect reversed sublist back
        prev.next = newHead;
        start.next = after;

        return dummy.next;
    }

}

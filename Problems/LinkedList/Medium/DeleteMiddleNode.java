package Problems.LinkedList.Medium;

public class DeleteMiddleNode {
    // Problem: https://leetcode.com/problems/delete-the-middle-node-of-a-linked-list/

    public ListNode deleteMiddle(ListNode head) {
        if (head == null || head.next == null) return null;

        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // Now `slow` is middle, and `prev` is node before it
        prev.next = slow.next;

        return head;
    }
}

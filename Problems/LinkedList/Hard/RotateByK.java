package Problems.LinkedList.Hard;

import Problems.LinkedList.Medium.ListNode;

public class RotateByK {
    // Problem: https://leetcode.com/problems/rotate-list/

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;

        // Step 1: Find the length and tail
        int len = 1; // start from 1 because we're already at head
        ListNode tail = head;
        while (tail.next != null) {
            len++;
            tail = tail.next;
        }

        // Step 2: Normalize k
        k = k % len;
        if (k == 0) return head;

        // Step 3: Find the new tail: (len - k - 1)th node
        ListNode newTail = head;
        for (int i = 1; i < len - k; i++) {
            newTail = newTail.next;
        }

        // Step 4: Reassign head and break the list
        ListNode newHead = newTail.next;
        newTail.next = null;
        tail.next = head;  // tail which is already at last node from step 1

        return newHead;
    }
}

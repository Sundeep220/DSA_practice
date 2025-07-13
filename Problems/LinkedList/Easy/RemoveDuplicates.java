package Problems.LinkedList.Easy;

import Problems.LinkedList.Medium.ListNode;

public class RemoveDuplicates {
    // PRoblem: https://leetcode.com/problems/remove-duplicates-from-sorted-list/

    public ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;

        while (current != null && current.next != null) {
            if (current.val == current.next.val) {
                current.next = current.next.next; // skip duplicate
            } else {
                current = current.next;
            }
        }

        return head;
    }

}

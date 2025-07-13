package Problems.LinkedList.Medium;

public class StartOfCycle {
    // Problem: https://leetcode.com/problems/linked-list-cycle-ii/
    // Time Complexity: O(n) time | O(1) space
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head, entry = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                // Start traversing through entry point until slow == entry
                while (slow != entry) {
                    slow = slow.next;
                    entry = entry.next;
                }
                return entry;
            }
        }
        return null;
    }
}

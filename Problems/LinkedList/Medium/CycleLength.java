package Problems.LinkedList.Medium;

public class CycleLength {
    // Problem: Find the length of the cycle in a linked list

    // Time Complexity: O(n) time | O(1) space
    public int getCycleLength(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                // Cycle detected — count its length
                int length = 1;
                ListNode temp = slow.next;
                while (temp != slow) {
                    length++;
                    temp = temp.next;
                }
                return length;
            }
        }
        return 0;  // No cycle
    }
}

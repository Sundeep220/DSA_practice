package Problems.LinkedList.Medium;

public class RotateListByOne {

    public static ListNode rotateByOne(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode prev = null;
        ListNode curr = head;

        // Traverse to the last node
        while (curr.next != null) {
            prev = curr;
            curr = curr.next;
        }

        // Now, curr is last node, prev is second-last
        prev.next = null;    // remove last node
        curr.next = head;    // point last node to head
        head = curr;         // update head to last node

        return head;
    }
}

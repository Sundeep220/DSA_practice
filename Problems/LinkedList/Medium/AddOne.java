package Problems.LinkedList.Medium;

public class AddOne {
    //Problem: https://leetcode.com/problems/plus-one-linked-list/
    // Time Complexity: O(n) time | O(1) space
    public ListNode plusOne(ListNode head) {
        // Step 1: Reverse the list
        head = reverse(head);

        // Step 2: Add 1 to the reversed list
        ListNode curr = head;
        int carry = 1;

        while (curr != null) {
            int sum = curr.val + carry;
            curr.val = sum % 10;
            carry = sum / 10;

            // If no carry, we're done early
            if (carry == 0) break;

            // If next is null and we still have a carry
            if (curr.next == null && carry > 0) {
                curr.next = new ListNode(carry);
                carry = 0;
                break;
            }

            curr = curr.next;
        }

        // Step 3: Reverse again to restore original order
        return reverse(head);
    }

    private ListNode reverse(ListNode head) {
        ListNode prev = null, curr = head;
        while (curr != null) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        return prev;
    }
}

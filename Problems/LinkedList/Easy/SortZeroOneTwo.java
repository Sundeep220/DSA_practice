package Problems.LinkedList.Easy;

import Problems.LinkedList.Medium.ListNode;

public class SortZeroOneTwo {
    // Problem: https://www.naukri.com/code360/problems/sort-linked-list-of-0s-1s-2s_1071937

    // Brute Force: Counting and then inserting into the list
    // Time Complexity: O(n) time | O(1) space
    public ListNode sortList012(ListNode head) {
        int zeroCount = 0, oneCount = 0, twoCount = 0;

        // First pass: count 0s, 1s, and 2s
        ListNode temp = head;
        while (temp != null) {
            if (temp.val == 0) zeroCount++;
            else if (temp.val == 1) oneCount++;
            else twoCount++;
            temp = temp.next;
        }

        // Second pass: overwrite values
        temp = head;
        while (temp != null) {
            if (zeroCount-- > 0) temp.val = 0;
            else if (oneCount-- > 0) temp.val = 1;
            else temp.val = 2;
            temp = temp.next;
        }

        return head;
    }

    // Another Solution: Link Based
    public ListNode sortList012Linking(ListNode head) {
        if (head == null || head.next == null) return head;

        // Create dummy heads and tails for 0s, 1s, and 2s
        ListNode zeroD = new ListNode(0), zero = zeroD;
        ListNode oneD = new ListNode(0), one = oneD;
        ListNode twoD = new ListNode(0), two = twoD;

        // Traverse original list and separate into 3 lists
        ListNode curr = head;
        while (curr != null) {
            if (curr.val == 0) {
                zero.next = curr;
                zero = zero.next;
            } else if (curr.val == 1) {
                one.next = curr;
                one = one.next;
            } else {
                two.next = curr;
                two = two.next;
            }
            curr = curr.next;
        }

        // Connect three lists: 0s → 1s → 2s
        zero.next = (oneD.next != null) ? oneD.next : twoD.next;
        one.next = twoD.next;
        two.next = null;  // Important to terminate the list

        return zeroD.next;
    }
}

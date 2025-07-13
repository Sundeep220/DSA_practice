package Problems.LinkedList.Medium;

public class AddTwoNumbers {
    // Problem: https://leetcode.com/problems/add-two-numbers/

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0), curr = dummyHead;
        int carry = 0;

        while (l1 != null || l2 != null) {
            int val1 = l1 != null ? l1.val : 0;
            int val2 = l2 != null ? l2.val : 0;
            int sum = val1 + val2 + carry;
            carry = sum / 10;
            int finalVal = sum % 10;
            curr.next = new ListNode(finalVal);
            l1 = l1 != null ? l1.next : null;
            l2 = l2 != null ? l2.next : null;
            curr = curr.next;
        }

        if(carry > 0){
            curr.next = new ListNode(carry);
            curr = curr.next;
        }

        return dummyHead.next;

    }
}

package Problems.LinkedList.Hard;

import Problems.LinkedList.Medium.ListNode;

public class ReverseNodeInKGroups {
    // Problem Link: https://leetcode.com/problems/reverse-nodes-in-k-group/

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode temp = head;
        ListNode prevTail = null, nextNode = null;

        while (temp != null) {
            ListNode KNode = findKthNode(temp, k);
            if (KNode == null) {
                if (prevTail != null) prevTail.next = temp; // connect remaining nodes as is
                break;
            }

            nextNode = KNode.next;
            KNode.next = null; // temporarily cut to reverse
            ListNode newHead = reverse(temp);

            if (temp == head) head = KNode; // first group sets new head
            else prevTail.next = KNode;     // connect previous group to current

            prevTail = temp;    // current group head becomes tail after reversal
            temp = nextNode;    // move to next group
        }

        return head;
    }

    public ListNode findKthNode(ListNode head, int k){
        ListNode temp = head;
        while (--k > 0 && temp != null) {
            temp = temp.next;
        }
        return temp;
    }

    public ListNode reverse(ListNode head){
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }

        return prev;
    }

}

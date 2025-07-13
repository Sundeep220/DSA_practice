package Problems.LinkedList.Easy;

import Problems.LinkedList.Medium.ListNode;

public class MiddleNode {
    //Problem: https://leetcode.com/problems/middle-of-the-linked-list/

    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        MiddleNode obj = new MiddleNode();
        System.out.println(obj.middleNode(head));
    }


}

package Problems.LinkedList.Medium;


public class DeleteNode {
    // Problem Link: https://leetcode.com/problems/delete-node-in-a-linked-list/

    public void deleteNode(ListNode node) {
        ListNode nextNode = node.next;
        node.val = nextNode.val;
        node.next = nextNode.next;
        nextNode.next = null;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(4);
        head.next = new ListNode(5);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(9);
        DeleteNode obj = new DeleteNode();
        obj.deleteNode(head.next);
    }
}

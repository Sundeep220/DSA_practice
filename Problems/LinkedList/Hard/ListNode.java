package Problems.LinkedList.Hard;

/**
 * This node is same SLL node with random pointer
 */
public class ListNode {
    int val;
    ListNode next;
    ListNode random;

    public ListNode(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}

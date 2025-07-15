package Problems.LinkedList.Hard;

/**
 * This node is same SLL node with random pointer
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode random;

    public ListNode(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}

package Problems.LinkedList.Medium;

public class RemoveDuplicatesII {
    // Problem: https://www.naukri.com/code360/problems/remove-duplicates-from-a-sorted-doubly-linked-list_2420283

    public static Node removeDuplicates(Node head) {
        Node curr = head;
        while (curr != null && curr.next != null) {
            if (curr.data == curr.next.data) {
                // Skip all duplicates
                Node nextNode = curr.next;
                while (nextNode != null && nextNode.data == curr.data) {
                    nextNode = nextNode.next;
                }
                curr.next = nextNode;
                if (nextNode != null) nextNode.prev = curr;
            }
            curr = curr.next;
        }
        return head;
    }
}

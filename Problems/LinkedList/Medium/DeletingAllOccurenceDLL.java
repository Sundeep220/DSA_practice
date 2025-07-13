package Problems.LinkedList.Medium;

public class DeletingAllOccurenceDLL {
    // https://www.geeksforgeeks.org/delete-all-occurrences-of-a-given-key-in-a-doubly-linked-list/
    // https://www.naukri.com/code360/problems/delete-all-occurrences-of-a-given-key-in-a-doubly-linked-list_8160461?leftPanelTabValue=PROBLEM

    public static Node deleteAllOccurences(Node head, int key) {
        if (head == null) return null;
        Node curr = head;
        while (curr != null) {
            if (curr.data == key) {
                // If node to be deleted is head node, update head pointer
                if (curr == head) head = curr.next;
                // If node to be deleted is not head node, update prev pointer
                Node nextNode = curr.next;
                Node prevNode = curr.prev;

                if(nextNode != null) nextNode.prev = prevNode;
                if(prevNode != null) prevNode.next = nextNode;
                curr = nextNode;
            } else {
                curr = curr.next;
            }
        }
        return head;
    }
}

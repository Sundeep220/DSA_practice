package Problems.LinkedList.Medium;

public class RemoveNthNodefromEnd {
    // Problem: https://leetcode.com/problems/remove-nth-node-from-end-of-list/

    //Brute Force: Finding size of list and then finding the node to be deleted
    //Time Complexity: O(n) time | O(1) space
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int size = 0;
        ListNode temp = head;
        while(temp != null) {
            size++;
            temp = temp.next;
        }

        if(size == n) {
            return head.next;
        }

        temp = head;
        int index = size - n;
        while(index > 1){
            temp = temp.next;
            index--;
        }

        temp.next = temp.next.next;
        return head;
    }

    // Another way for writing the above code
    public ListNode removeNthFromEndII(ListNode head, int n) {
        int size = 0;
        ListNode temp = head;

        // Step 1: Get the size
        while (temp != null) {
            size++;
            temp = temp.next;
        }

        // Step 2: Handle edge case - remove head
        if (n == size) {
            return head.next;
        }

        // Step 3: Go to the (size - n - 1)th node
        temp = head;
        for (int i = 1; i < size - n; i++) {
            temp = temp.next;
        }

        // Step 4: Remove the nth node
        if (temp.next != null) {
            temp.next = temp.next.next;
        }

        return head;
    }

    // More Optimal Solution: Using fast and slow pointers
    // Time Complexity: O(n) time | O(1) space
    public ListNode removeNthFromEndOptimal(ListNode head, int n) {
        ListNode fast = head, slow = head;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        if (fast == null) {
            return head.next;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return head;
    }
}

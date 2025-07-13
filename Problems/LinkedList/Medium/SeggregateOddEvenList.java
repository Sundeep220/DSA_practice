package Problems.LinkedList.Medium;

import java.util.ArrayList;
import java.util.List;

public class SeggregateOddEvenList {
    // Problem: https://leetcode.com/problems/odd-even-linked-list/

    //Brute Force: Storing list in array and then seggregating it
    //Time Complexity: O(n) time | O(n) space
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) return head;

        List<ListNode> oddList = new ArrayList<>();
        List<ListNode> evenList = new ArrayList<>();

        ListNode curr = head;
        int index = 1;

        while (curr != null) {
            if (index % 2 == 1) {
                oddList.add(curr);
            } else {
                evenList.add(curr);
            }
            curr = curr.next;
            index++;
        }

        // Link odd nodes
        for (int i = 0; i < oddList.size() - 1; i++) {
            oddList.get(i).next = oddList.get(i + 1);
        }

        // Link even nodes
        for (int i = 0; i < evenList.size() - 1; i++) {
            evenList.get(i).next = evenList.get(i + 1);
        }

        // Connect odd list to even list
        if (!oddList.isEmpty()) {
            oddList.get(oddList.size() - 1).next = evenList.isEmpty() ? null : evenList.get(0);
        }

        // Set last node's next to null
        if (!evenList.isEmpty()) {
            evenList.get(evenList.size() - 1).next = null;
        }

        return oddList.get(0);  // head of the new list
    }


    // Optimal Solution: Swapping prev and next pointers
    // Time Complexity: O(n) time | O(1) space
    public ListNode oddEvenListOptimal(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode odd = head, even = head.next, evenHead = even;

        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
//            // another way of writing the above lines
//            odd.next = odd.next.next;
//            even.next = even.next.next;
//            odd = odd.next;
//            even = even.next;
        }

        odd.next = evenHead;
        return head;
    }
}

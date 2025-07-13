package Problems.LinkedList.Medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortList {

    // Problem: https://leetcode.com/problems/sort-list/

    //Brute Force: Using Arrays and then sorting it and then inserting it into the list
    // Time Complexity: O(nlogn) time | O(n) space
    public ListNode sortListBrute(ListNode head) {
        if (head == null || head.next == null) return head;

        // Step 1: Convert linked list to array
        List<Integer> values = new ArrayList<>();
        ListNode temp = head;
        while (temp != null) {
            values.add(temp.val);
            temp = temp.next;
        }

        // Step 2: Sort the array
        Collections.sort(values);

        // Step 3: Write sorted values back into list
        temp = head;
        for (int val : values) {
            temp.val = val;
            temp = temp.next;
        }

        return head;
    }

    // Optimal Solution: Using Merge Sort
    // Time Complexity: O(nlogn) time | O(1) space
    public ListNode sortList(ListNode head) {
        // Base case
        if (head == null || head.next == null) return head;

        // Step 1: Split list into halves
        ListNode beforeMid = getBeforeMid(head);
        ListNode left = head;
        ListNode right = beforeMid.next;
        beforeMid.next = null;  // Break the list

        // Step 2: Sort both halves recursively
        left = sortList(left);
        right = sortList(right);

        // Step 3: Merge sorted halves
        return merge(left, right);
    }

    // Function to find the middle node
    private ListNode getBeforeMid(ListNode head) {
        ListNode slow = head, fast = head, prev = null;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        return prev == null ? head : prev;
    }

    // Merge two sorted lists
    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }

        tail.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }
}

package Problems.LinkedList.Medium;

import java.util.ArrayList;
import java.util.List;

public class PartitionList {
    // Problem: https://leetcode.com/problems/partition-list/description/?envType=problem-list-v2&envId=linked-list

    // Brute Force: Add values to array list and then recreate list
    // Time Complexity: O(n) Space Complexity: O(n)
    public ListNode partition(ListNode head, int x) {
        List<Integer> smaller = new ArrayList<>();
        List<Integer> greater = new ArrayList<>();

        ListNode curr = head;

        while (curr != null) {

            if (curr.val < x) {
                smaller.add(curr.val);
            } else {
                greater.add(curr.val);
            }

            curr = curr.next;
        }

        curr = head;

        for (int num : smaller) {
            curr.val = num;
            curr = curr.next;
        }

        for (int num : greater) {
            curr.val = num;
            curr = curr.next;
        }

        return head;
    }

    // Better Approach: Create Two New Lists: smaller list and large list
    // Time Complexity: O(n) Space Complexity: O(n)
    public ListNode partitionBetter(ListNode head, int x) {
        ListNode smallDummy = new ListNode(-1);
        ListNode largeDummy = new ListNode(-1);

        ListNode smallTail = smallDummy;
        ListNode largeTail = largeDummy;

        ListNode curr = head;

        while (curr != null) {

            if (curr.val < x) {
                smallTail.next = new ListNode(curr.val);
                smallTail = smallTail.next;
            } else {
                largeTail.next = new ListNode(curr.val);
                largeTail = largeTail.next;
            }

            curr = curr.next;
        }

        smallTail.next = largeDummy.next;

        return smallDummy.next;
    }

    // Optimal Approach: Reuse existing list to rearrange them
    // Time Complexity: O(n) Space Complexity: O(1)
    public ListNode partitionOptimal(ListNode head, int x) {
        ListNode lessDummy = new ListNode(-1);
        ListNode greaterDummy = new ListNode(-1);

        ListNode less = lessDummy;
        ListNode greater = greaterDummy;

        ListNode curr = head;

        while (curr != null) {

            if (curr.val < x) {
                less.next = curr;
                less = less.next;
            } else {
                greater.next = curr;
                greater = greater.next;
            }

            curr = curr.next;
        }
        // as the last node of this might still be pointing to lesser node
        // for eg: 1 -> 4 -> 3 -> 2
        // while rearrange: greater:
        // 4 -> 3 -> 2
        // Node 3 originally points to 2.
        // After partitioning:
        // less:
        // 1 -> 2
        // greater:
        // 4 -> 3 -> 2
        greater.next = null;

        less.next = greaterDummy.next;

        return lessDummy.next;
    }
}

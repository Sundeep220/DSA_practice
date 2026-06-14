package Problems.LinkedList.Medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InsertionSortLst {
    // Problem: https://leetcode.com/problems/insertion-sort-list/description/?envType=problem-list-v2&envId=linked-list

    // Brute Force: copy elements in array and then sort it
    // Time Complexity: O(nlogn) Space Complexity: O(n)
    public ListNode insertionSortList(ListNode head) {
        List<Integer> list = new ArrayList<>();

        ListNode curr = head;
        while(curr != null) {
            list.add(curr.val);
            curr = curr.next;
        }
        curr = head;
        Collections.sort(list);
        for(int num: list){
            curr.val = num;
            // curr = curr.next; // This line causes NullPointerException because curr becomes null after the loop
            curr = curr.next;
        }
        return head;
    }


    // Required Solution: Using Insertion sort itself
    // Time Complexity: O(n^2) Space Complexity: O(1)
    public ListNode insertionSortListOptimal(ListNode head) {
        ListNode curr = head;
        ListNode dummy = new ListNode(Integer.MIN_VALUE);

        // Iterate through the original list
        while(curr != null){
            ListNode nextNode = curr.next;
            ListNode prev = dummy;

            while(prev.next != null && prev.next.val < curr.val){
                prev = prev.next;
            }
            curr.next = prev.next;
            prev.next = curr;

            curr = nextNode;
        }
        return dummy.next;
    }
}

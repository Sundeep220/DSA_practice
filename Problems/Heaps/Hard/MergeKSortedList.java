package Problems.Heaps.Hard;

import Problems.LinkedList.Hard.ListNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class MergeKSortedList {
    // Problem: https://leetcode.com/problems/merge-k-sorted-lists/

    // Brute Force: O(N + NlogN + N) time | O(N) space
    public static ListNode mergeKListsBrute(ListNode[] lists) {
        List<Integer> values = new ArrayList<>();

        // Step 1: Collect all node values
        for (ListNode head : lists) {
            while (head != null) {
                values.add(head.val);
                head = head.next;
            }
        }

        // Step 2: Sort values
        Collections.sort(values);

        // Step 3: Build new sorted linked list
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int val : values) {
            current.next = new ListNode(val);
            current = current.next;
        }

        return dummy.next;
    }


    // Better Sollution: Using COncept of Merge Two Sorted List Question
    // Merge K Lists using "merge two sorted lists"
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        ListNode merged = lists[0];

        for (int i = 1; i < lists.length; i++) {
            merged = mergeTwoLists(merged, lists[i]);
        }

        return merged;
    }

    // Helper: merge two sorted lists
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
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

        if (l1 != null) tail.next = l1;
        if (l2 != null) tail.next = l2;

        return dummy.next;
    }

    // Most OPtimal Using Min Heap
    // Time: O(NlogK) | Space: O(N)
    // where N is the total number of nodes in all k lists

    public static ListNode mergeKListsOptimal(ListNode[] lists) {
        if(lists == null || lists.length == 0) return null;

        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode head : lists) {
            if(head != null) minHeap.offer(head);
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while(!minHeap.isEmpty()) {
            // Get the current min node out
            ListNode min = minHeap.poll();

            // Add it to the final list
            tail.next = min;
            tail = tail.next;

            // Add its next node back to the heap, to recheck the min node
            if(min.next != null) minHeap.offer(min.next);
        }
        return dummy.next;
    }
}

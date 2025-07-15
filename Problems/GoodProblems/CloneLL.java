package Problems.GoodProblems;

import Problems.LinkedList.Hard.ListNode;

import java.util.HashMap;
import java.util.Map;

public class CloneLL {
    // Problem: https://leetcode.com/problems/copy-list-with-random-pointer/description/

    //Brute Force: Storing the random pointers in a map and then copying the list with the help of map
    public ListNode copyRandomList(ListNode head) {
        Map<ListNode, ListNode> map = new HashMap<>();
        ListNode temp = head;
        while(temp != null) {
            // Create a copy of the current node and insert it into the map
            ListNode copy = new ListNode(temp.val);
            map.put(temp, copy);
            temp = temp.next;
        }
        temp = head;
        while(temp != null) {
            // Get the copy node out of the map and set its next and random pointers
            ListNode copy = map.get(temp);
            copy.next = map.get(temp.next);
            copy.random = map.get(temp.random);
            temp = temp.next;
        }

        // Return the head of the copied linked list
        return map.get(head);
    }

    // Optimal Solution: Without using map to store copied nodes, instead add copied node in between the original node and its next node
    public ListNode copyRandomListOptimal(ListNode head) {
        if(head == null) return null;
        ListNode temp = head;
        while(temp != null) {
            // Create a copy of the current node and insert it between the current node and its next node
            ListNode copy = new ListNode(temp.val);
            ListNode nextElement = temp.next;
            copy.next = nextElement;
            temp.next = copy;
            temp = nextElement;
        }

        temp = head;
        while(temp != null) {
            // Get the copy node and set its random pointer
            ListNode copy = temp.next;
            if(temp.random != null) copy.random = temp.random.next;  // temp.random.next is the copy of temp.random
            else copy.random = null;
            temp = temp.next.next;
        }

        // Remove the copied nodes and return the head of the copied linked list
        ListNode original = head;
        ListNode copy = head.next;
        ListNode newHead = copy;

        while(original != null) {
            original.next = original.next.next;
            if(copy.next != null) {
                copy.next = copy.next.next;
            }
            original = original.next;
            copy = copy.next;
        }
        return newHead;
    }
}

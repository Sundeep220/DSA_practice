package Problems.LinkedList.Easy;

import Problems.LinkedList.Medium.ListNode;

import java.util.HashSet;

public class IntersectionOfList {
    // Problem Link: https://leetcode.com/problems/intersection-of-two-linked-lists/

    // Brute Force: O(n^2) time | O(1) space
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        while(a != null){
            while(b != null){
                if(a == b) return a;
                b = b.next;
            }
            b = headB;
            a = a.next;
        }
        return null;
    }

    // Better Solution: Using HashSet
    // Time Complexity: O(n) time | O(n) space
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        HashSet<ListNode> set = new HashSet<>();
        while(headA != null){
            set.add(headA);
            headA = headA.next;
        }
        while(headB != null){
            if(set.contains(headB)) return headB;
            headB = headB.next;
        }
        return null;
    }

    // Optimal Solution: Using Two Pointers
    // Time Complexity: O(n) time | O(1) space
    public ListNode getIntersectionNode3(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        while(a != b){
            a = (a == null) ? headB : a.next;
            b = (b == null) ? headA : b.next;
        }
        return a;
    }

    // Another Solution: Skipping the first n nodes of the longer list
    // Time Complexity: O(n) time | O(1) space
    public ListNode getIntersectionNode4(ListNode headA, ListNode headB) {
        int lenA = 0, lenB = 0;
        ListNode a = headA, b = headB;
        while(a != null){
            lenA++;
            a = a.next;
        }
        while(b != null){
            lenB++;
            b = b.next;
        }
        a = headA;
        b = headB;
        if(lenA > lenB){
            for(int i = 0; i < lenA - lenB; i++){
                a = a.next;
            }
        } else {
            for(int i = 0; i < lenB - lenA; i++){
                b = b.next;
            }
        }
        while(a != b){
            a = a.next;
            b = b.next;
        }
        return a;
    }
}

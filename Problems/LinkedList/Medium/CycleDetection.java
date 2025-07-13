package Problems.LinkedList.Medium;

import java.util.HashMap;

public class CycleDetection {
    // Problem: https://leetcode.com/problems/linked-list-cycle/

    //Brute Force: Using HashMap
    //Time Complexity: O(n) time | O(n) space
    public boolean hasCycleBrute(Node head) {
        // Initialize a pointer 'temp'
        // at the head of the linked list
        Node temp = head;

        // Create a map to keep track
        // of encountered nodes
        HashMap<Node, Integer> nodeMap = new HashMap<>();

        // Step 2: Traverse the linked list
        while (temp != null) {
            // If the node is already in
            // the map, there is a loop
            if (nodeMap.containsKey(temp)) {
                return true;
            }
            // Store the current node in the map
            nodeMap.put(temp, 1);
            // Move to the next node
            temp = temp.next;
        }

        // Step 3: If the list is successfully
        // traversed without a loop, return false
        return false;
    }

    // Using Floyd's Cycle-Finding Algorithm
    // Time Complexity: O(n) time | O(1) space
    public boolean hasCycle(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }
}

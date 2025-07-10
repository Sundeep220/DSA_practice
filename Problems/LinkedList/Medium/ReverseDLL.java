package Problems.LinkedList.Medium;

import java.util.Stack;

public class ReverseDLL {



    //Brute Force: USing Stack
    //Time Complexity: O(n) Space Complexity: O(n)
    public Node reverseDLL(Node head){
        Stack<Integer> stack = new Stack<>();
        Node curr = head;
        while(curr != null){
            stack.push(curr.data);
            curr = curr.next;
        }
        curr = head;
        while(curr != null){
            curr.data = stack.pop();
            curr = curr.next;
        }
        return head;
    }

    // Optimal Solution: Swapping prev and next pointers
    // Time Complexity: O(n) Space Complexity: O(1)
    public Node reverseDLLOptimal(Node head){
        Node cur = head;
        Node temp = null;
        while(cur != null){
            temp = cur.prev;
            cur.prev = cur.next;
            cur.next = temp;

            // Move to next, in this case the next node will be the previous node, as we swapped
            cur = cur.prev;
        }

        // After loop, temp will be pointing to the node *before* null (new head)
        if(temp != null){
            head = temp.prev;
        }
        return head;
    }
}

package Problems.LinkedList.Hard;



public class FlattenList {
    // Problem: Given a Linked List of size n, where every node represents a sub-linked-list and contains two pointers:
    //next pointer to the next node
    //bottom pointer to a linked list where this node is head.
    //Each of the sub-linked-list is in sorted order.
    //Flatten the Linked List such that all the nodes appear in a single level while maintaining the sorted order.

    // Solution: First Flatten the list, then sort it
    public static DualLink flatten(DualLink head) {
        // Base case
        if (head == null || head.next == null) return head;

        // Step 1: Flatten the list
        DualLink dummy = new DualLink(-1);
        DualLink tail = dummy;
        while (head != null) {
            DualLink temp = head;
            while (temp != null) {
                tail.next = temp;
                tail = tail.next;

                DualLink nextChild = temp.child;
                temp.child = null;
                temp = nextChild;
            }
            head = head.next;
        }

        // Set tail of dummy to null
        tail.next = null;

        // Step 2: Sort the list
        return sortList(dummy.next);
    }

    public static DualLink sortList(DualLink head) {
        // Base case
        if (head == null || head.next == null) return head;

        // Step 1: Split list into halves
        DualLink beforeMid = getBeforeMid(head);
        DualLink left = head;
        DualLink right = beforeMid.next;
        beforeMid.next = null;  // Break the list

        // Step 2: Sort both halves recursively
        left = sortList(left);
        right = sortList(right);

        // Step 3: Merge sorted halves
        return merge(left, right);
    }

    public static DualLink getBeforeMid(DualLink head) {
        DualLink slow = head;
        DualLink fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static DualLink merge(DualLink left, DualLink right) {
        DualLink dummy = new DualLink(-1);
        DualLink tail = dummy;
        while (left != null && right != null) {
            if (left.val < right.val) {
                tail.next = left;
                left = left.next;
            } else {
                tail.next = right;
                right = right.next;
            }
            tail = tail.next;
            tail.child = null;
        }
        tail.next = left != null ? left : right;
        return dummy.next;
    }

    // Optimal Solution: DOing merging in place
    // Time Complexity: O(nlogn) time | O(1) space
    // Here we are a flatenning list to its next pointers
    public static DualLink flattenOptimal(DualLink head) {
        // Base case
        if (head == null || head.next == null) return head;

        // Step 1: Go deep and flatten the list
        DualLink mergedHead = flatten(head.next);

        // Step 2: Disconnect the list
        head.next = null;

        // Step 3: Merge the list
        return merge(head, mergedHead);
    }

}



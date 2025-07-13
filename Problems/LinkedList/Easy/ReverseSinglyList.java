package Problems.LinkedList.Easy;

import Problems.LinkedList.Basics.SLL.Node;

import java.util.LinkedList;

public class ReverseSinglyList {

    public static Node reverse(Node head){
        Node prev = null;
        Node curr = head;

        while (curr != null) {
            Node nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }

        return prev;
    }

    public static Node reverseII(Node head, Node tail) {
        Node prev = null;
        Node curr = head;
        while (curr != tail) {
            Node nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        return prev; // new head of the reversed segment
    }

    // Recursive solution
    public static Node reverseRecursive(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        // Recursive call on the rest of the list
        Node newHead = reverseRecursive(head.next);

        // Reverse the current node
        Node front = head.next;
        front.next = head;
        head.next = null;

        return newHead;
    }

    public static void main(String[] args) {
       Node head = new Node(10);
       head.next = new Node(20);
       head.next.next = new Node(30);
       head.next.next.next = new Node(40);
       head.next.next.next.next = new Node(50);

       Node reversed = reverse(head);
       for (Node node = reversed; node != null; node = node.next) {
           System.out.print(node.data + "->");
       }
        System.out.print("null");
    }
}

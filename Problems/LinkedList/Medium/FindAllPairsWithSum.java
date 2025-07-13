package Problems.LinkedList.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAllPairsWithSum {
    // Problem: https://www.naukri.com/code360/problems/find-pair-with-a-given-sum-in-a-doubly-linked-list_1164172

    //Brute Force: Using traversal
    // Time Complexity: O(n^2) time | O(1) space
    public static List<List<Integer>> findPairsWithSum(Node head, int sum) {
        Node temp1 = head;
        List<List<Integer>> res = new ArrayList<>();
        while (temp1 != null) {
            Node temp2 = temp1.next;
            while(temp2 != null && temp1.data + temp2.data <= sum){
                if(temp1.data + temp2.data == sum){
                    List<Integer> pair = new ArrayList<>();
                    pair.add(temp1.data);
                    pair.add(temp2.data);
                    res.add(pair);
                }
                temp2 = temp2.next;
            }
            temp1 = temp1.next;
        }
        return res;
    }

    // Optimal Solution: Using Two Pointers
    // Time Complexity: O(n) time | O(1) space
    public static List<List<Integer>> findPairsWithSumOptimal(Node head, int sum) {
        List<List<Integer>> res = new ArrayList<>();

        // Edge case
        if (head == null || head.next == null) return res;

        Node left = head;
        Node right = head;

        // Move right to the last node
        while (right.next != null) {
            right = right.next;
        }

        // Two pointer approach
        while (left.data < right.data) {
            int pairSum = left.data + right.data;

            if (pairSum == sum) {
                res.add(Arrays.asList(left.data, right.data));
                left = left.next;
                right = right.prev;
            } else if (pairSum < sum) {
                left = left.next;
            } else {
                right = right.prev;
            }
        }

        return res;
    }


    public static void main(String[] args) {
        Node head = new Node(2);
        Node node2 = new Node(4);
        Node node3 = new Node(8);
        Node node4 = new Node(10);

// Link next
        head.next = node2;
        node2.next = node3;
        node3.next = node4;

// Link prev
        node2.prev = head;
        node3.prev = node2;
        node4.prev = node3;

        int sum = 6;
        List<List<Integer>> res = findPairsWithSum(head, sum);
        List<List<Integer>> res2 = findPairsWithSumOptimal(head, sum);
        System.out.println("Brute Force Solution: ");
        System.out.println(res);

        System.out.println("Optimal Solution: ");
        System.out.println(res2);
    }
}

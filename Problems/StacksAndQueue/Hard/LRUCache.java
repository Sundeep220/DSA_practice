package Problems.StacksAndQueue.Hard;

import java.util.HashMap;

public class LRUCache {
    class Node {
        int key, value;
        Node prev, next;

        Node(int k, int v) {
            key = k;
            value = v;
        }
    }

    private int capacity;
    private Node head, tail;  // dummy nodes
    private HashMap<Integer, Node> map;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        moveToHead(node);
        return node.value;
    }


    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            moveToHead(node);
        } else {
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            addNodeFront(newNode);

            if (map.size() > capacity) {
                Node lru = popTail(); // remove LRU
                map.remove(lru.key);
            }
        }
    }

    // Add node right after head
    private void addNodeFront(Node node) {
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    // Remove a node from the linked list
    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;

        prev.next = next;
        next.prev = prev;
    }

    // Move node to the head (MRU)
    private void moveToHead(Node node) {
        removeNode(node);
        addNodeFront(node);
    }

    // Pop the tail (LRU)
    private Node popTail() {
        Node res = tail.prev;
        removeNode(res);
        return res;
    }
}

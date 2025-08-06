package Problems.StacksAndQueue.Hard;

import java.util.*;

class LFUCache {
    static class Node {
        int key, value, freq;
        Node prev, next;

        Node(int k, int v) {
            key = k;
            value = v;
            freq = 1;
        }
    }

    static class DoublyLinkedList {
        Node head, tail;
        int size;

        DoublyLinkedList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        void addNodeFront(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }

        void removeNode(Node node) {
            Node prev = node.prev;
            Node next = node.next;

            prev.next = next;
            next.prev = prev;
            size--;
        }

        Node removeLast() {
            if (size > 0) {
                Node last = tail.prev;
                removeNode(last);
                return last;
            }
            return null;
        }
    }

    private final int capacity;
    private int minFreq;
    private final Map<Integer, Node> nodeMap;
    private final Map<Integer, DoublyLinkedList> freqMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        this.nodeMap = new HashMap<>();
        this.freqMap = new HashMap<>();
    }

    public int get(int key) {
        if (!nodeMap.containsKey(key)) return -1;

        Node node = nodeMap.get(key);
        updateFreq(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (nodeMap.containsKey(key)) {
            Node node = nodeMap.get(key);
            node.value = value;
            updateFreq(node);
            return;
        }

        // Evict if needed
        if (nodeMap.size() >= capacity) {
            DoublyLinkedList minList = freqMap.get(minFreq);
            Node toRemove = minList.removeLast();
            nodeMap.remove(toRemove.key);
        }

        // Add new node
        Node newNode = new Node(key, value);
        nodeMap.put(key, newNode);
        minFreq = 1; // Reset minFreq to 1 for new node

        freqMap.putIfAbsent(1, new DoublyLinkedList());
        freqMap.get(1).addNodeFront(newNode);
    }

    private void updateFreq(Node node) {
        int freq = node.freq;
        DoublyLinkedList oldList = freqMap.get(freq);
        oldList.removeNode(node);  // remove from old list

        if (freq == minFreq && oldList.size == 0) {
            minFreq++;
        }

        // update freq and add to new list if needed
        node.freq++;
        freqMap.putIfAbsent(node.freq, new DoublyLinkedList());
        freqMap.get(node.freq).addNodeFront(node);
    }
}

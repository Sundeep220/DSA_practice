package Problems.StacksAndQueue.Basics;

public class QueueUsingLinkedList {
    Node start = null;
    Node end = null;
    int size = 0;

    public void enqueue(int data) {
        Node newNode = new Node(data);
        if (start == null) {
            start = newNode;
            end = newNode;
        } else {
            end.next = newNode;
            end = newNode;
        }
        size++;
    }

    public int dequeue() {
        if (start == null) {
            return -1;
        }
        int val = start.data;
        start = start.next;
        size--;
        return val;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int peek() {
        if (start == null) {
            return -1;
        }
        return start.data;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node temp = start;
        while (temp != null) {
            sb.append(temp.data).append(" -> ");
            temp = temp.next;
        }
        sb.append("null");
        return sb.toString();
    }
}

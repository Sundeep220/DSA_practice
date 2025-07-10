package Problems.LinkedList.Basics.SLL;

public class SingleLinkedList {
    Node head;
    Node tail;
    int size;

    public SingleLinkedList(Node head, Node tail, int size) {
        this.head = head;
        this.tail = tail;
        this.size = 0;
    }

    public SingleLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public Node getHead() {
        return this.head;
    }

    public Node getTail() {
        return this.tail;
    }

    public int getSize() {
        return this.size;
    }

    public Node addFirst(int data){
        Node newNode = new Node(data);
        if(this.head == null){
            this.head = newNode;
            this.tail = newNode;
        }else{
            newNode.next = head;
            this.head = newNode;
        }
        size++;
        return this.head;
    }

    public Node addLast(int data){
        Node newNode = new Node(data);
        if(this.head == null){
            this.head = newNode;
        }else{
            this.tail.next = newNode;
        }
        this.tail = newNode;
        size++;
        return this.tail;
    }

    public Node addAtIndex(int index, int data){
        Node newNode = new Node(data);
        if(index == 0){
            return addFirst(data);
        }else if(index == size){
            return addLast(data);
        }else{
            Node temp = head;
            while(temp.next != null && index - 1 > 0){
                temp = temp.next;
                index--;
            }
            newNode.next = temp.next;
            temp.next = newNode;
            size++;
        }
        return newNode;
    }

    public Node removeFirst(){
        if(head == null){
            return null;
        }
        if(head == tail){
            Node removed = head;
            head = null;
            tail = null;
            size--;
            return removed;
        }

        Node temp = head;
        head = head.next;
        size--;
        return temp;
    }

    public Node removeLast() {
        if (head == null) {
            return null; // List is empty
        }

        if (head == tail) {
            Node removed = head;
            head = null;
            tail = null;
            size--;
            return removed;
        }

        Node temp = head;
        while (temp.next != tail) {
            temp = temp.next;
        }

        Node removed = tail;
        temp.next = null;
        tail = temp;
        size--;
        return removed;
    }

    public Node removeAtIndex(int index) {
        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node temp = head;
            while (index - 1 > 0) {
                temp = temp.next;
                index--;
            }
            Node removed = temp.next;
            temp.next = removed.next;

            // Update tail if last element is removed
            if (removed == tail) {
                tail = temp;
            }
            size--;
            return removed;
        }
    }

    public Node find(int data){
        if(head == null){
            return null;
        }
        Node temp = head;
        while(temp != null){
            if(temp.data == data){
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    public boolean contains(int data){
        return find(data) != null;
    }

    public void print(){
        if(head == null){
            return;
        }
        Node temp = head;
        while(temp != null){
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }


    public int size() {
        int count = 0;
        Node temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    @Override
    public String toString() {
        Node temp = head;
        StringBuilder sb = new StringBuilder();
        while (temp != null) {
            sb.append(temp.data).append(" -> ");
            temp = temp.next;
        }
        sb.append("null");
        return sb.toString();
    }


}

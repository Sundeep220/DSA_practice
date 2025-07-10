package Problems.LinkedList.Basics.DLL;

public class DoublyLinkedList {
    Node head;
    Node tail;
    int size;

    public DoublyLinkedList(Node head, Node tail, int size) {
        this.head = head;
        this.tail = tail;
        this.size = size;
    }

    public DoublyLinkedList() {
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

    public void addFirst(int data){
        Node newNode = new Node(data);

        if(this.head == null){
            head = newNode;
            tail = newNode;
        }else{
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(int data){
        Node newNode = new Node(data);

        if(this.head == null){
            head = newNode;
        }else{
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    public void addAtIndex(int index, int data){
        Node newNode = new Node(data);

        if(index == 0){
            addFirst(data);
        }else if(index == size){
            addLast(data);
        }else{
            Node temp = head;
            while(index - 1 > 0){
                temp = temp.next;
                index--;
            }
            newNode.next  = temp.next;
            newNode.prev = temp;
            temp.next.prev = newNode;
            temp.next = newNode;
            size++;
        }
    }

    public void removeFirst(){
        if(head == null){
            return;
        }
            Node removed = head;
            head = head.next;
            head.prev = null;
            size--;
    }

    public void removeLast(){
        if(head == null){
            return;
        }
        Node removed = tail;
        tail = tail.prev;
        tail.next = null;
        size--;
    }

    public void removeAtIndex(int index){
        if(index == 0) {
            removeFirst();
            return;
        } else if(index == size - 1) {
            removeLast();
            return;
        }
        Node temp = head;
        while(index - 1 > 0){
            temp = temp.next;
            index--;
        }
        Node removed = temp.next;
        temp.next = removed.next;
        removed.next.prev = temp;
        size--;
    }

    public int find(int data){
        Node temp = head;
        int index = 0;
        while(temp != null){
            index++;
            if(temp.data == data){
                return index;
            }
            temp = temp.next;
        }
        return -1;
    }

    public boolean contains(int data){
        return find(data) >= 0;
    }

    public void print() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }

        Node current = head;
        System.out.print("null <-> ");
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" <-> ");
            }
            current = current.next;
        }
        System.out.println(" <-> null");
    }

    @Override
    public String toString(){
        Node temp = head;
        StringBuilder sb = new StringBuilder();
        while(temp != null){
            sb.append(temp.data).append(" <-> ");
            temp = temp.next;
        }
        sb.append("null");
        return sb.toString();
    }

}
